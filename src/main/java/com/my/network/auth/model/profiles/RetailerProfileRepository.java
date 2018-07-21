package com.my.network.auth.model.profiles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RetailerProfileRepository extends CrudRepository<RetailerProfile, String> {


    @Query("SELECT p FROM RetailerProfile p WHERE p.user.userId = :userId")
    RetailerProfile findRetailerProfileByUserId(@Param("userId") String userId);
}
