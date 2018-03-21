package com.my.network.socialnetwork.model.post;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;

@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserClass user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
