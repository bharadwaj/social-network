package com.my.network.socialnetwork.model.pincode;

import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {
    State findByName(String state);
}
