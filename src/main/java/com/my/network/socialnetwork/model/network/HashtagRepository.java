package com.my.network.socialnetwork.model.network;
import com.my.network.socialnetwork.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface HashtagRepository extends PagingAndSortingRepository<Hashtag, Long> {

    //Get Trending Hashtags

    @Query("select h.posts from Hashtag h where h.hashtag= :hashtag")
    Page<Post> allPostsOfAHastag(@Param("hashtag") String hashtag, Pageable pageable);

    @Query("select h.subscribedUsers from Hashtag h where h.hashtag= :hashtag")
    Page<Post> allUsersOfAHastag(@Param("hashtag") String hashtag, Pageable pageable);

    @Query("select h from Hashtag h where h.hashtag= :hashtag")
    Hashtag hashTagByHashtag(@Param("hashtag") String hashtag);
}