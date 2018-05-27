package com.my.network.socialnetwork.storage;

import com.my.network.socialnetwork.model.NotificationClass;
import com.my.network.socialnetwork.model.SendNotification;
import com.my.network.socialnetwork.model.Tokens;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PushNotificationApi {

//    OkHttpClient client = new OkHttpClient();
//
//    RuntimeNode.Request request = new Request.Builder()
//            .url("http://mydukan.org:8080/mydukanapi/notification/send")
//            .get()
//            .addHeader("Cache-Control", "no-cache")
//            .addHeader("Postman-Token", "7f4eba10-9762-4bd3-9c39-497aba903bea")
//            .build();
//
//    Response response = client.newCall(request).execute();

    public void getEmployees(String user_token/*, String tokens*/, String title, String message)
    {
        final String uri = "http://mydukan.org:8080/mydukanapi/notification/send";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, user_token);
        headers.add("x-api-key","eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1MTM2NTkwMzMsImV4cCI6MTU0NTE5NTAzMywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IndlYiIsIlN1cm5hbWUiOiJXRUIiLCJFbWFpbCI6IndlYkBleGFtcGxlLmNvbSJ9");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
//        headers.add("Authorization",user_token);
//        headers.add("Content-Type", "application/json");
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//        headers.add("Authorization", user_token);
//        headers.add("Content-Type", "application/json");


        NotificationClass notificationClass = new NotificationClass();

        notificationClass.setTitle(title);
        notificationClass.setBody(message);

        Tokens token = new Tokens();
        token.setToken("ejVc4Mnd_4I:APA91bFtl_1DhURkfW6gGQaKk0L0P-0MMsRL0eP5ir0V002gSkpqnGWZz6ogZ2qBCIM_DAp4DgTjV8SW2Yw-nb4ED-RdJvimp2KeDr2rqotZWjM0h40rwqp_jcepHRoVq6WXPtlHjMjB");

        List<Tokens> tokensList = new ArrayList<>();
        tokensList.add(token);

        SendNotification notification = new SendNotification();
        notification.setNotification(notificationClass);
        notification.setTokens(tokensList);
        HttpEntity<SendNotification> entity = new HttpEntity<SendNotification>(notification, headers);

//        ResponseEntity<SendNotification> result = restTemplate.exchange(uri, HttpMethod.POST, notification, SendNotification.class);
        ResponseEntity<SendNotification> responseEntity = restTemplate.postForEntity(uri, entity, SendNotification.class);

        System.out.println(responseEntity);
    }

}
