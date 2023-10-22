package com.HomeSahulat.service;

import com.HomeSahulat.dto.ServiceProviderDto;

import java.util.List;

public interface ServiceProviderService {
    ServiceProviderDto save(ServiceProviderDto serviceProviderDto);
    List<ServiceProviderDto> getAll();
    ServiceProviderDto findById(Long id);
    void deleteById(Long id);
    ServiceProviderDto update(Long id, ServiceProviderDto serviceProviderDto);
}

