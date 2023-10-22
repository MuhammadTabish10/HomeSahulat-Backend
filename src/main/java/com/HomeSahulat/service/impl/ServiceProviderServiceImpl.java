package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.ServiceProviderDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.Attachment;
import com.HomeSahulat.model.Review;
import com.HomeSahulat.model.Role;
import com.HomeSahulat.model.ServiceProvider;
import com.HomeSahulat.repository.AttachmentRepository;
import com.HomeSahulat.repository.ServiceProviderRepository;
import com.HomeSahulat.repository.ServicesRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.ServiceProviderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final ServicesRepository servicesRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository, UserRepository userRepository, AttachmentRepository attachmentRepository, ServicesRepository servicesRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.servicesRepository = servicesRepository;
    }

    @Override
    @Transactional
    public ServiceProviderDto save(ServiceProviderDto serviceProviderDto) {
        ServiceProvider serviceProvider = toEntity(serviceProviderDto);
        serviceProvider.setStatus(true);

        serviceProvider.setServices(servicesRepository.findById(serviceProvider.getServices().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for id => %d", serviceProvider.getServices().getId()))));

        serviceProvider.setUser((userRepository.findById(serviceProvider.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", serviceProvider.getUser().getId())))));

        ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);

        List<Attachment> attachmentList = serviceProvider.getAttachment();
        if(attachmentList != null && !attachmentList.isEmpty()){
            for (Attachment attachment : attachmentList) {
                attachment.setServiceProvider(createdServiceProvider);
                attachment.setStatus(true);
                attachmentRepository.save(attachment);
            }
            createdServiceProvider.setAttachment(attachmentList);
            serviceProviderRepository.save(createdServiceProvider);
        }
        return toDto(createdServiceProvider);
    }

    @Override
    public List<ServiceProviderDto> getAll() {
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        List<ServiceProviderDto> serviceProviderDtoList = new ArrayList<>();

        for (ServiceProvider serviceProvider : serviceProviderList) {
            ServiceProviderDto serviceProviderDto = toDto(serviceProvider);
            serviceProviderDtoList.add(serviceProviderDto);
        }
        return serviceProviderDtoList;
    }

    @Override
    public ServiceProviderDto findById(Long id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", id)));
        return toDto(serviceProvider);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", id)));
        serviceProviderRepository.setStatusInactive(serviceProvider.getId());
    }

    @Override
    @Transactional
    public ServiceProviderDto update(Long id, ServiceProviderDto serviceProviderDto) {
        int count = 0;
        ServiceProvider existingServiceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", id)));

        existingServiceProvider.setStatus(serviceProviderDto.getStatus());
        existingServiceProvider.setDescription(serviceProviderDto.getDescription());
        existingServiceProvider.setHourlyPrice(serviceProviderDto.getHourlyPrice());
        existingServiceProvider.setTotalExperience(serviceProviderDto.getTotalExperience());
        existingServiceProvider.setTotalRating(serviceProviderDto.getTotalRating());
        existingServiceProvider.setAtWork(serviceProviderDto.getAtWork());
        existingServiceProvider.setHaveShop(serviceProviderDto.getHaveShop());

        existingServiceProvider.setServices(servicesRepository.findById(serviceProviderDto.getServices().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Services not found for id => %d", serviceProviderDto.getServices().getId()))));
        existingServiceProvider.setUser(userRepository.findById(serviceProviderDto.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", serviceProviderDto.getUser().getId()))));

        List<Attachment> existingAttachmentValues = existingServiceProvider.getAttachment();
        List<Attachment> newAttachmentValues = serviceProviderDto.getAttachment();
        List<Attachment> newValuesToAdd = new ArrayList<>();

        Iterator<Attachment> iterator = existingAttachmentValues.iterator();
        while (iterator.hasNext()) {
            Attachment existingAttachment = iterator.next();
            if (newAttachmentValues.stream().noneMatch(attachment -> attachment.getId().equals(existingAttachment.getId()))) {
                iterator.remove();
                attachmentRepository.delete(existingAttachment);
            }
        }

        for (Attachment newValue : newAttachmentValues) {
            Optional<Attachment> existingValue = existingAttachmentValues.stream()
                    .filter(aValue -> aValue.getId().equals(newValue.getId())).findFirst();
            if (existingValue.isPresent()) {
                Attachment existingAttachmentValue = existingValue.get();
                existingAttachmentValue.setName(newValue.getName());
                existingAttachmentValue.setFileUrl(newValue.getFileUrl());
                existingAttachmentValue.setStatus(newValue.getStatus());
            } else {
                newValue.setServiceProvider(existingServiceProvider);
                newValuesToAdd.add(newValue);
                count++;
            }
        }
        if (count > 0) {
            existingAttachmentValues.addAll(newValuesToAdd);
        }

        ServiceProvider updatedServiceProvider = serviceProviderRepository.save(existingServiceProvider);
        return toDto(updatedServiceProvider);
    }

    public ServiceProviderDto toDto(ServiceProvider serviceProvider) {
        return ServiceProviderDto.builder()
                .id(serviceProvider.getId())
                .createdAt(serviceProvider.getCreatedAt())
                .description(serviceProvider.getDescription())
                .hourlyPrice(serviceProvider.getHourlyPrice())
                .totalExperience(serviceProvider.getTotalExperience())
                .totalRating(serviceProvider.getTotalRating())
                .atWork(serviceProvider.getAtWork())
                .haveShop(serviceProvider.getHaveShop())
                .status(serviceProvider.getStatus())
                .user(serviceProvider.getUser())
                .services(serviceProvider.getServices())
                .attachment(serviceProvider.getAttachment())
                .build();
    }

    public ServiceProvider toEntity(ServiceProviderDto serviceProviderDto) {
        return ServiceProvider.builder()
                .id(serviceProviderDto.getId())
                .createdAt(serviceProviderDto.getCreatedAt())
                .description(serviceProviderDto.getDescription())
                .hourlyPrice(serviceProviderDto.getHourlyPrice())
                .totalExperience(serviceProviderDto.getTotalExperience())
                .totalRating(serviceProviderDto.getTotalRating())
                .atWork(serviceProviderDto.getAtWork())
                .haveShop(serviceProviderDto.getHaveShop())
                .status(serviceProviderDto.getStatus())
                .user(serviceProviderDto.getUser())
                .services(serviceProviderDto.getServices())
                .attachment(serviceProviderDto.getAttachment())
                .build();
    }
}
