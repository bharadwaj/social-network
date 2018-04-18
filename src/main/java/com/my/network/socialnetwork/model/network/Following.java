package com.my.network.socialnetwork.model.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;

@Entity
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass user;

    //TODO typo, change to following
    @OneToOne
    UserClass followingUser;

    @JsonIgnore
    boolean isApproved;

    boolean isBlockPostsOfFollowUser;

    //TODO Add timestamps

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserClass getUser() {
        return user;
    }

    public void setUser(UserClass user) {
        this.user = user;
    }

    public UserClass getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(UserClass followingUser) {
        this.followingUser = followingUser;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isBlockPostsOfFollowUser() {
        return isBlockPostsOfFollowUser;
    }

    public void setBlockPostsOfFollowUser(boolean blockPostsOfFollowUser) {
        isBlockPostsOfFollowUser = blockPostsOfFollowUser;
    }
}
