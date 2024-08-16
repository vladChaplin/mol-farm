package com.mol_ferma.web.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mol_ferma.web.service.GcsStorageService;
import com.mol_ferma.web.service.impl.GcsStorageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration
public class GcsConfig {

    @Value("${gcs.credentials.file}")
    private String credentialsFilePath;

    @Value("${gcs.bucket.name}")
    private String bucketName;

    @Bean
    public Storage googleStorage() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(credentialsFilePath))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        return StorageOptions.newBuilder().setCredentials(googleCredentials).build().getService();
    }

    @Bean
    public GcsStorageService gcsStorageService(Storage storage) {
        return new GcsStorageServiceImpl(storage, bucketName);
    }
}
