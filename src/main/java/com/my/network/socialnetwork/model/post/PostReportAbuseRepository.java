package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostReportAbuseRepository extends CrudRepository<PostReportAbuse, Long> {

    @Query("select count(p) from PostReportAbuse p where p.post.id = :postId")
    int countOfAllByPost(@Param("postId") Long postId);
}
