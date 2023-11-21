package com.HomeSahulat.controller;

import com.HomeSahulat.dto.ServiceProviderDto;
import com.HomeSahulat.service.ServiceProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    public ServiceProviderController(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @PostMapping("/service-provider")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> createServiceProvider(@Valid @RequestBody ServiceProviderDto serviceProviderDto) {
        return ResponseEntity.ok(serviceProviderService.save(serviceProviderDto));
    }

    @GetMapping("/service-provider")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvider() {
        List<ServiceProviderDto> serviceProviderDtoList = serviceProviderService.getAll();
        return ResponseEntity.ok(serviceProviderDtoList);
    }

    @GetMapping("/service-provider/service/{service}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersByService(@PathVariable String service) {
        List<ServiceProviderDto> serviceProviderDtoList = serviceProviderService.getServiceProviderByService(service);
        return ResponseEntity.ok(serviceProviderDtoList);
    }

    @GetMapping("/service-provider/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> getServiceProviderById(@PathVariable Long id) {
        ServiceProviderDto serviceProviderDto = serviceProviderService.findById(id);
        return ResponseEntity.ok(serviceProviderDto);
    }

    @DeleteMapping("/service-provider/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        serviceProviderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/service-provider/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> updateServiceProvider(@PathVariable Long id,@Valid @RequestBody ServiceProviderDto serviceProviderDto) {
        ServiceProviderDto updatedServiceProviderDto = serviceProviderService.update(id, serviceProviderDto);
        return ResponseEntity.ok(updatedServiceProviderDto);
    }
}
