package com.my.network.socialnetwork.model.post.schemes;

import com.my.network.socialnetwork.model.product.phone.PhoneModel;

import javax.persistence.*;

@Entity
public class QuantityPriceScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isForAll;

    @OneToOne
    private PhoneModel phoneModel;

    private int start_range;
    private int end_range;
    private int percent;
    private int value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getForAll() {
        return isForAll;
    }

    public void setForAll(Boolean forAll) {
        isForAll = forAll;
    }

    public PhoneModel getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(PhoneModel phoneModel) {
        this.phoneModel = phoneModel;
    }

    public int getStart_range() {
        return start_range;
    }

    public void setStart_range(int start_range) {
        this.start_range = start_range;
    }

    public int getEnd_range() {
        return end_range;
    }

    public void setEnd_range(int end_range) {
        this.end_range = end_range;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
