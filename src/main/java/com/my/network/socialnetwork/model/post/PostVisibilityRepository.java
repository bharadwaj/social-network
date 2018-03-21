package com.my.network.socialnetwork.model.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVisibilityRepository extends CrudRepository<PostVisibility, Long> {
}
