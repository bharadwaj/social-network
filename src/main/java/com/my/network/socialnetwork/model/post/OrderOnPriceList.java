package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"user_id", "post_id"})})
public class OrderOnPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SubscribedUser subscribedUser;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RequestForQuotation> orderRequestForQuotations;

    private Boolean isConfirmed;

    @OneToOne
    private OrderStatus orderStatus;

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

    public List<RequestForQuotation> getOrderRequestForQuotations() {
        return orderRequestForQuotations;
    }

    public void setOrderRequestForQuotations(List<RequestForQuotation> orderRequestForQuotations) {
        this.orderRequestForQuotations = orderRequestForQuotations;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
