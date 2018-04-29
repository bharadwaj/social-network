package com.my.network.socialnetwork.model.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;

@Entity
public class PollItemVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    UserClass votedUser;

    @ManyToOne
    PollItem pollItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserClass getVotedUser() {
        return votedUser;
    }

    public void setVotedUser(UserClass votedUser) {
        this.votedUser = votedUser;
    }

    public PollItem getPollItem() {
        return pollItem;
    }

    public void setPollItem(PollItem pollItem) {
        this.pollItem = pollItem;
    }
}
