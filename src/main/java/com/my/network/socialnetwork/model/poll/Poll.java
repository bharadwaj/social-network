package com.my.network.socialnetwork.model.poll;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass createdBy;

    String title;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserClass getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserClass createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public List<PollItem> getPollItems() {
        return pollItems;
    }

    public void setPollItems(List<PollItem> pollItems) {
        this.pollItems = pollItems;
    }*/
}
