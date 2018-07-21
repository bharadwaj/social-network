package com.my.network.socialnetwork.model.response;


import com.my.network.auth.model.profiles.CompanyProfile;
import com.my.network.auth.model.profiles.RetailerProfile;
import com.my.network.auth.model.profiles.ServiceCenterProfile;
import com.my.network.auth.model.profiles.SupplierProfile;
import com.my.network.socialnetwork.model.SubscribedUser;

public class UserProfileResponse {

    private SubscribedUser subscribedUser;

    private CompanyProfile companyProfile;

    private RetailerProfile retailerProfile;

    private ServiceCenterProfile serviceCenterProfile;

    private SupplierProfile supplierProfile;

    public SubscribedUser getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(SubscribedUser subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }

    public RetailerProfile getRetailerProfile() {
        return retailerProfile;
    }

    public void setRetailerProfile(RetailerProfile retailerProfile) {
        this.retailerProfile = retailerProfile;
    }

    public ServiceCenterProfile getServiceCenterProfile() {
        return serviceCenterProfile;
    }

    public void setServiceCenterProfile(ServiceCenterProfile serviceCenterProfile) {
        this.serviceCenterProfile = serviceCenterProfile;
    }

    public SupplierProfile getSupplierProfile() {
        return supplierProfile;
    }

    public void setSupplierProfile(SupplierProfile supplierProfile) {
        this.supplierProfile = supplierProfile;
    }
}
