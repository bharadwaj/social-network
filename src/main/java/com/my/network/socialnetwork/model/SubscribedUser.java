package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.post.Post;

import javax.persistence.*;
import java.sql.Timestamp;
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
}
