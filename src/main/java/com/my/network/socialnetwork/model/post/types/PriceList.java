package com.my.network.socialnetwork.model.post.types;

import com.my.network.socialnetwork.model.product.phone.PhoneModel;
import javax.persistence.*;

@Entity
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private PhoneModel phoneModel;

    private int quantity;

    private int price;

    private String description;

    private boolean isLimitedTime;

    private boolean isLimitedStock;

    private boolean isPriceIncreased;

    private boolean isPriceDecreased;

    private boolean isPriceInclusiveOfGST;

    private boolean isNegotiable;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isLimitedTime() {
        return isLimitedTime;
    }

    public void setLimitedTime(boolean limitedTime) {
        isLimitedTime = limitedTime;
    }

    public boolean isLimitedStock() {
        return isLimitedStock;
    }

    public void setLimitedStock(boolean limitedStock) {
        isLimitedStock = limitedStock;
    }

    public boolean isPriceIncreased() {
        return isPriceIncreased;
    }

    public void setPriceIncreased(boolean priceIncreased) {
        isPriceIncreased = priceIncreased;
    }

    public boolean isPriceInclusiveOfGST() {
        return isPriceInclusiveOfGST;
    }

    public void setPriceInclusiveOfGST(boolean priceInclusiveOfGST) {
        isPriceInclusiveOfGST = priceInclusiveOfGST;
    }

    public boolean isPriceDecreased() {
        return isPriceDecreased;
    }

    public void setPriceDecreased(boolean priceDecreased) {
        isPriceDecreased = priceDecreased;
    }

    public boolean isNegotiable() {
        return isNegotiable;
    }

    public void setNegotiable(boolean negotiable) {
        isNegotiable = negotiable;
    }
}
