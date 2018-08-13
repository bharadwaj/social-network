package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.poll.Poll;
import com.my.network.socialnetwork.model.poll.PollItemVote;
import com.my.network.socialnetwork.model.poll.PollItemVoteRepository;
import com.my.network.socialnetwork.model.poll.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/poll")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollItemVoteRepository pollItemVoteRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SubscribedUserRepository subscribedUserRepository;

    //TODO read current user from jwt.
    @PostMapping(value = "/new")
    public ResponseEntity createPoll(@RequestBody Poll poll, @RequestHeader(value= "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Validate follow user & user's existence.
        SubscribedUser subscribedUser = subscribedUserRepository.findById(userId).get();
        poll.setCreatedBy(subscribedUser);
        return new ResponseEntity<>(pollRepository.save(poll), HttpStatus.OK);
    }

    @PostMapping(value = "/vote")
    public ResponseEntity voteAPollItem(@RequestBody PollItemVote pollItemVote, @RequestHeader(value= "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        //Validate follow user & user's existence.
        SubscribedUser subscribedUser = subscribedUserRepository.findById(userId).get();
        pollItemVote.setVotedUser(subscribedUser);
        return new ResponseEntity<>(pollItemVoteRepository.save(pollItemVote), HttpStatus.OK);
    }

    //TODO limit only polls of the user.
   @GetMapping(value = "/")
    public ResponseEntity allPolls() {
        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/votes/{pollItemId}")
    public ResponseEntity votesOfPollItem(@PathVariable Long pollItemId) {
        return new ResponseEntity<>(pollItemVoteRepository.getAllByPollItemId(pollItemId), HttpStatus.OK);
    }


}
