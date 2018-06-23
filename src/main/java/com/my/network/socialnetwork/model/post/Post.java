package com.my.network.socialnetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.SubscribedUser;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Indexed
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SubscribedUser user;

    //@Field
    private String title;

    //@Field
    private String body;

    private String uniqueHandle;

    private String imageUrl;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PostVisibility postVisibility;

    private Boolean isPublicPost;

    private Boolean isFriendsOnlyPost;

    /*@OneToMany
    ArrayList<Comment> comment;*/

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    //@IndexedEmbedded
    private List<PriceList> priceLists;

    @OneToOne
    private PostCondition postCondition;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<QuantityPriceScheme> quantityPriceSchemes;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ValueScheme> valueSchemes;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RequestForQuotation> requestForQuotations;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PostLike> postLikeList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> commentList;

    private Boolean isLiked = false;

    private Boolean isReported = false;

    private int commentCount = 0;

    private int viewCount = 0;

    private int likeCount = 0;

    private int reportAbuseCount = 0;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    private Integer priorityList;

    private Date postExpiryDate;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUniqueHandle() {
        return uniqueHandle;
    }

    public void setUniqueHandle(String uniqueHandle) {
        this.uniqueHandle = uniqueHandle;
    }

    public int getReportAbuseCount() {
        return reportAbuseCount;
    }

    public void setReportAbuseCount(int reportAbuseCount) {
        this.reportAbuseCount = reportAbuseCount;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<PostLike> getPostLikeList() {
        return postLikeList;
    }

    public void setPostLikeList(List<PostLike> postLikeList) {
        this.postLikeList = postLikeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Integer getPriorityList() {
        return priorityList;
    }

    public void setPriorityList(Integer priorityList) {
        this.priorityList = priorityList;
    }

    public Date getPostExpiryDate() {
        return postExpiryDate;
    }

    public void setPostExpiryDate(Date postExpiryDate) {
        this.postExpiryDate = postExpiryDate;
    }

    public Boolean getReported() {
        return isReported;
    }

    public void setReported(Boolean reported) {
        isReported = reported;
    }

    public Boolean getIsFriendsOnlyPost() {
        return isFriendsOnlyPost;
    }

    public void setIsFriendsOnlyPost(Boolean isFriendsOnlyPost) {
        this.isFriendsOnlyPost = isFriendsOnlyPost;
    }
}
