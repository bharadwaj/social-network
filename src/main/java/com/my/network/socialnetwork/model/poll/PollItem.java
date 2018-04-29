package com.my.network.socialnetwork.model.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class PollItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String itemName;

    @JsonIgnore
    @OneToMany(mappedBy = "pollItem", cascade = CascadeType.ALL)
    List<PollItemVote> pollItemVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<PollItemVote> getPollItemVotes() {
        return pollItemVotes;
    }

    public void setPollItemVotes(List<PollItemVote> pollItemVotes) {
        this.pollItemVotes = pollItemVotes;
    }
}
