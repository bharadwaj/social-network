package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.auth.model.profiles.*;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.network.group.UserGroup;
import com.my.network.socialnetwork.model.network.group.UserGroupRepository;
import com.my.network.socialnetwork.model.post.PostLikeRepository;
import com.my.network.socialnetwork.model.response.MyNetworkSubscriptionResponse;
import com.my.network.socialnetwork.model.response.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    FollowingRepository followingRepository;

    @Autowired
    private RetailerProfileRepository retailerProfileRepository;

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private SupplierProfileRepository supplierProfileRepository;

    @Autowired
    private ServiceCenterProfileRepository serviceCenterProfileRepository;

    @GetMapping("/all")
    public ResponseEntity viewAllUser(@RequestHeader(value = "Authorization") String authTokenHeader) {
        return new ResponseEntity<>(subscribedUserRepository.findAll(), HttpStatus.OK);
    }

    //TODO Add algorithm to give suggestions.
    @GetMapping("/suggestions")
    public ResponseEntity suggestUsersToFollow(@RequestHeader(value = "Authorization") String authTokenHeader,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "20") int size) {



        String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        Page<SubscribedUser> responseSubscribedUsers = subscribedUserRepository.findAll(PageRequest.of(page, size));

        for(SubscribedUser su: responseSubscribedUsers){
            su.setRetailerProfile(retailerProfileRepository.findRetailerProfileByUserId(su.getId()));
            su.setCompanyProfile(companyProfileRepository.findCompanyProfileByUserId(su.getId()));
            su.setSupplierProfile(supplierProfileRepository.findSupplierProfileByUserId(su.getId()));
            su.setServiceCenterProfile(serviceCenterProfileRepository.findServiceCenterProfileByUserId(su.getId()));
        }

        return new ResponseEntity<>(responseSubscribedUsers, HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity viewProfileOfAUser(@PathVariable String userId, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //We want to see the profile of somebody else other than loggedIn User.
            //If user is valid and the logged in user is also valid.
            if(subscribedUserRepository.findById(userId).isPresent() && usersRepository.existsById(loggedInUserId)) {
                SubscribedUser viewingUser = subscribedUserRepository.findById(userId).get();
                //Users loggedInUser = usersRepository.findById(loggedInUserId).get();
                //if the logged-in user is following a user.
                if(followingRepository.findByUserIdAndFollowingUserId(loggedInUserId, viewingUser.getId()) != null){
                    viewingUser.setUserFollowStatus(1);
                    //If the logged in user is following the user to view, but not approved.
                    if(!followingRepository.findByUserIdAndFollowingUserId(loggedInUserId, viewingUser.getId()).isApproved())
                        viewingUser.setUserFollowStatus(2);
                } else {
                    //Logged in user is not following this User.
                    viewingUser.setUserFollowStatus(0);
                }

                viewingUser.setRetailerProfile(retailerProfileRepository.findRetailerProfileByUserId(userId));
                viewingUser.setCompanyProfile(companyProfileRepository.findCompanyProfileByUserId(userId));
                viewingUser.setSupplierProfile(supplierProfileRepository.findSupplierProfileByUserId(userId));
                viewingUser.setServiceCenterProfile(serviceCenterProfileRepository.findServiceCenterProfileByUserId(userId));

                //UserProfileResponse up = new UserProfileResponse();
                //up.setSubscribedUser(viewingUser);
                //up.setRetailerProfile(retailerProfileRepository.findRetailerProfileByUserId(userId));
                return new ResponseEntity<>(viewingUser, HttpStatus.OK);
            }
        return new ResponseEntity<>("No user Found", HttpStatus.BAD_REQUEST);
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


        if(!usersRepository.findById(toBeSubscribedUser.getId()).isPresent())
            return new ResponseEntity<>(new MyNetworkSubscriptionResponse("User does not exist in MyDukan DB.", 400, null), HttpStatus.BAD_REQUEST);

        Users existingUser = usersRepository.findById(toBeSubscribedUser.getId()).get();


        toBeSubscribedUser.setId(existingUser.getUserId());
        toBeSubscribedUser.setContactNumber(existingUser.getContactNumber());
        toBeSubscribedUser.setName(existingUser.getName());
        toBeSubscribedUser.setEmail(existingUser.getEmail());
        //toBeSubscribedUser.setAccessToken(existingUser.getAccessToken());
        //toBeSubscribedUser.setGcmToken(existingUser.getGcmToken());

        //Get Zip Code
        SubscribedUser newSubscribedUser = subscribedUserRepository.save(toBeSubscribedUser);

        /*UserGroup zipCodeGroup = userGroupRepository.findDistinctByHashtag(toBeSubscribedUser.getZipCode().toString());
        List<SubscribedUser> toUpdateUserList = zipCodeGroup.getGroupMemberUsers();
        if(toUpdateUserList == null){
            toUpdateUserList = new ArrayList<>();
        }
        toUpdateUserList.add(newSubscribedUser);
        zipCodeGroup.setGroupMemberUsers(toUpdateUserList);
        userGroupRepository.save(zipCodeGroup);*/
        return new ResponseEntity<>(new MyNetworkSubscriptionResponse("User Successfully Subscribed", 200, newSubscribedUser), HttpStatus.OK);
    }

    @GetMapping("/init/existing")
    public ResponseEntity initSubscribeExistingUsers(){
        Iterable<Users> existingUsers =  usersRepository.findAll();
        String userId;

        for(Users u: existingUsers){
            SubscribedUser toSave = new SubscribedUser();
            userId = u.getUserId();
            toSave.setId(userId);
            toSave.setName(u.getName());
            toSave.setEmail(u.getEmail());
            toSave.setContactNumber(u.getContactNumber());
            subscribedUserRepository.save(toSave);
        }
        return new ResponseEntity<>("Loaded existing Users.", HttpStatus.OK);
    }

}
