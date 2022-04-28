package com.ncedu.nc_edu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("/admin")
    public ResponseEntity<?> adminRoot() {
        return ResponseEntity.ok("admin rt");
    }
}
