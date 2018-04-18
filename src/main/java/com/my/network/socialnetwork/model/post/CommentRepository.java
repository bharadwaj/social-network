package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = :commentId")
    List<Comment> allCommentsOfPost(@Param("commentId") Long commentId);
}
