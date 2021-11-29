package com.comert.mhl.database.member.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table( name = "Member",
        uniqueConstraints = {@UniqueConstraint(name = "UC_Member_memberEmail", columnNames = "memberEmail")},
        indexes = {@Index(name = "I_MemberAuthentication", columnList = "memberEmail,memberPassword")}
)
@Cacheable(value = false)
@Inheritance(strategy = InheritanceType.JOINED )
public abstract class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Version
    private int version;

    @NotNull
    @Column(name = "memberName", length = 50)
    private String memberName;

    @NotNull
    @Column(name = "memberSurname", length = 50)
    private String memberSurname;

    @NotNull
    @Embedded
    private Membership membership;

    public Member() {}

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMemberName() {
        return this.memberName;
    }

    public void setMemberName(final String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSurname() {
        return this.memberSurname;
    }

    public void setMemberSurname(final String memberSurname) {
        this.memberSurname = memberSurname;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setMembership(final Membership membership) {
        this.membership = membership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return getMemberId().equals(member.getMemberId());
    }

    @Override
    public int hashCode() {
        return getMemberId().hashCode();
    }
}
