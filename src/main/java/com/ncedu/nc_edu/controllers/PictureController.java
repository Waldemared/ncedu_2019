package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.exceptions.EntityDoesNotExistsException;
import com.ncedu.nc_edu.services.PictureStorageService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
public class PictureController {
    private PictureStorageService pictureStorageService;

    public PictureController(@Autowired PictureStorageService pictureStorageService) {
        this.pictureStorageService = pictureStorageService;
    }

    @PostMapping("/pictures")
    public ResponseEntity<JSONObject> upload(@RequestParam("file") MultipartFile file) {
        UUID id = this.pictureStorageService.upload(file);

        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("status", HttpStatus.CREATED.value());

        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    @GetMapping("/pictures/{id}")
    public ResponseEntity<Resource> get(@PathVariable UUID id) {
        if (!this.pictureStorageService.existsById(id)) {
            throw new EntityDoesNotExistsException("Picture with id " + id.toString());
        }

        InputStream stream = this.pictureStorageService.getById(id);
        InputStreamResource resource = new InputStreamResource(stream);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-type", "image");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
