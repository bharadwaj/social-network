package com.my.network.socialnetwork.model.pincode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * SELECT md_District.id, md_District.districtName, md_District.state_id, md_Pincode.pincode
 * FROM md_District
 * INNER JOIN md_Pincode ON md_District.id=md_Pincode.district_id
 * WHERE md_Pincode.pincode = 110001;
 *
 * */

public interface DistrictRepository extends CrudRepository<District, Long> {

    @Query("select d from MD_District d INNER JOIN d.pincodes dp where dp.pincode= :pincode")
    District findDistrictByPincode(@Param("pincode") int pincode);
}
