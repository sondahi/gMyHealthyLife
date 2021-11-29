package com.comert.mhl.database.member.repository;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.comert.mhl.database.member.model.entity.Member;
import com.comert.mhl.database.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MemberRepository implements MemberService {

    private static final String MEMBER_AUTHENTICATION = "select new com.comert.mhl.database.common.model.dto.Authentication(m.memberId, m.memberName, m.membership.memberType)" +
            " from Member m " +
            " where m.membership.memberEmail=:memberEmail and m.membership.memberPassword=:memberPassword";

    private static final String USER_MEAL_RECORD = "delete from UserMealRecord usm where usm.savedTime <= :savedDateTime";

    private static final String IS_EMAIL_EXIST = "select count(m.membership.memberEmail) from Member m where m.membership.memberEmail = :memberEmail";

    @Resource
    private SessionContext sessionContext;

    @PersistenceContext
    private EntityManager entityManager;

    public MemberRepository() {
    }

    public MemberRepository(SessionContext sessionContext, EntityManager entityManager) {
        this.sessionContext = sessionContext;
        this.entityManager = entityManager;
    }

    @Override
    public Member findMemberById(Long memberId) {
        return entityManager.find(Member.class, memberId);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void saveMember(Member member) {
        entityManager.persist(member);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public Member updateMember(Member member) {
        return entityManager.merge(member);
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void deleteMember(final Long memberId) {
        final Member member = entityManager.getReference(Member.class, memberId);
        entityManager.remove(member);
    }

    @Override
    public Set<Member> listMembers(int firstResult, int maxResult) {
        Collection<Member> members = entityManager
                .createNamedQuery("Member.listMembers", Member.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
        return new HashSet<>(members);
    }

    @Override
    public Authentication authenticate(String memberEmail, String memberPassword) {
        TypedQuery<Authentication> query = entityManager.createQuery(MEMBER_AUTHENTICATION, Authentication.class);
        query.setParameter("memberEmail", memberEmail);
        query.setParameter("memberPassword", memberPassword);
        return query.getSingleResult();
    }

    @Override
    public int removeUsersMealRecords(LocalDateTime dateTime) {
        Query query = entityManager.createQuery(USER_MEAL_RECORD);
        query.setParameter("savedDateTime", dateTime);
        return query.executeUpdate();
    }

    @Override
    public boolean isEmailExist(String memberEmail) {
        Query query = entityManager.createQuery(IS_EMAIL_EXIST);
        query.setParameter("memberEmail", memberEmail);
        long result = (long) query.getSingleResult();
        return result >= 1;
    }

}
