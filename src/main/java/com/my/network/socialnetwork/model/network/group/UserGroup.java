package com.my.network.socialnetwork.model.network.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SubscribedUser> groupAdmins;

    private String groupName;

    @Column(unique=true)
    private String hashtag;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SubscribedUser> groupMemberUsers;

    @JsonIgnore
    @ManyToMany
    private List<Post> posts;

    private Boolean isPublicGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public List<SubscribedUser> getGroupMemberUsers() {
        return groupMemberUsers;
    }

    public void setGroupMemberUsers(List<SubscribedUser> groupMemberUsers) {
        this.groupMemberUsers = groupMemberUsers;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Boolean getIsPublicGroup() {
        return isPublicGroup;
    }

    public void setIsPublicGroup(Boolean publicGroup) {
        this.isPublicGroup = publicGroup;
    }

    public List<SubscribedUser> getGroupAdmins() {
        return groupAdmins;
    }

    public void setGroupAdmins(List<SubscribedUser> groupAdmins) {
        this.groupAdmins = groupAdmins;
    }
}
