package com.my.network.socialnetwork.model.network;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends CrudRepository<Following, Long> {

    @Query("select f from Following f where f.user.id = :userId and f.isApproved = true ")
    List<Following> findFollowingByUserId(@Param("userId") Long userId);

    @Query("select f from Following f where f.user.id = :userId and f.isApproved = false ")
    List<Following> pendingFollowingRequest(@Param("userId") Long userId);

    @Query("select f from Following f where f.followingUser.id = :userId and f.isApproved = true")
    List<Following> findFollowersByUserId(@Param("userId") Long userId);

    @Query("select f from Following f where f.followingUser.id = :userId and f.isApproved = false")
    List<Following> followRequestsToApproveByUser(Long userId);

    @Query("select f from Following f where f.followingUser.id = :followingUserId and f.user.id = :userId")
    Following findByUserIdAndFollowingUserId(@Param("userId") Long userId, @Param("followingUserId") Long followingUserId);


}
