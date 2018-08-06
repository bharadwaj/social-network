package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.notification.Notification;
import com.my.network.socialnetwork.notification.NotificationData;
import com.my.network.socialnetwork.notification.PushNotificationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hash")
public class HashtagsController {

    @Autowired
    private PushNotificationApi pushNotificationApi;

    //This is my fcm token: ddyhHdz6h6Y:APA91bFU_zbVwUAxc4IxpRkb9XM6EIsYEXS6-adAuQ6LjUs_LUSrbI_tfLuOPhMiEVG5KkzRhpVeZt1fx4j909Z21L2Ic-48clowOfZ5AgQu20C_e3fE5q7TUKWkKqHUfebuIuDrGhkRGRfm935cCIV2LLXHg8isjg
    @GetMapping("/")
    public ResponseEntity testNotif(){
        String to = "ddyhHdz6h6Y:APA91bFU_zbVwUAxc4IxpRkb9XM6EIsYEXS6-adAuQ6LjUs_LUSrbI_tfLuOPhMiEVG5KkzRhpVeZt1fx4j909Z21L2Ic-48clowOfZ5AgQu20C_e3fE5q7TUKWkKqHUfebuIuDrGhkRGRfm935cCIV2LLXHg8isjg";

        Notification n = new Notification();
        n.setTitle("What's up?");
        n.setBody("yo");

        NotificationData nd = new NotificationData();
        nd.setBody("Hey");
        nd.setTitle("Hello!");

        pushNotificationApi.sendNotification(to, "type_a", n, nd);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
