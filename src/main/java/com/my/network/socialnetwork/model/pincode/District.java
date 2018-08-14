package com.my.network.socialnetwork.model.pincode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity(name = "md_District")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable=false)
    private String districtName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    @JsonIgnoreProperties("district")
    private List<Pincode> pincodes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("districts")
    private State state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public List<Pincode> getPincodes() {
        return pincodes;
    }

    public void setPincodes(List<Pincode> pincodes) {
        this.pincodes = pincodes;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
