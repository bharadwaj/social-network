package com.my.network.socialnetwork.model.post.ordersbids;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BidOnRFQRepository extends CrudRepository<BidOnRFQ, Long>{

    @Query("select b from BidOnRFQ b where b.post.id = :postId")
    List<BidOnRFQ> findAllBidsOfPostByPostId(@Param("postId")Long postId);

    @Query("select b from BidOnRFQ b where b.subscribedUser.id = :userId")
    List<BidOnRFQ> findAllBidsReceivedByUserId(@Param("userId")String userId);

    @Query("select b from BidOnRFQ b where b.post.id in (select p.id from Post p where p.user.id = :userId)")
    List<BidOnRFQ> findAllBidsOfPostByUserId(@Param("userId")String userId);
}
