package com.HomeSahulat.controller;

import com.HomeSahulat.dto.ReportUserDto;
import com.HomeSahulat.service.ReportUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportUserController {
    private final ReportUserService reportUserService;

    public ReportUserController(ReportUserService reportUserService) {
        this.reportUserService = reportUserService;
    }

    @PostMapping("/report-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportUserDto> createReportUser(@Valid @RequestBody ReportUserDto reportUserDto) {
        return ResponseEntity.ok(reportUserService.save(reportUserDto));
    }

    @GetMapping("/report-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ReportUserDto>> getAllReportUser() {
        List<ReportUserDto> reportUserDtoList = reportUserService.getAll();
        return ResponseEntity.ok(reportUserDtoList);
    }

    @GetMapping("/report-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportUserDto> getReportUserById(@PathVariable Long id) {
        ReportUserDto reportUserDto = reportUserService.findById(id);
        return ResponseEntity.ok(reportUserDto);
    }

    @DeleteMapping("/report-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteReportUser(@PathVariable Long id) {
        reportUserService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/report-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ReportUserDto> updateReportUser(@PathVariable Long id,@Valid @RequestBody ReportUserDto reportUserDto) {
        ReportUserDto updatedReportUserDto = reportUserService.update(id, reportUserDto);
        return ResponseEntity.ok(updatedReportUserDto);
    }
}
