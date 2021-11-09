package com.comert.mhl.database.common.model.dto;

import com.comert.mhl.database.member.model.entity.MemberType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Authentication implements Serializable {

    private final Integer memberId;
    private final String memberName;
    private final MemberType memberType;

    public Authentication(Integer memberId, String memberName, MemberType memberType) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Authentication that = (Authentication) o;

        return new EqualsBuilder()
                .append(memberId, that.memberId)
                .append(memberName, that.memberName)
                .append(memberType, that.memberType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(memberId)
                .append(memberName)
                .append(memberType)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("memberId", memberId)
                .append("memberName", memberName)
                .append("memberType", memberType)
                .toString();
    }
}
