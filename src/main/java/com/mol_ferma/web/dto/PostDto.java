package com.mol_ferma.web.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String photoUrl;
    private String content;
    private String address;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
