package com.my.network.socialnetwork.model.pincode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictRepository extends CrudRepository<District, Long> {

    @Query("select d from md_District d INNER JOIN d.pincodes dp where dp.pincode= :pincode")
    District findDistrictByPincode(@Param("pincode") int pincode);
}
