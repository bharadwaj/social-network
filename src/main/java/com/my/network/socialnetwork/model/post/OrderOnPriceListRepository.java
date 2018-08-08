package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderOnPriceListRepository extends CrudRepository<OrderOnPriceList, Long>{

    @Query("select o from OrderOnPriceList o where o.post.id = :postId")
    List<OrderOnPriceList> findAllOrdersOfPostByPostId(@Param("postId") Long postId);

    @Query("select o from OrderOnPriceList o where o.subscribedUser.id = :userId")
    List<OrderOnPriceList> findAllOrdersOfPostByUserId(@Param("userId") String userId);

    @Query("select o from OrderOnPriceList o where o.post.id in (select p.id from Post p where p.user.id = :userId)")
    List<OrderOnPriceList> findAllOrdersReceivedByUser(@Param("userId") String userId);

    /*@Query("select o from OrderOnPriceList o where o.post.id in (select p.id from Post p where p.user.id = :userId)")
    List<OrderOnPriceList> findAllOrdersReceivedByUser(@Param("userId") String userId);*/
}
