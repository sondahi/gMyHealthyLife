package com.comert.mhl.database.member.model.entity;

import com.comert.mhl.database.common.model.entity.component.Component;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * Kritik olan senkronizasyon boolean. uygulama üzerinden kayıtta false olacak. Clienttan gelen true olacak.
 * Client sorgu attığında falselar alınacak ve clienta gönderilecek. Client kendisine true yapıp kaydedecek ve onayladıktan sonra
 * app de true olacak.
 * Bu işlemlerin hepsi user üzerinden mealrecordu false olanlar ile yapılacak. Burda index falan yapmıyacağız. Zaten fk user
 * indexli
*/
@Entity
@Table(name = "UserMealRecord")
@AttributeOverride(name="entityId", column=@Column(name="userMealRecordId"))
@Cacheable(value = false)
public class UserMealRecord extends Component implements MealTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userMealRecordId;

    @Column(name = "savedTime", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime savedTime;

    @Column(name = "userMealRecordSynchronized",columnDefinition="bit(1) default 0")
    private boolean userMealRecordSynchronized;

    @ManyToOne
    @JoinColumn(
            name = "memberId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_UserMealRecord_User")
    )
    private User user;

    public UserMealRecord() {}

    public Integer getUserMealRecordId() {
        return userMealRecordId;
    }

    public void setUserMealRecordId(Integer userMealRecordId) {
        this.userMealRecordId = userMealRecordId;
    }

    public LocalDateTime getSavedTime() {
        return this.savedTime;
    }

    public void setSavedTime(final LocalDateTime savedTime) {
        this.savedTime = savedTime;
    }

    public boolean isUserMealRecordSynchronized() {
        return this.userMealRecordSynchronized;
    }

    public void setUserMealRecordSynchronized(final boolean userMealRecordSynchronized) {
        this.userMealRecordSynchronized = userMealRecordSynchronized;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}
