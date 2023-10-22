package com.HomeSahulat.controller;

import com.HomeSahulat.dto.ReviewDto;
import com.HomeSahulat.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.save(reviewDto));
    }

    @GetMapping("/review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReviewDto>> getAllReview() {
        List<ReviewDto> reviewDtoList = reviewService.getAll();
        return ResponseEntity.ok(reviewDtoList);
    }

    @GetMapping("/review/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.findById(id);
        return ResponseEntity.ok(reviewDto);
    }

    @DeleteMapping("/review/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/review/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id,@Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReviewDto = reviewService.update(id, reviewDto);
        return ResponseEntity.ok(updatedReviewDto);
    }
}
