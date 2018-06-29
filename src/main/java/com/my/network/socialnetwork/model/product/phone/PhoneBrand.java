package com.my.network.socialnetwork.model.product.phone;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
public class PhoneBrand {
    @Id
    private Long id;

    @Column(unique=true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
