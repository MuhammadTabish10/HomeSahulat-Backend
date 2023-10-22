package com.HomeSahulat.service;

import com.HomeSahulat.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto save(ReviewDto reviewDto);
    List<ReviewDto> getAll();
    ReviewDto findById(Long id);
    void deleteById(Long id);
    ReviewDto update(Long id, ReviewDto reviewDto);
}
