package com.comert.mhl.database.mealcategory.model.entity;

import com.comert.mhl.database.common.model.entity.GenericEntity;

import com.comert.mhl.database.meal.model.entity.Meal;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        name = "MealCategory",
        uniqueConstraints = {@UniqueConstraint(name = "UC_MealCategory_mealCategoryName",columnNames = "mealCategoryName")}
)
@Cacheable(value = true)
//@AttributeOverride(name="entityId", column=@Column(name="mealCategoryId"))
@NamedQueries({
        @NamedQuery(name = "MealCategory.mealCategoryCount", query = "select count(mg) from MealCategory mg")
})
public class MealCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealCategoryId;

    @Version
    private int version;

    @Column(name = "mealCategoryName", length = 50, nullable = false)
    private String mealCategoryName;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "mealCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Meal> meals;

    public MealCategory() {}

    public Integer getMealCategoryId() {
        return mealCategoryId;
    }

    public void setMealCategoryId(Integer mealCategoryId) {
        this.mealCategoryId = mealCategoryId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMealCategoryName() {
        return this.mealCategoryName;
    }

    public void setMealCategoryName(final String mealCategoryName) {
        this.mealCategoryName = mealCategoryName;
    }

    public List<Meal> getMeals() {
        return this.meals;
    }

    public void setMeals(final List<Meal> meals) {
        this.meals = meals;
    }

    public void addMeal(Meal meal){
        meals.add(meal);
        meal.setMealCategory(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealCategory that = (MealCategory) o;

        return getMealCategoryId().equals(that.getMealCategoryId());
    }

    @Override
    public int hashCode() {
        return getMealCategoryId().hashCode();
    }
}
