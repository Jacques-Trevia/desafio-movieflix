package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findByGenre(Pageable pageable, String genreId){
		
		Long id = null;
		if(!genreId.isBlank() && !genreId.equals("0")) {
			id = Long.parseLong(genreId);
		}
		
		Page<Movie> result = repository.findByGenre(id, pageable);
		return result.map(movie -> new MovieCardDTO(movie));
	}
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return new MovieDetailsDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findReviewByMovieId(String movieId){
		
		Long id = null;
		if(movieId != null && !movieId.isBlank() && !movieId.equals("0")) {
			id = Long.parseLong(movieId);
		}
		
		List<Review> result = reviewRepository.findByMovieId(id);
		return result.stream().map(x -> new ReviewDTO(x)).toList();
	}
}