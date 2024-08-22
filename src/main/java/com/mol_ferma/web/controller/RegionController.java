package com.mol_ferma.web.controller;

import com.mol_ferma.web.dto.RegionDto;
import com.mol_ferma.web.models.Region;
import com.mol_ferma.web.service.RegionService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String showRegions(HttpServletRequest request, Model model) {
        Region region = new Region();
        List<RegionDto> regions = regionService.findAllRegions();

        model.addAttribute("region", region);
        model.addAttribute("regions_list", regions);
        model.addAttribute("currentURI", request.getRequestURI());
        
        return "regions";
    }

    @PostMapping("/regions/create")
    public String createNewRegion(@ModelAttribute("region") RegionDto regionDto) {
        regionService.createRegion(regionDto);
        return "redirect:/regions";
    }

    @GetMapping("/regions/{regionId}/edit")
    public String showEditRegionForm(@PathVariable("regionId") Long regionId,
                             HttpServletRequest request,
                             Model model) {
        RegionDto regionDto = regionService.findByRegionId(regionId);
        List<RegionDto> regions = regionService.findAllRegions();

        model.addAttribute("region", regionDto);
        model.addAttribute("regions_list", regions);
        model.addAttribute("currentURI", request.getRequestURI());

        return "regions";
    }

    @PostMapping("/regions/{regionId}/edit")
    public String updateRegion(@PathVariable("regionId") Long regionId,
                                @ModelAttribute("region") RegionDto regionDto) {

        regionService.updateRegion(regionDto);
        return "redirect:/regions";
    }

    @GetMapping("/regions/{regionId}/delete")
    public String deleteRegion(@PathVariable("regionId") Long regionId) {
        regionService.deleteRegion(regionId);
        return "redirect:/regions";
    }



}
