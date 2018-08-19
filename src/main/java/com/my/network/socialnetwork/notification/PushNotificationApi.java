package com.my.network.socialnetwork.notification;

import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.socialnetwork.model.network.Following;
import com.my.network.socialnetwork.model.network.FollowingRepository;
import com.my.network.socialnetwork.model.post.Comment;
import com.my.network.socialnetwork.model.post.Post;
import com.my.network.socialnetwork.model.post.PostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PushNotificationApi {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Value("${notifications.fcm.api_url}")
    private String FCM_API_URL;

    @Value("${notifications.fcm.auth_key}")
    private String FCM_AUTH_KEY;

    @Async("threadPoolTaskExecutor")
    public void sendPostNotification(Post post){
        List<Following> followings = followingRepository.findFollowersByUserId(post.getUser().getId());

        for(Following f: followings){
            Optional<Users> optUser = usersRepository.findById(f.getUser().getId());
            if(!optUser.isPresent())
                return;
            String toFCMToken = optUser.get().getGcmToken();

            String message = "";

            Notification n = new Notification();

            if (post.getPriceLists() != null && post.getPriceLists().size() > 0) {
                message = "Your friend " + post.getUser().getName() + " posted new PriceList";
            } else if (post.getRequestForQuotations() != null && post.getRequestForQuotations().size() > 0) {
                message = "Your friend " + post.getUser().getName() + " posted new Request For Quotations";
            } else if (post.getImageUrl() != null && !post.getImageUrl().equalsIgnoreCase("")) {
                message = "Your friend " + post.getUser().getName() + " posted new Image";
            } else {
                message = "Your friend " + post.getUser().getName() + " posted in MyDukan";
            }

            n.setTitle(message);
            n.setBody(post.getTitle());

            NotificationData nd = new NotificationData();
            Post toSend = new Post();
            toSend.setId(post.getId());
            toSend.setTitle(post.getTitle());

            nd.setPost(toSend);

            sendNotification(toFCMToken, "type_a", n, nd);

        }

    }

    @Async("threadPoolTaskExecutor")
    public void sendPostLikeNotification(PostLike postLike){
        Optional<Users> optUser = usersRepository.findById(postLike.getPost().getUser().getId());
        if(!optUser.isPresent())
            return;

        String toFCMToken = optUser.get().getGcmToken();

        Notification n = new Notification();
        n.setTitle(postLike.getUser().getName()+ " Liked your Post.");
        n.setBody("Click to who else liked " + postLike.getPost().getTitle());

        NotificationData nd = new NotificationData();
        PostLike toSend = new PostLike();
        toSend.setId(postLike.getId());
        Post p = new Post();
        p.setId(postLike.getPost().getId());
        toSend.setPost(p);

        nd.setPostLike(toSend);

        sendNotification(toFCMToken, "type_a", n, nd);

    }

    @Async("threadPoolTaskExecutor")
    public void sendCommentNotification(Comment comment){
        Optional<Users> optUser = usersRepository.findById(comment.getPost().getUser().getId());
        if(!optUser.isPresent())
            return;
        String toFCMToken = optUser.get().getGcmToken();

        Notification n = new Notification();
        n.setTitle(comment.getComment() + " Commented on your Post.");
        n.setBody(comment.getComment());

        NotificationData nd = new NotificationData();

        Comment toSend = new Comment();
        toSend.setId(comment.getId());
        toSend.setComment(comment.getComment());
        Post p = new Post();
        p.setId(comment.getPost().getId());
        toSend.setPost(p);

        nd.setComment(toSend);

        sendNotification(toFCMToken, "type_a", n, nd);

    }


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
