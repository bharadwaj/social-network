package com.my.network.auth.model.profiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.auth.model.ProductBrand;
import com.my.network.auth.model.TypeRole;
import com.my.network.auth.model.Users;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="company_profile")
public class CompanyProfile {

    @Id
    @Column(name="companyProfileId")
    private String id;

    //BRAND WORKING FOR/DESIGNATION/OFFICIAL MAIL ID/BUSINESS CARD
    //SALES  MANAGERS/ BRAND PROMOTERS/

    @JoinColumn(name="brandId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ProductBrand productBrand;

    @Column(name="designation")
    private String designation;

    @Column(name="officialEmail")
    private String officialEmail;

    @Column(name="bizCardImgUrl", nullable = false, columnDefinition = "")
    private String bizCardImgUrl;

    @JoinColumn(name="userId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private Users user;

    @JoinColumn(name="typeRoleId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TypeRole typeRoleId;

    @Column(name="bizDesc")
    private String bizDesc;

    @Column(name="createdOn")
    private Timestamp createdOn;

    @Column(name="lastUpdatedOn")
    private Timestamp lastUpdatedOn;

    public CompanyProfile() {
        super();
    }

    public CompanyProfile(String id, ProductBrand productBrand, String designation, String officialEmail, String bizCardImgUrl, Users user, TypeRole typeRoleId, String bizDesc) {
        this.id = id;
        this.productBrand = productBrand;
        this.designation = designation;
        this.officialEmail = officialEmail;
        this.bizCardImgUrl = bizCardImgUrl;
        this.user = user;
        this.typeRoleId = typeRoleId;
        this.bizDesc = bizDesc;
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

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
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
