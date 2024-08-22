package com.mol_ferma.web.service.impl;

import com.mol_ferma.web.dto.RegionDto;
import com.mol_ferma.web.models.Region;
import com.mol_ferma.web.repository.RegionRepository;
import com.mol_ferma.web.service.RegionService;
import com.mol_ferma.web.utils.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public void createRegion(RegionDto regionDto) {
        Region region = RegionMapper.mapToRegion(regionDto);
        regionRepository.save(region);
    }

    @Override
    public List<RegionDto> findAllRegions() {
        List<Region> regions = regionRepository.findAll();
        return regions.stream()
                .map(RegionMapper::mapToRegionDto)
                .collect(Collectors.toList());
    }

    @Override
    public RegionDto findByRegionId(Long regionId) {
        Region region = regionRepository.findById(regionId).get();
        return RegionMapper.mapToRegionDto(region);
    }

    @Override
    public void updateRegion(RegionDto regionDto) {
        regionRepository.save(RegionMapper.mapToRegion(regionDto));
    }

    @Override
    public void deleteRegion(Long regionId) {
        regionRepository.deleteById(regionId);
    }
}
