package com.comert.mhl.database.member.service.impl;

import com.comert.mhl.database.common.exception.AuthenticationException;
import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.Member;
import com.comert.mhl.database.member.repository.MemberRepository;
import com.comert.mhl.database.member.service.MemberService;
import jakarta.ejb.*;

import java.time.LocalDateTime;
import java.util.Set;

@Local(MemberService.class)
@Stateless(name = "MemberServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MemberServiceImpl implements MemberService {

    @EJB
    private MemberRepository memberRepository;

    @Override
    public Member findMemberById(Long memberId) {
        return memberRepository.findMemberById(memberId);
    }

    @Override
    public void saveMember(Member member) {
        memberRepository.saveMember(member);
    }

    @Override
    public Member updateMember(Member member) {
        return memberRepository.updateMember(member);
    }

    @Override
    public void deleteMember(Long memberId) {
        memberRepository.deleteMember(memberId);
    }

    @Override
    public Set<Member> listMembers(int firstResult, int maxResult) {
        return memberRepository.listMembers(firstResult, maxResult);
    }

    @Override
    public Authentication authenticate(String memberEmail, String memberPassword) throws AuthenticationException {
        return memberRepository.authenticate(memberEmail, memberPassword);
    }

    @Override
    public int removeUsersMealRecords(LocalDateTime dateTime) {
        return memberRepository.removeUsersMealRecords(dateTime);
    }

    @Override
    public boolean isEmailExist(String memberEmail) {
        return memberRepository.isEmailExist(memberEmail);
    }
}
