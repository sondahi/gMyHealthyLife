package com.comert.mhl.web.controller;

import com.comert.mhl.web.controller.util.FacesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;

import java.io.Serializable;
import java.util.*;

/*
* Kullanıcı ilk giriş yaptığında, hiçbir cookie yoksa kullanıcının locale bilgilerine göre facesconfig'deki
* * varsa supported locale, yoksa standart locale session için memberLocale'e kaydedilir ve Header'da seçenek olarak
* * hazır tılı gelir. Kullanıcı değiştirmezse herhangi bir cookie eklenmez ve bu işler aynen her oturumda tekrar edilir.
* Ancak kullanıcı değiştirirse cookie olarak önce viewroot'a kaydedilir. Sonrada cookie olarak gönderilir. Viewrootta
* artık her sayfa f:view ile bu sınıftan memberLocale'i çağırmak zorundadır çünkü aksi halde kayboluyor veri. Cookie varsa
* zaten cookie membersessiona kaydedilir.
* view roota kaydetmeye gerek yok sanırım çünkü sessiondan çağırıryoruz. eğer session da tutabilirsek baştan değiştireceğiz herşeyi
*
 */
@Named (value = "localeController")
@SessionScoped
public class LocaleBean implements Serializable {

    private Locale memberLocale;
    private final List<Locale> applicationLocales;

    @Inject
    private FacesUtils facesUtils;

    public LocaleBean ( ) {
        applicationLocales = new ArrayList<> (  );
        applicationLocales.add ( new Locale ( "en", "US" ) );
        applicationLocales.add ( new Locale ( "de", "DE" ) );
        applicationLocales.add ( new Locale ( "tr", "TR" ) );
    }

    @PostConstruct
    private void initMemberLocale(){
        Cookie cookie = facesUtils.getCookie ( "memberLocale" );
        if(cookie != null){
            String[] parsedValue = cookie.getValue ().split ( "_" );
            String language = parsedValue[0];
            String country = parsedValue[1];
            memberLocale = new Locale ( language, country );
        } else
            memberLocale = facesUtils.getLocale ();
    }

    public Locale getMemberLocale ( ) {
        return memberLocale;
    }

    public void setMemberLocale ( Locale memberLocale ) {
        this.memberLocale = memberLocale;
    }

    public List<Locale> getApplicationLocales ( ) {
        return applicationLocales;
    }

    public void changeMemberLocale(){
        facesUtils.changeLocale ( memberLocale );
        String name = "memberLocale";
        String value = memberLocale.toString ();
        Map<String, Object> properties = new HashMap<> ();
        properties.put("maxAge", 525600);
        properties.put ( "path","/" ); // bunu yapmazsan sadece ilgili sayfaya göre cookie gönderir.
        facesUtils.addCookie ( name, value, properties );
    }

}
