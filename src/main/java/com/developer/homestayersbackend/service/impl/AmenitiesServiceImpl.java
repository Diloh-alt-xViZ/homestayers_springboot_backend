package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.AmenityCategoryCreationRequest;
import com.developer.homestayersbackend.dto.AmenityCreationRequest;
import com.developer.homestayersbackend.entity.Amenity;
import com.developer.homestayersbackend.entity.AmenityCategory;
import com.developer.homestayersbackend.exception.AmenityCategoryExistsException;
import com.developer.homestayersbackend.exception.AmenityCategoryNotFoundException;
import com.developer.homestayersbackend.exception.AmenityExistsException;
import com.developer.homestayersbackend.exception.AmenityNotFoundException;
import com.developer.homestayersbackend.repository.AmenityCategoryRepository;
import com.developer.homestayersbackend.repository.AmenityRepository;
import com.developer.homestayersbackend.service.api.AmenitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AmenitiesServiceImpl implements AmenitiesService {

    private final AmenityCategoryRepository amenityCategoryRepository;
    private final AmenityRepository amenityRepository;


    @Override
    public List<Amenity> addAmenities(List<AmenityCreationRequest> amenityCreationRequestList) {
        return amenityCreationRequestList.stream().map((am)->{
            Amenity amenity = new Amenity();
            AmenityCategory amenityCategory = amenityCategoryRepository.findById(am.getCategoryId()).orElseThrow(AmenityCategoryNotFoundException::new);
            amenity.setIconUrl(am.getIconUrl());
            amenity.setDescription(am.getDescription());
            amenity.setName(am.getName());
            amenity.setCategory(amenityCategory);
            return amenityRepository.save(amenity);
        }).collect(Collectors.toList());
    }

    @Override
    public List<AmenityCategory> addCategories(List<AmenityCategoryCreationRequest> request) {

        return request.stream().map((am)->{
            AmenityCategory amenityCategory = new AmenityCategory();
            amenityCategory.setName(am.getCategoryName());
            amenityCategory.setDescription(am.getDescription());
            amenityCategory.setIconUrl(am.getIconUrl());
            return amenityCategoryRepository.save   (amenityCategory);
        }).toList();
    }



    @Override
    public Amenity addAmenity(AmenityCreationRequest amenityCreationRequest) {
        Optional<Amenity> dbAmenity = amenityRepository.findByName(amenityCreationRequest.getName());
        Optional<AmenityCategory> dbAmenityCategory = amenityCategoryRepository.findById(amenityCreationRequest.getCategoryId());

        Amenity savedAmenityEntity = new Amenity();
        if(dbAmenity.isPresent()){
            throw new AmenityExistsException();
        }
        if(dbAmenityCategory.isEmpty()){
            throw new AmenityCategoryNotFoundException();
        }

        savedAmenityEntity.setName(amenityCreationRequest.getName());
        savedAmenityEntity.setDescription(amenityCreationRequest.getDescription());
        savedAmenityEntity.setCategory(dbAmenityCategory.get());
        savedAmenityEntity.setIconUrl(amenityCreationRequest.getIconUrl());
        return amenityRepository.save(savedAmenityEntity);
    }

    @Override
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    @Override
    public String deleteCategory(int categoryId) throws AmenityCategoryNotFoundException {
        String message = "Category deleted successfully";
        Optional<AmenityCategory>   amenityCategory =     amenityCategoryRepository.findById(categoryId);
        if(amenityCategory.isPresent()) {
            amenityCategoryRepository.delete(amenityCategory.get());
            return  message;
        }
        else{
            throw new AmenityCategoryNotFoundException();
        }

    }

    @Override
    public String deleteAmenity(Long amenityId) {
        String message = "Amenity deleted successfully";
        Optional<Amenity> dbAmenity = amenityRepository.findById(amenityId);
        if(dbAmenity.isPresent()) {
            amenityRepository.delete(dbAmenity.get());
            return  message;
        }
        else{
            throw new AmenityNotFoundException();
        }
    }

    @Override
    public AmenityCategory getAmenityCategory(int categoryId) throws AmenityCategoryNotFoundException {
        return amenityCategoryRepository.findById(categoryId).orElseThrow(AmenityCategoryNotFoundException::new);
    }

    @Override
    public AmenityCategory addAmenityCategory(AmenityCategoryCreationRequest amenityCategory) throws AmenityCategoryExistsException {
        AmenityCategory amenityCategoryEntity = new AmenityCategory();
        Optional<AmenityCategory> dbAmenityCategory = amenityCategoryRepository.findByName(amenityCategory.getCategoryName());
        if(dbAmenityCategory.isPresent()) {
            throw new AmenityCategoryExistsException();
        }
        amenityCategoryEntity.setName(amenityCategory.getCategoryName());
        amenityCategoryEntity.setDescription(amenityCategory.getDescription());
        System.out.println(amenityCategoryEntity);
        return amenityCategoryRepository.save(amenityCategoryEntity);
    }

    @Override
    public Amenity editAmenity(Long amenityId, AmenityCreationRequest amenityCreationRequest) {
        Amenity dbAmenity = getAmenity(amenityId);
        AmenityCategory category = getAmenityCategory(amenityCreationRequest.getCategoryId());

        if(dbAmenity != null) {
            if(category != null && category.getId() != dbAmenity.getCategory().getId()){
                dbAmenity.setCategory(category);
            }
            if(!dbAmenity.getName().equals(amenityCreationRequest.getName())){
                dbAmenity.setName(amenityCreationRequest.getName());
            }

            if(!dbAmenity.getDescription().equals(amenityCreationRequest.getDescription())){
                dbAmenity.setDescription(amenityCreationRequest.getDescription());
            }
            return amenityRepository.save(dbAmenity);

        }
        else throw new AmenityNotFoundException();
    }

    @Override
    public Amenity getAmenity(Long amenityId){
        Optional<Amenity> dbAmenity = amenityRepository.findById(amenityId);
        if(dbAmenity.isPresent()) {
            return dbAmenity.get();
        }
        else {
            throw new AmenityNotFoundException();
        }
    }
    @Override
    public AmenityCategory updateCategory(int id,AmenityCategoryCreationRequest request) {
        Optional<AmenityCategory> amenityCategory = amenityCategoryRepository.findById(id);

        if(amenityCategory.isPresent()){
            AmenityCategory amenityCategoryEntity = amenityCategory.get();
            amenityCategoryEntity.setName(request.getCategoryName());
            amenityCategoryEntity.setDescription(request.getDescription());
            return amenityCategoryRepository.save(amenityCategoryEntity);
        }
        else{
         throw new AmenityCategoryNotFoundException();
        }
    }

    @Override
    public List<AmenityCategory> getAllCategories(){
        return amenityCategoryRepository.findAll();
       }
}
