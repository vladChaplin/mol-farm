package com.mol_ferma.web.service.impl;


import com.mol_ferma.web.dto.RoleDto;
import com.mol_ferma.web.models.Role;
import com.mol_ferma.web.repository.RoleRepository;
import com.mol_ferma.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList());
    }

    private RoleDto mapToRoleDto(Role role) {
        RoleDto roleDto = RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
        return roleDto;
    }
}
