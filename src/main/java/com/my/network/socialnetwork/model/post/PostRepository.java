package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("select p from Post p where p.user.id = :userId ORDER BY p.id desc ")
    List<Post> findAllByPostsByUserId(@Param("userId") String userId);

    //OR p.postVisibility.visibleToUsers.id = :userId
    @Query("select p from Post p where p.isPublicPost = true AND p.user.id <> :userId ORDER BY p.id desc")
    List<Post> feedOfUser(@Param("userId") String userId);

    @Query("select p from Post p where p.uniqueHandle = :uniqueHandle")
    Post findByUniqueHandle(@Param("uniqueHandle") String uniqueHandle);
}
