package com.my.network.socialnetwork.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String bio;

    String userHandle;

    int postCount;

    int followingCount;

    int followerCount;

    boolean isVerifiedUser;


}
