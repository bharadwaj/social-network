package com.my.network.socialnetwork.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribedUserRepository extends PagingAndSortingRepository<SubscribedUser, String> {

    @Query("SELECT u FROM SubscribedUser u WHERE u.id NOT IN" +
            "(select f.followingUser.id from Following f " +
            "WHERE f.user.id = :userId)")
    List<SubscribedUser> suggestUsersToFollow(@Param("userId") String userId);

    @Query("SELECT u FROM SubscribedUser u WHERE u.userName = :userId")
    SubscribedUser getSubscribedUser(@Param("userId") String userId);

    @Query("SELECT u FROM SubscribedUser u WHERE u.contactNumber = :phoneNumber")
    List<SubscribedUser> getSubscribedUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT u FROM SubscribedUser u WHERE u.email = :email")
    List<SubscribedUser> getSubscribedUserByEmail(@Param("email") String email);
}
