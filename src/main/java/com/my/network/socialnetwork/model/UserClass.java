package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    List<Group> groupList;*/


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

    /*public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }*/
}
