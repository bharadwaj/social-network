package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.network.HashtagRepository;
import com.my.network.socialnetwork.notification.Notification;
import com.my.network.socialnetwork.notification.NotificationData;
import com.my.network.socialnetwork.notification.PushNotificationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hashtag")
public class HashtagsController {

    @Autowired
    private PushNotificationApi pushNotificationApi;

    @Autowired
    private HashtagRepository hashtagRepository;

    //This is my fcm token: ddyhHdz6h6Y:APA91bFU_zbVwUAxc4IxpRkb9XM6EIsYEXS6-adAuQ6LjUs_LUSrbI_tfLuOPhMiEVG5KkzRhpVeZt1fx4j909Z21L2Ic-48clowOfZ5AgQu20C_e3fE5q7TUKWkKqHUfebuIuDrGhkRGRfm935cCIV2LLXHg8isjg
    @GetMapping("/")
    public ResponseEntity testNotif() {
//        String to = "ddyhHdz6h6Y:APA91bFU_zbVwUAxc4IxpRkb9XM6EIsYEXS6-adAuQ6LjUs_LUSrbI_tfLuOPhMiEVG5KkzRhpVeZt1fx4j909Z21L2Ic-48clowOfZ5AgQu20C_e3fE5q7TUKWkKqHUfebuIuDrGhkRGRfm935cCIV2LLXHg8isjg";
        String to = "dsBqEw2AHoY:APA91bH5VLKoFd58qaI2dVG3eEHBjJCzsiQiFL-KwPqCMNmsGqO1MZHFMCzQ6RqpIhADvU3_JPABWnCdoFbPyqZsc7A_K15l4ZqmPi_Zio5vISzM-G8CkV9RW2wOYcms2_WPGy1de4G8rntmNceDv7CVr2b3nVhtVw";
        Notification n = new Notification();
        n.setTitle("What's up?");
        n.setBody("yo");

        NotificationData nd = new NotificationData();
        nd.setBody("Hey");
        nd.setTitle("Hello!");

        pushNotificationApi.sendNotification(to, "type_a", n, nd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{hashtag}")
    public ResponseEntity getPostsByHashtag(@PathVariable String hashtag,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "20") int size){

        return new ResponseEntity<>(hashtagRepository.allPostsOfAHashtag(hashtag,PageRequest.of(page, size)), HttpStatus.OK);
    }

    @GetMapping("users/{hashtag}")
    public ResponseEntity getUsersByHashtag(@PathVariable String hashtag,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "20") int size){

        return new ResponseEntity<>(hashtagRepository.allUsersOfAHashtag(hashtag,PageRequest.of(page, size)), HttpStatus.OK);
    }
}
