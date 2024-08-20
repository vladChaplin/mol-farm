package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.RegionDto;
import com.mol_ferma.web.models.Region;
import com.mol_ferma.web.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/regions")
    public String createNewRegionForm(Model model) {
        Region region = new Region();
        model.addAttribute("region", region);

        List<RegionDto> regions = regionService.findAllRegions();
        model.addAttribute("regions_list", regions);

        return "regions";
    }

    @PostMapping("/regions/create")
    public String createNewRegion(@ModelAttribute("region") RegionDto regionDto, Model model) {
        regionService.createRegion(regionDto);
        return "redirect:/regions";
    }
}
