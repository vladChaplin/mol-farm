package com.mol_ferma.web.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface GcsStorageService {
    String uploadFile(File file, String fileName);
    boolean deleteFile(String fileUrlGCS);
}
