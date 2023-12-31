package com.HomeSahulat.service.impl;

import com.HomeSahulat.Util.Helper;
import com.HomeSahulat.dto.ServiceProviderDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.ServiceProvider;
import com.HomeSahulat.repository.ServiceProviderRepository;
import com.HomeSahulat.repository.ServicesRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.ServiceProviderService;
import com.amazonaws.services.budgets.model.DuplicateRecordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;
    private final ServicesRepository servicesRepository;
    private final Helper helper;
    private static final Logger logger = LoggerFactory.getLogger(bucketServiceImpl.class);


    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository, UserRepository userRepository, ServicesRepository servicesRepository, Helper helper) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.userRepository = userRepository;
        this.servicesRepository = servicesRepository;
        this.helper = helper;
    }

    @Override
    @Transactional
    public ServiceProviderDto save(ServiceProviderDto serviceProviderDto) {
        ServiceProvider serviceProvider = toEntity(serviceProviderDto);

        // Check if there is already a record with the same user ID
        Optional<ServiceProvider> existingServiceProvider = serviceProviderRepository.findByUser_Id(serviceProvider.getUser().getId());
        if (existingServiceProvider.isPresent()) {
            throw new RecordNotFoundException("ServiceProvider already exists");
        }

        serviceProvider.setStatus(true);
        serviceProvider.setCnicUrl("url");
        serviceProvider.setTotalRating(0.0);
        serviceProvider.setAtWork(true);
        serviceProvider.setVerified(false);

        serviceProvider.setServices(servicesRepository.findById(serviceProvider.getServices().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service not found for id => %d", serviceProvider.getServices().getId()))));

        serviceProvider.setUser((userRepository.findById(serviceProvider.getUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("User not found for id => %d", serviceProvider.getUser().getId())))));

        ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
        return toDto(createdServiceProvider);
    }

    @Override
    @Transactional
    public String uploadCnic(Long id, MultipartFile file) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for id => %d", id)));

        if (file != null && !file.isEmpty()) {
            String folderName = "ServiceProvider_" + serviceProvider.getId();
            String savedUrl = helper.saveCnicToS3(file, folderName);
            serviceProvider.setCnicUrl(savedUrl);
            logger.info("File is uploaded to S3 in folder '{}'.", folderName);
            serviceProviderRepository.save(serviceProvider);
            return "File uploaded successfully";
        }
        return "File upload failed";
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
    @Transactional
    public void verifyServiceProvider(Long id) {
        serviceProviderRepository.setVerifiedTrue(id);
    }

    @Override
    public List<ServiceProviderDto> getServiceProviderByService(String service) {
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findByServiceNameAndStatusTrue(service);
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
    public ServiceProviderDto findByUserId(Long id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findByUser_Id(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Service Provider not found for Userid => %d", id)));
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

        ServiceProvider updatedServiceProvider = serviceProviderRepository.save(existingServiceProvider);
        return toDto(updatedServiceProvider);
    }

    public ServiceProviderDto toDto(ServiceProvider serviceProvider) {
        return ServiceProviderDto.builder()
                .id(serviceProvider.getId())
                .createdAt(serviceProvider.getCreatedAt())
                .description(serviceProvider.getDescription())
                .cnicNo(serviceProvider.getCnicNo())
                .hourlyPrice(serviceProvider.getHourlyPrice())
                .totalExperience(serviceProvider.getTotalExperience())
                .totalRating(serviceProvider.getTotalRating())
                .atWork(serviceProvider.getAtWork())
                .haveShop(serviceProvider.getHaveShop())
                .status(serviceProvider.getStatus())
                .verified(serviceProvider.getVerified())
                .user(serviceProvider.getUser())
                .services(serviceProvider.getServices())
                .build();
    }

    public ServiceProvider toEntity(ServiceProviderDto serviceProviderDto) {
        return ServiceProvider.builder()
                .id(serviceProviderDto.getId())
                .createdAt(serviceProviderDto.getCreatedAt())
                .description(serviceProviderDto.getDescription())
                .cnicNo(serviceProviderDto.getCnicNo())
                .hourlyPrice(serviceProviderDto.getHourlyPrice())
                .totalExperience(serviceProviderDto.getTotalExperience())
                .totalRating(serviceProviderDto.getTotalRating())
                .atWork(serviceProviderDto.getAtWork())
                .haveShop(serviceProviderDto.getHaveShop())
                .status(serviceProviderDto.getStatus())
                .verified(serviceProviderDto.getVerified())
                .user(serviceProviderDto.getUser())
                .services(serviceProviderDto.getServices())
                .build();
    }
}
