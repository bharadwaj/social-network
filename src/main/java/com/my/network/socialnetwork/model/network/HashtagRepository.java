package com.my.network.socialnetwork.model.network;
import com.my.network.socialnetwork.model.SubscribedUser;
import com.my.network.socialnetwork.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface HashtagRepository extends PagingAndSortingRepository<Hashtag, Long> {

    //Get Trending Hashtags

    @Query("select hp from Hashtag h JOIN h.posts hp WHERE h.hashtag= :hashtag ORDER BY hp.createDate DESC")
    Page<Post> allPostsOfAHastag(@Param("hashtag") String hashtag, Pageable pageable);

    @Query("select h.subscribedUsers from Hashtag h where h.hashtag= :hashtag")
    Page<SubscribedUser> allUsersOfAHastag(@Param("hashtag") String hashtag, Pageable pageable);

    @Query("select h from Hashtag h where h.hashtag= :hashtag")
    Hashtag hashTagByHashtag(@Param("hashtag") String hashtag);

    //UPDATE Tag t set t.count = t.count + 1 WHERE t.id = :id;
    //TODO update Hashtag Counts and Stats.
    /*@Query("select count(h) from Hashtag h where h.hashtag= :hashtag")
    void countHastagUsers();
    void countHashtagPosts();*/
}
