package com.my.network.socialnetwork.controller;

import com.my.network.auth.model.UsersRepository;
import com.my.network.socialnetwork.model.UserClass;
import com.my.network.socialnetwork.model.UserClassRepository;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.group.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/groups")
public class GroupsController {

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserClassRepository userClassRepository;

    @PostMapping(value = "/new")
    public ResponseEntity createGroup(@RequestBody UserGroup userGroup){
        ArrayList<UserClass> validatedUserList = new ArrayList<>();

        if(userGroup.getGroupMemberUsers() != null) {
            for (UserClass nonValidatedUser : userGroup.getGroupMemberUsers()) {
                if (userClassRepository.findById(nonValidatedUser.getId()) != null) {
                    validatedUserList.add(userClassRepository.findById(nonValidatedUser.getId()).get());
                }
            }
        }

        userGroup.setGroupMemberUsers(validatedUserList);

        return new ResponseEntity<>(userGroupRepository.save(userGroup), HttpStatus.OK);
    }

    @PostMapping(value = "/update/users/{groupId}")
    public ResponseEntity addUsers(@RequestBody ArrayList<UserClass> userList, @PathVariable Long groupId){
        UserGroup userGroupToUpdate = userGroupRepository.findById(groupId).get();
        ArrayList<UserClass> validatedUserList = new ArrayList<>();

        for(UserClass nonValidatedUser: userList){
            if(userClassRepository.findById(nonValidatedUser.getId())!= null){
                validatedUserList.add(userClassRepository.findById(nonValidatedUser.getId()).get());
            }
        }

        userGroupToUpdate.setGroupMemberUsers(validatedUserList);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value="/all")
    public ResponseEntity allGroups(){
        return new ResponseEntity<>(userGroupRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value="/{groupId}")
    public ResponseEntity groupById(@PathVariable Long groupId){
        return new ResponseEntity<>(userGroupRepository.findById(groupId), HttpStatus.OK);
    }
}
