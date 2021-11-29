package com.comert.mhl.database.mealcategory.model.entity;

import com.comert.mhl.database.common.model.entity.GenericEntity;

import com.comert.mhl.database.food.model.entity.Food;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.comert.mhl.database.meal.model.entity.Meal;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.QueryHints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MealCategory")
@Cacheable
@NamedQueries({
        @NamedQuery(
                name = "MealCategory.listMealCategories",
                query = "select mg from MealCategory as mg",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        ),
        @NamedQuery(
                name = "MealCategory.listMealCategoriesByIdAndName",
                query = "select new com.comert.mhl.database.common.model.dto.IdAndName(mg.mealCategoryId,mg.mealCategoryName) from MealCategory as mg",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        )
})
public class MealCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealCategoryId;

    @Version
    private int version;

    @NotNull(message = "Field can not be null")
    @Size(min = 2, max = 50, message = "Field size must be between 2 and 50 characters")
    @Column(name = "mealCategoryName",unique = true)
    private String mealCategoryName;

    @JsonManagedReference
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL) // by JTA
    @Fetch(value = FetchMode.JOIN) // avoiding from lazy-initialization exception
    @OneToMany(
            mappedBy = "mealCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Meal> meals = new HashSet<>();

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
        return mealCategoryName;
    }

    public void setMealCategoryName(String mealCategoryName) {
        this.mealCategoryName = mealCategoryName;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public void addMeal(Meal meal) {
        meal.setMealCategory(this);
        meals.add(meal);
    }

    public void removeFood(Meal meal) {
        meals.remove(meal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MealCategory)) return false;

        MealCategory that = (MealCategory) o;

        return new EqualsBuilder()
                .append(getMealCategoryName(), that.getMealCategoryName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getMealCategoryName())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("mealCategoryId", mealCategoryId)
                .append("mealCategoryName", mealCategoryName)
                .toString();
    }

}
