package com.developer.homestayersbackend.controller;


import com.developer.homestayersbackend.dto.*;

import com.developer.homestayersbackend.entity.*;

import com.developer.homestayersbackend.service.api.PropertyService;
import com.developer.homestayersbackend.util.ListingType;
import com.developer.homestayersbackend.util.ListingTypeDto;
import com.developer.homestayersbackend.util.RentalTypeDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/props")
    public String getString(){
        return "Hey Fella";
    }


    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/addCustomHouseRules")
    public ResponseEntity<String> addCustomHouseRules(@PathVariable("propertyId") Long propertyId,@RequestBody CustomHouseRuleRequest dto){

        return ResponseEntity.ok(propertyService.addCustomHouseRule(propertyId,dto));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editGettingAroundDetails")
    public  ResponseEntity<String> editGettingAroundDetails(@PathVariable("propertyId")Long propertyId,@RequestBody EditNeighbourhoodDetailsDto editNeighbourhoodDetailsDto){

        return ResponseEntity.ok(propertyService.editGettingAroundDetails(editNeighbourhoodDetailsDto.getDetails(),propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editNeighbourhoodDetails")
    public ResponseEntity<String> editNeighbourhoodDetails(@PathVariable("propertyId")Long propertyId,@RequestBody EditNeighbourhoodDetailsDto editNeighbourhoodDetailsDto){
                return ResponseEntity.ok(propertyService.editNeighbourhoodDetails(editNeighbourhoodDetailsDto.getDetails(),propertyId));
    }


    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editGuestCount")
    public ResponseEntity<String> editGuestCount(@PathVariable("propertyId") Long propertyId,@RequestBody EditPropertyGuestCountDto editPropertyGuestCountDto){

        return ResponseEntity.ok(propertyService.editPropertyGuestCount(editPropertyGuestCountDto.getGuestCount(),propertyId));

    }


    @GetMapping("/sharedSpaces")
    public ResponseEntity<List<SharedSpaceDto>> getSharedSpaces(){
        return ResponseEntity.ok(propertyService.getSharedSpaces());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editAddress")
    public ResponseEntity<String> editAddress(@PathVariable("propertyId")Long propertyId,@RequestBody EditAddressDto editAddressDto){

        return ResponseEntity.ok(propertyService.editPropertyAddress(editAddressDto,propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editDescription")
    public ResponseEntity<String> editDescription(@PathVariable("propertyId")Long propertyId,@RequestBody EditPropertyDescriptionDto editPropertyDescriptionDto){
        return ResponseEntity.ok(propertyService.editPropertyDescription(editPropertyDescriptionDto.getPropertyDescription(),propertyId));
    }


    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/editTitle")
    public ResponseEntity<String> editPropertyTitle(@PathVariable("propertyId")Long propertyId,@RequestBody EditPropertyTitleDto editPropertyTitleDto){
        return ResponseEntity.ok(propertyService.editPropertyTitle(editPropertyTitleDto.getPropertyTitle(),propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ResponseEntity<List<PropertyResponseDto>> getAllListings(){
        List<PropertyResponseDto> propertyResponse = propertyService.getAllProperties();
        return ResponseEntity.ok(propertyResponse);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/rooms")
    public ResponseEntity<RoomRequest> addRoom(@RequestBody RoomRequest room){
        RoomRequest request = new RoomRequest();
        Room roomEntity  = propertyService.createRoom(room);
        return ResponseEntity.ok(request);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{roomID}/pricing")
    public ResponseEntity<Price> addRoomPricing(@PathVariable("roomID") Long roomId, @RequestBody PricingRequest request){
        return  ResponseEntity.ok(propertyService.createRoomPricing(roomId,request));
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{propertyId}/pricing")
    public  ResponseEntity<Pricing> addPropertyPricing(@PathVariable("propertyId") Long propertyId, @RequestBody PricingRequest request){

        return ResponseEntity.ok(propertyService.createPropertyPricing(propertyId,request));
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/rooms/{roomId}/editServices")
    public ResponseEntity<List<Services>> editRoomServices(@PathVariable("roomId")Long roomId,@RequestBody List<String> services){


        return ResponseEntity.ok(propertyService.editRoomServices(roomId,services));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/rooms/{roomId}/editRoomAmenities")
    public ResponseEntity<List<Amenity>> editRoomAmenities(@PathVariable("roomId")Long roomId,@RequestBody List<String> amenities){

        return ResponseEntity.ok(propertyService.editRoomAmenities(roomId,amenities));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{propertyId}/editServices")
    public ResponseEntity<List<Services>> editServices(@PathVariable("propertyId")Long propertyId ,@RequestBody List<String> services){

        return ResponseEntity.ok(propertyService.editPropertyServices(propertyId,services));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{propertyId}/editAmenities")
    public ResponseEntity<List<Amenity>> editAmenities(@PathVariable("propertyId")Long propertyId,@RequestBody List<String> amenities){

        System.out.println("My Amenities:"+amenities);
        return ResponseEntity.ok(propertyService.editPropertyAmenities(propertyId,amenities));
    }

    private List<ListingTypeResponse> sanitizeListingType(List<ListingType> listingType) {
        List<ListingTypeResponse> listingTypeResponse;
        listingTypeResponse = listingType.stream().
                map((type)->
                {
                    String name = switch (type.name()) {
                        case "ENTIRE_PLACE" -> "Entire Place";
                        case "ROOM" -> "Room";
                        case "SHARED_ROOM" -> "Shared Room";
                        case "HOME_STAYERS_EXPERIENCE" -> "Home Stayers Experience";
                        default -> "";
                    };
                    return ListingTypeResponse.builder().name(name).description(type.getDescription()).build();
                }).toList();

        return listingTypeResponse;
    }


    @PostMapping("/create-amenity")
    public ResponseEntity<Amenity> createAmenity(@RequestBody AmenityCreationRequest request) {
        return  ResponseEntity.ok().body(propertyService.createAmenity(request));
    }



    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getProperty(@PathVariable("propertyId")Long propertyId){

        PropertyResponseDto propertyResponseDto = propertyService.getProperty(propertyId);

        return ResponseEntity.ok(propertyResponseDto);

    }


    @GetMapping("/attachmentTypes")
    public ResponseEntity<List<AttachmentTypeDto>> getAttachmentTypes(){

        List<AttachmentTypeDto> attachmentTypeDtos = propertyService.getAttachmentTypes();

        return ResponseEntity.ok(attachmentTypeDtos);
    }

    @GetMapping("/room-types")
    public ResponseEntity<List<RoomTypeDto>> getRoomTypes(){

        List<RoomTypeDto> roomTypeDtos = propertyService.getRoomTypes();
        roomTypeDtos.forEach((rt)->{
            System.out.println("Room Type:"+rt.getName());
        });
        return ResponseEntity.ok().body(roomTypeDtos);
    }

    @PostMapping("/filter/getAll")
    public ResponseEntity<List<PropertyResponseDto>> getFilteredListings(@RequestBody PropertyQueryDto dto){

        return ResponseEntity.ok(propertyService.getFilteredListings(dto));

    }

    private List<String> sampleData = Arrays.asList("React","Java","React Native");


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/defaultHouseRules")
    public ResponseEntity<List<HouseRule>> getDefaultHouseRules(){

        return ResponseEntity.ok(propertyService.getDefaultHouseRules());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/searchQuery")
    public List<String> searchForProperty(@RequestParam String query){
        return  sampleData.stream().filter((item)->item
                .toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/amenities/{categoryId}")
    public ResponseEntity<List<Amenity>> getAmenities(@PathVariable int categoryId) {

        return ResponseEntity.ok(propertyService.getAmenitiesByCategory(categoryId));
    }

    @PostMapping("/amenity-category")
    public ResponseEntity<AmenityCategory> createAmenityCategory(@RequestBody AmenityCategoryCreationRequest request){

        return ResponseEntity.ok().body(propertyService.createAmenityCategory(request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/amenity-category")
    public ResponseEntity<List<AmenityCategory>> getAllAmenityCategories(){

        return ResponseEntity.ok().body(propertyService.getAmenityCategories());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/rooms/{roomId}/updateAmenities")
    public ResponseEntity<List<Amenity>> updateRoomAmenities(@PathVariable("roomId")Long roomId,@RequestBody List<String> amenities){

        return ResponseEntity.ok(propertyService.editRoomAmenities(roomId,amenities));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/rooms/{roomId}/updateRoom")
    public ResponseEntity<RoomDetailsUpdateDto> updateRoomDetails(@PathVariable("roomId")Long roomId,@RequestBody RoomDetailsUpdateDto dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateRoom(roomId,dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cancellation-policy")
    public ResponseEntity<CancellationPolicy> createCancellationPolicy(@RequestBody CancellationPolicyRequest request){
        CancellationPolicy cancellationPolicy = propertyService.addCancellationPolicy(request);

        return ResponseEntity.ok().body(cancellationPolicy);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{cancellationPolicyId}")
    public ResponseEntity<CancellationPolicy> updateCancellationPolicy(@PathVariable("cancellationPolicyId") Long cancellationPolicyId,@RequestBody CancellationPolicyRequest request){

        return ResponseEntity.ok(propertyService.updatePolicy(cancellationPolicyId, request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{propertyId}/edit/commonData")
    public ResponseEntity<EditCommonDataDto> updateCommonData(@PathVariable("propertyId")Long propertyId,@RequestBody EditCommonDataDto editCommonDataDto){
        return ResponseEntity.ok(propertyService.updateCommonData(propertyId,editCommonDataDto));
    }

    @GetMapping("/listing-types")
    public ResponseEntity<List<ListingTypeDto>> getPropertyListingTypes(){

        return ResponseEntity.ok().body(propertyService.getListingTypes());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/rental-types")
    public ResponseEntity<List<RentalTypeDto>> getRentalTypes(){
        return ResponseEntity.ok().body(propertyService.getRentalTypes());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hosts/{hostId}/getAllTypes")
    public ResponseEntity<List<CommonPropertyDto>> getCommonPropertyData(@PathVariable("hostId")Long hostId){

        return ResponseEntity.ok(propertyService.getCommonProperties(hostId));
    }



    @GetMapping("/homestay/getTypes")
    public ResponseEntity<List<String>> getHomeStayTypes(){

        return ResponseEntity.ok(propertyService.getHomeStayTypes());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/homestay/create")
    public ResponseEntity<HomeStay> createHomestay(@RequestBody HomestayCreationRequest request){

        System.out.println("Homestay:"+request);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createHomestay(request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/rental/create")
    public ResponseEntity<Rental> createRental(@RequestBody RentalCreationRequest request){
        System.out.println("My Request:"+request);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createRental(request));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<Property> createProperty(@RequestBody PropertyCreationRequest property) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(property));
    }


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/other/{propertyId}")
    public ResponseEntity<PropertyResponseDto> getOtherProperty(@PathVariable("propertyId")Long propertyId){

        return ResponseEntity.ok().body(propertyService.getProperty(propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/homestay/{propertyId}")
    public  ResponseEntity<HomeStayResponseDto> getHomeStayById(@PathVariable("propertyId")Long propertyId){

        return ResponseEntity.ok(propertyService.getHomeStayProperty(propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/rental/{propertyId}")
    public ResponseEntity<RentalResponseDto> getRentalById(@PathVariable("propertyId")Long propertyId){

        return ResponseEntity.ok(propertyService.getRentalProperty(propertyId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/hosts/{hostId}")
    public ResponseEntity<List<PropertyResponseDto>> getHostListings(@PathVariable("hostId") Long hostId){
        List<PropertyResponseDto> hostProperties = propertyService.getHostProperties(hostId);
        return  ResponseEntity.ok().body(hostProperties);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("cancellation-policy/{cancellationId}")
    public ResponseEntity<String> removeCancellationPolicy(@PathVariable("cancellationId") Long cancellationId){

        return ResponseEntity.ok(propertyService.deleteCancellationPolicy(cancellationId));
    }
}