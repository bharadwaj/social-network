package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.storage.PushNotificationApi;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    PostVisibilityRepository postVisibilityRepository;

    @Autowired
    PostConditionRepository postConditionRepository;

    @Autowired
    BidOnRFQRepository bidOnRFQRepository;

    @Autowired
    OrderOnPriceListRepository orderOnPriceListRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    SubscribedUserRepository subscribedUserRepository;

    @Autowired
    FollowingRepository followingRepository;


    /**
     * Create a new Post.
     * Each post can have a template
     */
    //TODO Read user from jwt
    @PostMapping()
    public ResponseEntity newPost(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        //String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if(!subscribedUserRepository.findById(userId).isPresent()){
            return new ResponseEntity<>("User is Not Subscribed", HttpStatus.BAD_REQUEST);
        }

        post.setUser(subscribedUserRepository.findById(userId).get());


        //Post can be seen by everyone.
        if (post.getPostVisibility() == null) {
            //User Chose to share the post with no one.
            if (post.getIsPublicPost() == null) {
                post.setIsPublicPost(true);
            } else {

            }

        } else {
            //List<PostVisibility> toSavePostVisibility = new
            //Assign userClass for every Id.
            List<SubscribedUser> toSaveUserList = new ArrayList<>();
            for (SubscribedUser u : post.getPostVisibility().getVisibleToUsers()) {
                toSaveUserList.add(subscribedUserRepository.findById(userId).get());
            }
        }

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            post.setUniqueHandle(generateUniqueHandle(post.getTitle()));
        }

        String tokens = getTokens(userId);
        PushNotificationApi notificationApi = new PushNotificationApi();
        String message = "";
        if(post.getPriceLists() != null && post.getPriceLists().size() > 0){
            message = post.getUser().getName() + " has posted PriceList";
        }
        else if(post.getRequestForQuotations() != null && post.getRequestForQuotations().size() > 0){
            message = post.getUser().getName() + "has posted RFQ";
        }
        else if(post.getImageUrl() != null && !post.getImageUrl().equalsIgnoreCase("")){
            message = post.getUser().getName() + "has posted Image";
        }
        else{
            message = post.getUser().getName() + "has posted in MyDukan";
        }
        //notificationApi.getEmployees(userId, "MyDukan Post Notification", message);

        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity getAllConditions() {
        return new ResponseEntity<>(postConditionRepository.findAll(), HttpStatus.OK);
    }


    @PutMapping("/price-template")
    public ResponseEntity editPriceList(@RequestBody Post post) {
        if (postRepository.findById(post.getId()).isPresent()) {
            Post postToSave = postRepository.findById(post.getId()).get();
            postToSave.setPriceLists(post.getPriceLists());

            return new ResponseEntity<>(postRepository.save(postToSave), HttpStatus.OK);
        }
        //No post exists return bad request.
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@RequestHeader(value= "Authorization") String authTokenHeader, @PathVariable Long postId) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Check if 1. Post is present.
        // 2. User is present.
        // 3. The owner of the post and current user session is the same.
        if(!postRepository.findById(postId).isPresent() &&
                !subscribedUserRepository.findById(userId).isPresent() &&
                !postRepository.findById(postId).get().getUser().getId().equals(userId))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        postRepository.deleteById(postId);
        return new ResponseEntity<>(postRepository.feedOfUser(userId), HttpStatus.OK);
    }

    //TODO remove userId and read the UserId from jwt.
    @GetMapping(value = {"/{postId}", "all"})
    public ResponseEntity viewPosts(HttpServletRequest request, @PathVariable Optional<Long> postId) {
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        if (postId.isPresent()) {
            //Individual post based on Id
            Post resPosts = postRepository.findById(postId.get()).get();

            //Set if the post has been liked by the current user.
            if (postLikeRepository.didUserLikeThisPost(userId, resPosts.getId()) != null) {
                resPosts.setLiked(true);
            }
            return new ResponseEntity<>(resPosts, HttpStatus.OK);
        } else {
            //List of all posts based on userId
            List<Post> resPosts = postRepository.findAllByPostsByUserId(userId);

            for (Post p : resPosts) {
                if (postLikeRepository.didUserLikeThisPost(userId, p.getId()) != null) {
                    p.setLiked(true);
                }
            }
            return new ResponseEntity<>(resPosts, HttpStatus.OK);
        }


    }

    @GetMapping("handle/{handle}")
    public ResponseEntity viewPostByHandle(@PathVariable String handle) {
        String encodedStr = "";
        try {
            handle = URLDecoder.decode(handle.toLowerCase(), "UTF-8");
            encodedStr = URLEncoder.encode(handle.toLowerCase(), "UTF-8");
            return new ResponseEntity<>(postRepository.findByUniqueHandle(encodedStr), HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = {"/feed"})
    public ResponseEntity userFeed(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        List<Post> resPosts = postRepository.feedOfUser(userId);

        for (Post p : resPosts) {
            if (postLikeRepository.didUserLikeThisPost(userId, p.getId()) != null) {
                p.setLiked(true);
            }
            // Set the Follow Button of each Post.
            if(followingRepository.findByUserIdAndFollowingUserId(userId, p.getUser().getId())!= null) {
                p.getUser().setUserFollowStatus(1);

                if(!followingRepository.findByUserIdAndFollowingUserId(userId, p.getUser().getId()).isApproved())
                    p.getUser().setUserFollowStatus(2);
            }else{
                //The logged in user is not following the creator of Post.
                p.getUser().setUserFollowStatus(0);
            }

        }

        return new ResponseEntity<>(resPosts, HttpStatus.OK);
    }

    //TODO read current user from jwt.
    @PatchMapping(value = "/like/{postId}")
    public ResponseEntity likePost(@PathVariable Long postId, HttpServletRequest request) {
        PostLike postLike = new PostLike();
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        //Check if the user liked already.
        if (postLikeRepository.didUserLikeThisPost(userId, postId) == null) {
            if (!postRepository.findById(postId).isPresent() &&
                    !subscribedUserRepository.findById(userId).isPresent())
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            postLike.setUser(subscribedUserRepository.findById(userId).get());
            postLike.setPost(postRepository.findById(postId).get());
            postLikeRepository.save(postLike);
            updateLikesOfPost(postId);

            Post respPost = postRepository.findById(postId).get();
            if (postLikeRepository.didUserLikeThisPost(userId, respPost.getId()) != null) {
                respPost.setLiked(true);
                PushNotificationApi notificationApi = new PushNotificationApi();
                notificationApi.getEmployees(token, "MyDukan Notification", respPost.getUser().getName()+" has liked your post.");
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

    //TODO check if the user has permission to unlike.
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

    @PostMapping(value = "/report")
    public ResponseEntity reportPostAbuse(@RequestBody Post post) {
        if (postRepository.findById(post.getId()).isPresent()) {
            post = postRepository.findById(post.getId()).get();
            post.setReportAbuse(post.getReportAbuse() + 1);
            return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/search/{query}")
    public ResponseEntity searchPosts(@PathVariable String query) {
        //return new ResponseEntity<>(postSearch(query), HttpStatus.OK);
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    void updateLikesOfPost(Long postId) {
        //Update likes of Post.
        Post post = postRepository.findById(postId).get();
        post.setLikeCount(postLikeRepository.likeCount(postId));
        postRepository.save(post);
    }

    String generateUniqueHandle(String str) {

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

    private String getTokens(String userId) {
        String tokens = "";
        StringBuilder builder = new StringBuilder();
        List<Following> followingRepositories = followingRepository.findFollowingByUserId(userId);
        for(int i=0; i<followingRepositories.size(); i++){

            SubscribedUser subscribedUser = subscribedUserRepository.getSubscribedUser(userId);
            if((followingRepositories.size() - 1) == i){
                builder.append(subscribedUser.getGcmToken());
            }
            else{
                builder.append(subscribedUser.getGcmToken() + ", ");
            }

        }

        return tokens = builder.toString();

    }

}