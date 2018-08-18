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
    @Query("select p from Post p where p.isPublicPost = true ORDER BY p.promotionFactor DESC, p.createDate desc")
    List<Post> feedOfUser(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.isPublicPost = true ORDER BY p.promotionFactor DESC, p.createDate desc")
    Page<Post> pageFeedOfUser(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.uniqueHandle = :uniqueHandle")
    Post findByUniqueHandle(@Param("uniqueHandle") String uniqueHandle);

    @Query("select p from Post p where p.reportAbuseCount > 0 ORDER BY p.reportAbuseCount desc")
    List<Post> getAllReportedPosts();

    @Query("select p from Post p where p.user.id = :userId AND p.isPublicPost = true ORDER BY p.id desc ")
    Page<Post> findAllProfilePostsOfNonFriend(@Param("userId") String userId, Pageable pageable);

    /**
     * select
     *     A.*
     * from
     *     social_network_3.Post A
     * inner join social_network_3.Following B
     *     on A.user_id = B.following_user_id Where A.isPublicPost = false AND B.user_id = (current user.) '427598b5-b769-4d7a-842e-0d36f36324b1'
     * */
    @Query("select p from Post p where p.promotionFactor > 0 ORDER BY p.promotionFactor DESC, p.createDate desc")
    List<Post> feedPostOfPromotions(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.postVisibility pv JOIN pv.visibleToUsers pvu " +
            "WHERE p.isPostVisibleToUsers = true " +
            "AND pvu.id = :userId " +
            "ORDER BY p.promotionFactor DESC, p.createDate desc")
    List<Post> feedPostOfDirectShare(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.isFriendsOnlyPost = true " +
            "AND p.user.id IN (SELECT f.user.id FROM Following f WHERE f.followingUser.id = :userId AND f.isApproved = true ) " +
            "AND p.promotionFactor = 0 " +
            "ORDER BY p.createDate desc")
    List<Post> feedPostsOfFriends(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.isPublicPost = true ORDER BY p.promotionFactor DESC, p.createDate desc")
    List<Post> feedPostOfHashtags(@Param("userId") String userId, Pageable pageable);

    @Query("select p from Post p where p.isPublicPost = true AND p.promotionFactor = 0 ORDER BY p.promotionFactor DESC, p.createDate desc")
    List<Post> feedPostsOfPublic(@Param("userId") String userId, Pageable pageable);

    /*@Query(value = "SELECT p (select p from Post p where p.promotionFactor > 0 ORDER BY p.promotionFactor DESC, p.createDate desc "+
            " UNION "+
            "SELECT p FROM Post p JOIN p.postVisibility pv JOIN pv.visibleToUsers pvu " +
            "WHERE p.isPostVisibleToUsers = true " +
            "AND pvu.id = :userId " +
            "ORDER BY p.promotionFactor DESC, p.createDate desc"+
            " UNION "+
            "select p from Post p where p.isFriendsOnlyPost = true " +
            "AND p.user.id IN (SELECT f.user.id FROM Following f WHERE f.followingUser.id = :userId AND f.isApproved = true ) " +
            "AND p.promotionFactor = 0 " +
            "ORDER BY p.createDate desc" +
            " UNION " +
            "select p from Post p where p.isPublicPost = true AND p.promotionFactor = 0 ORDER BY p.promotionFactor DESC, p.createDate desc", nativeQuery = true)
    List<Post> unifiedFeedv2(@Param("userId") String userId, Pageable pageable);*/
}
