package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
    @Query("select count(p) from PostLike p where p.post.id = :postId")
    int likeCount(Long postId);

    @Query("select p from PostLike p where p.post.id = :postId")
    List<PostLike> allLikesOfPost(Long postId);

    @Query("select p from PostLike p where p.user.id = :userId")
    List<PostLike> allLikesOfUser(Long userId);
}
