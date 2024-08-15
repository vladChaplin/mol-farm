package com.mol_ferma.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {
    private Long id;
    @NotEmpty(message = "Поле название фермы должно быть заполнено!")
    private String title;
    private MultipartFile photoUrl;
    @NotEmpty(message = "Нужно добавить подробную информацию о ферме!")
    @Size(max = 400, message = "Подробная информация должна содержать не более 400 символов")
    @Size(min = 10, message = "Подробная информация должна содержать не менее 10 символов")
    private String content;
    @NotEmpty(message = "Поле адрес должно быть заполнено!")
    private String address;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
