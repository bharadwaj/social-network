package com.my.network.socialnetwork.model.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;

@Entity
/*@Table(name = "MY_TABLE",
        uniqueConstraints = { @UniqueConstraint(columnNames =
                { "FIELD_A", "FIELD_B" }) })*/
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SubscribedUser user;

    //TODO typo, change to following
    @OneToOne
    private SubscribedUser followingUser;

    @JsonIgnore
    private boolean isApproved;

    private boolean isBlockPostsOfFollowUser;

    //TODO Add timestamps

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscribedUser getUser() {
        return user;
    }

    public void setUser(SubscribedUser user) {
        this.user = user;
    }

    public SubscribedUser getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(SubscribedUser followingUser) {
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
