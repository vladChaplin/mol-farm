package com.mol_ferma.web.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.mol_ferma.web.service.GcsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class GcsStorageServiceImpl implements GcsStorageService {

    private final Storage storage;
    private final String bucketName;

    @Autowired
    public GcsStorageServiceImpl(Storage storage, @Value("${gcs.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    @Override
    public String uploadFile(File file, String fileName) {

        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));
            return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to GCS", e);
        }
    }

    @Override
    public boolean deleteFile(String fileName) {
        return false;
    }
}
