package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.*;
import com.my.network.socialnetwork.model.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserClassRepository userClassRepository;

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


    /**
     * Create a new Post.
     * Each post can have a template
     *
     */
    //TODO Read user from jwt
    @PostMapping("/{userId}")
    public ResponseEntity newPost(@RequestBody Post post, @PathVariable Long userId) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        post.setUser(userClassRepository.findById(userId).get());
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
            List<UserClass> toSaveUserList = new ArrayList<>();
            for (UserClass u : post.getPostVisibility().getVisibleToUsers()) {
                toSaveUserList.add(userClassRepository.findById(u.getId()).get());
            }
        }
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }

    @GetMapping("/conditions")
    public ResponseEntity getAllConditions(){
        return new ResponseEntity<>(postConditionRepository.findAll(), HttpStatus.OK);
    }


    @PutMapping("/price-template")
    public ResponseEntity editPost(@RequestBody Post post) {
        if (postRepository.findById(post.getId()).isPresent()) {
            Post postToSave = postRepository.findById(post.getId()).get();
            postToSave.getPriceLists();

            return new ResponseEntity<>(postRepository.save(postToSave), HttpStatus.OK);
        }
        //No post exists return bad request.
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/")
    public ResponseEntity deletePost(@RequestBody Post post) {
        return new ResponseEntity(HttpStatus.MULTI_STATUS);
    }

    //TODO remove userId and read the UserId from jwt.
    @GetMapping(value = {"/{userId}/{postId}", "/{userId}"})
    public ResponseEntity viewPosts(@PathVariable Optional<Long> userId, @PathVariable Optional<Long> postId) {
        if (postId.isPresent()) {
            return new ResponseEntity<>(postRepository.findById(postId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(postRepository.findAllByPostsByUserId(userId.get()), HttpStatus.OK);
        }


    }

    @GetMapping(value = {"/feed/{userId}"})
    public ResponseEntity userFeed(@PathVariable Long userId) {
        return new ResponseEntity<>(postRepository.feedOfUser(userId), HttpStatus.OK);
    }

    //TODO read current user from jwt.
    @PostMapping(value = "/like/{userId}")
    public ResponseEntity likePost(@RequestBody PostLike postLike, @PathVariable Long userId) {
        if (!postRepository.findById(postLike.getPost().getId()).isPresent() &&
                !userClassRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        postLike.setUser(userClassRepository.findById(userId).get());
        postLikeRepository.save(postLike);
        updateLikesOfPost(postLike.getPost().getId());

        return new ResponseEntity<>(postRepository.findById(postLike.getPost().getId()), HttpStatus.OK);
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

    void updateLikesOfPost(Long postId) {
        //Update likes of Post.
        Post post = postRepository.findById(postId).get();
        post.setLikeCount(postLikeRepository.likeCount(postId));
        postRepository.save(post);
    }

}