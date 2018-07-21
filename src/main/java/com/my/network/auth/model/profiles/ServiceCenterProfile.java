package com.my.network.auth.model.profiles;



import com.my.network.auth.model.TypeRole;
import com.my.network.auth.model.Users;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "service_center_profile")
public class ServiceCenterProfile {

    @Id
    @Column(name = "serviceCenterProfileId")
    private String id;
    //FIRM NAME/GST NUMBER/BUSINESS CARD/PHOTO OF SERVICE CENTRE/SERVICE CENTRE FOR WHICH BRANDS

    @Column(name = "firmName")
    private String firmName;

    @Column(name = "gstNumber")
    private String gstNumber;

    @Column(name = "bizCardImgUrl")
    private String bizCardImgUrl;

    @Column(name = "photoServiceCenterImgUrl")
    private String photoServiceCenterImgUrl;

    @Column(name="bizDesc")
    private String bizDesc;

//    @JoinColumn(name = "brandId")
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

    public ServiceCenterProfile() {
        super();
    }

//    public ServiceCenterProfile(String id, String firmName, String gstNumber, String bizCardImgUrl, String photoServiceCenterImgUrl, String bizDesc, ProductBrand productBrand, Users user, TypeRole typeRoleId) {
//        this.id = id;
//        this.firmName = firmName;
//        this.gstNumber = gstNumber;
//        this.bizCardImgUrl = bizCardImgUrl;
//        this.photoServiceCenterImgUrl = photoServiceCenterImgUrl;
//        this.bizDesc = bizDesc;
//        this.productBrand = productBrand;
//        this.user = user;
//        this.typeRoleId = typeRoleId;
//    }

    public ServiceCenterProfile(String id, String firmName, String gstNumber, String bizCardImgUrl, String photoServiceCenterImgUrl, String bizDesc, Users user, TypeRole typeRoleId) {
        this.id = id;
        this.firmName = firmName;
        this.gstNumber = gstNumber;
        this.bizCardImgUrl = bizCardImgUrl;
        this.photoServiceCenterImgUrl = photoServiceCenterImgUrl;
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

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getBizCardImgUrl() {
        return bizCardImgUrl;
    }

    public void setBizCardImgUrl(String bizCardImgUrl) {
        this.bizCardImgUrl = bizCardImgUrl;
    }

    public String getPhotoServiceCenterImgUrl() {
        return photoServiceCenterImgUrl;
    }

    public void setPhotoServiceCenterImgUrl(String photoServiceCenterImgUrl) {
        this.photoServiceCenterImgUrl = photoServiceCenterImgUrl;
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
