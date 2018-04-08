package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrderOnPriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass userClass;

    @OneToOne
    Post post;

    @OneToMany(cascade = CascadeType.ALL)
    List<RequestForQuotation> orderRequestForQuotations;

    Boolean isConfirmed;

    @OneToOne
    OrderStatus orderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserClass getUserClass() {
        return userClass;
    }

    public void setUserClass(UserClass userClass) {
        this.userClass = userClass;
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
