package com.my.network.socialnetwork.model.network.group;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGroupRepository extends CrudRepository<UserGroup, Long>{
    @Query("select u  from UserGroup u where u.hashtag = :hashtag")
    UserGroup findDistinctByHashtag(@Param("hashtag") String hashtag);
}
