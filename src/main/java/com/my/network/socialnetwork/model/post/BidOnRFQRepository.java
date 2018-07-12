package com.my.network.socialnetwork.model.post;

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
    List<BidOnRFQ> findAllBidsOfPostByUserId(@Param("userId")String userId);

    @Query(value = "select b from BidOnRFQ b where b.postId in (select p.id from Post p where p.user = :userId)", nativeQuery = true)
    List<BidOnRFQ> findAllBidsOfPost(@Param("userId")String userId);
}
