package com.my.network.socialnetwork.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT u FROM SubscribedUser u WHERE u.name like %:userName% OR u.userName like %:userName%")
    Page<SubscribedUser> subscribedUsersLikeUsername(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.contactNumber like :phoneNumber% ")
    Page<SubscribedUser> subscribedUsersLikePhoneNumber(@Param("phoneNumber") String phoneNumber , Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.email like :email% ")
    Page<SubscribedUser> subscribedUsersLikeEmail(@Param("email") String email , Pageable pageable);

    /**
     * FROM SubscribedUser
     * LEFT JOIN SubscribedUser_Hashtag
     * ON SubscribedUser.id = SubscribedUser_Hashtag.subscribedUsers_id
     * WHERE SubscribedUser_Hashtag.hashtags_id = 20222
     * ORDER BY SubscribedUser.userMstTypeId asc;
     * */
    @Query("SELECT u FROM SubscribedUser u JOIN u.hashtags h WHERE h.hashtag=:hashtag AND u.id NOT IN (SELECT f.followingUser.id FROM Following f WHERE f.user.id = :currentUser) ORDER BY u.promotionFactor DESC, u.userMstTypeId DESC")
    Page<SubscribedUser> suggestionsForRetailersByHashtag(@Param("hashtag") String hashtag, @Param("currentUser")String currentUser, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u JOIN u.hashtags h WHERE h.hashtag=:hashtag AND u.id NOT IN (SELECT f.followingUser.id FROM Following f WHERE f.user.id = :currentUser) ORDER BY u.promotionFactor DESC, u.userMstTypeId ASC")
    Page<SubscribedUser> suggestionsForSuppliersByHashtag(@Param("hashtag") String hashtag, @Param("currentUser")String currentUser, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u JOIN u.hashtags h WHERE h.hashtag=:hashtag AND u.id NOT IN (SELECT f.followingUser.id FROM Following f WHERE f.user.id = :currentUser) ORDER BY u.promotionFactor DESC, u.userMstTypeId ASC")
    Page<SubscribedUser> suggestionsForCompanyByHashtag(@Param("hashtag") String hashtag, @Param("currentUser")String currentUser, Pageable pageable);

}
