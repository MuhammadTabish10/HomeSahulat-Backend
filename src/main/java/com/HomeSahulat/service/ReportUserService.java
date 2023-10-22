package com.HomeSahulat.service;

import com.HomeSahulat.dto.ReportUserDto;

import java.util.List;

public interface ReportUserService {
    ReportUserDto save(ReportUserDto reportUserDto);
    List<ReportUserDto> getAll();
    ReportUserDto findById(Long id);
    void deleteById(Long id);
    ReportUserDto update(Long id, ReportUserDto reportUserDto);
}
