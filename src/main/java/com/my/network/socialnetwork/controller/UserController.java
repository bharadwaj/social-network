package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.auth.model.UserAddress;
import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.auth.model.profiles.*;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.network.Hashtag;
import com.my.network.socialnetwork.model.network.HashtagRepository;
import com.my.network.socialnetwork.model.network.group.UserGroupRepository;
import com.my.network.socialnetwork.model.pincode.District;
import com.my.network.socialnetwork.model.pincode.DistrictRepository;
import com.my.network.socialnetwork.model.post.PostLikeRepository;
import com.my.network.socialnetwork.model.response.ErrorResponse;
import com.my.network.socialnetwork.model.response.MyNetworkSubscriptionResponse;
import com.my.network.socialnetwork.model.response.SuccessResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private RetailerProfileRepository retailerProfileRepository;

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private SupplierProfileRepository supplierProfileRepository;

    @Autowired
    private ServiceCenterProfileRepository serviceCenterProfileRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @GetMapping("/")
    public ResponseEntity viewCurrentUserProfile(@RequestHeader(value = "Authorization") String authTokenHeader) {
        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        return new ResponseEntity<>(subscribedUserRepository.findById(currentUserId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity viewAllUser(@RequestHeader(value = "Authorization") String authTokenHeader) {
        return new ResponseEntity<>(subscribedUserRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("suggestions")
    public ResponseEntity suggestUsersToFollowBasedOnHashtag(@RequestHeader(value = "Authorization") String authTokenHeader,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "20") int size) {

        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> optCurrentUser = subscribedUserRepository.findById(currentUserId);

        if (!optCurrentUser.isPresent() || optCurrentUser.get().getZipCode() == null) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Not a Valid User or Zipcode is not Present for this User", "/user/suggestion"), HttpStatus.BAD_REQUEST);
        }

        SubscribedUser currentUser = optCurrentUser.get();


        Page<SubscribedUser> responseSubscribedUsers = null;

        //Ex: 500010 gets converted to 5 * 100000 = 500000
        //get state and district hashtag from zipcode.
        District district = districtRepository.findDistrictByPincode(currentUser.getZipCode());


        if (currentUser.getUserMstTypeId() <= 2) {
            //Retailer
            responseSubscribedUsers = subscribedUserRepository.suggestionsForRetailersByHashtag(district.getDistrictName().replace(" ", ""),currentUser.getId(), PageRequest.of(page, size));
        } else if (currentUser.getUserMstTypeId() == 3) {
            //Supplier
            responseSubscribedUsers = subscribedUserRepository.suggestionsForSuppliersByHashtag(district.getDistrictName().replace(" ", ""),currentUser.getId(), PageRequest.of(page, size));
        } else {
            //By Default Show Company Profile Suggestions.
            responseSubscribedUsers = subscribedUserRepository.suggestionsForCompanyByHashtag(district.getDistrictName().replace(" ", ""),currentUser.getId(), PageRequest.of(page, size));

        }

        /*for (SubscribedUser su : responseSubscribedUsers) {
            switch (su.getUserMstTypeId()) {
                case 2:
                    su.setRetailerProfile(retailerProfileRepository.findRetailerProfileByUserId(su.getId()));
                    break;
                case 3:
                    su.setSupplierProfile(supplierProfileRepository.findSupplierProfileByUserId(su.getId()));
                    break;
                case 4:
                    su.setCompanyProfile(companyProfileRepository.findCompanyProfileByUserId(su.getId()));
                    break;
                case 5:
                    su.setServiceCenterProfile(serviceCenterProfileRepository.findServiceCenterProfileByUserId(su.getId()));
                    break;
            }
        }*/

        return new ResponseEntity<>(responseSubscribedUsers,
                HttpStatus.OK);
    }

    @GetMapping("/suggestions/all")
    public ResponseEntity suggestAllUsersToFollow(@RequestHeader(value = "Authorization") String authTokenHeader,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "20") int size) {

        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        Page<SubscribedUser> responseSubscribedUsers = subscribedUserRepository.findAll(PageRequest.of(page, size));

        return new ResponseEntity<>(responseSubscribedUsers, HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity viewProfileOfAUser(@PathVariable String userId, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String loggedInUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        //We want to see the profile of somebody else other than loggedIn User.
        //If user is valid and the logged in user is also valid.
        if (subscribedUserRepository.findById(userId).isPresent() && usersRepository.existsById(loggedInUserId)) {
            SubscribedUser viewingUser = subscribedUserRepository.findById(userId).get();
            //Users loggedInUser = usersRepository.findById(loggedInUserId).get();
            //if the logged-in user is following a user.
            if (followingRepository.findByUserIdAndFollowingUserId(loggedInUserId, viewingUser.getId()) != null) {
                viewingUser.setUserFollowStatus(1);
                //If the logged in user is following the user to view, but not approved.
                if (!followingRepository.findByUserIdAndFollowingUserId(loggedInUserId, viewingUser.getId()).isApproved())
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

    @PutMapping("profile/photo")
    public ResponseEntity updateProfilePhoto(@RequestBody SubscribedUser su, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> optUser = subscribedUserRepository.findById(currentUserId);

        if (!optUser.isPresent() || su.getProfilePhotoUrl() == null || su.getProfilePhotoUrl().isEmpty())
            return new ResponseEntity<>("Invalid Image URL or SubsbscribedUser", HttpStatus.BAD_REQUEST);

        SubscribedUser toSave = optUser.get();
        toSave.setProfilePhotoUrl(su.getProfilePhotoUrl());
        SubscribedUser updatedProfile = subscribedUserRepository.save(toSave);

        return new ResponseEntity<>(new SuccessResponse(HttpStatus.OK, "Successfully Updated Photo",
                updatedProfile, "/user/profile/photo"), HttpStatus.OK);
    }

    @GetMapping("search/name/{userName}")
    public ResponseEntity searchByUserName(@PathVariable String userName,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "20") int size) {
        return new ResponseEntity<>(subscribedUserRepository.subscribedUsersLikeUsername(userName, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("search/phone/{phoneNumber}")
    public ResponseEntity searchByUserPhoneNumber(@PathVariable String phoneNumber,
                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "size", defaultValue = "20") int size) {
        return new ResponseEntity<>(subscribedUserRepository.subscribedUsersLikePhoneNumber(phoneNumber, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("search/email/{email}")
    public ResponseEntity searchByUserEmail(@PathVariable String email,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "20") int size) {
        return new ResponseEntity<>(subscribedUserRepository.subscribedUsersLikeEmail(email, PageRequest.of(page, size)), HttpStatus.OK);
    }


    /**
     * Useful for populating the activity of a user or notifications.
     */
    @GetMapping("/likes/{userId}")
    public ResponseEntity allLikesOfUser(@PathVariable String userId) {
        return new ResponseEntity<>(postLikeRepository.allLikesOfUser(userId), HttpStatus.OK);
    }

    /**
     * Suggestions of Hashtags of the user.
     */
    @GetMapping("/hashtags/following")
    public ResponseEntity allfollowingHashtagsOfUser(@RequestHeader(value = "Authorization") String authTokenHeader) {
        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> optUser = subscribedUserRepository.findById(currentUserId);
        if (!optUser.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        return new ResponseEntity<>(districtRepository.findDistrictByPincode(optUser.get().getZipCode()), HttpStatus.OK);
    }


    @PostMapping(value = "/new")
    public ResponseEntity createUserClass(@RequestBody SubscribedUser toBeSubscribedUser) {

        if (!usersRepository.findById(toBeSubscribedUser.getId()).isPresent())
            return new ResponseEntity<>(new MyNetworkSubscriptionResponse("User does not exist in MyDukan DB.", 400, null), HttpStatus.BAD_REQUEST);

        Users existingUser = usersRepository.findById(toBeSubscribedUser.getId()).get();

        //Get Zip Code
        SubscribedUser mappedUser = mapUserToSubscribedUser(existingUser);
        SubscribedUser newSubscribedUser = subscribedUserRepository.save(mappedUser);

        return new ResponseEntity<>(new MyNetworkSubscriptionResponse("User Successfully Subscribed", 200, newSubscribedUser), HttpStatus.OK);
    }

    @GetMapping("/init/existing")
    public ResponseEntity initSubscribeExistingUsers() {
        Iterable<Users> existingUsers = usersRepository.findAll(PageRequest.of(0, 100));

        for (int i = 1; i <= ((Page<Users>) existingUsers).getTotalPages(); i++) {
            for (Users u : existingUsers) {
                subscribedUserRepository.save(mapUserToSubscribedUser(u));
            }
            existingUsers = usersRepository.findAll(PageRequest.of(i, 100));
        }

        return new ResponseEntity<>("Loaded existing Users.", HttpStatus.OK);
    }

    private int firstDigit(int n) {
        // Find total number of digits - 1
        int digits = (int) (Math.log10(n));

        // Find first digit
        n = (int) (n / (int) (Math.pow(10, digits)));

        // Return first digit
        return n;
    }

    private SubscribedUser mapUserToSubscribedUser(Users u) {
        SubscribedUser toSave = new SubscribedUser();
        String userId = u.getUserId();
        //User Details, Name, UserName, Email, Contact Number
        toSave.setId(userId);
        toSave.setName(u.getName());
        toSave.setUserName(u.getUserName());
        toSave.setEmail(u.getEmail());
        toSave.setContactNumber(u.getContactNumber());

        //Default Open Follow, Message and Profile
        toSave.setOpenFollow(true);
        toSave.setOpenMessage(true);
        toSave.setOpenProfile(true);

        //Default Promotion Status is 0
        toSave.setPromotionFactor(0);

        //Set Address: City, ZipCode, State
        if (u.getUserAddresses() != null && u.getUserAddresses().size() > 0) {
            UserAddress ua = u.getUserAddresses().get(0);
            try {
                if (ua.getCity() != null && !ua.getCity().isEmpty()) {
                    toSave.setLocation(ua.getCity());
                } else {
                    toSave.setLocation(ua.getState());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (ua.getPincode() != null && !ua.getPincode().isEmpty()) {
                    String onlyNumbers = ua.getPincode().replaceAll("[^0-9]", "");
                    Integer zipCode = Integer.parseInt(onlyNumbers);
                    toSave.setZipCode(zipCode);
                } else {
                    //By Default Set the User up in Delhi Area.
                    toSave.setZipCode(110001);
                }

            } catch (Exception e) {
                e.printStackTrace();
                //By Default Set the User up in Delhi Area.
                toSave.setZipCode(110001);
            }

            try {
                toSave.setState(ua.getState());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //By Default Set the User up in Delhi Area.
            toSave.setZipCode(110001);
        }

        //Process Hashtags. Add all hashtags pertaining to a district.
        District d = districtRepository.findDistrictByPincode(toSave.getZipCode());
        if (d != null) {
            try {
                ArrayList<Hashtag> toAdd = new ArrayList<>();
                //Add District Hashtag
                toAdd.add(hashtagRepository.hashTagByHashtag(d.getDistrictName().replace(" ", "")));
                //Add to State Hashtag
                toAdd.add(hashtagRepository.hashTagByHashtag(d.getState().getName().replace(" ", "")));
                //Save Both state and District Hashtag.
                toSave.setHashtags(toAdd);
            } catch (Exception e) {
                logger.error("User " + toSave.getId() + ", Does not have valid location and pincode.");
                e.printStackTrace();
            }
        }

        //Set Profiles Metadata.
        if (u.getUsersTypes() != null && u.getUsersTypes().size() > 0) {
            //Set User MST Types
            toSave.setUserMstTypeId(u.getUsersTypes().get(0).getMstType().getTypeId());
            toSave.setUserMstTypeName(u.getUsersTypes().get(0).getMstType().getTypeName());
            switch (toSave.getUserMstTypeId()) {
                case 2:
                    RetailerProfile retailerProfileByUserId = retailerProfileRepository.findRetailerProfileByUserId(toSave.getId());
                    if (retailerProfileByUserId != null) {
                        //Set User TypeRoles
                        toSave.setUserTypeRoleName(retailerProfileByUserId.getTypeRoleId().getName());
                        //Set Main Title
                        toSave.setMainTitle(retailerProfileByUserId.getTypeRoleId().getName());
                        //Set Sub Title
                        toSave.setSubTitle(retailerProfileByUserId.getOutletName());
                    }
                    break;
                case 3:
                    SupplierProfile supplierProfileByUserId = supplierProfileRepository.findSupplierProfileByUserId(toSave.getId());
                    if (supplierProfileByUserId != null) {
                        //Set User TypeRoles
                        toSave.setUserTypeRoleName(supplierProfileByUserId.getTypeRoleId().getName());
                        //Set Main Title
                        toSave.setMainTitle(supplierProfileByUserId.getTypeRoleId().getName());
                        //Set Sub Title
                        toSave.setSubTitle(supplierProfileByUserId.getFirstName());
                    }
                    break;
                case 4:
                    CompanyProfile companyProfileByUserId = companyProfileRepository.findCompanyProfileByUserId(toSave.getId());
                    if (companyProfileByUserId != null) {
                        //Set User TypeRoles
                        toSave.setUserTypeRoleName(companyProfileByUserId.getTypeRoleId().getName());
                        //Set Main Title
                        toSave.setMainTitle(companyProfileByUserId.getDesignation());
                        //Set Sub Title
                        toSave.setSubTitle(companyProfileByUserId.getTypeRoleId().getName());
                    }
                    break;
                case 5:
                    ServiceCenterProfile serviceCenterProfileByUserId = serviceCenterProfileRepository.findServiceCenterProfileByUserId(toSave.getId());
                    if (serviceCenterProfileByUserId != null) {
                        //Set User TypeRoles
                        toSave.setUserTypeRoleName("");
                        //Set Main Title
                        toSave.setMainTitle(serviceCenterProfileByUserId.getFirmName());
                        //Set Sub Title
                        toSave.setSubTitle("");
                    }
                    break;

            }//End of Switch Case


        } else {
            //Default In case the above account did not have.
            toSave.setUserMstTypeId(2);
            toSave.setUserTypeRoleName("Retailer");
        }
        return toSave;
    }

}
