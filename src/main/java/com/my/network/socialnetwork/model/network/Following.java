package com.my.network.socialnetwork.model.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.SubscribedUser;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames =
                {"user_id", "following_user_id"}) })
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SubscribedUser user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "following_user_id")
    private SubscribedUser followingUser;

    @JsonIgnore
    private boolean isApproved;

    private boolean isBlockPostsOfFollowUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    public Following() {
    }

    public Following(SubscribedUser followingUser) {
        this.followingUser = followingUser;
    }

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
}
