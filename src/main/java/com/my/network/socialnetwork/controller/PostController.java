package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.socialnetwork.model.*;
import com.my.network.socialnetwork.model.post.*;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
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


    /**
     * Create a new Post.
     * Each post can have a template
     *
     */
    //TODO Read user from jwt
    @PostMapping()
    public ResponseEntity newPost(@RequestBody Post post, @RequestHeader(value= "Authorization") String authTokenHeader) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        //String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

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
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity getAllConditions(){
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

    @DeleteMapping()
    public ResponseEntity deletePost(@RequestBody Post post) {
        return new ResponseEntity(HttpStatus.MULTI_STATUS);
    }

    //TODO remove userId and read the UserId from jwt.
    @GetMapping(value = {"/{postId}", "all"})
    public ResponseEntity viewPosts(HttpServletRequest request, @PathVariable Optional<Long> postId) {
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        if (postId.isPresent()) {
            return new ResponseEntity<>(postRepository.findById(postId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(postRepository.findAllByPostsByUserId(userId), HttpStatus.OK);
        }


    }

    @GetMapping(value = {"/feed"})
    public ResponseEntity userFeed(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        return new ResponseEntity<>(postRepository.feedOfUser(userId), HttpStatus.OK);
    }

    //TODO read current user from jwt.
    @PostMapping(value = "/like/{postId}")
    public ResponseEntity likePost(@PathVariable Long postId,HttpServletRequest request) {
        PostLike postLike = new PostLike();
        String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        if (!postRepository.findById(postId).isPresent() &&
                !subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        postLike.setUser(subscribedUserRepository.findById(userId).get());
        postLike.setPost(postRepository.findById(postId).get());
        postLikeRepository.save(postLike);
        updateLikesOfPost(postId);

        return new ResponseEntity<>(postRepository.findById(postId), HttpStatus.OK);
    }

    //TODO check if the user has permission to unlike.
    @PostMapping(value = "/unlike/{userId}")
    public ResponseEntity unLikePost(@RequestBody PostLike postLike, @PathVariable Long userId) {
        if (!postLikeRepository.findById(postLike.getId()).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        postLike = postLikeRepository.findById(postLike.getId()).get();
        postLikeRepository.delete(postLike);
        updateLikesOfPost(postLike.getPost().getId());
        return new ResponseEntity<>(HttpStatus.OK);
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
        return new ResponseEntity<>(postSearch(query), HttpStatus.OK);
    }



    void updateLikesOfPost(Long postId) {
        //Update likes of Post.
        Post post = postRepository.findById(postId).get();
        post.setLikeCount(postLikeRepository.likeCount(postId));
        postRepository.save(post);
    }

    private List<Post> postSearch(String q) {

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
    }

}