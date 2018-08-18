package com.my.network.socialnetwork.controller;

import com.my.network.auth.JwtTokenUtil;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.SubscribedUserRepository;
import com.my.network.socialnetwork.model.post.*;
import com.my.network.socialnetwork.notification.PushNotificationApi;
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
    private SubscribedUserRepository subscribedUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostConditionRepository postConditionRepository;

    @Autowired
    private BidOnRFQRepository bidOnRFQRepository;

    @Autowired
    private OrderOnPriceListRepository orderOnPriceListRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @PostMapping("/rfq/bid")
    public ResponseEntity placeABidOnRequestForQuantity(@RequestBody BidOnRFQ bidOnRFQ, @RequestHeader(value = "Authorization") String authTokenHeader) {

        String currentUserId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        Post postToSave = null;
        SubscribedUser userToSave = null;

        if(postRepository.findById(bidOnRFQ.getPost().getId()).isPresent() || subscribedUserRepository.findById(currentUserId).isPresent()) {
            postToSave = postRepository.findById(bidOnRFQ.getPost().getId()).get();
            userToSave = subscribedUserRepository.findById(currentUserId).get();
        }

        if (postToSave != null && userToSave != null && !postToSave.getUser().getId().matches(currentUserId)) {
            bidOnRFQ.setPost(postToSave);
            bidOnRFQ.setSubscribedUser(userToSave);
            if (bidOnRFQ.getOrderStatus() != null && bidOnRFQ.getOrderStatus().getId() != null)
                bidOnRFQ.setOrderStatus(orderStatusRepository.findById(bidOnRFQ.getOrderStatus().getId()).get());

            //TODO Notification.
            Post post = postRepository.findById(bidOnRFQ.getPost().getId()).get();
            SubscribedUser subscribedUser = subscribedUserRepository.findById(post.getUser().getId()).get();
            //String tokens = subscribedUser.getGcmToken();
            PushNotificationApi notificationApi = new PushNotificationApi();
            String message = "Price-List Order has been confirmed by " + userToSave.getName();
            //notificationApi.sendNotification(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long) 0);

            return new ResponseEntity<>(bidOnRFQRepository.save(bidOnRFQ), HttpStatus.OK);
        }

        return new ResponseEntity<>("Either the Post Or You Cannot Bid For this.", HttpStatus.BAD_REQUEST);
    }

    /**
     * This can be seen or accessed only by the owner of Post.
     * The call can be made from the orders page.
     */
    @GetMapping("/rfq/bid/{postId}")
    public ResponseEntity listOfBidsForAPost(@PathVariable Long postId) {
        return new ResponseEntity<>(bidOnRFQRepository.findAllBidsOfPostByPostId(postId), HttpStatus.OK);
    }

    @PutMapping("/rfq/bid/confirm")
    public ResponseEntity confirmBid(@RequestBody BidOnRFQ bidOnRFQ, @RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if (userToSave != null && userToSave.getName() != null) {
            if (bidOnRFQRepository.findById(bidOnRFQ.getId()).isPresent()) {
                BidOnRFQ toConfirmBid = bidOnRFQRepository.findById(bidOnRFQ.getId()).get();
                toConfirmBid.setConfirmed(true);

                //TODO Notification
                Post post = postRepository.findById(toConfirmBid.getPost().getId()).get();
                SubscribedUser subscribedUser = subscribedUserRepository.findById(post.getUser().getId()).get();
                //String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Request for Quotation has been confirmed by " + userToSave.getName();
                //notificationApi.sendNotification(authTokenHeader, tokens, "MyDukan RFQ Notification", message, (long) 0);
                return new ResponseEntity<>(bidOnRFQRepository.save(toConfirmBid), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No Bid Found", HttpStatus.BAD_REQUEST);
    }

    //TODO This can be seen only by the owner of post
    @PostMapping("/price-list/order")
    public ResponseEntity orderForItemsOnAPriceList(@RequestBody OrderOnPriceList orderOnPriceList, @RequestHeader(value = "Authorization") String authTokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);

        Post postToSave = null;
        SubscribedUser userToSave = null;

        if (subscribedUserRepository.findById(userId).isPresent() &&
                postRepository.findById(orderOnPriceList.getPost().getId()).isPresent()) {

            userToSave = subscribedUserRepository.findById(userId).get();
            postToSave = postRepository.findById(orderOnPriceList.getPost().getId()).get();
        }

        if (userToSave == null || postToSave == null || userToSave.getId().matches(postToSave.getUser().getId())) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        } else {
            orderOnPriceList.setPost(postToSave);
            orderOnPriceList.setSubscribedUser(userToSave);
            //TODO Notification
            Post post = postRepository.findById(orderOnPriceList.getPost().getId()).get();
            SubscribedUser subscribedUser = subscribedUserRepository.findById(post.getUser().getId()).get();
            //String tokens = subscribedUser.getGcmToken();
            PushNotificationApi notificationApi = new PushNotificationApi();
            String message = "Placed order on your Price-List by " + userToSave.getName();
            //notificationApi.sendNotification(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long) 0);
            return new ResponseEntity<>(orderOnPriceListRepository.save(orderOnPriceList), HttpStatus.OK);
        }
    }

    @GetMapping("/price-list/order/{postId}")
    public ResponseEntity listOfOrdersForAPost(@PathVariable Long postId) {
        return new ResponseEntity<>(orderOnPriceListRepository.findAllOrdersOfPostByPostId(postId), HttpStatus.OK);
    }

    @PutMapping("/price-list/order/confirm")
    public ResponseEntity confirmOrder(@RequestBody OrderOnPriceList orderOnPriceList, @RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = subscribedUserRepository.findById(userId).get();

        if (userToSave != null && userToSave.getName() != null) {
            if (orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent()) {
                OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
                toConfirmOrder.setConfirmed(true);

                //TODO Notification
                Post postId = postRepository.findById(toConfirmOrder.getPost().getId()).get();
                SubscribedUser subscribedUser = subscribedUserRepository.findById(postId.getUser().getId()).get();
                //String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Price-List Order has been confirmed by " + userToSave.getName();
                //notificationApi.sendNotification(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long) 0);
                return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/price-list/order-statuses")
    public ResponseEntity listOfOrderStatuses() {
        return new ResponseEntity<>(orderStatusRepository.findAll(), HttpStatus.OK);
    }

    /**
     * A Class to update just the order status of a PriceList Order.
     * */
    @PatchMapping("/price-list/order/status")
    public ResponseEntity updateStatusOfOrder(@RequestBody OrderOnPriceList orderOnPriceList, @RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        SubscribedUser userToSave = null;
        if (subscribedUserRepository.findById(userId).isPresent())
            userToSave = subscribedUserRepository.findById(userId).get();

        if (userToSave != null && userToSave.getName() != null) {
            if (orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent() &&
                    orderStatusRepository.findById(orderOnPriceList.getOrderStatus().getId()).isPresent()) {
                OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
                toConfirmOrder.setOrderStatus(orderOnPriceList.getOrderStatus());
                //TODO send Notification.
                Post post = postRepository.findById(toConfirmOrder.getPost().getId()).get();
                SubscribedUser subscribedUser = subscribedUserRepository.findById(post.getUser().getId()).get();
                //String tokens = subscribedUser.getGcmToken();
                PushNotificationApi notificationApi = new PushNotificationApi();
                String message = "Price-List Order status changed to " + orderOnPriceList.getOrderStatus().getStatusName();
                //notificationApi.sendNotification(authTokenHeader, tokens, "MyDukan Price-List Notification", message, (long) 0);
                return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }

    /**
     * All the Orders Placed by the current User.
     * Logic: Check all the occurrences of user Id in OrdersOnPriceList table.
     *
     * */
    @GetMapping("/orders/placed")
    public ResponseEntity allOrdersOfCurrentUser(@RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (!subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(orderOnPriceListRepository.findAllOrdersPlacedByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/bids/placed")
    public ResponseEntity allBidsOfCurrentUser(@RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (!subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(bidOnRFQRepository.findAllBidsReceivedByUserId(userId), HttpStatus.OK);
    }


    /**View All Orders Received By the Current user.*/
    @GetMapping("/orders/received")
    public ResponseEntity allOrdersRecievedByCurrentUser(@RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (!subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(orderOnPriceListRepository.findAllOrdersOfPostByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/bids/received")
    public ResponseEntity allBidsRecievedByCurrentUser(@RequestHeader(value = "Authorization") String authTokenHeader) {

        String userId = jwtTokenUtil.getUserIdFromToken(authTokenHeader);
        if (!subscribedUserRepository.findById(userId).isPresent())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(bidOnRFQRepository.findAllBidsOfPostByUserId(userId), HttpStatus.OK);
    }



}
