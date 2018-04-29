package com.my.network.socialnetwork.model.poll;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollItemVoteRepository extends CrudRepository<PollItemVote, Long>{

    @Query("SELECT pv FROM PollItemVote pv WHERE pv.pollItem.id = :pollItemId")
    List<PollItemVote> getAllByPollItemId(@Param("pollItemId") Long pollItemId);
}
