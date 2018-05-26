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
        token.setToken("dHGVOzYFkc0:APA91bGCGcmYod_b8JXl8R-017tRlqaAvyv7X3gHpuPY25JQR0OFNOfa9MU11MogkNm38zLJnErWnynLiCEV89r0IZQidM4lBxMMxh_Z7AK6OJ5g9JO7Nxa70mnDqlOUdEg8qL5jjVTY");

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
