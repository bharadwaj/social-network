package com.my.network.socialnetwork.controller;

import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
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
    SubscribedUserRepository subscribedUserRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping(value = {"/" , "/{userId}"})
    public ResponseEntity viewAllUser(@PathVariable Optional<String> userId){
        if(userId.isPresent()){
            if(subscribedUserRepository.findById(userId.get()).isPresent())
                return new ResponseEntity<>(subscribedUserRepository.findById(userId.get()).get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(subscribedUserRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Useful for populating the activity of a user or notifications.
     */
    @GetMapping("/likes/{userId}")
    public ResponseEntity allLikesOfUser(@PathVariable String userId){
        return new ResponseEntity<>(postLikeRepository.allLikesOfUser(userId), HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity createUserClass(@RequestBody SubscribedUser toBeSubscribedUser){

        Users existingUser = usersRepository.findById(toBeSubscribedUser.getId()).get();

        if(existingUser.getUserId() == null)
            return new ResponseEntity("User Does not exist", HttpStatus.BAD_REQUEST);

        toBeSubscribedUser.setId(existingUser.getUserId());
        toBeSubscribedUser.setContactNumber(existingUser.getContactNumber());
        toBeSubscribedUser.setName(existingUser.getName());
        toBeSubscribedUser.setEmail(existingUser.getEmail());
        //toBeSubscribedUser.setAccessToken(existingUser.getAccessToken());
        //toBeSubscribedUser.setGcmToken(existingUser.getGcmToken());

        //Get Zip Code
        SubscribedUser newSubscribedUser = subscribedUserRepository.save(toBeSubscribedUser);

        UserGroup zipCodeGroup = userGroupRepository.findDistinctByHashtag(toBeSubscribedUser.getZipCode().toString());
        List<SubscribedUser> toUpdateUserList = zipCodeGroup.getGroupMemberUsers();
        if(toUpdateUserList == null){
            toUpdateUserList = new ArrayList<>();
        }
        toUpdateUserList.add(newSubscribedUser);
        zipCodeGroup.setGroupMemberUsers(toUpdateUserList);
        userGroupRepository.save(zipCodeGroup);
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }

}
