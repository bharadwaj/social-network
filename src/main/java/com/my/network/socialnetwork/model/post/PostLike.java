package com.my.network.socialnetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.auth.model.Users;
import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;

@Entity
//TODO this uniqueConstraint is not working.
@Table(uniqueConstraints= {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SubscribedUser user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
