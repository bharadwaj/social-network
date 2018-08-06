package com.my.network.socialnetwork.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PushNotificationApi {

    @Value("${notifications.fcm.api_url}")
    private String FCM_API_URL;

    @Value("${notifications.fcm.auth_key}")
    private String FCM_AUTH_KEY;

    public ResponseEntity sendNotification(String toFcmToken, String collapseKey, Notification notification, NotificationData data){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "key="+FCM_AUTH_KEY);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");


        NotificationRequest notificationRequest = new NotificationRequest();

        notificationRequest.setTo(toFcmToken);
        notificationRequest.setCollapse_key(collapseKey);


        notificationRequest.setData(data);
        notificationRequest.setNotification(notification);

        HttpEntity<NotificationRequest> entity = new HttpEntity<>(notificationRequest, headers);

        return restTemplate.postForEntity(FCM_API_URL, entity, NotificationRequest.class);
    }

}
