package com.my.network.socialnetwork.model.poll;

import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;

@Entity
public class PollItemVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    SubscribedUser votedUser;

    @ManyToOne
    PollItem pollItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscribedUser getVotedUser() {
        return votedUser;
    }

    public void setVotedUser(SubscribedUser votedUser) {
        this.votedUser = votedUser;
    }

    public PollItem getPollItem() {
        return pollItem;
    }

    public void setPollItem(PollItem pollItem) {
        this.pollItem = pollItem;
    }
}
