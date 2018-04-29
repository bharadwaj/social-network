package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.UserClass;
import com.my.network.socialnetwork.model.UserClassRepository;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.group.UserGroupRepository;
import com.my.network.socialnetwork.model.post.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserClassRepository userClassRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

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

    @PostMapping(value = "/new")
    public ResponseEntity createUserClass(@RequestBody UserClass userClass){
        //Get Zip Code
        UserClass savedUser = userClassRepository.save(userClass);
        UserGroup zipCodeGroup = userGroupRepository.findDistinctByHashtag(userClass.getZipCode().toString());
        List<UserClass> toUpdateUserList = zipCodeGroup.getGroupMemberUsers();
        if(toUpdateUserList == null){
            toUpdateUserList = new ArrayList<>();
        }
        toUpdateUserList.add(savedUser);
        zipCodeGroup.setGroupMemberUsers(toUpdateUserList);
        userGroupRepository.save(zipCodeGroup);
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }




}
