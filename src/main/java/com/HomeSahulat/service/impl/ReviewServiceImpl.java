package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.ReviewDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Review;
import com.HomeSahulat.repository.ReviewRepository;
import com.HomeSahulat.repository.ServiceProviderRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.ReviewService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, ServiceProviderRepository serviceProviderRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @Override
    @Transactional
    public ReviewDto save(ReviewDto reviewDto) {
        Review review = toEntity(reviewDto);
        review.setStatus(true);

        review.setUser(userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", review.getUser().getId()))));
        review.setServiceProvider(serviceProviderRepository.findById(review.getServiceProvider().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider from user not found for id => %d", review.getServiceProvider().getId()))));

        Review createdReview = reviewRepository.save(review);
        return toDto(createdReview);
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewDto reviewDto = toDto(review);
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    @Override
    public ReviewDto findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Review not found for id => %d", id)));
        return toDto(review);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Review not found for id => %d", id)));
        reviewRepository.setStatusInactive(review.getId());
    }

    @Override
    @Transactional
    public ReviewDto update(Long id, ReviewDto reviewDto) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Review not found for id => %d", id)));

        existingReview.setStatus(reviewDto.getStatus());
        existingReview.setNote(reviewDto.getNote());
        existingReview.setRating(reviewDto.getRating());

        existingReview.setServiceProvider(serviceProviderRepository.findById(reviewDto.getServiceProvider().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", reviewDto.getServiceProvider().getId()))));
        existingReview.setUser(userRepository.findById(reviewDto.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", reviewDto.getUser().getId()))));

        Review updatedReview = reviewRepository.save(existingReview);
        return toDto(updatedReview);
    }

    public ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .createdAt(review.getCreatedAt())
                .note(review.getNote())
                .rating(review.getRating())
                .status(review.getStatus())
                .user(review.getUser())
                .serviceProvider(review.getServiceProvider())
                .build();
    }

    public Review toEntity(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .createdAt(reviewDto.getCreatedAt())
                .note(reviewDto.getNote())
                .rating(reviewDto.getRating())
                .status(reviewDto.getStatus())
                .user(reviewDto.getUser())
                .serviceProvider(reviewDto.getServiceProvider())
                .build();
    }
}
