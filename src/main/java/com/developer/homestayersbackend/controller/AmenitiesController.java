package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.dto.AmenityCategoryCreationRequest;
import com.developer.homestayersbackend.dto.AmenityCreationRequest;
import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.AmenityCategory;
import com.developer.homestayersbackend.service.api.AmenitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/amenities")
public class AmenitiesController {
    private final AmenitiesService amenitiesService;


    @PostMapping("/category/addCategories")
    public ResponseEntity<List<AmenityCategory>> addCategories(@RequestBody List<AmenityCategoryCreationRequest> request){

        return ResponseEntity.status(HttpStatus.CREATED).body(amenitiesService.addCategories(request));
    }

    @PostMapping("/addAmenities")
    public ResponseEntity<List<Amenity>> addAmenities(@RequestBody List<AmenityCreationRequest> amenityCreationRequestList){

        return ResponseEntity.status(HttpStatus.CREATED).body(amenitiesService.addAmenities(amenityCreationRequestList));
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{amenityId}")
    public ResponseEntity<Amenity> getAmenity(@PathVariable Long amenityId) {

        return ResponseEntity.ok(amenitiesService.getAmenity(amenityId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<Amenity>> getAllAmenities(){

        return ResponseEntity.ok(amenitiesService.getAllAmenities());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestBody AmenityCreationRequest amenityCreationRequest){

        return ResponseEntity.ok(amenitiesService.addAmenity(amenityCreationRequest));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{amenityId}")
    public ResponseEntity<String> deleteAmenity(@PathVariable Long amenityId){

        return ResponseEntity.ok(amenitiesService.deleteAmenity(amenityId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PutMapping("/{amenityId}")
    public ResponseEntity<Amenity> editAmenity(@PathVariable Long amenityId , @RequestBody AmenityCreationRequest amenityCreationRequest){

        return ResponseEntity.ok(amenitiesService.editAmenity(amenityId,amenityCreationRequest));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/categories")
    public ResponseEntity<List<AmenityCategory>> getCategories() {
        return ResponseEntity.ok(amenitiesService.getAllCategories());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<AmenityCategory> updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody AmenityCategoryCreationRequest request){
        return ResponseEntity.ok(amenitiesService.updateCategory(categoryId,request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") int categoryId){
        return ResponseEntity.ok(amenitiesService.deleteCategory(categoryId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<AmenityCategory> createCategory(@RequestBody AmenityCategoryCreationRequest category) {

        return ResponseEntity.ok().body(amenitiesService.addAmenityCategory(category));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<AmenityCategory> getCategory(@PathVariable("categoryId") int categoryId){

        return ResponseEntity.ok().body(amenitiesService.getAmenityCategory(categoryId));
    }
}
