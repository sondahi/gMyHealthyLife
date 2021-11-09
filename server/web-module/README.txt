* SelectItem'lar SelectItemCahce'de liste içerisinde equal metodu override edilmediği için remove edilemiyor. Başka bir dto ile
değiştirilecek.


# -> JSF ve CDI paterni gibi önce sayfa sonra kontroller gelirse bu kullanılır. Ertelemeli-Defert
$ -> MVC, Spring, JSP? gibi önce controller sonra sayfa gelirse bu kullanılır. Beklemesiz-JSTL
* Messages yada Message yapmazsan yalnışlıklar conversion gibi JSF'in kendi hata yönetim kontrolünde olan
konular konsola basılır. Message koyarsak ise sayfaya yani client'a gider. JSF'te olmayan yani
kendi içindeki otomatik dönüşümlerin dışında hatalarda ise Genel Exception alırız.
* Custom converter ve validator yapacaksak şuna dikkat etmeliyiz. İkisi beraber kullanılacaksa önce converter
sonra ise validator çalışmakta. Bunun sebebi eğer converter yoksa validator sadece String üzerinden işlem yapar halbuki
bizim nesneye ihtiyacımız varsa convertion olmadan validator nesneye ulaşamaz.
* İkisi de benzer işi yapabilir converter'da zorunlu olmasada hatalar fırlatabilir.
* Burada ilişkiyi iyi kurmak lazım. Örneğin converter sicim nesne dönüşümünü validator ise business logic yani
rol yönetimini yapabilir.
* Converter ve Validator generic kullanımla artık direk ilgili nesne türünden metodlar uygulayabilir. Ancak denildiği gibi
validator bu şekilde kullanılacaksa önce converter çalışması gerek. Diğer türlü validator direk String üzerinde işlem yapabilir.
* Her ikiside f ile kullanılıp converter yada validator id ile binding yapılabileceği gibi tag içinde de kullanılabilir.
* İstisnalara FacesMessage eklenirse direk message outputunda görülebilir. Bunun için metodda çok lu gönderim için
sıralı hata textleri oluşturulup FacesMessage içinde summery detail severity ile konulabilir.
* Converter forClass ile otomatik tanımlansada Validator için value tanımlaması yapılmalı ve bunu JSF Page içinde
bind etmeli. Ancak ben anlaşılır olması açısından ikisinide value ile tanımlayıp Page içinde bind edeceğim.
* String Date gibi çok kullanılan sınıflar için hazır converter ve validatorlar var. Bunlar validate convert gibi isimlerler
kullanılıyor ve büyüklük pattern gibi kontroller yapabiliyor. Ancak biz daha gelişmiş ihtiyaçlar duyabilirz.
* Convert işlemleri nesne dönüşümü varsa kesin şart Js bırakamayız çünkü String alıp örneğin date'e çevireceğiz(direk string ise js ile yapabilirz).
Ancak Validation JS ile yapılabilir tabi businnes logic yoksa mesela bizim formata uygun alıp convert etmemiz için. Örneğin 12.12.1983. Ancak bazı validationlar var ki bunu
mutlaka biz yapmalıyız örneğin role control, db email check vs.
* Genel olarak businnes validation gerekli değilse converter ile handling yapacağım.
* Required için kendi mesajını kullanmalıyız. FacesMessage üretmiyor gördüğüm kadarıyla.
* Hazır convertarlar forClass ile işaretlenmişse otomatik dönüştürüyor. Converterları tag içinde buluyor ama validatorlar için
tag içindede olsa f ile de olsa mecbur id tanımlaması yapacağız. Şimdilik.
* Converter String için anlamsız zaten default int to String yapıyor. Bu durumda validator kullanmak gerek. O da hazırza
patern ve örneğin number currency gibi yapılıyor. Default değilse biz.
* Tüm conv ve valid işlemlerini JSF içinde yapacam. Aşama aşam js ye geçircem. Ama JSF ile çalışacağım çünkü
REST üzerinden değil JSF üzerinden communication yapacağım. JSF gömülü JS üzerinden.
* Navigation düşün. Örneğin loginden sonra success olursa Authentication user type a göre nereye gidecek? koşullu explicit yaptık mesela
ama tabi filter durumlarını düşün. MEsela food user 1.html gitti user browserdan user2.html isterse ne olacak?
* actionlistener tüm formu yeni değerleri ile gönderiyor. normal metod çağırmak gibi. valuChange ise sadece ilgili componenti gönderiyor
ama form komple gitmediğinden value bean e set edilmiyor. Sen kendin güncelleyeceksin.
* ui:repeat bileşen koymaz. Sana bırakır. Yani tablo yerine core html tablo yada ul falan koyabilirsin. Repeat sadece
döngüyü yapıyor. Yani data table yerine. varstatus="status" daha sonra status.index dersen indexi gösterir. step ile ikişer ikişer gönderebilirsin
offset dersen nerden başlayacağını gösterir. İhtiyaç olursa araştır bak. belki datatable kullanmadan döngü yaptıraiblirsin
mesela maddeler falan. Render ile if yapısını kullanabilirsin.status içinde baya bişey gömülü. Mesela tekse normalde
%2 eq 1 diybilirdik ama status.odd ile tek status.even ile çift gösteriliyor.
* primefaces ta viewscope ile çalışmak lazım. request eğer düzeltilmediyse sorun çıkartıyor.

* Qualifier tanımlanmamışsa ve aynı interfac'in bir çok impl varsa hepsi Alternativ işaretlenebilir.
* Bu durumda beans.xml'de hangisnin kullanılacağı bildirilir.
* Qualifier tanımlanmışsa zaten ilgili impl kullanılır. Default bir seçenek olarak kalabilir,
çünkü qualifier inject edilirken eklenilmeyebilir, unutulabilir.

* önce interceptorlar çalışır (kendi aralrında sıra ile) sonra decoraterlar

* interceptorlearı gruplayabiliriz yada stereotype ile scopler dahil herşeyi tek kullanıma sokabiliriz.
* şimdilik gerek yok. Tekrar videoya bakarsın

* ayrıca annotationlardan bak ne neymiş internetten araştır örneğin model hazır stero.

* mesela 10 tane interceptor varsa stero yapabilirsin

decarotorda ortak interface tanımlanmalı. ama decore eden clas isterse abstract olup
sadece ilgili metod yada metodları dekore edebilir.
Payment metodu impl edeceğiz.

Genel olarak businesse ortak olur.

Sıralama client çağırır, proxy önce decaratoru çağırır sonra dekaratordan ilgili sınıf çağrılır.

Exstra contex yada metod inject edilmesine gerek yok sadece ortak metod decore edilecek.

interceptor için ortak interface tanımlanmıyor. Ama her iki tarafta annotation uygulamalı.

businnese karışmamalı intrecept edip security log vs yapmalı

Sıralama client proxy çağırır proxy interceptorı çağırır sonra otomatik proced ile instance ı çağırır.

Bu nedenle Innovacitoncontex içeren metod yapılmalı.

çoklu binding için yeni bir binding yapıp diğerleri inherited üstüne ekleriz
böylece sınıfta tek bir combined binding kullanıırz ve hepsi geçerli olur. Örneğin hem güvenik
hem loglama vs.

public class LogInterceptor{

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
                // Log to console before executing method
		System.out.println("Entering method:" + context.getMethod().getName());

                // Execute method
		Object result = context.proceed();

                // Log to console after executing method
		System.out.println("Leaving method: " + context.getMethod().getName() );

		return result;
	}
}

@Stateless
@Interceptors(LogInterceptor.class)
public class SomeStatelessEJB{

	public String foo(String bar) {
            // Do something ...
	}

}

bu daha kolay...

interceptorda iş mantığı yok logg güvenlik gibi genel konular. businsse karışma sadece yol kes kontrol yap
decoraive biraz daha iş mantıığna gir.
inter ile de parametleri al kontrol yap vs olur ama uzun iş. ya intercept edilen birçok metod ve parmetler varsa
tüm metodları kontrol et vs olmaz. Bir çok nesne olabilr.

decerator aynı interfaci implement eder ve ihtiyaç duyulmayan metodlar ii abstract olur
sonucu değiştirebiir ekelme çıkarma yapabilir. yani örneğin bir deco boyadı diğer deco temizledi vs.
aynı super class override ederken super ile önce super metod işlerini hallettirip ıvır zıvr.
bu kesiciler aynı soydan gelmeyen türler için süper. yani biraz businise bölüyor. bence ihtiyaç yoksa gerke yo
business halletsin. ama tabi belli olmaz örneğin database paketinden çıakcaksa en son hepsine db modülden çıktı
diyebiliriz. yapıyı değiştirmeyecek değiştireemz zaten.

decarotor deyince impl olarak ilk bunu bulur. bunun üzerinde delegete ediyoruz ve inject ediyoruz
bu defa diğer impl bulunuyor.

client class lar için yapılabilir. hani hangi paet. mesela pakete db. falan diyebilirz.