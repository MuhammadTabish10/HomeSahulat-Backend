package com.HomeSahulat.service.impl;

import com.HomeSahulat.dto.PermissionDto;
import com.HomeSahulat.model.Permission;
import com.HomeSahulat.service.PermissionService;
import com.HomeSahulat.exception.RecordNotFoundException;
import com.HomeSahulat.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<PermissionDto> getAll() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();

        for (Permission permission : permissionList) {
            PermissionDto permissionDto = toDto(permission);
            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }

    @Override
    public PermissionDto findById(Long id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);

        if (optionalPermission.isPresent()) {
            Permission permission = optionalPermission.get();
            return toDto(permission);
        } else {
            throw new RecordNotFoundException(String.format("Permission not found for id => %d", id));
        }
    }

    public PermissionDto toDto(Permission permission) {
        return PermissionDto.builder()
                .id(permission.getId())
                .name(permission.getName())
                .value(permission.getValue())
                .build();
    }

    public Permission toEntity(PermissionDto permissionDto) {
        return Permission.builder()
                .id(permissionDto.getId())
                .name(permissionDto.getName())
                .value(permissionDto.getValue())
                .build();
    }
}
