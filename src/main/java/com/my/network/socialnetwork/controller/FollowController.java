package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.myrequest.FollowPhoneContact;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.response.ErrorResponse;
import com.my.network.socialnetwork.model.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    /*
     * Suggest which user to follow.
     * When the user is in his homepage the user gets
     * TODO: 1. get userId from jwt
     * TODO: 2. Recommendations will plugin here.
     * */
    @GetMapping("/suggest")
    public ResponseEntity suggestUsersToFollow(@PathVariable String userId) {
        return new ResponseEntity<>(subscribedUserRepository.suggestUsersToFollow(userId), HttpStatus.OK);
    }

    /**
     * Follow a friend:
     * Params: UserId, FollowUserId
     */
    @PostMapping("/")
    public ResponseEntity followUser(@Valid @RequestBody Following following, @RequestHeader(value = "Authorization") String authTokenHeader, Boolean defaultAccept) {

        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Boolean userIsValid = subscribedUserRepository.findById(currentUserId).isPresent();
        Optional<SubscribedUser> followUser = subscribedUserRepository.findById(following.getFollowingUser().getId());
        Optional<SubscribedUser> currentUser = subscribedUserRepository.findById(currentUserId);

        //Validate follow user & user's existence.
        if (!currentUser.isPresent() || !followUser.isPresent() ||
                currentUserId.equals(following.getFollowingUser().getId()) )
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid User to Follow"), HttpStatus.BAD_REQUEST);

        //Return bad request if already user is following user.
        Following isCurrentUserFollowingUser = followingRepository.findByUserIdAndFollowingUserId(currentUserId, followUser.get().getId());
        if (isCurrentUserFollowingUser != null)
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK, "You are already following this user."), HttpStatus.OK);

        /*
         * Get from jwt token and set following user Id.
         * following.setCreatedBy(TODO Get the user from jwt token);
         * */
        following.setUser(currentUser.get());
        following.setFollowingUser(followUser.get());

        // Check if the user's isOpenFollow is true.
        following.setApproved(false);
        if (following.getFollowingUser().isOpenFollow() || defaultAccept)
            following.setApproved(true);

        Following savedFollow = followingRepository.save(following);

        // Update both users following and followers count.
        if(savedFollow != null){
            //Current User's Following Count gets incremented.
            currentUser.get().setFollowingCount(followingRepository.countOfFollowingByUserId(currentUserId));
            //Following User gets a follower count incremented.
            followUser.get().setFollowersCount(followingRepository.countOfFollowersByUserId(followUser.get().getId()));
            subscribedUserRepository.save(currentUser.get());
            subscribedUserRepository.save(followUser.get());
        }

        return new ResponseEntity<>(savedFollow, HttpStatus.CREATED);
    }

    /**
     * Un-follow a friend:
     * Params: UserId, FollowerUserId
     */
    @DeleteMapping("/")
    public ResponseEntity unfollowUser(@Valid @RequestBody Following following, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        Optional<Following> optionalFollowing = followingRepository.findById(following.getId());
        if(!optionalFollowing.isPresent() || !optionalFollowing.get().getUser().getId().equals(currentUserId)){
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Not a valid UnFollow request."), HttpStatus.BAD_REQUEST);
        }

        followingRepository.deleteById(following.getId());

        updateFollowCounts(following, currentUserId);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * List of Pending friend requests current User can accept.
     */
    @GetMapping("/accept")
    public ResponseEntity getFollowRequests(@RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        return new ResponseEntity<>(followingRepository.followRequestsToApproveByUser(userId), HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity approveFollowRequest(@RequestBody Following following, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<Following> opF = followingRepository.findById(following.getId());

        if(!currentUserId.equals(following.getUser().getId()) || !opF.isPresent()) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN, "You cannot accept this request.", "/follow/accept")
                    , HttpStatus.FORBIDDEN);
        }

        following = opF.get();
        following.setApproved(true);
        Following f = followingRepository.save(following);

        updateFollowCounts(following, currentUserId);

        return new ResponseEntity<>(f, HttpStatus.OK);
    }

    private void updateFollowCounts(Following following, String currentUserId){
        Optional<SubscribedUser> followUser = subscribedUserRepository.findById(following.getFollowingUser().getId());
        Optional<SubscribedUser> currentUser = subscribedUserRepository.findById(currentUserId);


        currentUser.get().setFollowingCount(followingRepository.countOfFollowingByUserId(currentUserId));
        //Following User gets a follower count incremented.
        followUser.get().setFollowersCount(followingRepository.countOfFollowersByUserId(followUser.get().getId()));
        subscribedUserRepository.save(currentUser.get());
        subscribedUserRepository.save(followUser.get());
    }

    /**
     * List of All Current User's friend requests yet to be approved.
     */
    @GetMapping("/pending")
    public ResponseEntity pendingFollowRequests(@RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        return new ResponseEntity<>(followingRepository.pendingFollowingRequest(userId), HttpStatus.OK);
    }

    /**
     * List Users current user is Following.
     * Params: userId
     */
    @GetMapping(value={"/following/", "/following/{viewUserId}"})
    public ResponseEntity listOfFollowingUsers(@PathVariable Optional<String> viewUserId, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //View Following List of others profile.
        if(viewUserId.isPresent()){
            userId = viewUserId.get();
        }
        return new ResponseEntity<>(followingRepository.findFollowingByUserId(userId), HttpStatus.OK);
    }

    /**
     * List Users who are following current user.
     * Params: userId
     */
    @GetMapping("/followers/{viewUserId}")
    public ResponseEntity listOfFollowers(@PathVariable Optional<String> viewUserId, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //View Followers of others profile.
        if(viewUserId.isPresent()){
            userId = viewUserId.get();
        }
        return new ResponseEntity<>(followingRepository.findFollowersByUserId(userId), HttpStatus.OK);
    }

    /**
     * Pass the list of Phone Numbers and map them to Subscribed users and Current User starts following.
     */
    @PostMapping("/phone/sync")
    public ResponseEntity followContactNumbersFromPhone(@RequestBody List<FollowPhoneContact> phoneContactList,
                                                        @RequestHeader(value = "Authorization") String authTokenHeader) {

        final String validEmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        int countOfProfilesAdded = 0;


        for (FollowPhoneContact followPhoneContact : phoneContactList) {

            List<SubscribedUser> toFollowFullList = null;

            //First preference to phone number
            if (followPhoneContact.getPhoneNumber() == null ||
                    !followPhoneContact.getPhoneNumber().isEmpty() ||
                    cleanPhoneNumber(followPhoneContact.getPhoneNumber()) != null) {
                //Valid Phone Number. Get Subscribed user from the phone number.
                toFollowFullList = subscribedUserRepository.getSubscribedUserByPhoneNumber(followPhoneContact.getPhoneNumber());

            } else if (followPhoneContact.getEmail() == null ||
                    !followPhoneContact.getEmail().isEmpty() ||
                    followPhoneContact.getEmail().matches(validEmailRegex)) {
                //Check for a valid email.
                toFollowFullList = subscribedUserRepository.getSubscribedUserByEmail(followPhoneContact.getEmail());
            }

            if (toFollowFullList != null) {
                for(SubscribedUser toFollowUser: toFollowFullList){
                    //System.out.println("Found User: " + toFollowUser.getContactNumber() +": "+ toFollowUser.getName());
                    ResponseEntity r = followUser(new Following(toFollowUser), authTokenHeader, true);
                    if (r.getStatusCode() == HttpStatus.CREATED)
                        countOfProfilesAdded++;
                }

            }
        }

        return new ResponseEntity<>(new SuccessResponse(HttpStatus.CREATED,
                String.valueOf(countOfProfilesAdded) + "   Profiles have been added."),
                HttpStatus.CREATED);
    }

    private String cleanPhoneNumber(String toCleanPhoneNumber) {
        String cleanedNumber = toCleanPhoneNumber;
        if (toCleanPhoneNumber.startsWith("+91")) {
            cleanedNumber = toCleanPhoneNumber.substring("+91".length());
        }
        if (toCleanPhoneNumber.startsWith("0")) {
            cleanedNumber = toCleanPhoneNumber.substring("0".length());
        }

        cleanedNumber = cleanedNumber.replaceAll("[\\D]", "");

        if (cleanedNumber.matches("[0-9]+") && cleanedNumber.length() == 10) {
            return cleanedNumber;
        }

        return null;

    }
}
