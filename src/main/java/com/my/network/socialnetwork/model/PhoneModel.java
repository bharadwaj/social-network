package com.my.network.socialnetwork.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Indexed
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Field
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

    /*public List<PriceList> getBidPriceLists() {
        return priceLists;
    }

    public void setBidPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }*/
}
