package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.SubscribedUser;

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
    Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    SubscribedUser subscribedUser;

    @OneToOne
    Post post;

    @OneToMany(cascade = CascadeType.ALL)
    List<PriceList> bidPriceLists;

    @OneToOne
    OrderStatus orderStatus;

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
}
