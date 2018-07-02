package com.my.network.socialnetwork.notification;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class PushNotificationApi {

    public void sendNotification(String jwtAuthToken, List<String> subscribedUserIds, String title, String message, Long postid)
    {
        final String uri = "http://mydukan.org:8080/mydukanapi/notification/send";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, jwtAuthToken);
        headers.add("x-api-key","eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1MTM2NTkwMzMsImV4cCI6MTU0NTE5NTAzMywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IndlYiIsIlN1cm5hbWUiOiJXRUIiLCJFbWFpbCI6IndlYkBleGFtcGxlLmNvbSJ9");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");


        MyDukanNotification myDukanNotification = new MyDukanNotification();

        myDukanNotification.setTitle(title);
        myDukanNotification.setBody(message);

        List<UserGcmToken> userGcmTokenList = new ArrayList<>();
        UserGcmToken gt;
        /*for(String token: tokens){
            gt = new UserGcmToken();
            gt.setToken(token);
            userGcmTokenList.add(gt);
        }*/

        SendNotification notification = new SendNotification();
        notification.setNotification(myDukanNotification);
        notification.setTokens(userGcmTokenList);
        HttpEntity<SendNotification> entity = new HttpEntity<SendNotification>(notification, headers);

//        ResponseEntity<SendNotification> result = restTemplate.exchange(uri, HttpMethod.POST, notification, SendNotification.class);
        ResponseEntity<SendNotification> responseEntity = restTemplate.postForEntity(uri, entity, SendNotification.class);

        System.out.println(responseEntity);
    }

}
