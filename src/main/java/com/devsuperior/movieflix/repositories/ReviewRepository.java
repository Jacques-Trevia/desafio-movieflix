package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query( "SELECT DISTINCT obj FROM Review obj " +
		       "JOIN FETCH obj.movie m " +
		       "LEFT JOIN FETCH m.genre g " +
		       "JOIN FETCH obj.user u " +
		       "LEFT JOIN FETCH u.roles rol " +
		       "WHERE obj.movie.id = :movieId")
	List<Review> findByMovieId(Long movieId);
}
