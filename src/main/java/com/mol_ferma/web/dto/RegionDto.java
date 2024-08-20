package com.mol_ferma.web.dto;

import com.mol_ferma.web.models.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Set<Post> posts = new HashSet<>();
}
