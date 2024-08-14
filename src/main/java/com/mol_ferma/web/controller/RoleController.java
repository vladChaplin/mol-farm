package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.RoleDto;
import com.mol_ferma.web.service.RoleService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String listRoles(Model model){
        List<RoleDto> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);

        return "roles-list";
    }
}
