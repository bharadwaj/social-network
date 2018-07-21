package com.my.network.auth.model.profiles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SupplierProfileRepository extends CrudRepository<SupplierProfile, String> {

    @Query("SELECT p FROM SupplierProfile p WHERE p.user.userId = :userId")
    SupplierProfile findSupplierProfileByUserId(@Param("userId") String userId);
}
