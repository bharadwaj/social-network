package com.my.network.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"testimonials"})
    private SubscribedUser user;

    @JsonIgnoreProperties({"testimonials"})
    @OneToOne
    private SubscribedUser author;

    private int rating = 0;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String testimonial;

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

    public SubscribedUser getAuthor() {
        return author;
    }

    public void setAuthor(SubscribedUser author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }
}
