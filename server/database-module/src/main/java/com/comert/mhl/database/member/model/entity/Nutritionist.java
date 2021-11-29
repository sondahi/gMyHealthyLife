package com.comert.mhl.database.member.model.entity;

import com.comert.mhl.database.meal.model.entity.Meal;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Nutritionist")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FK_AdminNutritionist_Member"), referencedColumnName = "memberId")
@NamedQueries({
        @NamedQuery(name = "AdminNutritionist.adminNutritionistCount", query = "select count(an) from Nutritionist an")
})
public class Nutritionist extends Member{

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinTable(
            name = "AdminNutritionistMeal",
            joinColumns = @JoinColumn(name = "memberId"),
            foreignKey = @ForeignKey(name = "FK_AdminNutritionistMeal_AdminNutritionist"),
            inverseJoinColumns = @JoinColumn(name="mealId"),
            inverseForeignKey = @ForeignKey(name = "FK_AdminNutritionistMeal_Meal"),
            uniqueConstraints = {@UniqueConstraint(name = "UC_AdminNutritionistMeal_mealId", columnNames = "mealId")}
    )
    private List<Meal> meals;

    public Nutritionist () {}

    public List<Meal> getMeals() {
        return this.meals;
    }

    public void setMeals(final List<Meal> meals) {
        this.meals = meals;
    }

}