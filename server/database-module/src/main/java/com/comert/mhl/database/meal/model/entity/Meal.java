package com.comert.mhl.database.meal.model.entity;


import com.comert.mhl.database.common.model.entity.component.Component;
import com.comert.mhl.database.mealcategory.model.entity.MealCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.QueryHints;

import java.io.Serializable;

@Entity
@Table(name = "Meal")
@Cacheable(value = true)
@NamedQueries({
        @NamedQuery(
                name = "Meal.listMeals",
                query = "select m from Meal as m",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        ),
        @NamedQuery(
                name = "Meal.listMealsByIdAndName",
                query = "select new com.comert.mhl.database.common.model.dto.IdAndName(m.mealId,m.mealName) from Meal as m",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        )
})

public class Meal extends Component implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealId;

    @Version
    private int version;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "mealName")
    private String mealName;

    private String logoPath;

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // cache'de hit sayısını bir azaltıyoruz.
    @JoinColumn(
            name = "mealCategoryId",
            foreignKey = @ForeignKey(name = "FK_Meal_MealCategory")
    )
    private MealCategory mealCategory;

    public Meal() {
    }

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

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public MealCategory getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Meal)) return false;

        Meal meal = (Meal) o;

        return new EqualsBuilder().append(getMealId(), meal.getMealId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getMealId()).toHashCode();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", mealCategoryId=" + mealCategory.getMealCategoryId() +
                '}';
    }
}
