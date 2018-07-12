package com.my.network.auth.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, String> {
	@Query("SELECT u FROM Users u where u.userId = :userPhoneNumber")
	Users loadUserByUsername(@Param("userPhoneNumber") String userPhoneNumber);

	//Users loadUserByUsername(String userPhoneNumber);


}
