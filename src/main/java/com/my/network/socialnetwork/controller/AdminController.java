package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.Post;
import com.my.network.socialnetwork.model.post.PostReportAbuseRepository;
import com.my.network.socialnetwork.model.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PostReportAbuseRepository postReportAbuseRepository;

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(value = "posts/reported")
    public ResponseEntity getAllReportedPosts(@RequestHeader(value = "Authorization") String authTokenHeader) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        //String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if(!subscribedUserRepository.findById(userId).isPresent()){
            return new ResponseEntity<>("User is Not Admin", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(postRepository.getAllReportedPosts(), HttpStatus.OK);
    }

    @PutMapping("promote/user")
    public ResponseEntity updatePromotionStatusOfUser(@RequestBody SubscribedUser su, @RequestHeader(value = "Authorization") String authTokenHeader){

        Optional<SubscribedUser> optUser = subscribedUserRepository.findById(su.getId());

        if(!optUser.isPresent())
            return new ResponseEntity<>("Invalid SubsbscribedUser", HttpStatus.BAD_REQUEST);

        SubscribedUser toSave = optUser.get();
        toSave.setPromotionFactor(optUser.get().getPromotionFactor());

        return new ResponseEntity<>(subscribedUserRepository.save(toSave), HttpStatus.OK);
    }

    @PutMapping("promote/post")
    public ResponseEntity updatePromotionStatusOfPost(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader){

        Optional<Post> optionalPost = postRepository.findById(post.getId());

        if(!optionalPost.isPresent())
            return new ResponseEntity<>("Invalid Post Id", HttpStatus.BAD_REQUEST);

        Post toSave = optionalPost.get();
        toSave.setPromotionFactor(optionalPost.get().getPromotionFactor());

        return new ResponseEntity<>(postRepository.save(toSave), HttpStatus.OK);
    }
}
