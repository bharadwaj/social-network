package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.PostReportAbuseRepository;
import com.my.network.socialnetwork.model.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
