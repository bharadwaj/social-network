package com.my.network.socialnetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class PostVisibility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToOne
    private Post post;

    @ManyToMany
    private List<SubscribedUser> visibleToUsers;

    //TODO Add Groups
    /*@ManyToMany
    List<UserGroup> visibleToGroups;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<SubscribedUser> getVisibleToUsers() {
        return visibleToUsers;
    }

    public void setVisibleToUsers(List<SubscribedUser> visibleToUsers) {
        this.visibleToUsers = visibleToUsers;
    }
}
