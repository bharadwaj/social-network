package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidOnRFQRepository extends CrudRepository<BidOnRFQ, Long>{

    @Query("select b from BidOnRFQ b where b.post.id = :postId")
    List<BidOnRFQ> findAllBidsOfPostByPostId(@Param("postId")Long postId);
}
