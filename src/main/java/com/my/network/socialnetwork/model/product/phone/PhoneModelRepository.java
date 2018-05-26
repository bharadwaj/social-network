package com.my.network.socialnetwork.model.product.phone;

import com.my.network.socialnetwork.model.product.phone.PhoneModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneModelRepository extends CrudRepository<PhoneModel, Long>{

    //@Query("select p from PhoneModel p where p.name like % :name%")
    List<PhoneModel> findPhoneModelByNameIgnoreCaseContaining(@Param("name") String name);
}
