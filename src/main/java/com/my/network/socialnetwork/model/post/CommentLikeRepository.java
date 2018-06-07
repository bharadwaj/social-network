package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikeRepository extends CrudRepository<CommentLike, Long> {

    @Query("select count(p) from CommentLike p where p.comment.id = :commentId")
    int likeCount(@Param("commentId") Long commentId);

    /*@Query("select c from CommentLike c where c.post.id = :commentId")
    List<CommentLike> allLikesOfComment(Long commentId);*/

    @Query("select p from CommentLike p where p.user.id = :userId AND p.comment.id = :commentId")
    CommentLike didUserLikeThisComment(@Param("userId") String userId, @Param("commentId") Long commentId);
}
