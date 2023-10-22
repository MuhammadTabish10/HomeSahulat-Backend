package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.ReportUserDto;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.model.ReportUser;
import com.HomeSahulat.repository.ReportUserRepository;
import com.HomeSahulat.repository.UserRepository;
import com.HomeSahulat.service.ReportUserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportUserServiceImpl implements ReportUserService {

    private final ReportUserRepository reportUserRepository;
    private final UserRepository userRepository;

    public ReportUserServiceImpl(ReportUserRepository reportUserRepository, UserRepository userRepository) {
        this.reportUserRepository = reportUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ReportUserDto save(ReportUserDto reportUserDto) {
        ReportUser reportUser = toEntity(reportUserDto);
        reportUser.setStatus(true);

        reportUser.setReportToUser(userRepository.findById(reportUser.getReportToUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Report to user not found for id => %d", reportUser.getReportToUser().getId()))));
        reportUser.setReportFromUser(userRepository.findById(reportUser.getReportFromUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Report from user not found for id => %d", reportUser.getReportFromUser().getId()))));

        ReportUser createdReportUser = reportUserRepository.save(reportUser);
        return toDto(createdReportUser);
    }

    @Override
    public List<ReportUserDto> getAll() {
        List<ReportUser> reportUserList = reportUserRepository.findAll();
        List<ReportUserDto> reportUserDtoList = new ArrayList<>();

        for (ReportUser reportUser : reportUserList) {
            ReportUserDto reportUserDto = toDto(reportUser);
            reportUserDtoList.add(reportUserDto);
        }
        return reportUserDtoList;
    }

    @Override
    public ReportUserDto findById(Long id) {
        ReportUser reportUser = reportUserRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ReportUser not found for id => %d", id)));
        return toDto(reportUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ReportUser reportUser = reportUserRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ReportUser not found for id => %d", id)));
        reportUserRepository.setStatusInactive(reportUser.getId());
    }

    @Override
    @Transactional
    public ReportUserDto update(Long id, ReportUserDto reportUserDto) {
        ReportUser existingReportUser = reportUserRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("ReportUser not found for id => %d", id)));

        existingReportUser.setStatus(reportUserDto.getStatus());
        existingReportUser.setNote(reportUserDto.getNote());

        existingReportUser.setReportFromUser(userRepository.findById(reportUserDto.getReportFromUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Report from user not found for id => %d", reportUserDto.getReportFromUser().getId()))));
        existingReportUser.setReportToUser(userRepository.findById(reportUserDto.getReportToUser().getId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Report to user not found for id => %d", reportUserDto.getReportToUser().getId()))));

        ReportUser updatedReportUser = reportUserRepository.save(existingReportUser);
        return toDto(updatedReportUser);
    }

    public ReportUserDto toDto(ReportUser reportUser) {
        return ReportUserDto.builder()
                .id(reportUser.getId())
                .createdAt(reportUser.getCreatedAt())
                .note(reportUser.getNote())
                .reportFromUser(reportUser.getReportFromUser())
                .reportToUser(reportUser.getReportToUser())
                .status(reportUser.getStatus())
                .build();
    }

    public ReportUser toEntity(ReportUserDto reportUserDto) {
        return ReportUser.builder()
                .id(reportUserDto.getId())
                .createdAt(reportUserDto.getCreatedAt())
                .note(reportUserDto.getNote())
                .reportFromUser(reportUserDto.getReportFromUser())
                .reportToUser(reportUserDto.getReportToUser())
                .status(reportUserDto.getStatus())
                .build();
    }
}
