package com.comert.mhl.database.member.service;

import com.comert.mhl.database.common.exception.AuthenticationException;
import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.Member;

import java.time.LocalDateTime;
import java.util.Set;

public interface MemberService {

    Member findMemberById(Long memberId);

    void saveMember(Member member);

    Member updateMember(Member member);

    void deleteMember(final Long memberId);

    Set<Member> listMembers(int firstResult, int maxResult);

    Authentication authenticate(final String memberEmail, final String memberPassword) throws AuthenticationException;

    int removeUsersMealRecords(final LocalDateTime dateTime);

    boolean isEmailExist(final String memberEmail);

}
