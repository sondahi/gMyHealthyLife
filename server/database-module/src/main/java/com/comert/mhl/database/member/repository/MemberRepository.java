package com.comert.mhl.database.member.repository;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.Member;
import jakarta.ejb.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;

@Stateless
@LocalBean
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class MemberRepository {

    private static final String MEMBER_AUTHENTICATION = "select new com.comert.mhl.database.common.model.dto.Authentication(m.memberId, m.memberName, m.membership.memberType)" +
            " from Member m " +
            " where m.membership.memberEmail=:memberEmail and m.membership.memberPassword=:memberPassword";

    private static final String USER_MEAL_RECORD = "delete from UserMealRecord usm where usm.savedTime <= :savedDateTime";

    private static final String IS_EMAIL_EXIST = "select count(m.membership.memberEmail) from Member m where m.membership.memberEmail = :memberEmail";

    @PersistenceContext
    private EntityManager entityManager;

    public MemberRepository() {
    }

    public Member findMemberById(Integer memberId){
        return entityManager.find(Member.class,memberId);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void persistEntity(Member entity) {
        entityManager.persist(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public Member mergeEntity(Member entity) {
        return entityManager.merge(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void refreshEntity(Member entity) {
        entityManager.refresh(entity);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void removeEntity(Member entity) {
        entityManager.remove(entity);
    }

    public Authentication authenticate(String memberEmail, String memberPassword) {
        TypedQuery<Authentication> query = entityManager.createQuery(MEMBER_AUTHENTICATION, Authentication.class);
        query.setParameter("memberEmail", memberEmail);
        query.setParameter("memberPassword", memberPassword);
        return query.getSingleResult();
    }

    public int removeUsersMealRecords(LocalDateTime dateTime) {
        Query query = entityManager.createQuery(USER_MEAL_RECORD);
        query.setParameter("savedDateTime", dateTime);
        return query.executeUpdate();
    }

    public boolean isEmailExist(String memberEmail) {
        Query query = entityManager.createQuery(IS_EMAIL_EXIST);
        query.setParameter("memberEmail", memberEmail);
        long result = (long) query.getSingleResult();
        return result >= 1;
    }

}
