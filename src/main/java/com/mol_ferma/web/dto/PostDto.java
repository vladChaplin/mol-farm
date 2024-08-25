package com.mol_ferma.web.dto;

import com.mol_ferma.web.models.Region;
import com.mol_ferma.web.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty(message = "Поле название фермы должно быть заполнено!")
    private String title;
    private String photoUrl;
    private MultipartFile photoFile;
    @NotEmpty(message = "Нужно добавить подробную информацию о ферме!")
    private String content;
    @NotEmpty(message = "Поле адрес должно быть заполнено!")
    private String address;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Region region;
    private UserEntity createdBy;

    public boolean isNullPhotoFile() {
        return photoFile == null || photoFile.isEmpty();
    }

    public boolean isNullPhotoUrl() {
        return photoUrl == null || photoUrl.isEmpty();
    }
}
