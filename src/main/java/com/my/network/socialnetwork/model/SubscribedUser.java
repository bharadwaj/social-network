package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.auth.model.profiles.CompanyProfile;
import com.my.network.auth.model.profiles.RetailerProfile;
import com.my.network.auth.model.profiles.ServiceCenterProfile;
import com.my.network.auth.model.profiles.SupplierProfile;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.post.Post;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class SubscribedUser {

    @Id
    @Column(unique=true, nullable=false)
    private String id;

    private Timestamp createdOn;

    private boolean isApproved;

    private boolean isDeleted;

    private boolean isLocked;

    private Timestamp lastUpdatedOn;

    private String name;

    @Column(name = "userAccessToken")
    private String accessToken;

    @Column(name = "userEmail")
    private String email;

    private String userName;

    private String designation;

    private String location;

    private String mainTitle;

    private String subTitle;

    @Column(name = "userPassword")
    private String password;

    @Column(name = "userPhoneNumber")
    private String contactNumber;

    @Column(name = "userRefreshToken")
    private String refreshToken;

    private String userSalt;

    private String userWebsite;

    @Column(name = "gcmToken")
    private String gcmToken;


    private boolean isOpenFollow;

    private boolean isOpenMessage;

    private boolean isOpenProfile;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Post> postList;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Following> followingList;

    @JsonIgnore
    @ManyToMany(mappedBy = "groupMemberUsers", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    private Long zipCode;

    private String state;

    private String countryCode;

    private int userType;

    private String userTypeName;

    /*
    * 0 = User is not Following
    * 1 = user is Following
    * 2 = User is Following but not Approved
    * */
    @Transient
    private int userFollowStatus;

    //TODO
    private int followingCount;

    private int followersCount;

    @Transient
    private CompanyProfile companyProfile;

    @Transient
    private RetailerProfile retailerProfile;

    @Transient
    private ServiceCenterProfile serviceCenterProfile;

    @Transient
    private SupplierProfile supplierProfile;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpenFollow() {
        return isOpenFollow;
    }

    public void setOpenFollow(boolean openFollow) {
        isOpenFollow = openFollow;
    }

    public boolean isOpenMessage() {
        return isOpenMessage;
    }

    public void setOpenMessage(boolean openMessage) {
        isOpenMessage = openMessage;
    }

    public boolean isOpenProfile() {
        return isOpenProfile;
    }

    public void setOpenProfile(boolean openProfile) {
        isOpenProfile = openProfile;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public List<Following> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<Following> followingList) {
        this.followingList = followingList;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Timestamp getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Timestamp lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserWebsite() {
        return userWebsite;
    }

    public void setUserWebsite(String userWebsite) {
        this.userWebsite = userWebsite;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public int getUserFollowStatus() {
        return userFollowStatus;
    }

    public void setUserFollowStatus(int userFollowStatus) {
        this.userFollowStatus = userFollowStatus;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
