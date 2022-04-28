package com.ncedu.nc_edu.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface PictureStorageService {
    UUID upload(MultipartFile file);

    boolean existsById(UUID id);

    InputStream getById(UUID id);
}
