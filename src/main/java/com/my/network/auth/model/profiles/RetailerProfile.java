package com.my.network.auth.model.profiles;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.auth.model.TypeRole;
import com.my.network.auth.model.Users;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "retailer_profile")
public class RetailerProfile {

    @Id
    @Column(name = "retailerProfileId")
    private String id;

    @Column(name = "outletName")
    private String outletName;

    @Column(name = "gstNumber")
    private String gstNumber;

    @Column(name = "shopPhotoImgUrl")
    private String shopPhotoImgUrl;

    @Column(name = "bizCardImgUrl")
    private String bizCardImgUrl;

    @Column(name="bizDesc")
    private String bizDesc;

    @JsonIgnore
    @JoinColumn(name = "userId")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Users user;

    @JoinColumn(name = "typeRoleId")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TypeRole typeRoleId;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "lastUpdatedOn")
    private Timestamp lastUpdatedOn;

    public RetailerProfile() {
        super();
    }

    public RetailerProfile(String id, String outletName, String gstNumber, String shopPhotoImgUrl, String bizCardImgUrl, String bizDesc, Users user, TypeRole typeRoleId) {
        this.id = id;
        this.outletName = outletName;
        this.gstNumber = gstNumber;
        this.shopPhotoImgUrl = shopPhotoImgUrl;
        this.bizCardImgUrl = bizCardImgUrl;
        this.bizDesc = bizDesc;
        this.user = user;
        this.typeRoleId = typeRoleId;
    }

    public String getBizDesc() {
        return bizDesc;
    }

    public void setBizDesc(String bizDesc) {
        this.bizDesc = bizDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getShopPhotoImgUrl() {
        return shopPhotoImgUrl;
    }

    public void setShopPhotoImgUrl(String shopPhotoImgUrl) {
        this.shopPhotoImgUrl = shopPhotoImgUrl;
    }

    public String getBizCardImgUrl() {
        return bizCardImgUrl;
    }

    public void setBizCardImgUrl(String bizCardImgUrl) {
        this.bizCardImgUrl = bizCardImgUrl;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public TypeRole getTypeRoleId() {
        return typeRoleId;
    }

    public void setTypeRoleId(TypeRole typeRoleId) {
        this.typeRoleId = typeRoleId;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}

