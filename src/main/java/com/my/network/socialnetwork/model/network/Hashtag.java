package com.my.network.socialnetwork.model.network;

import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.post.Post;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hashtag;

    private String description;

    private int postsCount;

    private int followersCount;

    @ManyToMany(mappedBy = "hashtags")
    private List<Post> posts;

    @ManyToMany(mappedBy = "hashtags")
    private List<SubscribedUser> subscribedUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubscribedUser> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<SubscribedUser> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
}
