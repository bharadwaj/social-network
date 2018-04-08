package com.my.network.socialnetwork.controller;

import com.my.network.socialnetwork.model.UserClass;
import com.my.network.socialnetwork.model.UserClassRepository;
import com.my.network.socialnetwork.model.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/post")
public class OrdersAndBidsController {
    @Autowired
    UserClassRepository userClassRepository;

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

    @PostMapping("/rfq/bid/{userId}")
    public ResponseEntity placeABidOnRequestForQuantity(@RequestBody BidOnRFQ bidOnRFQ, @PathVariable Long userId){
        Post postToSave = postRepository.findById(bidOnRFQ.getPost().getId()).get();
        UserClass userToSave = userClassRepository.findById(bidOnRFQ.getUserClass().getId()).get();

        if(postToSave != null && userToSave != null){
            bidOnRFQ.setPost(postToSave);
            bidOnRFQ.setUserClass(userToSave);

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

    //TODO This can be seen only by the owner of post
    @PostMapping("/price-list/order/{postId}")
    public ResponseEntity orderForItemsOnAPriceList(@RequestBody OrderOnPriceList orderOnPriceList, @PathVariable Long postId){
        Post postToSave = postRepository.findById(orderOnPriceList.getPost().getId()).get();
        UserClass userToSave = userClassRepository.findById(orderOnPriceList.getUserClass().getId()).get();

        if(postToSave != null && userToSave != null){
            orderOnPriceList.setPost(postToSave);
            orderOnPriceList.setUserClass(userToSave);

            return new ResponseEntity<>(orderOnPriceListRepository.save(orderOnPriceList), HttpStatus.OK);
        }

        return new ResponseEntity<>("Incorrect Post", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/price-list/order/{postId}")
    public ResponseEntity listOfOrdersForAPost(@PathVariable Long postId){
        return new ResponseEntity<>(orderOnPriceListRepository.findAllOrdersOfPostByPostId(postId), HttpStatus.OK);
    }

    @PutMapping("/price-list/order/confirm")
    public ResponseEntity confirmOrder(@RequestBody OrderOnPriceList orderOnPriceList){

        if(orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent()){
            OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
            toConfirmOrder.setConfirmed(true);
            return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
        }

     return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/price-list/order-statuses")
    public ResponseEntity listOfOrderStatuses(){
        return new ResponseEntity<>(orderStatusRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/price-list/order/status")
    public ResponseEntity updateStatusOfOrder(@RequestBody OrderOnPriceList orderOnPriceList){

        if(orderOnPriceListRepository.findById(orderOnPriceList.getId()).isPresent() &&
               orderStatusRepository.findById(orderOnPriceList.getOrderStatus().getId()).isPresent() ){
            OrderOnPriceList toConfirmOrder = orderOnPriceListRepository.findById(orderOnPriceList.getId()).get();
            toConfirmOrder.setOrderStatus(orderOnPriceList.getOrderStatus());
            return new ResponseEntity<>(orderOnPriceListRepository.save(toConfirmOrder), HttpStatus.OK);
        }

        return new ResponseEntity<>("No order Found", HttpStatus.BAD_REQUEST);
    }


}
