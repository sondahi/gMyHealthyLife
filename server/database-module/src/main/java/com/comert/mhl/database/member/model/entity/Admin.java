package com.comert.mhl.database.member.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FK_AdminSuperUser_Member"), referencedColumnName = "memberId")
public class Admin extends Member{

    public Admin () {}

}