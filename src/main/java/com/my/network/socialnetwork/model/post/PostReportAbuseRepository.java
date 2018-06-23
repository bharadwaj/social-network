package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PostReportAbuseRepository extends CrudRepository<PostReportAbuse, Long> {

    @Query("select count(p) from PostReportAbuse p where p.post.id = :postId")
    int countOfAllByPost(@Param("postId") Long postId);

    @Query("select p from PostReportAbuse p where p.post.id = :postId")
    List<PostReportAbuse> getAllReportedPosts(@Param("postId") Long postId);

    @Query("select p from PostReportAbuse p where p.userWhoReported.id = :userId AND p.post.id = :postId")
    PostReportAbuse didUserReportThisPost(@Param("userId") String userId, @Param("postId") Long postId);
}
