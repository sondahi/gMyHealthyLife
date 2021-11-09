package com.comert.mhl.web.controller;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.MemberType;
import com.comert.mhl.database.member.model.entity.Membership;
import com.comert.mhl.database.member.model.entity.User;
import com.comert.mhl.database.member.service.MemberService;
import com.comert.mhl.web.controller.util.FacesUtils;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

@Named(value = "signUpController")
@ViewScoped
public class SignUpBean implements Serializable {

    private String memberEmail;
    private boolean emailValid;
    private String memberEmailConfirm;
    private String memberPassword;
    private String memberPasswordConfirm;
    private String memberName;
    private String memberSurname;
    private LocalDate memberBirthday;

    @EJB
    private MemberService service;

    @Inject
    private FacesUtils facesUtils;

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public String getMemberEmailConfirm() {
        return memberEmailConfirm;
    }

    public void setMemberEmailConfirm(String memberEmailConfirm) {
        this.memberEmailConfirm = memberEmailConfirm;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberPasswordConfirm() {
        return memberPasswordConfirm;
    }

    public void setMemberPasswordConfirm(String memberPasswordConfirm) {
        this.memberPasswordConfirm = memberPasswordConfirm;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSurname() {
        return memberSurname;
    }

    public void setMemberSurname(String memberSurname) {
        this.memberSurname = memberSurname;
    }

    public LocalDate getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(LocalDate memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public void checkMemberEmail() {
        if (service.isEmailExist(memberEmail)) {
            emailValid = false;
            facesUtils.addMessage("signUpForm:memberEmail", FacesMessage.SEVERITY_ERROR, "Email: Validation Error:", "Email is already exist.");
        } else {
            emailValid = true;
        }
    }

    public void register() {
        if (memberEmailConfirm == null || !memberEmailConfirm.equals(memberEmail)) {
            facesUtils.addMessage("signUpForm:memberEmailConfirm", FacesMessage.SEVERITY_ERROR, "Email: Validation Error:", "Email is not same.");
        }
        if (memberPasswordConfirm == null || !memberPasswordConfirm.equals(memberPassword)) {
            facesUtils.addMessage("signUpForm:memberPasswordConfirm", FacesMessage.SEVERITY_ERROR, "Password: Validation Error:", "Password is not same.");
        }
        if (facesUtils.isMessageListEmpty()) {
            User user = new User();
            user.setMemberName(memberName);
            user.setMemberSurname(memberSurname);
            Membership membership = new Membership();
            membership.setMemberEmail(memberEmail);
            membership.setMemberPassword(memberPassword);
            membership.setMemberBirthday(memberBirthday);
            membership.setMemberType(MemberType.USER_TYPE_0);
            membership.setMemberRegisterdate(LocalDate.now());
            user.setMembership(membership);
            service.saveMember(user);
            Authentication authentication = new Authentication(user.getMemberId(), user.getMemberName(), user.getMembership().getMemberType());
            facesUtils.setSessionAttribute("authentication", authentication);
            try {
                facesUtils.getExternalContext().dispatch("/login");
                facesUtils.getFacesContext().responseComplete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
