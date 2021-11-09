package com.comert.mhl.database.food.model.entity;

import com.comert.mhl.database.common.model.entity.component.Component;
import com.comert.mhl.database.foodcategory.model.entity.FoodCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.QueryHints;

import java.io.Serializable;

@Entity
@Table(name = "Food")
@Cacheable
@NamedQueries({
        @NamedQuery(
                name = "Food.listFoods",
                query = "select f from Food as f",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        ),
        @NamedQuery(
                name = "Food.listFoodsByIdAndName",
                query = "select new com.comert.mhl.database.common.model.dto.IdAndName(f.foodId,f.foodName) from Food f",
                hints = {
                        @QueryHint(
                                name = QueryHints.CACHEABLE,
                                value = "true"
                        )
                }
        )
})

public class Food extends Component implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foodId;

    @Version
    private int version;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "foodName", unique = true)
    private String foodName;

    @NotNull
    private String logoPath;

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // cache'de hit sayısını bir azaltıyoruz.
    @JoinColumn(
            name = "foodCategoryId",
            foreignKey = @ForeignKey(name = "FK_Food_FoodCategory")
    )
    private FoodCategory foodCategory;

    public Food() {
    }

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Food)) return false;

        Food food = (Food) o;

        return new EqualsBuilder()
                .append(getFoodName(), food.getFoodName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(foodName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("foodId", foodId)
                .append("foodName", foodName)
                .append("foodCategoryId", foodCategory.getFoodCategoryId())
                .toString();
    }
}
