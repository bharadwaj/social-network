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

import javax.servlet.http.HttpServletRequest;
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
        SubscribedUser user = subscribedUserRepository.getSubscribedUser(comment.getPost().getUser().getId());
        notificationApi.getEmployees(authTokenHeader, user.getGcmToken(), "MyDukan Notification", comment.getUser().getName()+" has Commented on your post.", comment.getPost().getId());
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    @GetMapping(value="/post/{postId}")
    public ResponseEntity commentsOfPost(@PathVariable Long postId, @RequestHeader(value= "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if(!postRepository.findById(postId).isPresent() ||
                !subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<Comment> respComments = commentRepository.allCommentsOfPost(postId);
        for(Comment c : respComments){
            if (commentLikeRepository.didUserLikeThisComment(userId, c.getId()) != null) {
                c.setLiked(true);
                //PushNotificationApi notificationApi = new PushNotificationApi();
                //notificationApi.getEmployees(token, "MyDukan Notification", respPost.getUser().getName()+" has liked your post.");
            } else {
                c.setLiked(false);
            }
        }
        return new ResponseEntity<>(respComments,HttpStatus.OK);
    }

    @DeleteMapping(value="/{commentId}")
    public ResponseEntity deleteComment(@RequestHeader(value= "Authorization") String authTokenHeader, @PathVariable Long commentId){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if(!commentRepository.findById(commentId).isPresent() ||
                !subscribedUserRepository.findById(userId).isPresent() ||
                !commentRepository.findById(commentId).get().getUser().getId().equals(userId))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        Comment comment = commentRepository.findById(commentId).get();
        Long postId = comment.getPost().getId();

        commentRepository.deleteById(commentId);
        updateCommentsCount(postId);
        return new ResponseEntity<>(commentRepository.allCommentsOfPost(postId), HttpStatus.OK);
    }

    @PatchMapping(value = "/like/{commentId}")
    public ResponseEntity likeComment(@PathVariable Long commentId, @RequestHeader(value= "Authorization") String authTokenHeader) {
        CommentLike commentLike = new CommentLike();
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        //Check if the user liked already.
        if (commentLikeRepository.didUserLikeThisComment(userId, commentId) == null) {
            if (!commentRepository.findById(commentId).isPresent() ||
                    !subscribedUserRepository.findById(userId).isPresent())
                return new ResponseEntity(HttpStatus.BAD_REQUEST);

            commentLike.setUser(subscribedUserRepository.findById(userId).get());
            commentLike.setComment(commentRepository.findById(commentId).get());
            commentLikeRepository.save(commentLike);
            updateLikesOfComment(commentId);

            Comment respPost = commentRepository.findById(commentId).get();
            if (commentLikeRepository.didUserLikeThisComment(userId, respPost.getId()) != null) {
                respPost.setLiked(true);
                //PushNotificationApi notificationApi = new PushNotificationApi();
                //notificationApi.getEmployees(token, "MyDukan Notification", respPost.getUser().getName()+" has liked your post.");
            } else {
                respPost.setLiked(false);
            }

            return new ResponseEntity<>(respPost, HttpStatus.OK);

        } else {
            //Perform unlike since the user already liked the post
            commentLike = commentLikeRepository.didUserLikeThisComment(userId, commentId);
            commentLikeRepository.delete(commentLike);
            updateLikesOfComment(commentLike.getComment().getId());
            Comment respComment = commentRepository.findById(commentId).get();
            if (commentLikeRepository.didUserLikeThisComment(userId, respComment.getId()) != null) {
                respComment.setLiked(true);
            } else {
                respComment.setLiked(false);
            }
            return new ResponseEntity<>(respComment, HttpStatus.OK);
        }
    }

    void updateLikesOfComment(Long commentId){
        //Update likes of Comment.
        Comment comment = commentRepository.findById(commentId).get();
        comment.setLikeCount(commentLikeRepository.likeCount(commentId));
        commentRepository.save(comment);
    }

    void updateCommentsCount(Long postId){
        Post postToUpdateCommentCount = postRepository.findById(postId).get();
        postToUpdateCommentCount.setCommentCount(commentRepository.countOfAllByPost(postId));
        postRepository.save(postToUpdateCommentCount);
    }
}
