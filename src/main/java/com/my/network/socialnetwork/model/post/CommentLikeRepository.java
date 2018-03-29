package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentLikeRepository extends CrudRepository<CommentLike, Long> {
    /*@Query("select count(c) from CommentLike c where c.post.id = :commentId")
    int likeCount(Long commentId);*/

    /*@Query("select c from CommentLike c where c.post.id = :commentId")
    List<CommentLike> allLikesOfComment(Long commentId);*/
}
