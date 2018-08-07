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

    @Query("SELECT u FROM SubscribedUser u WHERE u.zipCode >= :zipcode ORDER BY u.userMstTypeId DESC, u.zipCode ASC")
    Page<SubscribedUser> getSuggestionsForRetailersByZipCode(@Param("zipcode") int zipcode, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.zipCode >= :zipcode ORDER BY u.userMstTypeId ASC, u.zipCode ASC")
    Page<SubscribedUser> getSuggestionsForSuppliersByZipCode(@Param("zipcode") int zipcode, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.zipCode >= :zipcode ORDER BY u.userMstTypeId ASC, u.zipCode ASC")
    Page<SubscribedUser> getSuggestionsForCompanyByZipCode(@Param("zipcode") int zipcode, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.name like %:userName% OR u.userName like %:userName%")
    Page<SubscribedUser> getUsersLikeUsername(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.contactNumber like :phoneNumber% ")
    Page<SubscribedUser> getUsersLikePhoneNumber(@Param("phoneNumber") String phoneNumber , Pageable pageable);

    @Query("SELECT u FROM SubscribedUser u WHERE u.contactNumber like :email% ")
    Page<SubscribedUser> getUsersLikeEmail(@Param("email") String email , Pageable pageable);

}
