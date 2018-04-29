package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.poll.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/poll")
public class PollController {

    @Autowired
    PollRepository pollRepository;

    @Autowired
    PollItemVoteRepository pollItemVoteRepository;
    //TODO read current user from jwt.
    @PostMapping(value = "/new")
    public ResponseEntity createPoll(@RequestBody Poll poll) {
        return new ResponseEntity<>(pollRepository.save(poll), HttpStatus.OK);
    }

    @PostMapping(value = "/vote")
    public ResponseEntity voteAPollItem(@RequestBody PollItemVote pollItemVote) {
        return new ResponseEntity<>(pollItemVoteRepository.save(pollItemVote), HttpStatus.OK);
    }

   @GetMapping(value = "/")
    public ResponseEntity allPolls() {
        return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/votes/{pollItemId}")
    public ResponseEntity votesOfPollItem(@PathVariable Long pollItemId) {
        return new ResponseEntity<>(pollItemVoteRepository.getAllByPollItemId(pollItemId), HttpStatus.OK);
    }


}
