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

    @OneToMany(cascade=CascadeType.ALL)
    List<PriceList> priceLists;

    @OneToOne
    PostCondition postCondition;

    @OneToMany(cascade = CascadeType.ALL)
    List<QuantityPriceScheme> quantityPriceSchemes;

    @OneToMany(cascade = CascadeType.ALL)
    List<ValueScheme> valueSchemes;

    @OneToMany(cascade = CascadeType.ALL)
    List<RequestForQuotation> requestForQuotations;

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

    public List<QuantityPriceScheme> getQuantityPriceSchemes() {
        return quantityPriceSchemes;
    }

    public void setQuantityPriceSchemes(List<QuantityPriceScheme> quantityPriceSchemes) {
        this.quantityPriceSchemes = quantityPriceSchemes;
    }

    public List<ValueScheme> getValueSchemes() {
        return valueSchemes;
    }

    public void setValueSchemes(List<ValueScheme> valueSchemes) {
        this.valueSchemes = valueSchemes;
    }

    public List<RequestForQuotation> getRequestForQuotations() {
        return requestForQuotations;
    }

    public void setRequestForQuotations(List<RequestForQuotation> requestForQuotations) {
        this.requestForQuotations = requestForQuotations;
    }
}
