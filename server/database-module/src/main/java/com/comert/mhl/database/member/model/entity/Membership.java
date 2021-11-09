package com.comert.mhl.database.member.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class Membership implements Serializable {

    @NotNull
    @Column(name = "memberEmail", length = 50)
    private String memberEmail;

    @NotNull
    @Column(name = "memberPassword", length = 50)
    private String memberPassword;

    @NotNull
    @Column(name = "memberBirthday", length = 8, columnDefinition = "DATE")
    private LocalDate memberBirthday;

    @NotNull
    @Enumerated
    @Column(name = "memberType")
    private MemberType memberType;

    @NotNull
    @Column(name = "memberRegisterdate", length = 8, updatable = false, columnDefinition = "DATE")
    private LocalDate memberRegisterdate;

    @Transient
    private int age;

    public Membership() {}

    public String getMemberEmail() {
        return this.memberEmail;
    }

    public void setMemberEmail(final String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPassword() {
        return this.memberPassword;
    }

    public void setMemberPassword(final String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public LocalDate getMemberBirthday() {
        return memberBirthday;
    }

    public void setMemberBirthday(LocalDate memberBirthday) {
        this.memberBirthday = memberBirthday;
    }

    public MemberType getMemberType() {
        return this.memberType;
    }

    public void setMemberType(final MemberType memberType) {
        this.memberType = memberType;
    }

    public LocalDate getMemberRegisterdate() {
        return this.memberRegisterdate;
    }

    public void setMemberRegisterdate(final LocalDate memberRegisterdate) {
        this.memberRegisterdate = memberRegisterdate;
    }

    public int getAge() {
        int now = LocalDate.now().getYear();
        int birth = memberBirthday.getYear();
        return now-birth;
    }

}
