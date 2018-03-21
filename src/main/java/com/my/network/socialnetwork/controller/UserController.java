package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.UserClassRepository;
import com.my.network.socialnetwork.model.post.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserClassRepository userClassRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @GetMapping(value = {"/" , "/{userId}"})
    public ResponseEntity viewAllUser(@PathVariable Optional<Long> userId){
        if(userId.isPresent()){
            if(userClassRepository.findById(userId.get()).isPresent())
                return new ResponseEntity<>(userClassRepository.findById(userId.get()).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(userClassRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Useful for populating the activity of a user or notifications.
     */
    @GetMapping("/likes/{userId}")
    public ResponseEntity allLikesOfUser(@PathVariable Long userId){
        return new ResponseEntity<>(postLikeRepository.allLikesOfUser(userId), HttpStatus.OK);
    }




}
