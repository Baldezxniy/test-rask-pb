package org.example.dataservice.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "credit_card_number", nullable = false, length = 16)
    private String creditCardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public CreditCard() {
    }

    public CreditCard(long id, String creditCardNumber, User user) {
        this.id = id;
        this.creditCardNumber = creditCardNumber;
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
