package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.AmenityCategoryCreationRequest;
import com.developer.homestayersbackend.dto.AmenityCreationRequest;
import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.AmenityCategory;
import com.developer.homestayersbackend.exception.AmenityCategoryExistsException;
import com.developer.homestayersbackend.exception.AmenityCategoryNotFoundException;

import java.util.List;

public interface AmenitiesService {

    List<Amenity> addAmenities(List<AmenityCreationRequest> amenityCreationRequestList);

    AmenityCategory updateCategory(int id,AmenityCategoryCreationRequest request) throws AmenityCategoryNotFoundException;
    AmenityCategory addAmenityCategory(AmenityCategoryCreationRequest amenityCategory) throws AmenityCategoryExistsException;
    List<AmenityCategory> getAllCategories() ;
    AmenityCategory getAmenityCategory(int categoryId) throws AmenityCategoryNotFoundException;

    String deleteCategory(int categoryId) throws AmenityCategoryNotFoundException;

    List<Amenity> getAllAmenities();

    Amenity addAmenity(AmenityCreationRequest amenityCreationRequest);

    String deleteAmenity(Long amenityId);
    Amenity getAmenity(Long amenityId);
    Amenity editAmenity(Long amenityId, AmenityCreationRequest amenityCreationRequest);

    List<AmenityCategory> addCategories(List<AmenityCategoryCreationRequest> request);
}
