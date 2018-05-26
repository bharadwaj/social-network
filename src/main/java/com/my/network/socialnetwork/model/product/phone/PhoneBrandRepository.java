package com.my.network.socialnetwork.model.product.phone;

import org.springframework.data.repository.CrudRepository;

public interface PhoneBrandRepository extends CrudRepository<PhoneBrand, Long>{

    PhoneBrand findPhoneBrandByName(String name);
}
