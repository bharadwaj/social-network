package com.my.network.socialnetwork.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserClassRepository extends CrudRepository<UserClass, Long> {

    @Query("SELECT u FROM UserClass u WHERE u.id NOT IN" +
            "(select f.followingUser.id from Following f " +
            "WHERE f.user.id = :userId)")
    List<UserClass> suggestUsersToFollow(Long userId);
}
