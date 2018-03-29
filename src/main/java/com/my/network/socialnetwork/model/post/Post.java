package com.my.network.socialnetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass user;

    String title;

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

    //TODO remove this.
    /*@OneToMany(cascade=CascadeType.ALL, mappedBy = "post")
    List<PostRate> postRate;*/

    @OneToMany(cascade=CascadeType.ALL)
    List<PriceList> priceLists;

    @OneToOne
    PostCondition postCondition;

    @OneToMany
    List<QunatityPriceScheme> qunatityPriceSchemes;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<PriceList> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceList> priceLists) {
        this.priceLists = priceLists;
    }

    public PostCondition getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(PostCondition postCondition) {
        this.postCondition = postCondition;
    }

    public List<QunatityPriceScheme> getQunatityPriceSchemes() {
        return qunatityPriceSchemes;
    }

    public void setQunatityPriceSchemes(List<QunatityPriceScheme> qunatityPriceSchemes) {
        this.qunatityPriceSchemes = qunatityPriceSchemes;
    }
}
