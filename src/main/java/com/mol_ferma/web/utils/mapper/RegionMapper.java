package com.mol_ferma.web.utils.mapper;

import com.mol_ferma.web.dto.RegionDto;
import com.mol_ferma.web.models.Region;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RegionMapper {
    public static Region mapToRegion(RegionDto regionDto) {
        return Region.builder()
                .id(regionDto.getId())
                .name(regionDto.getName())
                .latitude(regionDto.getLatitude())
                .longitude(regionDto.getLongitude())
                .posts(regionDto.getPosts())
                .build();
    }

    public static RegionDto mapToRegionDto(Region region) {
        return RegionDto.builder()
                .id(region.getId())
                .name(region.getName())
                .latitude(region.getLatitude())
                .longitude(region.getLongitude())
                .posts(region.getPosts())
                .build();
    }
}
