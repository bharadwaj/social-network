package com.my.network.socialnetwork.model.pincode;

import javax.persistence.*;

@Entity(name = "md_Pincode")
public class Pincode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int pincode;

    @ManyToOne
    private District district;

    public Pincode(int pincode) {
        this.pincode = pincode;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
