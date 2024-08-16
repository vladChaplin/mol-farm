package com.mol_ferma.web.utils;

import com.mol_ferma.web.service.GcsStorageService;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@UtilityClass
public class FileUploadUtil {

    public static String uploadFileToGCS(MultipartFile multipartFile, GcsStorageService gcsStorageService) {
        StringBuilder strBuilder = new StringBuilder(LocalDateTime.now().getSecond());
        String fileName = strBuilder.append("_").append(multipartFile.getOriginalFilename()).toString();
        String urlFileFull;

        try {
            File tempFile = File.createTempFile("upload-", fileName);
            multipartFile.transferTo(tempFile);
            urlFileFull = gcsStorageService.uploadFile(tempFile, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error upload file Google Cloud Storage!" + e);
        }
        return  urlFileFull;
    }
}
