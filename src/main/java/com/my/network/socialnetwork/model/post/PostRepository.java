package com.my.network.socialnetwork.model.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    @Query("select p from Post p where p.user.id = :userId ORDER BY p.id desc ")
    Page<Post> findAllByPostsByUserId(@Param("userId") String userId, Pageable pageable);

    //OR p.postVisibility.visibleToUsers.id = :userId
    @Query("select p from Post p where p.isPublicPost = true ORDER BY p.createDate desc")
    List<Post> feedOfUser(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.isPublicPost = true ORDER BY p.createDate desc")
    Page<Post> pageFeedOfUser(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.uniqueHandle = :uniqueHandle")
    Post findByUniqueHandle(@Param("uniqueHandle") String uniqueHandle);

    @Query("select p from Post p where p.reportAbuseCount > 0 ORDER BY p.reportAbuseCount desc")
    List<Post> getAllReportedPosts();

    @Query("select p from Post p where p.user.id = :userId AND p.isPublicPost = true ORDER BY p.id desc ")
    Page<Post> findAllProfilePostsOfNonFriend(@Param("userId") String userId, Pageable pageable);
}
