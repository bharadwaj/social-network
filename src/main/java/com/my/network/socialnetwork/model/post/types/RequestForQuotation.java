package com.my.network.socialnetwork.model.post.types;

import com.my.network.socialnetwork.model.product.phone.PhoneModel;

import javax.persistence.*;

@Entity
public class RequestForQuotation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private PhoneModel phoneModel;

    private int quantity;

    private int expectedPrice;

    private String description;

    private Boolean isAllInclusivePrice;

    private Boolean isNegotiable;

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

    public Boolean getAllInclusivePrice() {
        return isAllInclusivePrice;
    }

    public void setAllInclusivePrice(Boolean allInclusivePrice) {
        isAllInclusivePrice = allInclusivePrice;
    }

    public Boolean getNegotiable() {
        return isNegotiable;
    }

    public void setNegotiable(Boolean negotiable) {
        isNegotiable = negotiable;
    }
}
