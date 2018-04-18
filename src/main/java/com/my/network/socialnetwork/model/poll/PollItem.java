package com.my.network.socialnetwork.model.poll;

import com.my.network.socialnetwork.model.UserClass;

import javax.persistence.*;

@Entity
public class PollItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    UserClass user;

    String item;
}
