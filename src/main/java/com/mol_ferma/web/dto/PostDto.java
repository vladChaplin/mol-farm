package com.mol_ferma.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Long id;
    @NotEmpty(message = "Поле название фермы должно быть заполнено!")
    private String title;
    private String photoUrl;
    @NotEmpty(message = "Нужно добавить подробную информацию о ферме!")
    private String content;
    @NotEmpty(message = "Поле адрес должно быть заполнено!")
    private String address;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
