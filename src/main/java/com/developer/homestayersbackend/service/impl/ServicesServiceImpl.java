package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.ServiceCategoryCreationRequest;
import com.developer.homestayersbackend.dto.ServiceCreationRequest;
import com.developer.homestayersbackend.entity.ServiceCategory;
import com.developer.homestayersbackend.entity.Services;
import com.developer.homestayersbackend.repository.ServiceCategoryRepository;
import com.developer.homestayersbackend.repository.ServicesRepository;
import com.developer.homestayersbackend.service.api.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository servicesRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;


    @Override
    public List<Services> getServicesByCategory(Long categoryId) {
        List<Services> servicesList = new ArrayList<>();
        Optional<ServiceCategory> serviceCategory = serviceCategoryRepository.findById(categoryId);
        if(serviceCategory.isPresent()) {
            servicesList = servicesRepository.findByCategoryId(categoryId);
            return servicesList;
        }
        return null;
    }

    @Override
    public ServiceCategory addServicesCategory(ServiceCategoryCreationRequest request) {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setDescription(request.getDescription());
        serviceCategory.setName(request.getName());
        serviceCategory.setIconUrl(request.getIconUrl());
        return serviceCategoryRepository.save(serviceCategory);
    }

    @Override
    public ServiceCategory updateServicesCategory(ServiceCategoryCreationRequest request) {
        return null;
    }

    @Override
    public ServiceCategory deleteServicesCategory(ServiceCategoryCreationRequest request) {
        return null;
    }

    @Override
    public List<ServiceCategory> getAllServicesCategory() {
        return serviceCategoryRepository.findAll();
    }

    @Override
    public Services addService(ServiceCreationRequest request) {

        Services service = new Services();
        Optional<ServiceCategory> serviceCategory = serviceCategoryRepository.findById(request.getCategoryId());
        if (serviceCategory.isPresent()) {
            service.setCategory(serviceCategory.get());
        }
        service.setDescription(request.getDescription());
        service.setName(request.getName());
        service.setIconUrl(request.getIconUrl());
        return servicesRepository.save(service);
    }

    @Override
    public Services updateService(ServiceCreationRequest request) {
        return null;
    }

    @Override
    public Services deleteService(ServiceCreationRequest request) {
        return null;
    }

    @Override
    public List<Services> getAllServices() {
        return servicesRepository.findAll();
    }

    @Override
    public List<ServiceCategory> addCategories(List<ServiceCategoryCreationRequest> request) {

       List<ServiceCategory> dbServiceCategories = request.stream().map((cat)->{
            ServiceCategory serviceCategory = new ServiceCategory();
            serviceCategory.setDescription(cat.getDescription());
            serviceCategory.setName(cat.getName());
            serviceCategory.setIconUrl(cat.getIconUrl());
            return serviceCategoryRepository.save(serviceCategory);
        }).toList();
       return dbServiceCategories;
    }


    @Override
    public List<Services> addServices(List<ServiceCreationRequest> request) {

            List<Services> services = request.stream().map((serv)->{
                Services service = new Services();
                Optional<ServiceCategory> serviceCategory = serviceCategoryRepository.findById(serv.getCategoryId());
                service.setDescription(serv.getDescription());
                service.setName(serv.getName());
                if(serviceCategory.isPresent()) {
                    service.setCategory(serviceCategory.get());
                }
                service.setIconUrl(serv.getIconUrl());
                return servicesRepository.save(service);
            }).toList();


            return services;

         }
}
