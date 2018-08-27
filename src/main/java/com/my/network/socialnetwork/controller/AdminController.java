package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.model.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(value = "posts/reported")
    public ResponseEntity getAllReportedPosts(@RequestHeader(value = "Authorization") String authTokenHeader) {
        //if(post.getPostVisibility() == null || postVisibilityRepository.findAllById())
        //String token = request.getHeader(tokenHeader);
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> user = subscribedUserRepository.findById(userId);
        if(user.isPresent() && permittedUser(user)){
            return new ResponseEntity<>(postRepository.getAllReportedPosts(), HttpStatus.OK);

        }else {

            return new ResponseEntity<>("What are you doing here.", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("promote/user")
    public ResponseEntity updatePromotionStatusOfUser(@RequestBody SubscribedUser su, @RequestHeader(value = "Authorization") String authTokenHeader){

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> user = subscribedUserRepository.findById(userId);
        if(user.isPresent() && permittedUser(user) ){
            SubscribedUser toSave = user.get();
            toSave.setPromotionFactor(su.getPromotionFactor());

            return new ResponseEntity<>(subscribedUserRepository.save(toSave), HttpStatus.OK);

        }else {

            return new ResponseEntity<>("What are you doing here.", HttpStatus.FORBIDDEN);
        }


    }

    @PutMapping("promote/post")
    public ResponseEntity updatePromotionStatusOfPost(@RequestBody Post post, @RequestHeader(value = "Authorization") String authTokenHeader){

        Optional<Post> optionalPost = postRepository.findById(post.getId());

        if(!optionalPost.isPresent())
            return new ResponseEntity<>("Invalid Post Id", HttpStatus.BAD_REQUEST);

        Post toSave = optionalPost.get();
        toSave.setPromotionFactor(post.getPromotionFactor());

        return new ResponseEntity<>(postRepository.save(toSave), HttpStatus.OK);
    }

    @GetMapping(value = "posts/user/{profileId}")
    public ResponseEntity allpostsOfUser(@PathVariable("profileId") String profileId,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                         @RequestHeader(value = "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> user = subscribedUserRepository.findById(userId);
        if(user.isPresent() && permittedUser(user)){

            return new ResponseEntity<>(postRepository.findAllByPostsByUserId(profileId, PageRequest.of(page, size)), HttpStatus.OK);

        }else {

            return new ResponseEntity<>("What are you doing here.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "post")
    public ResponseEntity deletePostOfUser(@RequestBody Post post,
                                         @RequestHeader(value = "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> user = subscribedUserRepository.findById(userId);
        if(user.isPresent() && permittedUser(user)){
            postRepository.delete(post);

            return new ResponseEntity<>(new SuccessResponse(HttpStatus.OK, "Successfully Deleted."), HttpStatus.OK);

        }else {

            return new ResponseEntity<>("What are you doing here.", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "comment")
    public ResponseEntity deleteCommentOfUser(@RequestBody Comment comment,
                                         @RequestHeader(value = "Authorization") String authTokenHeader){
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Optional<SubscribedUser> user = subscribedUserRepository.findById(userId);
        if(user.isPresent() && permittedUser(user)){
            commentRepository.delete(comment);

            return new ResponseEntity<>(new SuccessResponse(HttpStatus.OK, "Successfully Deleted."), HttpStatus.OK);

        }else {

            return new ResponseEntity<>("What are you doing here.", HttpStatus.FORBIDDEN);
        }
    }

    public Boolean permittedUser(Optional<SubscribedUser> user){
        if(user.get().getEmail().equals("mydukanapp@gmail.com")
                || user.get().getEmail().equals("mydukaninfo@gmail.com")
                || user.get().getEmail().equals("myappdukan@gmail.com")
                || user.get().getEmail().equals("contactmydukan@gmail.com")
                || user.get().getEmail().equals("bharadwaj.j@gmail.com")){
            return true;
        }
        return false;
    }
}
