package com.my.network.socialnetwork.model.poll;

import com.my.network.socialnetwork.model.SubscribedUser;

import javax.persistence.*;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    SubscribedUser createdBy;

    String title;

    @OneToMany(cascade = CascadeType.ALL)
    List<PollItem> pollItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscribedUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SubscribedUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PollItem> getPollItems() {
        return pollItems;
    }

    public void setPollItems(List<PollItem> pollItems) {
        this.pollItems = pollItems;
    }
}
