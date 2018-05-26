package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.storage.PushNotificationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SubscribedUserRepository subscribedUserRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    FollowingRepository followingRepository;

    @PostMapping(value="/")
    public ResponseEntity commentOnPost(@RequestBody Comment comment, @RequestHeader(value= "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        comment.setUser(subscribedUserRepository.findById(userId).get());
        if(comment.getPost() == null || !postRepository.findById(comment.getPost().getId()).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Comment savedComment = commentRepository.save(comment);
        updateCommentsCount(comment.getPost().getId());
        PushNotificationApi notificationApi = new PushNotificationApi();
        notificationApi.getEmployees(authTokenHeader, "MyDukan Notification", comment.getUser().getName()+" has Commented on your post.");
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

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

    @GetMapping(value="/post/{postId}")
    public ResponseEntity commentsOfPost(@PathVariable Long postId){
        return new ResponseEntity<>(commentRepository.allCommentsOfPost(postId),HttpStatus.OK);
    }

    @DeleteMapping(value="/{commentId}")
    public ResponseEntity deleteComment(@RequestHeader(value= "Authorization") String authTokenHeader, @PathVariable Long commentId){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Comment comment = commentRepository.findById(commentId).get();
        Long postId = comment.getPost().getId();
        if(!commentRepository.findById(commentId).isPresent() &&
                !subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        commentRepository.deleteById(commentId);
        updateCommentsCount(postId);
        return new ResponseEntity<>(commentRepository.allCommentsOfPost(postId), HttpStatus.OK);
    }

    //TODO read current user from jwt.
    @PostMapping(value = "/like")
    public ResponseEntity likeComment(@RequestBody CommentLike commentLike,  @RequestHeader(value= "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if(!postRepository.findById(commentLike.getComment().getId()).isPresent() &&
                !subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        commentLike.setUser(subscribedUserRepository.findById(userId).get());
        commentLikeRepository.save(commentLike);
        updateLikesOfComment(commentLike.getComment().getId());

        return new ResponseEntity<>(commentRepository.findById(commentLike.getComment().getId()), HttpStatus.OK);
    }

    void updateLikesOfComment(Long postId){
        //Update likes of Post.
        Comment comment = commentRepository.findById(postId).get();
        //comment.setLikeCount(commentLikeRepository.likeCount(postId));
        commentRepository.save(comment);
    }

    void updateCommentsCount(Long postId){
        Post postToUpdateCommentCount = postRepository.findById(postId).get();
        postToUpdateCommentCount.setCommentCount(commentRepository.countOfAllByPost(postId));
        postRepository.save(postToUpdateCommentCount);
    }
}
