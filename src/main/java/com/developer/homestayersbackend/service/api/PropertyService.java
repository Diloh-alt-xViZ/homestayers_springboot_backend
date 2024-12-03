package com.developer.homestayersbackend.service.api;

import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.*;
import com.developer.homestayersbackend.util.ListingTypeDto;
import com.developer.homestayersbackend.util.RentalTypeDto;

import java.util.List;

public interface PropertyService {
    Property createProperty(PropertyCreationRequest request) throws Exception;
    Amenity createAmenity(AmenityCreationRequest request);
    AmenityCategory createAmenityCategory(AmenityCategoryCreationRequest request);
    List<Amenity> getAmenitiesByCategory(int categoryId);
    List<AmenityCategory> getAmenityCategories();
    List<ListingTypeDto> getListingTypes();
    CancellationPolicy updatePolicy(Long policyId, CancellationPolicyRequest request);
    CancellationPolicy addCancellationPolicy(CancellationPolicyRequest request);

    String editPropertyTitle(String title, Long id);

    Property verifyProperty(Long propertyId);
    String deleteCancellationPolicy(Long cancellationId);

    Property updateProperty(PropertyCreationRequest request, Long propertyId);
    Services createService(ServicesRequest request);

    List<Services> getAllServices();
    List<RentalTypeDto> getRentalTypes();
    Services updateService(Long serviceId, ServicesRequest request);
    Room createRoom(RoomRequest room);
    Room updateRoom(RoomRequest room, Long roomId);
    Room deleteRoom(RoomRequest room);
    Pricing createPropertyPricing(Long propertyId, PricingRequest request);

    Price createRoomPricing(Long roomId, PricingRequest request);

    List<RoomTypeDto> getRoomTypes();

    List<PropertyResponseDto> getHostProperties(Long hostId);

    List<PropertyResponseDto> getAllProperties();

    String editPropertyGuestCount(int guestCount, Long propertyId);

    String editPropertyDescription(String propertyDescription, Long propertyId);

    String editPropertyAddress(EditAddressDto editAddressDto, Long propertyId);

    String editGettingAroundDetails(String details, Long propertyId);

    String editNeighbourhoodDetails(String details, Long propertyId);

    String addCustomHouseRule(Long propertyId, CustomHouseRuleRequest dto);

    List<AttachmentTypeDto> getAttachmentTypes();

    List<HouseRule> getDefaultHouseRules();

    PropertyResponseDto getProperty(Long propertyId);

    List<Amenity> editPropertyAmenities(Long propertyId, List<String> amenities);

    List<Services> editPropertyServices(Long propertyId, List<String> services);

    List<CommonPropertyDto> getCommonProperties(Long hostId);

    RentalResponseDto getRentalProperty(Long propertyId);

    Rental createRental(RentalCreationRequest request);

    List<SharedSpaceDto> getSharedSpaces();

    List<Services> editRoomServices(Long roomId, List<String> services);

    List<Amenity> editRoomAmenities(Long roomId, List<String> amenities);

    HomeStay createHomestay(HomestayCreationRequest request);

    List<String> getHomeStayTypes();

    HomeStayResponseDto getHomeStayProperty(Long propertyId);

    EditCommonDataDto updateCommonData(Long propertyId, EditCommonDataDto editCommonDataDto);

    RoomDetailsUpdateDto updateRoom(Long roomId, RoomDetailsUpdateDto dto);

    List<PropertyResponseDto> getFilteredListings(PropertyQueryDto dto);
}
