package com.HomeSahulat.service;

import com.HomeSahulat.dto.ServiceProviderDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceProviderService {
    ServiceProviderDto save(ServiceProviderDto serviceProviderDto);
    String uploadCnic(Long id, MultipartFile file);
    List<ServiceProviderDto> getAll();
    List<ServiceProviderDto> getAllUnVerifiedServiceProvider(Boolean verify);
    void verifyServiceProvider(Long id, Boolean verify);
    List<ServiceProviderDto> getServiceProviderByService(String service);
    ServiceProviderDto findById(Long id);
    ServiceProviderDto findByUserId(Long id);
    void deleteById(Long id);
    ServiceProviderDto update(Long id, ServiceProviderDto serviceProviderDto);
}

