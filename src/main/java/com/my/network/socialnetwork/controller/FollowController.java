package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @Autowired
    FollowingRepository followingRepository;

    @Autowired
    SubscribedUserRepository subscribedUserRepository;

    /*
    * Suggest which user to follow.
    * When the user is in his homepage the user gets
    * TODO: 1. get userId from jwt
    * TODO: 2. Recommendations will plugin here.
    * */
    @GetMapping("/{userId}")
    public ResponseEntity suggestUsersToFollow(@PathVariable String userId){
        return new ResponseEntity<>(subscribedUserRepository.suggestUsersToFollow(userId), HttpStatus.OK);
    }

    /*
    * Follow a friend:
    * Params: UserId, FollowUserId
    * TODO: 1. In future get userId with the context so authorization is handled
    * */
    /*@PostMapping("/")
    public ResponseEntity followUser(@RequestBody Following following) {

        //Validate follow user & user's existence.
        Boolean userIsValid = subscribedUserRepository.findById(following.getCreatedBy().getId()).isPresent();
        Boolean followingUserIsValid = subscribedUserRepository.findById(following.getFollowingUser().getId()).isPresent();

        if(!userIsValid || !followingUserIsValid || (following.getCreatedBy().getId() == following.getFollowingUser().getId()))
            return new ResponseEntity<>("Invalid User to Follow", HttpStatus.BAD_REQUEST);

        //TODO Return bad request if already user is following user.
        *//*if()
            return new ResponseEntity<>("You are already following the user.",HttpStatus.BAD_REQUEST);*//*

        *//*
        * Get from jwt token and set following user Id.
        * following.setCreatedBy(TODO Get the user from jwt token);
        * *//*
        following.setCreatedBy(subscribedUserRepository.findById(following.getCreatedBy().getId()).get());
        following.setFollowingUser(subscribedUserRepository.findById(following.getFollowingUser().getId()).get());

        // Check if the user's isOpenFollow is true.
        following.setApproved(false);
        if(following.getFollowingUser().isOpenFollow())
            following.setApproved(true);

        return new ResponseEntity<>(followingRepository.save(following), HttpStatus.CREATED);
    }*/

    /*
    * Un-follow a friend:
    * Params: UserId, FollowerUserId
    * TODO: 1. In future get userId with the context
    * */
    @DeleteMapping("/")
    public ResponseEntity unfollowUser(@RequestBody Following following) {
        followingRepository.deleteById(following.getId());
        System.out.println(following.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO: get rid of userId Path variable.
    @GetMapping("/pending/{userId}")
    public ResponseEntity pendingFollowRequests(@PathVariable Long userId){
        return new ResponseEntity<>(followingRepository.pendingFollowingRequest(userId), HttpStatus.OK);
    }

    //TODO: get rid of userId Path variable.
    @GetMapping("/accept/{userId}")
    public ResponseEntity approveFollowRequests(@PathVariable Long userId){
        return new ResponseEntity<>(followingRepository.followRequestsToApproveByUser(userId), HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity approveFriendRequest(@RequestBody Following following){
        following = followingRepository.findById(following.getId()).get();
        following.setApproved(true);
        return new ResponseEntity<>(followingRepository.save(following), HttpStatus.OK);
    }

    /*
    * List Users current user is Following.
    * Params: userId
    * */
    @GetMapping("/following/{userId}")
    public ResponseEntity listOfFollowing(@PathVariable Long userId) {
        return new ResponseEntity<>(followingRepository.findFollowingByUserId(userId), HttpStatus.OK);
    }

    /*
    * List Users who are following current user.
    * Params: userId
    * */
    @GetMapping("/followers/{userId}")
    public ResponseEntity listOfFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(followingRepository.findFollowersByUserId(userId), HttpStatus.OK);
    }
}
