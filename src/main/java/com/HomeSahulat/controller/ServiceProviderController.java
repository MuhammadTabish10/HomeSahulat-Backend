package com.HomeSahulat.controller;

import com.HomeSahulat.Util.Helper;
import com.HomeSahulat.dto.ServiceProviderDto;
import com.HomeSahulat.service.BucketService;
import com.HomeSahulat.service.ServiceProviderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;
    private final BucketService bucketService;
    private final Helper helper;

    public ServiceProviderController(ServiceProviderService serviceProviderService, BucketService bucketService, Helper helper) {
        this.serviceProviderService = serviceProviderService;
        this.bucketService = bucketService;
        this.helper = helper;
    }

    @PostMapping("/service-provider")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> createServiceProvider(@Valid @RequestBody ServiceProviderDto serviceProviderDto) {
        return ResponseEntity.ok(serviceProviderService.save(serviceProviderDto));
    }

    @PostMapping("/service-provider/{id}/upload")
    public ResponseEntity<String> uploadServiceProviderCnic(@RequestParam("file")  MultipartFile file,
                                                            @PathVariable Long id) {
        return ResponseEntity.ok(serviceProviderService.uploadCnic(id,file));
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

    @GetMapping("/service-provider/verified/{verify}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ServiceProviderDto>> getAllServiceProvidersByVerify(@PathVariable Boolean verify) {
        List<ServiceProviderDto> serviceProviderDtoList = serviceProviderService.getAllUnVerifiedServiceProvider(verify);
        return ResponseEntity.ok(serviceProviderDtoList);
    }

    @GetMapping("/service-provider/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> getServiceProviderById(@PathVariable Long id) {
        ServiceProviderDto serviceProviderDto = serviceProviderService.findById(id);
        return ResponseEntity.ok(serviceProviderDto);
    }

    @GetMapping("/service-provider/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceProviderDto> getServiceProviderByUserId(@PathVariable Long id) {
        ServiceProviderDto serviceProviderDto = serviceProviderService.findByUserId(id);
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

    @PutMapping("/service-provider/{id}/verify/{verify}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> verifyServiceProvider(@PathVariable(name = "id") Long id,
                                                      @PathVariable(name = "verify") Boolean verify) {
        serviceProviderService.verifyServiceProvider(id,verify);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/file/{folderType}/{folderName}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable String folderName,
            @PathVariable String fileName,
            @PathVariable String folderType
    ) {
        try {
            byte[] fileContent = bucketService.downloadFile(folderName, fileName, folderType);

            // Determine the media type based on the file extension
            MediaType mediaType = helper.determineMediaType(fileName);

            // Return the file content as a ResponseEntity with appropriate headers
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error downloading file: " + e.getMessage()).getBytes());
        }
    }
}
