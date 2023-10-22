package com.HomeSahulat.service;

import com.HomeSahulat.dto.ServicesDto;

import java.util.List;

public interface ServicesService {
    ServicesDto save(ServicesDto servicesDto);
    List<ServicesDto> getAll();
    ServicesDto findById(Long id);
    void deleteById(Long id);
    ServicesDto update(Long id, ServicesDto servicesDto);
}
