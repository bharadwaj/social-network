package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;

@Entity
//TODO this uniqueConstraint is not working.
@Table(uniqueConstraints= {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserClass user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
