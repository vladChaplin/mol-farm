package com.mol_ferma.web.service;

import com.mol_ferma.web.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleDto> findAllRoles();
}
