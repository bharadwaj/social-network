package com.my.network.socialnetwork.model.post;

import com.my.network.auth.model.Users;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.network.Following;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends CrudRepository<PostLike, Long> {
    @Query("select count(p) from PostLike p where p.post.id = :postId")
    int likeCount(@Param("postId") Long postId);

    @Query("select p from PostLike p where p.post.id = :postId")
    List<PostLike> allLikesOfPost(@Param("postId") Long postId);

    @Query("select p from PostLike p where p.user.id = :userId")
    List<PostLike> allLikesOfUser(@Param("userId") String userId);

    @Query("select p from PostLike p where p.user.id = :userId AND p.post.id = :postId")
    PostLike didUserLikeThisPost(@Param("userId") String userId, @Param("postId") Long postId);

    @Query(value = "select * from SubscribedUser u where u.id in (select f.id from Following f where f.id = :userId and f.isApproved = true)", nativeQuery = true)
    List<SubscribedUser> getUsersFollowers(@Param("userId") String userId);

}
