package com.my.network.socialnetwork.model.pincode;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * SELECT MD_District.id, MD_District.districtName, MD_State.name, MD_Pincode.pincode
 *  FROM MD_District
 *  INNER JOIN MD_Pincode ON MD_District.id=MD_Pincode.district_id
 *  INNER JOIN MD_State ON MD_District.state_id=MD_State.id
 *  WHERE MD_Pincode.pincode = 110001;
 *
 * */

public interface DistrictRepository extends CrudRepository<District, Long> {

    @Query("select d from MD_District d INNER JOIN d.pincodes dp where dp.pincode= :pincode")
    District findDistrictByPincode(@Param("pincode") int pincode);

}
