package com.my.network.socialnetwork.model.network;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

}
