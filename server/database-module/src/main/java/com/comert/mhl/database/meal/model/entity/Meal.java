package com.comert.mhl.database.meal.model.entity;


import com.comert.mhl.database.common.model.entity.component.Component;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Meal")
@Cacheable(value = true)
//@AttributeOverride(name="entityId", column=@Column(name="mealId"))
@NamedQueries({
        @NamedQuery(name = "Meal.mealCount", query = "select count(m) from Meal m")
})
public class Meal extends Component implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealId;

    @Version
    private int version;

    @Column(name = "mealName", length = 50, nullable = false)
    private String mealName;

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "mealCategoryId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Meal_MealCategory")
    )


    private MealCategory mealCategory;

    public Meal() {
    }

    public String getMealName() {
        return this.mealName;
    }

    public void setMealName(final String mealName) {
        this.mealName = mealName;
    }

    public MealCategory getMealCategory() {
        return this.mealCategory;
    }

    public void setMealCategory(final MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        return getMealId().equals(meal.getMealId());
    }

    @Override
    public int hashCode() {
        return getMealId().hashCode();
    }

}
