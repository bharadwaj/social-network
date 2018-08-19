package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.my.network.auth.model.profiles.CompanyProfile;
import com.my.network.auth.model.profiles.RetailerProfile;
import com.my.network.auth.model.profiles.ServiceCenterProfile;
import com.my.network.auth.model.profiles.SupplierProfile;
import com.my.network.socialnetwork.model.network.Hashtag;
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

    /**
     * Basic Information: Name, UserName, Email, ContactNumber
     * */
    private String name;
    private String userName;
    @Column(name = "userEmail")
    private String email;
    private String communicationEmail;
    @Column(name = "userPhoneNumber")
    private String contactNumber;
    private String profilePhotoUrl;

    /**
     * Titles useful displaying Profile: Main, Sub, TypeName, TypeRole
     * */
    private String mainTitle;
    private String subTitle;
    private int userMstTypeId;
    private String userMstTypeName;
    private String userTypeRoleName;
    private String userWebsite;

    /**
     * Social Network Settings
     * */
    private boolean isOpenFollow;
    private boolean isOpenMessage;
    private boolean isOpenProfile;

    private int followingCount;
    private int followersCount;

    /**
     * Address for Recommendations and Profile Display
     * */
    private String location;
    private Integer zipCode;
    private String state;
    private String countryCode;

    private double avgRating;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<Testimonial> testimonials;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Post> postList;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<Following> followingList;

    @JsonIgnore
    @ManyToMany(mappedBy = "groupMemberUsers", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    //  For Individual Metadata Generation of Response  //
    /**
     * 0 = User is not Following
     * 1 = user is Following
     * 2 = User is Following but not Approved
     * */
    @Transient
    private int userFollowStatus;

    @Transient
    private CompanyProfile companyProfile;

    @Transient
    private RetailerProfile retailerProfile;

    @Transient
    private ServiceCenterProfile serviceCenterProfile;

    @Transient
    private SupplierProfile supplierProfile;

    private boolean isApproved;

    private boolean isDeleted;

    private boolean isLocked;

    private int promotionFactor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"posts", "subscribedUsers"})
    private List<Hashtag> hashtags;

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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommunicationEmail() {
        return communicationEmail;
    }

    public void setCommunicationEmail(String communicationEmail) {
        this.communicationEmail = communicationEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserWebsite() {
        return userWebsite;
    }

    public void setUserWebsite(String userWebsite) {
        this.userWebsite = userWebsite;
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

    public int getUserMstTypeId() {
        return userMstTypeId;
    }

    public void setUserMstTypeId(int userMstTypeId) {
        this.userMstTypeId = userMstTypeId;
    }

    public void setUserTypeRoleName(String userTypeRoleName) {
        this.userTypeRoleName = userTypeRoleName;
    }

    public String getUserTypeRoleName() {
        return userTypeRoleName;
    }

    public String getUserMstTypeName() {
        return userMstTypeName;
    }

    public void setUserMstTypeName(String userMstTypeName) {
        this.userMstTypeName = userMstTypeName;
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

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public int getPromotionFactor() {
        return promotionFactor;
    }

    public void setPromotionFactor(int promotionFactor) {
        this.promotionFactor = promotionFactor;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Testimonial> getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(List<Testimonial> testimonials) {
        this.testimonials = testimonials;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
