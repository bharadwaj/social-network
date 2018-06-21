package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.storage.PushNotificationApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/post")
public class OrdersAndBidsController {
    @Autowired
    SubscribedUserRepository subscribedUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostConditionRepository postConditionRepository;

    @Autowired
    BidOnRFQRepository bidOnRFQRepository;

    @Autowired
    OrderOnPriceListRepository orderOnPriceListRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @PostMapping("/rfq/bid")
    public ResponseEntity placeABidOnRequestForQuantity(@RequestBody BidOnRFQ bidOnRFQ, @RequestHeader(value= "Authorization") String authTokenHeader){
        Post postToSave = postRepository.findById(bidOnRFQ.getPost().getId()).get();
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if(postToSave != null && userToSave != null){
            bidOnRFQ.setPost(postToSave);
            bidOnRFQ.setSubscribedUser(userToSave);
            if(bidOnRFQ.getOrderStatus() != null && bidOnRFQ.getOrderStatus().getId() != null)
                bidOnRFQ.setOrderStatus(orderStatusRepository.findById(bidOnRFQ.getOrderStatus().getId()).get());

            SubscribedUser subscribedUser = subscribedUserRepository.findById(bidOnRFQ.getPost().getUser().getId()).get();
            String tokens = subscribedUser.getGcmToken();
            PushNotificationApi notificationApi = new PushNotificationApi();
            String message = "Price-List Order has been confirmed by " + userToSave.getName();
            notificationApi.getEmployees(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long) 0);

            return new ResponseEntity<>(bidOnRFQRepository.save(bidOnRFQ), HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect Post", HttpStatus.BAD_REQUEST);
    }

    /**
     * This can be seen or accessed only by the owner of Post.
     * The call can be made from the orders page.
     * */
    @GetMapping("/rfq/bid/{postId}")
    public ResponseEntity listOfBidsForAPost(@PathVariable Long postId){
        return new ResponseEntity<>(bidOnRFQRepository.findAllBidsOfPostByPostId(postId), HttpStatus.OK);
    }

    @PutMapping("/rfq/bid/confirm")
    public ResponseEntity confirmBid(@RequestBody BidOnRFQ bidOnRFQ,@RequestHeader(value= "Authorization") String authTokenHeader){

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if(userToSave != null && userToSave.getName() != null) {
            if (bidOnRFQRepository.findById(bidOnRFQ.getId()).isPresent()) {
                BidOnRFQ toConfirmBid = bidOnRFQRepository.findById(bidOnRFQ.getId()).get();
                toConfirmBid.setConfirmed(true);
                SubscribedUser subscribedUser = subscribedUserRepository.findById(bidOnRFQ.getPost().getUser().getId()).get();
                String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Request for Quotation has been confirmed by "+userToSave.getName();
                notificationApi.getEmployees(authTokenHeader, tokens, "MyDukan RFQ Notification", message, (long)0);
                return new ResponseEntity<>(bidOnRFQRepository.save(toConfirmBid), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No Bid Found", HttpStatus.BAD_REQUEST);
    }

    //TODO This can be seen only by the owner of post
    @PostMapping("/price-list/order")
    public ResponseEntity orderForItemsOnAPriceList(@RequestBody OrderOnPriceList orderOnPriceList,@RequestHeader(value= "Authorization") String authTokenHeader){
        Post postToSave = postRepository.findById(orderOnPriceList.getPost().getId()).get();
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if(userToSave.getId() == postToSave.getUser().getId()){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else if(postToSave != null && userToSave != null){
            orderOnPriceList.setPost(postToSave);
            orderOnPriceList.setSubscribedUser(userToSave);
            SubscribedUser subscribedUser = subscribedUserRepository.findById(orderOnPriceList.getPost().getUser().getId()).get();
            String tokens = subscribedUser.getGcmToken();
            PushNotificationApi notificationApi = new PushNotificationApi();
            String message = "Placed order on your Price-List by " + userToSave.getName();
            notificationApi.getEmployees(authTokenHeader, tokens, "MyDukan Price-List Notification", message,(long)0);
            return new ResponseEntity<>(orderOnPriceListRepository.save(orderOnPriceList), HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect Post", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/price-list/order/{postId}")
    public ResponseEntity listOfOrdersForAPost(@PathVariable Long postId){
        return new ResponseEntity<>(orderOnPriceListRepository.findAllOrdersOfPostByPostId(postId), HttpStatus.OK);
    }

    @PutMapping("/price-list/order/confirm")
    public ResponseEntity confirmOrder(@RequestBody OrderOnPriceList orderOnPriceList,@RequestHeader(value= "Authorization") String authTokenHeader){

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if(userToSave != null && userToSave.getName() != null) {
            if (orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent()) {
                OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
                toConfirmOrder.setConfirmed(true);
                SubscribedUser subscribedUser = subscribedUserRepository.findById(orderOnPriceList.getPost().getUser().getId()).get();
                String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Price-List Order has been confirmed by " + userToSave.getName();
                notificationApi.getEmployees(authTokenHeader, tokens, "MyDukan Price-List Notification", message,(long)0);
                return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
            }
        }

     return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/price-list/order-statuses")
    public ResponseEntity listOfOrderStatuses(){
        return new ResponseEntity<>(orderStatusRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/price-list/order/status")
    public ResponseEntity updateStatusOfOrder(@RequestBody OrderOnPriceList orderOnPriceList,@RequestHeader(value= "Authorization") String authTokenHeader){

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if(userToSave != null && userToSave.getName() != null) {
            if (orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent() &&
                    orderStatusRepository.findById(orderOnPriceList.getOrderStatus().getId()).isPresent()) {
                OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
                toConfirmOrder.setOrderStatus(orderOnPriceList.getOrderStatus());
                SubscribedUser subscribedUser = subscribedUserRepository.findById(orderOnPriceList.getPost().getUser().getId()).get();
                String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Price-List Order status changed to " + orderOnPriceList.getOrderStatus().getStatusName();
                notificationApi.getEmployees(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long)0);
                return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }

}
