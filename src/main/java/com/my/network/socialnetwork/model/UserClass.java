package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    boolean isOpenFollow;

    boolean isOpenMessage;

    boolean isOpenProfile;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    List<Post> postList;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    List<Following> followingList;

    /*@ManyToMany(mappedBy = "usersOfGroup")
    List<UserGroup> groupList;*/

    @JsonIgnore
    @ManyToMany(mappedBy = "groupMemberUsers", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups;

    private Long zipCode;

    private String state;

    private String countryCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    /*public List<UserGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<UserGroup> groupList) {
        this.groupList = groupList;
    }*/
}
