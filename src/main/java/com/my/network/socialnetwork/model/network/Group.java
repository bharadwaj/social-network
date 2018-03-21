package com.my.network.socialnetwork.model.network;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    //TODO add constraints
    String groupName;

    //TODO Unique constraint
    String hashtag;

    /*@ManyToMany(cascade = CascadeType.ALL)
    List<UserClass> usersOfGroup;*/

    /*@ManyToMany(cascade = CascadeType.ALL, mappedBy = "visibleToGroups")
    List<PostVisibility> postVisibilities;*/

}
