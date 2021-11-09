package com.comert.mhl.database.member.model.entity;

import com.comert.mhl.database.meal.model.entity.Meal;
import jakarta.persistence.*;

import java.util.List;

//@Entity
@Table(name = "User")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FK_User_Member"),referencedColumnName = "memberId")
@NamedQueries({
        @NamedQuery(name = "User.userCount", query = "select count(u) from User u")
})
public class User extends Member{

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserMealRecord> userMealRecords;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "UserMeal",
            joinColumns = @JoinColumn(name = "memberId"),
            foreignKey = @ForeignKey(name = "FK_UserMeal_User"),
            inverseJoinColumns = @JoinColumn(name="mealId"),
            inverseForeignKey = @ForeignKey(name = "FK_UserMeal_Meal"),
            uniqueConstraints = {@UniqueConstraint(name = "UC_UserMeal_mealId", columnNames = "mealId")}
    )
    private List<Meal> meals;

    public User() {}

    public List<UserMealRecord> getUserMealRecords() {
        return this.userMealRecords;
    }

    public void setUserMealRecords(final List<UserMealRecord> userMealRecords) {
        this.userMealRecords = userMealRecords;
    }

    public List<Meal> getMeals() {
        return this.meals;
    }

    public void setMeals(final List<Meal> meals) {
        this.meals = meals;
    }

}
