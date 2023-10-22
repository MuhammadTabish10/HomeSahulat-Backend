package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.LocationDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Location;
import com.HomeSahulat.repository.LocationRepository;
import com.HomeSahulat.service.LocationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public LocationDto save(LocationDto locationDto) {
        Location location = toEntity(locationDto);
        location.setStatus(true);
        Location createdLocation = locationRepository.save(location);
        return toDto(createdLocation);
    }

    @Override
    public List<LocationDto> getAll() {
        List<Location> locationList = locationRepository.findAll();
        List<LocationDto> locationDtoList = new ArrayList<>();

        for (Location location : locationList) {
            LocationDto locationDto = toDto(location);
            locationDtoList.add(locationDto);
        }
        return locationDtoList;
    }

    @Override
    public LocationDto findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));
        return toDto(location);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));
        locationRepository.setStatusInactive(location.getId());
    }

    @Override
    @Transactional
    public LocationDto update(Long id, LocationDto locationDto) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Location not found for id => %d", id)));

        existingLocation.setName(locationDto.getName());
        existingLocation.setAddress(locationDto.getAddress());
        existingLocation.setCity(locationDto.getCity());
        existingLocation.setState(locationDto.getState());
        existingLocation.setPostalCode(locationDto.getPostalCode());
        existingLocation.setCountry(locationDto.getCountry());
        existingLocation.setLatitude(locationDto.getLatitude());
        existingLocation.setLongitude(locationDto.getLongitude());
        existingLocation.setState(locationDto.getState());

        Location updatedLocation = locationRepository.save(existingLocation);
        return toDto(updatedLocation);
    }

    public LocationDto toDto(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .status(location.getStatus())
                .address(location.getAddress())
                .city(location.getCity())
                .postalCode(location.getPostalCode())
                .country(location.getCountry())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public Location toEntity(LocationDto locationDto) {
        return Location.builder()
                .id(locationDto.getId())
                .name(locationDto.getName())
                .status(locationDto.getStatus())
                .address(locationDto.getAddress())
                .city(locationDto.getCity())
                .postalCode(locationDto.getPostalCode())
                .country(locationDto.getCountry())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .build();
    }
}
