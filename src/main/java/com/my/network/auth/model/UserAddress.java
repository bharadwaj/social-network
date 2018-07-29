package com.my.network.auth.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "user_address")
@NamedQuery(name = "UserAddress.findAll", query = "SELECT u FROM UserAddress u")
public class UserAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String userAddressId;

    private String addresslineOne;

    private String addresslineTwo;

    private String city;

    private Timestamp createdOn;

    private Timestamp lastUpdatedOn;

    private String pincode;

    private String serviceCenterId;

    private String district;

    private String state;

    private String country;

    // bi-directional many-to-one association to Users
    // @ManyToOne
    @JoinColumn(name = "userId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Users user;

    public UserAddress() {
    }

    public String getUserAddressId() {
        return this.userAddressId;
    }

    public void setUserAddressId(String userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getAddresslineOne() {
        return this.addresslineOne;
    }

    public void setAddresslineOne(String addresslineOne) {
        this.addresslineOne = addresslineOne;
    }

    public String getAddresslineTwo() {
        return this.addresslineTwo;
    }

    public void setAddresslineTwo(String addresslineTwo) {
        this.addresslineTwo = addresslineTwo;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getServiceCenterId() {
        return this.serviceCenterId;
    }

    public void setServiceCenterId(String serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
