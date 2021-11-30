package com.comert.mhl.web.controller;

import com.comert.mhl.database.common.exception.AuthenticationException;
import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.service.MemberService;
import com.comert.mhl.web.controller.util.FacesUtils;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named(value = "signInController")
@ViewScoped
public class SignInBean implements Serializable {

    private String eMail = "a@a.com";
    private String password = "1";
    private Authentication authentication;

    @EJB
    private MemberService service;

    @Inject
    private FacesUtils facesUtils;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void signIn() {
        try {
            authentication = service.authenticate(eMail, password);
            facesUtils.setSessionAttribute("authentication", authentication);
            try {
                facesUtils.getExternalContext().dispatch("/login");
                facesUtils.getFacesContext().responseComplete();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (AuthenticationException e) {
            //e.printStackTrace();
        }
    }
}
