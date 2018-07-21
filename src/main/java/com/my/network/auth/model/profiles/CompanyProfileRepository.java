package com.my.network.auth.model.profiles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CompanyProfileRepository extends CrudRepository<CompanyProfile, String> {

    @Query("SELECT p FROM CompanyProfile p WHERE p.user.userId = :userId")
    CompanyProfile findCompanyProfileByUserId(@Param("userId") String userId);
}
