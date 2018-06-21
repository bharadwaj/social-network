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

    public void getEmployees(String user_token, String tokens, String title, String message, Long postid)
    {
        final String uri = "http://mydukan.org:8080/mydukanapi/notification/send";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, user_token);
        headers.add("x-api-key","eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE1MTM2NTkwMzMsImV4cCI6MTU0NTE5NTAzMywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IndlYiIsIlN1cm5hbWUiOiJXRUIiLCJFbWFpbCI6IndlYkBleGFtcGxlLmNvbSJ9");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
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
//        token.setToken(/*tokens*/"emQOTSmhGbM:APA91bGAV3WZC44cjgk1GKAcTcFx-uDtJN2wtqkVb6knXtxPZhsZW4H0TUW5EO7hg5CGrUA4jaTaYVhtprMvfMxpaywUxvQE2yu13oqmPQ_Li7ZzgXHxh44Alv72ryrsenusc62DzShA, dW4FHCB3lXo:APA91bG1OtWQzSPwybF17qWnmWTaan3DtS0Y9bn-P3RxCh9O8LPe4yYJkYtdsZrNRTXgp4dNuL7YkjMXyaxytwtiwKJBgNq9Y-ZNeMO2Tr8ThiRLfvYdya313GzSfT1w9SGEoNrfnrvy");
        token.setToken(tokens/*"fZBR875D1x0:APA91bFFz7DgUy0PJ-dUdjXXoAVLHk7QM6JmPIPM5yL7gB4v0msVjwdd9F2RIEbxZlIqe6dVV9HQdU3INoNzcAMvOnz70mrcTsQ2mZD8h-PlgI6QWWlhb9PJi-3woDxgljxkInxaX19A"*/);

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
