package com.comert.mhl.database.foodcategory.model.entity;

import com.comert.mhl.database.food.model.entity.Food;
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
import java.util.Set;

@Entity
@Table(name = "FoodCategory")
@Cacheable
@NamedQueries({
        @NamedQuery(
                name = "FoodCategory.listFoodCategories",
                query = "select fg from FoodCategory fg",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        ),
        @NamedQuery(
                name = "FoodCategory.listFoodCategoriesByIdAndName",
                query = "select new com.comert.mhl.database.common.model.dto.IdAndName(fg.foodCategoryId,fg.foodCategoryName) from FoodCategory fg",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        )
})
public class FoodCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodCategoryId;

    @Version
    private int version;

    @NotNull(message = "Field can not be null")
    @Size(min = 2, max = 50, message = "Field size must be between 2 and 50 characters")
    @Column(name = "foodCategoryName", unique = true)
    private String foodCategoryName;

    @JsonManagedReference
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL) // by JTA
    @Fetch(value = FetchMode.JOIN) // avoiding from lazy-initialization exception
    @OneToMany(
            mappedBy = "foodCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Food> foods = new HashSet<>();

    public FoodCategory() {
    }

    public FoodCategory(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public Integer getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(Integer foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFoodCategoryName() {
        return this.foodCategoryName;
    }

    public void setFoodCategoryName(final String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    public void addFood(Food food) {
        food.setFoodCategory(this);
        foods.add(food);
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof FoodCategory)) return false;

        FoodCategory that = (FoodCategory) o;

        return new EqualsBuilder()
                .append(getFoodCategoryName(), that.getFoodCategoryName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getFoodCategoryName())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("foodCategoryId", foodCategoryId)
                .append("foodCategoryName", foodCategoryName)
                .toString();
    }
}
