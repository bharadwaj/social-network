package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.post.PriceList;

import javax.persistence.*;

@Entity
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    //@ManyToMany(cascade = CascadeType.ALL, mappedBy = "phoneModels")

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

    /*public List<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }*/
}
