package com.my.network.auth.model.profiles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ServiceCenterProfileRepository extends CrudRepository<ServiceCenterProfile, String> {

    @Query("SELECT p FROM ServiceCenterProfile p WHERE p.user.userId = :userId")
    ServiceCenterProfile findServiceCenterProfileByUserId(@Param("userId") String userId);
}
