package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.PhoneModel;

import javax.persistence.*;

@Entity
public class RequestForQuotation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    PhoneModel phoneModel;

    int quantity;

    int expectedPrice;

    String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneModel getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(PhoneModel phoneModel) {
        this.phoneModel = phoneModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(int expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
