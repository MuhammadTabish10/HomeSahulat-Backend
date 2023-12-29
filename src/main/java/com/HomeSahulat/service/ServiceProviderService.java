package com.HomeSahulat.service;

import com.HomeSahulat.dto.ServiceProviderDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceProviderService {
    ServiceProviderDto save(ServiceProviderDto serviceProviderDto, MultipartFile file);
    List<ServiceProviderDto> getAll();
    List<ServiceProviderDto> getServiceProviderByService(String service);
    ServiceProviderDto findById(Long id);
    void deleteById(Long id);
    ServiceProviderDto update(Long id, ServiceProviderDto serviceProviderDto);
}

