package com.my.network.socialnetwork.model.network;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowingRepository extends CrudRepository<Following, Long> {

    @Query("select f from Following f where f.user.id = :userId and f.isApproved = true ")
    List<Following> findFollowingByUserId(Long userId);

    @Query("select f from Following f where f.user.id = :userId and f.isApproved = false ")
    List<Following> pendingFollowingRequest(Long userId);

    @Query("select f from Following f where f.followingUser.id = :userId and f.isApproved = true")
    List<Following> findFollowersByUserId(Long userId);

    @Query("select f from Following f where f.followingUser.id = :userId and f.isApproved = false")
    List<Following> followRequestsToApproveByUser(Long userId);

    @Query("select f from Following f where f.followingUser.id = :followingUserId and f.user.id = :userId")
    Following findByUserIdAndFollowingUserId(Long userId, Long followingUserId);


}
