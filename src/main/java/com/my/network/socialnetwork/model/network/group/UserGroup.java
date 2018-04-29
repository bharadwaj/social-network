package com.my.network.socialnetwork.model.network.group;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    //TODO add constraints
    String groupName;

    //TODO Unique constraint
    String hashtag;

    @ManyToMany
    List<UserClass> groupMemberUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public List<UserClass> getGroupMemberUsers() {
        return groupMemberUsers;
    }

    public void setGroupMemberUsers(List<UserClass> groupMemberUsers) {
        this.groupMemberUsers = groupMemberUsers;
    }
}
