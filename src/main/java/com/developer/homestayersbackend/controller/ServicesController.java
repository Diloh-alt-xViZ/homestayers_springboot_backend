package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.dto.ServiceCategoryCreationRequest;
import com.developer.homestayersbackend.dto.ServiceCreationRequest;
import com.developer.homestayersbackend.entity.ServiceCategory;
import com.developer.homestayersbackend.entity.Services;
import com.developer.homestayersbackend.service.api.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/services")
public class ServicesController {
    private final ServicesService servicesService;



    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<Services>> getServicesByCategory(@PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok(servicesService.getServicesByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<List<Services>> addServices(@RequestBody List<ServiceCreationRequest> request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(servicesService.addServices(request));

    }

    @PostMapping("/categories")
    public ResponseEntity<List<ServiceCategory>> addCategories(@RequestBody List<ServiceCategoryCreationRequest> request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(servicesService.addCategories(request));
    }

    @GetMapping("categories")
    public ResponseEntity<List<ServiceCategory>> getAllCategories(){

        return ResponseEntity.ok(servicesService.getAllServicesCategory());
    }



    @GetMapping()
    public ResponseEntity<List<Services>> getAllServices(){

        return ResponseEntity.ok(servicesService.getAllServices());
    }
}
