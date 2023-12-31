package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.network.Hashtag;
import com.my.network.socialnetwork.model.network.HashtagRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.model.post.ordersbids.BidOnRFQRepository;
import com.my.network.socialnetwork.model.post.ordersbids.OrderOnPriceListRepository;
import com.my.network.socialnetwork.model.post.types.PostConditionRepository;
import com.my.network.socialnetwork.model.response.ErrorResponse;
import com.my.network.socialnetwork.notification.PushNotificationApi;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostVisibilityRepository postVisibilityRepository;

    @Autowired
    private PostConditionRepository postConditionRepository;

    @Autowired
    private BidOnRFQRepository bidOnRFQRepository;

    @Autowired
    private OrderOnPriceListRepository orderOnPriceListRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${post.edit-windows}")
    private int postEditWindowDays;

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private PostReportAbuseRepository postReportAbuseRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private PushNotificationApi pushNotificationApi;

    private int DEFAULT_PAGE = 0;
    private int DEFAULT_PAGE_SIZE = 20;


    /**
     * Create a new Post.
     * Each post can have a template
     */
    @PostMapping
    public ResponseEntity newPost(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        //String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (!subscribedUserRepository.findById(userId).isPresent()) {
            return new ResponseEntity<>("User is Not Subscribed", HttpStatus.BAD_REQUEST);
        }

        post.setUser(subscribedUserRepository.findById(userId).get());

        if (post.getIsPublicPost() == null || post.getIsPublicPost()) {
            //Post can be seen by everyone by default.
            post.setIsPublicPost(true);
            post.setIsFriendsOnlyPost(false);
            //Ignore PostVisibility
            post.setPostVisibility(null);

        } else if (!post.getIsPublicPost()) {
            //If isPostVisibleToUsers: true and publicPost: false and isFriendsOnlyPost: false
            //If user says false to public post and does not provide friends list, its a friends only post.
            if (post.getIsPostVisibleToUsers() && post.getIsPostVisibleToUsers() != null
                    && !post.getIsPublicPost() && !post.getIsFriendsOnlyPost()) {
                //User chooses to share the post with only few specific users
                //Showing to his friends might be optional.
                List<SubscribedUser> toSaveUserList = new ArrayList<>();
                for (SubscribedUser u : post.getPostVisibility().getVisibleToUsers()) {
                    if (subscribedUserRepository.findById(u.getId()).isPresent())
                        toSaveUserList.add(subscribedUserRepository.findById(u.getId()).get());
                }

                PostVisibility tpv = new PostVisibility();
                tpv.setVisibleToUsers(toSaveUserList);
                tpv.setPost(post);

                post.setPostVisibility(tpv);
                post.setIsFriendsOnlyPost(false);
            } else {
                post.setIsFriendsOnlyPost(true);
                post.setPostVisibility(null);
            }
        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "No Title Present"), HttpStatus.BAD_REQUEST);
        } else {
            post.setUniqueHandle(generateUniqueHandle(post.getTitle()));
        }

        //Get Hashtags.
        if (post.getHashtags() != null) {

            post.setHashtags(processHashtags(post.getHashtags()));
        }

        Post savedPost = postRepository.save(post);

        //Send Notifications to all his followers.
        pushNotificationApi.sendPostNotification(savedPost);

        return new ResponseEntity<>(savedPost, HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity getAllConditions() {
        return new ResponseEntity<>(postConditionRepository.findAll(), HttpStatus.OK);
    }

    /**
     * If the Current User is following profile user, all profile user posts can be seen
     */
    @GetMapping("/user/{profileOfUserId}")
    public ResponseEntity allPostsOfUser(@PathVariable String profileOfUserId,
                                         @RequestHeader(value = "Authorization") String authTokenHeader,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                         @RequestParam(value = "direct", defaultValue = "false") Boolean direct) {
        String currentUser = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Check if:
        // 1. Post is present.
        // 2. User is present.
        if (!subscribedUserRepository.findById(profileOfUserId).isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        //Current user is same as Profile User.
        if (profileOfUserId.matches(currentUser) && !direct) {
            return new ResponseEntity<>(postRepository.findAllPostsByFriendsProfile(profileOfUserId, PageRequest.of(page, size)), HttpStatus.OK);
        }

        //Is the current user following profileUser?
        Following isAuthUserFollowingUser = followingRepository.findByUserIdAndFollowingUserId(currentUser, profileOfUserId);
        if (isAuthUserFollowingUser != null && isAuthUserFollowingUser.isApproved() && !direct) {
            return new ResponseEntity<>(postRepository.findAllPostsByFriendsProfile(profileOfUserId, PageRequest.of(page, size)), HttpStatus.OK);
        }

        //Show all posts
        Page<Post> respPage;
        if (direct) {
            //If direct is switched to true then show only direct posts.
            respPage = postRepository.findDirectSharedPosts(currentUser, profileOfUserId, PageRequest.of(page, size));
        } else {
            //By Default show all Public Posts
            respPage = postRepository.findPublicPostsOfUser(profileOfUserId, PageRequest.of(page, size));
        }

        return new ResponseEntity<>(respPage, HttpStatus.OK);
    }

    @GetMapping("uq/{handle}")
    public ResponseEntity viewPostByHandle(@PathVariable String handle) {
        String encodedStr = "";
        /*try {
            //handle = URLDecoder.decode(handle.toLowerCase(), "UTF-8");
            //encodedStr = URLEncoder.encode(handle.toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return new ResponseEntity<>(postRepository.findByUniqueHandle(handle), HttpStatus.OK);


        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * A Post can be edited only between a 1 Day window.
     */
    @PatchMapping
    public ResponseEntity editTitleBodyOfAPost(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        if (!subscribedUserRepository.findById(userId).isPresent() ||
                post.getId() == null || !postRepository.findById(post.getId()).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Post postToUpdate = postRepository.findById(post.getId()).get();

        if (!userId.matches(postToUpdate.getUser().getId()) || !greaterThanNDays(postToUpdate.getCreateDate()))
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setBody(post.getBody());

        return new ResponseEntity<>(postRepository.save(postToUpdate), HttpStatus.OK);
    }


    @PutMapping("/price-list")
    public ResponseEntity editPriceList(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (post.getId() != null && postRepository.findById(post.getId()).isPresent()) {
            Post postToUpdate = postRepository.findById(post.getId()).get();

            if (!userId.matches(postToUpdate.getUser().getId()) || !greaterThanNDays(postToUpdate.getCreateDate()))
                return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN,
                        "You cannot make edits to this post."),
                        HttpStatus.FORBIDDEN);

            postToUpdate.setPriceLists(post.getPriceLists());

            return new ResponseEntity<>(postRepository.save(postToUpdate), HttpStatus.OK);
        }
        //No post exists return bad request.
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Either Post or User is invalid."),
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/rfq")
    public ResponseEntity editRFQ(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (post.getId() != null && postRepository.findById(post.getId()).isPresent()) {
            Post postToUpdate = postRepository.findById(post.getId()).get();

            if (!userId.matches(postToUpdate.getUser().getId()) || !greaterThanNDays(postToUpdate.getCreateDate()))
                return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN,
                        "You cannot make edits to this post."),
                        HttpStatus.FORBIDDEN);

            postToUpdate.setRequestForQuotations(post.getRequestForQuotations());

            return new ResponseEntity<>(postRepository.save(postToUpdate), HttpStatus.OK);
        }
        //No post exists return bad request.
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Either Post or User is invalid."),
                HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/schemes")
    public ResponseEntity editSchemes(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (post.getId() != null && postRepository.findById(post.getId()).isPresent()) {
            Post postToUpdate = postRepository.findById(post.getId()).get();

            if (!userId.matches(postToUpdate.getUser().getId()) || !greaterThanNDays(postToUpdate.getCreateDate()))
                return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN,
                        "You cannot make edits to this post."),
                        HttpStatus.FORBIDDEN);

            postToUpdate.setValueSchemes(post.getValueSchemes());
            postToUpdate.setQuantityPriceSchemes(post.getQuantityPriceSchemes());

            return new ResponseEntity<>(postRepository.save(postToUpdate), HttpStatus.OK);
        }
        //No post exists return bad request.
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Either Post or User is invalid."),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Check if 1. Post is present.
        // 2. User is present.
        // 3. The owner of the post and current user session is the same.
        if (!postRepository.existsById(postId) ||
                !subscribedUserRepository.existsById(userId) ||
                !postRepository.findById(postId).get().getUser().getId().equals(userId)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        System.out.println("Deleting the post...");

        postRepository.deleteById(postId);
        return new ResponseEntity<>(postRepository.feedOfUser(userId, PageRequest.of(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)), HttpStatus.OK);
    }

    @GetMapping(value = {"/{postId}", "all"})
    public ResponseEntity viewPosts(HttpServletRequest request, @PathVariable Optional<Long> postId,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "20") int size) {
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        if (postId.isPresent()) {
            //Individual post based on Id
            Post resPosts = postRepository.findById(postId.get()).get();

            //Set if the post has been liked by the current user.
            if (postLikeRepository.didUserLikeThisPost(userId, resPosts.getId()) != null) {
                resPosts.setLiked(true);
            }
            if (postReportAbuseRepository.didUserReportThisPost(userId, resPosts.getId()) != null) {
                resPosts.setReported(true);
            }
            return new ResponseEntity<>(resPosts, HttpStatus.OK);
        } else {
            //List of all posts based on userId
            Page<Post> resPosts = postRepository.findAllByPostsByUserId(userId, PageRequest.of(page, size));

            for (Post p : resPosts) {
                if (postLikeRepository.didUserLikeThisPost(userId, p.getId()) != null) {
                    p.setLiked(true);
                }
                if (postReportAbuseRepository.didUserReportThisPost(userId, p.getId()) != null) {
                    p.setReported(true);
                }
            }

            return new ResponseEntity<>(resPosts, HttpStatus.OK);
        }


    }

    @GetMapping(value = {"feed/v2"})
    public ResponseEntity unifiedFeed(HttpServletRequest request,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size) {
        String token = request.getHeader(tokenHeader);
        String currentUserId = jwtTokenUtil.getUserIdFromToken(token);

        List<Post> resPosts = postRepository.unifiedFeed(currentUserId, size, size * page);

        for (Post p : resPosts) {
            if (postLikeRepository.didUserLikeThisPost(currentUserId, p.getId()) != null) {
                p.setLiked(true);
            }
            //Did current user mark report.
            if (postReportAbuseRepository.didUserReportThisPost(currentUserId, p.getId()) != null) {
                p.setReported(true);
            }
            // Set the Follow Button of each Post.
            if (followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()) != null) {
                p.getUser().setUserFollowStatus(1);

                if (!followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()).isApproved())
                    p.getUser().setUserFollowStatus(2);
            } else {
                //The logged in user is not following the creator of Post.
                p.getUser().setUserFollowStatus(0);
            }

        }

        return new ResponseEntity<>(resPosts, HttpStatus.OK);
    }


    //Same Call for both like and unlike a post.
    @PutMapping(value = "/like/{postId}")
    public ResponseEntity likePost(@PathVariable Long postId, HttpServletRequest request, @RequestHeader(value = "Authorization") String authTokenHeader) {
        PostLike postLike = new PostLike();
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        //Check if the user liked already.
        if (postLikeRepository.didUserLikeThisPost(userId, postId) == null) {
            if (!postRepository.findById(postId).isPresent() ||
                    !subscribedUserRepository.findById(userId).isPresent())
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            postLike.setUser(subscribedUserRepository.findById(userId).get());
            postLike.setPost(postRepository.findById(postId).get());
            PostLike savedPostLike = postLikeRepository.save(postLike);
            updateLikesOfPost(postId);

            //Send Notification.
            pushNotificationApi.sendPostLikeNotification(savedPostLike);

            Post respPost = postRepository.findById(postId).get();
            if (postLikeRepository.didUserLikeThisPost(userId, respPost.getId()) != null) {
                respPost.setLiked(true);

            } else {
                respPost.setLiked(false);
            }

            return new ResponseEntity<>(respPost, HttpStatus.OK);

        } else {
            //Perform unlike since the user already liked the post
            postLike = postLikeRepository.didUserLikeThisPost(userId, postId);
            postLikeRepository.delete(postLike);
            updateLikesOfPost(postLike.getPost().getId());
            Post respPost = postRepository.findById(postId).get();
            if (postLikeRepository.didUserLikeThisPost(userId, respPost.getId()) != null) {
                respPost.setLiked(true);
            } else {
                respPost.setLiked(false);
            }
            return new ResponseEntity<>(respPost, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/unlike")
    public ResponseEntity unLikePost(@RequestBody PostLike postLike) {
        if (!postLikeRepository.findById(postLike.getId()).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        postLike = postLikeRepository.findById(postLike.getId()).get();
        postLikeRepository.delete(postLike);
        updateLikesOfPost(postLike.getPost().getId());
        Post responsePost = postRepository.findById(postLike.getId()).get();
        responsePost.setLiked(false);

        return new ResponseEntity<>(responsePost, HttpStatus.OK);
    }

    @GetMapping(value = "/likes/{postId}")
    public ResponseEntity likesOfPost(@PathVariable Long postId) {
        return new ResponseEntity<>(postLikeRepository.allLikesOfPost(postId), HttpStatus.OK);
    }

    @PutMapping(value = "/report/{postId}")
    public ResponseEntity reportPostAbuse(@RequestHeader(value = "Authorization") String authTokenHeader, @PathVariable Long postId) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Check if:
        // 1. Post is present.
        // 2. User is present.
        if (!postRepository.findById(postId).isPresent() ||
                !subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        SubscribedUser currentUser = subscribedUserRepository.findById(userId).get();
        Post existingPost = postRepository.findById(postId).get();

        postReportAbuseRepository.save(new PostReportAbuse(currentUser, existingPost));

        //Update Report Abuse Count.
        existingPost.setReportAbuseCount(postReportAbuseRepository.countOfAllByPost(postId));

        postRepository.save(existingPost);

        return new ResponseEntity<>(postRepository.feedOfUser(userId, PageRequest.of(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/search/{query}")
    public ResponseEntity searchPosts(@PathVariable String query) {
        //return new ResponseEntity<>(postSearch(query), HttpStatus.OK);
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    private List<Hashtag> processHashtags(List<Hashtag> hashtags) {
        List<Hashtag> savedHashtags = new ArrayList<>();
        for (Hashtag h : hashtags) {

            if (h.getHashtag() != null && !h.getHashtag().isEmpty()) {

                Hashtag existingHashtag = hashtagRepository.hashTagByHashtag(h.getHashtag());
                if (existingHashtag == null) {
                    savedHashtags.add(hashtagRepository.save(h));
                } else {
                    savedHashtags.add(existingHashtag);
                }
            }
        }
        return savedHashtags;
    }

    private void updateLikesOfPost(Long postId) {
        //Update likes of Post.
        Post post = postRepository.findById(postId).get();
        post.setLikeCount(postLikeRepository.likeCount(postId));
        postRepository.save(post);
    }

    private String generateUniqueHandle(String str) {

        String finalHandle = StringUtils.left(str, 50);
        try {
            finalHandle = URLEncoder.encode(finalHandle.toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finalHandle = finalHandle + "_" + RandomStringUtils.randomAlphanumeric(8);
        return finalHandle;
    }

    /*private List<Post> postSearch(String q) {

        EntityManager em = entityManagerFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        em.getTransaction().begin();

        // create native Lucene query using the query DSL
        // alternatively you can write the Lucene query using the Lucene query parser
        // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Post.class).get();
        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("title")
                .matching(q)
                .createQuery();

        // wrap Lucene query in a javax.persistence.Query
        javax.persistence.Query persistenceQuery =
                fullTextEntityManager.createFullTextQuery(query, Post.class);

        // execute search
        List<Post> result = persistenceQuery.getResultList();

        em.getTransaction().commit();
        em.close();

        return result;
    }*/

    /*private String getTokens(String userId) {
        StringBuilder builder = new StringBuilder();
        List<Following> followingRepositories = followingRepository.findFollowingByUserId(userId);
        SubscribedUser user = subscribedUserRepository.findById(userId).get();
        for (int i = 0; i < followingRepositories.size(); i++) {

            SubscribedUser subscribedUser = subscribedUserRepository.getSubscribedUser(userId);
            *//*if((followingRepositories.size() - 1) == i){
                builder.append(subscribedUser.getGcmToken());
            }
            else{*//*
            builder.append(subscribedUser.getGcmToken() + ", ");
//            }

        }
        builder.append(user.getGcmToken());

        return builder.toString();

    }*/

    private Boolean greaterThanNDays(Date d1) {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - d1.getTime();

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < postEditWindowDays;

    }

    @GetMapping(value = {"feed/v1"})
    public ResponseEntity newUserFeed(HttpServletRequest request,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size) {
        String token = request.getHeader(tokenHeader);
        String currentUserId = jwtTokenUtil.getUserIdFromToken(token);

        //The User of the jwt token is not present in the DB.
        if (!subscribedUserRepository.findById(currentUserId).isPresent())
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN, "User is not present."), HttpStatus.UNAUTHORIZED);

        if (size % 10 != 0)
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN, "Bad Page Size"), HttpStatus.BAD_REQUEST);

        //Page<Post> resPosts = postRepository.pageFeedOfUser(currentUserId, PageRequest.of(page, size));
        List<Post> resPosts = new ArrayList<>();


        //Total 20
        //Fetch 2 Promotion Post
        resPosts.addAll(postRepository.feedPostOfPromotions(currentUserId, PageRequest.of(page, 2)));
        //Fetch 3 Direct Post
        resPosts.addAll(postRepository.feedPostOfDirectShare(currentUserId, PageRequest.of(page, 3)));
        //Fetch 10 Friends post
        resPosts.addAll(postRepository.feedPostsOfFriends(currentUserId, PageRequest.of(page, 5)));
        //Fetch 5 Hashtag Posts
        resPosts.addAll(hashtagRepository.allPostsOfAHashtag("Hyderabad", PageRequest.of(page, 5)).getContent());
        //Fetch 5 Public posts
        resPosts.addAll(postRepository.feedPostsOfPublic(currentUserId, PageRequest.of(page, 10)));


        for (Post p : resPosts) {
            if (postLikeRepository.didUserLikeThisPost(currentUserId, p.getId()) != null) {
                p.setLiked(true);
            }
            //Did current user mark report.
            if (postReportAbuseRepository.didUserReportThisPost(currentUserId, p.getId()) != null) {
                p.setReported(true);
            }
            // Set the Follow Button of each Post.
            if (followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()) != null) {
                p.getUser().setUserFollowStatus(1);

                if (!followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()).isApproved())
                    p.getUser().setUserFollowStatus(2);
            } else {
                //The logged in user is not following the creator of Post.
                p.getUser().setUserFollowStatus(0);
            }

        }

        return new ResponseEntity<>(resPosts, HttpStatus.OK);
    }

    @GetMapping(value = {"/feed"})
    public ResponseEntity userFeed(HttpServletRequest request,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "20") int size) {
        String token = request.getHeader(tokenHeader);
        String currentUserId = jwtTokenUtil.getUserIdFromToken(token);

        //The User of the jwt token is not present in the DB.
        if (!subscribedUserRepository.findById(currentUserId).isPresent())
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "User is not present"), HttpStatus.UNAUTHORIZED);

        List<Post> resPosts = postRepository.feedOfUser(currentUserId, PageRequest.of(page, size));
        //List<Post> resPosts = new ArrayList<>();

        for (Post p : resPosts) {
            if (postLikeRepository.didUserLikeThisPost(currentUserId, p.getId()) != null) {
                p.setLiked(true);
            }
            //Did current user
            if (postReportAbuseRepository.didUserReportThisPost(currentUserId, p.getId()) != null) {
                p.setReported(true);
            }
            // Set the Follow Button of each Post.
            if (followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()) != null) {
                p.getUser().setUserFollowStatus(1);

                if (!followingRepository.findByUserIdAndFollowingUserId(currentUserId, p.getUser().getId()).isApproved())
                    p.getUser().setUserFollowStatus(2);
            } else {
                //The logged in user is not following the creator of Post.
                p.getUser().setUserFollowStatus(0);
            }

        }

        return new ResponseEntity<>(resPosts, HttpStatus.OK);
    }

}