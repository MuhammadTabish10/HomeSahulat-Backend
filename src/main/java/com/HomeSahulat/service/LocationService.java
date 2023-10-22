package com.HomeSahulat.service;

import com.HomeSahulat.dto.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto save(LocationDto locationDto);
    List<LocationDto> getAll();
    LocationDto findById(Long id);
    void deleteById(Long id);
    LocationDto update(Long id, LocationDto locationDto);
}
