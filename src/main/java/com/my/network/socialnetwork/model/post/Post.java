package com.my.network.socialnetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass user;

    String message;

    //TODO handle Images;
    //string image url;

    @JsonIgnore
    int reportAbuse;


    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    PostVisibility postVisibility;

    Boolean isPublicPost;

    /*@OneToMany
    ArrayList<Comment> comment;*/

    int likeCount = 0;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "post")
    List<PostRate> postRate;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReportAbuse() {
        return reportAbuse;
    }

    public void setReportAbuse(int reportAbuse) {
        this.reportAbuse = reportAbuse;
    }

    public PostVisibility getPostVisibility() {
        return postVisibility;
    }

    public void setPostVisibility(PostVisibility postVisibility) {
        this.postVisibility = postVisibility;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getIsPublicPost() {
        return isPublicPost;
    }

    public void setIsPublicPost(Boolean publicPost) {
        isPublicPost = publicPost;
    }

    public Boolean getPublicPost() {
        return isPublicPost;
    }

    public void setPublicPost(Boolean publicPost) {
        isPublicPost = publicPost;
    }


    public List<PostRate> getPostRate() {
        return postRate;
    }

    public void setPostRate(List<PostRate> postRate) {
        this.postRate = postRate;
    }
}
