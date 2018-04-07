package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.PhoneModel;
import javax.persistence.*;

@Entity
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    //TODO
    @OneToOne
    PhoneModel phoneModel;

    int quantity;

    int price;

    String description;

    boolean isLimitedTime;

    boolean isLimitedStock;

    boolean isPriceIncreased;

    boolean isPriceDecreased;

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

    public boolean isPriceDecreased() {
        return isPriceDecreased;
    }

    public void setPriceDecreased(boolean priceDecreased) {
        isPriceDecreased = priceDecreased;
    }
}
