package com.my.network.socialnetwork.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribedUserRepository extends CrudRepository<SubscribedUser, String> {

    @Query("SELECT u FROM SubscribedUser u WHERE u.id NOT IN" +
            "(select f.followingUser.id from Following f " +
            "WHERE f.user.id = :userId)")
    List<SubscribedUser> suggestUsersToFollow(@Param("userId") String userId);
}
