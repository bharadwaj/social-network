package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = :postId")
    List<Comment> allCommentsOfPost(@Param("postId") Long postId);

    @Query("select count(c) from Comment c where c.post.id = :postId")
    int countOfAllByPost(@Param("postId") Long postId);
}
