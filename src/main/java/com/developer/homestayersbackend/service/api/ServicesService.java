package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.ServiceCategoryCreationRequest;
import com.developer.homestayersbackend.dto.ServiceCreationRequest;
import com.developer.homestayersbackend.entity.ServiceCategory;
import com.developer.homestayersbackend.entity.Services;

import java.util.List;

public interface ServicesService {

    ServiceCategory addServicesCategory(ServiceCategoryCreationRequest request);
    ServiceCategory updateServicesCategory(ServiceCategoryCreationRequest request);
    ServiceCategory deleteServicesCategory(ServiceCategoryCreationRequest request);
    List<ServiceCategory> getAllServicesCategory();
    Services addService(ServiceCreationRequest request);
    Services updateService(ServiceCreationRequest request);
    Services deleteService(ServiceCreationRequest request);
    List<Services> getAllServices();
    List<ServiceCategory> addCategories(List<ServiceCategoryCreationRequest> request);
    List<Services> addServices(List<ServiceCreationRequest> request);

    List<Services> getServicesByCategory(Long categoryId);
}
