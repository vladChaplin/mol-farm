package com.mol_ferma.web.service;


import com.mol_ferma.web.dto.RegionDto;

import java.util.List;

public interface RegionService {
    void createRegion(RegionDto regionDto);
    List<RegionDto> findAllRegions();
    RegionDto findByRegionId(Long regionId);
    void updateRegion(RegionDto regionDto);
    void deleteRegion(Long regionId);
}
