package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.ServicesDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Services;
import com.HomeSahulat.repository.ServicesRepository;
import com.HomeSahulat.service.ServicesService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository servicesRepository;

    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    @Transactional
    public ServicesDto save(ServicesDto servicesDto) {
        Services services = toEntity(servicesDto);
        services.setStatus(true);
        Services createdServices = servicesRepository.save(services);
        return toDto(createdServices);
    }

    @Override
    public List<ServicesDto> getAll() {
        List<Services> serviceList = servicesRepository.findAll();
        List<ServicesDto> servicesDtoList = new ArrayList<>();

        for (Services services : serviceList) {
            ServicesDto servicesDto = toDto(services);
            servicesDtoList.add(servicesDto);
        }
        return servicesDtoList;
    }

    @Override
    public ServicesDto findById(Long id) {
        Services services = servicesRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for id => %d", id)));
        return toDto(services);
    }

    @Override
    public ServicesDto findByName(String name) {
        Services services = servicesRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for name => %s", name)));
        return toDto(services);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Services services = servicesRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for id => %d", id)));
        servicesRepository.setStatusInactive(services.getId());
    }

    @Override
    @Transactional
    public ServicesDto update(Long id, ServicesDto servicesDto) {
        Services existingService = servicesRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for id => %d", id)));

        existingService.setName(servicesDto.getName());
        existingService.setDescription(servicesDto.getDescription());
        existingService.setStatus(servicesDto.getStatus());

        Services updatedService = servicesRepository.save(existingService);
        return toDto(updatedService);
    }

    public ServicesDto toDto(Services services) {
        return ServicesDto.builder()
                .id(services.getId())
                .name(services.getName())
                .description(services.getDescription())
                .status(services.getStatus())
                .build();
    }

    public Services toEntity(ServicesDto servicesDto) {
        return Services.builder()
                .id(servicesDto.getId())
                .name(servicesDto.getName())
                .description(servicesDto.getDescription())
                .status(servicesDto.getStatus())
                .build();
    }
}
