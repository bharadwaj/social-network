package com.my.network.socialnetwork.model.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findAllByPostsByUserId(Long userId);

    //OR p.postVisibility.visibleToUsers.id = :userId
    @Query("select p from Post p where p.isPublicPost = true AND p.user.id <> :userId")
    List<Post> feedOfUser(Long userId);
}
