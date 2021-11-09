package com.comert.mhl.database.member.service.impl;

import com.comert.mhl.database.member.service.MemberService;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Local(MemberService.class)
@Stateless(name = "MemberServiceEJB")
@TransactionAttribute(TransactionAttributeType.NEVER)
public class MemberServiceImpl {

}
