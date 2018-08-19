package com.my.network.socialnetwork.model.post.ordersbids;

import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.post.Post;
import com.my.network.socialnetwork.model.post.types.PriceList;

import javax.persistence.*;
import java.util.List;


/**
 * The Seller places a bid on RFQ with his rates for each product.
 * Status of the RFQ needs to be present.
 * */
@Entity
public class BidOnRFQ {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SubscribedUser subscribedUser;

    @OneToOne
    private Post post;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PriceList> bidPriceLists;

    @ManyToOne
    private OrderStatus orderStatus;

    private Boolean isConfirmed = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscribedUser getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(SubscribedUser subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<PriceList> getBidPriceLists() {
        return bidPriceLists;
    }

    public void setBidPriceLists(List<PriceList> bidPriceLists) {
        this.bidPriceLists = bidPriceLists;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
