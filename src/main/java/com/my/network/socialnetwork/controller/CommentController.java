package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //TODO add permissions
    @PostMapping(value="/")
    public ResponseEntity commentOnPost(@RequestBody Comment comment, @RequestHeader(value= "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        comment.setUser(subscribedUserRepository.findById(userId).get());
        if(comment.getPost() == null || !postRepository.findById(comment.getPost().getId()).isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(commentRepository.save(comment),HttpStatus.OK);
    }

    @GetMapping(value="/post/{postId}")
    public ResponseEntity commentsOfPost(@PathVariable Long postId){
        return new ResponseEntity<>(commentRepository.allCommentsOfPost(postId),HttpStatus.OK);
    }

    @DeleteMapping(value="/")
    public ResponseEntity deleteComment(@PathVariable Comment comment){
        commentRepository.delete(comment);
        return new ResponseEntity<>(HttpStatus.OK);
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
}
