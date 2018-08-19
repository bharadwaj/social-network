package com.my.network.socialnetwork.model.post.schemes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ValueScheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int start_range;
    private int end_range;
    private int percent;
    private int value;

    private int priceAfterDiscount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(int priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }
}
