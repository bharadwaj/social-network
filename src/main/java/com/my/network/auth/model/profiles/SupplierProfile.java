package com.my.network.auth.model.profiles;

import com.my.network.auth.model.TypeRole;
import com.my.network.auth.model.Users;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="supplier_profile")
public class SupplierProfile {
    //MANUFACTURER/DISTRIBUTOR/DISTRIBUTOR SALES EXECUTIVE
    //FIRM NAME/GST NUMBER/BUSINESS CARD/SUPPLIER OF WHICH BRANDS (BRAND NAMES TO BE SHOWN, HE CAN TICK)
    @Id
    @Column(name = "supplierProfileId")
    private String id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "gstNumber")
    private String gstNumber;

    @Column(name = "bizCardImgUrl")
    private String businessCardImgURL;

    @Column(name="bizDesc")
    private String bizDesc;

//    @JoinColumn(name="brandId")
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private ProductBrand productBrand;

    @JoinColumn(name = "userId")
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Users user;

    @JoinColumn(name = "typeRoleId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TypeRole typeRoleId;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "lastUpdatedOn")
    private Timestamp lastUpdatedOn;

    public SupplierProfile() {
        super();
    }

    public SupplierProfile(String id) {
        this.id = id;
    }

    //    public SupplierProfile(String id, String firstName, String gstNumber, String businessCardImgURL, String bizDesc, ProductBrand productBrand, Users user, TypeRole typeRoleId) {
//        this.id = id;
//        this.firstName = firstName;
//        this.gstNumber = gstNumber;
//        this.businessCardImgURL = businessCardImgURL;
//        this.bizDesc = bizDesc;
//        this.productBrand = productBrand;
//        this.user = user;
//        this.typeRoleId = typeRoleId;
//    }

    public SupplierProfile(String id, String firstName, String gstNumber, String businessCardImgURL, String bizDesc, Users user, TypeRole typeRoleId) {
        this.id = id;
        this.firstName = firstName;
        this.gstNumber = gstNumber;
        this.businessCardImgURL = businessCardImgURL;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getBusinessCardImgURL() {
        return businessCardImgURL;
    }

    public void setBusinessCardImgURL(String businessCardImgURL) {
        this.businessCardImgURL = businessCardImgURL;
    }

//    public ProductBrand getProductBrand() {
//        return productBrand;
//    }
//
//    public void setProductBrand(ProductBrand productBrand) {
//        this.productBrand = productBrand;
//    }

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
