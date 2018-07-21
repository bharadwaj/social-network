package com.my.network.auth.model.profiles;

import com.my.network.auth.model.ProductBrand;
import com.my.network.auth.model.profiles.ServiceCenterProfile;
import com.my.network.auth.model.profiles.SupplierProfile;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="profile_brand_map")
@NamedQuery(name = "ProfileBrandMap.findAll", query = "SELECT pbm FROM ProfileBrandMap pbm")
public class ProfileBrandMap {

    @Id
    @Column(name="profileBrandMapId")
    private String id;

    @JoinColumn(name = "brandId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ProductBrand brandId;

    @JoinColumn(name = "supplierProfileId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private SupplierProfile supplierProfileId;

    @JoinColumn(name = "serviceCenterProfileId")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ServiceCenterProfile serviceCenterProfileId;

    @Column(name = "createdOn")
    private Timestamp createdOn;

    @Column(name = "lastUpdatedOn")
    private Timestamp lastUpdatedOn;

    public ProfileBrandMap() {
        super();
    }

    public ProfileBrandMap(String id, ProductBrand brandId, SupplierProfile supplierProfileId, ServiceCenterProfile serviceCenterProfileId) {
        this.id = id;
        this.brandId = brandId;
        this.supplierProfileId = supplierProfileId;
        this.serviceCenterProfileId = serviceCenterProfileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(ProductBrand brandId) {
        this.brandId = brandId;
    }

    public SupplierProfile getSupplierProfileId() {
        return supplierProfileId;
    }

    public void setSupplierProfileId(SupplierProfile supplierProfileId) {
        this.supplierProfileId = supplierProfileId;
    }

    public ServiceCenterProfile getServiceCenterProfileId() {
        return serviceCenterProfileId;
    }

    public void setServiceCenterProfileId(ServiceCenterProfile serviceCenterProfileId) {
        this.serviceCenterProfileId = serviceCenterProfileId;
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
