package com.HomeSahulat.controller;

import com.HomeSahulat.dto.ServicesDto;
import com.HomeSahulat.service.ServicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ServicesController {
    private final ServicesService service;

    public ServicesController(ServicesService service) {
        this.service = service;
    }
    @PostMapping("/service")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServicesDto> createService(@Valid @RequestBody ServicesDto servicesDto) {
        return ResponseEntity.ok(service.save(servicesDto));
    }

    @GetMapping("/service")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ServicesDto>> getAllServices() {
        List<ServicesDto> servicesDtoList = service.getAll();
        return ResponseEntity.ok(servicesDtoList);
    }

    @GetMapping("/service/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServicesDto> getServicesById(@PathVariable Long id) {
        ServicesDto servicesDto = service.findById(id);
        return ResponseEntity.ok(servicesDto);
    }

    @DeleteMapping("/service/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/service/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServicesDto> updateService(@PathVariable Long id,@Valid @RequestBody ServicesDto servicesDto) {
        ServicesDto updatedServicesDto = service.update(id, servicesDto);
        return ResponseEntity.ok(updatedServicesDto);
    }
}
