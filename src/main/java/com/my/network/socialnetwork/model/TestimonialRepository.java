package com.my.network.socialnetwork.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TestimonialRepository extends PagingAndSortingRepository<Testimonial, Long> {

    @Query("SELECT t from Testimonial t WHERE t.user.id = :userId")
    Page<Testimonial> findTestimonialsByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT t from Testimonial t WHERE t.author.id = :userId")
    Page<Testimonial> findTestimonialsByAuthorId(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT AVG(t.rating) from Testimonial t WHERE t.user.id = :userId AND t.rating > 0")
    double getAverageRatingsOfUser(@Param("userId") String userId);
}
