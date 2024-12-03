package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.*;
import com.developer.homestayersbackend.entity.*;
import com.developer.homestayersbackend.exception.*;
import com.developer.homestayersbackend.repository.*;
import com.developer.homestayersbackend.service.api.PropertyService;
import com.developer.homestayersbackend.util.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final AmenityRepository amenityRepository;
    private final AmenityCategoryRepository amenityCategoryRepository;
    private final CancellationPolicyRepository cancellationPolicyRepository;
    private final ModelMapper modelMapper;
    private final ServicesRepository servicesRepository;
    private final PricingRepository pricingRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final PricingHistoryRepository pricingHistoryRepository;
    private final HostRepository hostRepository;
    private final LocationRepository locationRepository;
    private final PhotoRepository photoRepository;
    private final HouseRuleRepository houseRuleRepository;
    private final UserProfileRepository userProfileRepository;
    private final CustomHouseRuleRepository customHouseRuleRepository;
    private final AvailableDatesRepository availableDatesRepository;
    private final RoomAttachmentRepository roomAttachmentRepository;
    private final UserRepository userRepository;
    private final RentalRoomRepository rentalRoomRepository;

    @Override
    public List<HouseRule> getDefaultHouseRules() {
        List<HouseRule> rules = houseRuleRepository.findAll();
        return rules;
    }

    @Override
    public String addCustomHouseRule(Long propertyId, CustomHouseRuleRequest dto) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }

        else {
            Property dbProperty = property.get();
            List<CustomHouseRule> customHouseRules = dto.getRules().stream().map((rul)->{
                CustomHouseRule customHouseRule = new CustomHouseRule();
                customHouseRule.setRuleText(rul);
                customHouseRule.setPropertyId(dbProperty);
                return customHouseRuleRepository.save(customHouseRule);
            }).collect(toList());
            dbProperty.setCustomHouseRules(customHouseRules);
            propertyRepository.save(dbProperty);
            return "Success";
        }

    }

    @Override
    public String editGettingAroundDetails(String details, Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }

        else {
            Property dbProperty = property.get();
            dbProperty.setGettingAroundDetails(details);
            propertyRepository.save(dbProperty);
            return "Success";
        }

    }

    @Override
    public String editNeighbourhoodDetails(String details, Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }

        else {
            Property dbProperty = property.get();
            dbProperty.setNeighbourhoodDetails(details);
            propertyRepository.save(dbProperty);
            return "Success";
        }

    }

    @Override
    public String editPropertyDescription(String propertyDescription, Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }
        else{
            Property dbProperty = property.get();
            dbProperty.setDescription(propertyDescription);
            propertyRepository.save(dbProperty);
            return "Success";
        }

    }

    @Override
    public String editPropertyAddress(EditAddressDto editAddressDto, Long propertyId) {
        Optional<Property> property  = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }
        else {
            Property dbProperty = property.get();
            Address address = new Address();
            address.setCity(editAddressDto.getCity());
            address.setState(editAddressDto.getState());
            address.setAddress(editAddressDto.getAddress());
            address.setStreet(editAddressDto.getStreet());
            Location newLocation = new Location();
            newLocation.setAddress(address);
            locationRepository.save(newLocation);
            dbProperty.setLocation(newLocation);
            propertyRepository.save(dbProperty);
            return "Success";
        }
    }

    @Override
    public String editPropertyGuestCount(int guestCount, Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }

        else {
            Property dbProperty = property.get();
            propertyRepository.save(dbProperty);
            return "Success";
        }
    }

    @Override
    public List<RoomTypeDto> getRoomTypes(){

        return sanitizeRoomTypes(RoomType.values());
    }

    @Override
    public List<SharedSpaceDto> getSharedSpaces() {

        return sanitizeSharedSpaces(SharedSpace.values());
    }

    private List<SharedSpaceDto> sanitizeSharedSpaces(SharedSpace[] sharedSpaces){

        return Arrays.stream(sharedSpaces).map((sharedSpace -> {
            SharedSpaceDto dto = new SharedSpaceDto();
            dto.setName(sharedSpace.getName());
            return dto;
        })).toList();
    }

    @Override
    public List<AttachmentTypeDto> getAttachmentTypes() {
        return sanitizeAttachmentTypes(AttachmentType.values());
    }

    private List<AttachmentTypeDto> sanitizeAttachmentTypes(AttachmentType[] types){
        List<AttachmentTypeDto> typeDtoList = new ArrayList<>();
        for(AttachmentType type:types){
            AttachmentTypeDto dto = new AttachmentTypeDto();
            dto.setName(type.getName());
            typeDtoList.add(dto);
        }
        return typeDtoList;
    }

    private List<RoomTypeDto> sanitizeRoomTypes(RoomType[] roomTypes){
        List<RoomTypeDto> roomTypesList = new ArrayList<>();
        for (RoomType roomType : roomTypes){
            RoomTypeDto roomTypeDto = new RoomTypeDto();
            String roomTypeText = switch (roomType){
                case DINING -> "Dining";
                case GARAGE -> "Garage";
                case BEDROOM -> "Bedroom";
                case KITCHEN -> "Kitchen";
                case BATHROOM -> "Bathroom";
                case LIVING_ROOM -> "Living Room";
                case SINGLE_ROOM -> "Single Room";
                case DOUBLE_ROOM -> "Double Room";
                case SUITE -> "Suite";
                case FAMILY_ROOM, FAMILY_LODGE_ROOM -> "Family Room";
                case ACCESSIBLE_ROOM -> "Accessible Room";
                case STANDARD_LODGE_ROOM, STANDARD_ROOM -> "Standard Room";
                case DELUXE_LODGE_ROOM -> "Deluxe Room";
                case PRIVATE_CABIN -> "Private Cabin";
                case LUXURY_SUITE -> "Luxury Suite";
                case DELUXE_ROOM -> "Deluxe";
                case TWIN_ROOM -> "Twin Room";
                case STUDIO_ROOM -> "Studio Room";
                case LUXURY_ROOM -> "Luxury Room";
                case THEMED_ROOM -> "Themed Room";
            };
            roomTypeDto.setName(roomTypeText);
            roomTypeDto.setCategory(roomType.getCategory());
            roomTypeDto.setDescription(roomType.getDescription());
            roomTypesList.add(roomTypeDto);
        }
        return roomTypesList;
    }

    @Override
    public HomeStayResponseDto getHomeStayProperty(Long propertyId) {
        HomeStay homeStay = (HomeStay) propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);

        return setHomeStayResponse(homeStay);
    }

    @Override
    public RentalResponseDto getRentalProperty(Long propertyId) {

        Rental  prop =(Rental) propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);
        return setRentalPropertyResponse(prop);
    }

    @Override
    public List<CommonPropertyDto> getCommonProperties(Long hostId) {
        List<Property> properties = new ArrayList<>();
        List<CommonPropertyDto> dto = new ArrayList<>();
        Host host = hostRepository.findById(hostId).orElseThrow(HostNotFoundException::new);
        properties = propertyRepository.findByHostId(host.getId());
        for(Property prop: properties){
            CommonPropertyDto commonPropertyDto = getCommonPropertyDto(prop);
            dto.add(commonPropertyDto);
        }
        return dto;
    }

    private static CommonPropertyDto getCommonPropertyDto(Property prop) {
        CommonPropertyDto commonPropertyDto = new CommonPropertyDto();
        commonPropertyDto.setStartDate(prop.getCreatedAt());
        commonPropertyDto.setId(prop.getId());

        String approvalStatus = switch (prop.getApprovalStatus()){

            case PENDING -> "Pending";
            case APPROVED -> "Approved";
            case REJECTED -> "Rejected";
        };
        String listingType = switch(prop.getListingType()){

            case BED_AND_BREAKFAST -> "Bed and Breakfast";
            case LODGE -> "Lodge";
            case HOTEL -> "Hotel";
            case HOME_STAYERS_EXPERIENCE -> "Homestay";
            case RENTAL -> "Rental";
        };
        if(!(listingType.equalsIgnoreCase("Homestay") || listingType.equalsIgnoreCase("Rental"))){
            if(prop.getRooms()!=null){
                List<CalendarRoomDto> roomsDto = new ArrayList<>();
                for(Room room:prop.getRooms()){
                        CalendarRoomDto dto = new CalendarRoomDto();
                        dto.setTitle(room.getRoomTitle());
                        dto.setBookedDates(room.getBookedDates().stream().map(Date::toString).toList());
                        roomsDto.add(dto);
                }

                commonPropertyDto.setRooms(roomsDto);
            }
        }
        commonPropertyDto.setListingType(listingType);
        if(prop.getPhotos()!=null){
            List<PhotoDto> photos = new ArrayList<>();
            for(Photo photo:prop.getPhotos()){
                PhotoDto photoDto = new PhotoDto();
                photoDto.setUri(photo.getUrl());
                photos.add(photoDto);
            }
            commonPropertyDto.setPhotos(photos);
        }

        if(prop.getBookedDates()!=null) {
            List<String> bookedDates = prop.getBookedDates().stream().map(Date::toString).toList();
            commonPropertyDto.setBookedDates(bookedDates);
        }
        if(prop.getLocation()!=null){
            commonPropertyDto.setLocation(prop.getLocation());
        }
        commonPropertyDto.setApprovalStatus(approvalStatus);
        commonPropertyDto.setTitle(prop.getTitle());
        return commonPropertyDto;
    }

    @Override
    public RoomDetailsUpdateDto updateRoom(Long roomId, RoomDetailsUpdateDto dto) {

        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        if(dto.getTitle()!=null){
            room.setRoomTitle(dto.getTitle());
        }
        if(dto.getDescription()!=null){
            room.setDescription(dto.getDescription());
        }

        if(dto.getPhotos()!=null){
            List<Photo> photosList = new ArrayList<>();
            List<PhotoDto> photos = dto.getPhotos();

            for(PhotoDto photo:photos){
                Photo dbPhoto = photoRepository.findPhotoByUrl(photo.getUri());
                Photo savePhoto = new Photo();
                if(dbPhoto==null){
                    savePhoto.setUrl(photo.getUri());
                    savePhoto = photoRepository.save(savePhoto);
                    photosList.add(savePhoto);
                }
                else {
                    photosList.add(dbPhoto);
                }
            }
            room.setPhotos(photosList);
        }

        if(dto.getPrice()!=null){
            PriceDto priceDto = dto.getPrice();
            Price roomPrice = room.getPrice();
            Price price = new Price();
            if(roomPrice.getAmount().compareTo(BigDecimal.valueOf(priceDto.getPrice()))!=0) {
                Currency curr = Currency.valueOf(priceDto.getCurrency());
                price.setCurrency(curr);
                PricingModel pricingModel = switch (priceDto.getPricingModel()) {

                    case "Per Night" -> PricingModel.PER_NIGHT;
                    case "Per Day" -> PricingModel.PER_DAY;
                    case "Per Week" -> PricingModel.PER_WEEK;
                    case "Per Month" -> PricingModel.PER_MONTH;
                    default -> null;
                };
                BigDecimal amount = BigDecimal.valueOf(priceDto.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
                price.setModel(pricingModel);
                price.setAmount(amount);
                PricingHistory priceH = new PricingHistory();
                priceH.setPrice(room.getPrice());
                priceH.setDateDisabled(new Date(System.currentTimeMillis()));
                pricingHistoryRepository.save(priceH);
                room.setPrice(pricingRepository.save(price));
            }
        }
        roomRepository.save(room);
        return dto;

    }

    @Override
    public List<PropertyResponseDto> getHostProperties(Long hostId) {

        List<Property> properties = new ArrayList<>();
        List<PropertyResponseDto>  dtos = new ArrayList<>();

        Optional<Host> host = hostRepository.findById(hostId);
        if (host.isPresent()) {
            properties = propertyRepository.findByHostId(hostId);
            for (Property property : properties) {
                PropertyResponseDto dto = new PropertyResponseDto();
                dto = setPropertyResponse(property);
                dtos.add(dto);
            }
            return dtos;
        }
        else throw new HostNotFoundException();
    }

    @Override
    public EditCommonDataDto updateCommonData(Long propertyId, EditCommonDataDto editCommonDataDto) {
        Property property = (Property) propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);
        System.out.println("The Property:"+ property.getTitle());
        property.setTitle(editCommonDataDto.getTitle());
        property.setDescription(editCommonDataDto.getDescription());
        property.setNeighbourhoodDetails(editCommonDataDto.getNeighbourhoodDetails());
        property.setGettingAroundDetails(editCommonDataDto.getGettingAround());

        if(editCommonDataDto.getPrice()!=null) {
            PriceDto priceDto = editCommonDataDto.getPrice();
            Price price = new Price();
            Currency curr = Currency.valueOf(priceDto.getCurrency());
            price.setCurrency(curr);
            PricingModel pricingModel = switch (priceDto.getPricingModel()){

                case "Per Night"-> PricingModel.PER_NIGHT;
                case "Per Day"-> PricingModel.PER_DAY;
                case "Per Week"-> PricingModel.PER_WEEK;
                case "Per Month"-> PricingModel.PER_MONTH;
                default -> null;
            };
            BigDecimal amount = BigDecimal.valueOf(priceDto.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
            price.setModel(pricingModel);
            price.setAmount(amount);
            PricingHistory priceH = new PricingHistory();
            priceH.setPrice(property.getPrice());
            priceH.setDateDisabled(new Date(System.currentTimeMillis()));
            pricingHistoryRepository.save(priceH);
            property.setPrice(pricingRepository.save(price));
        }
        property.setUpdatedAt(new Date(System.currentTimeMillis()));
        Property saved = propertyRepository.save(property);
        System.out.println("Saved"+saved);
        return editCommonDataDto;
    }

    @Override
    public String editPropertyTitle(String title, Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()){
            throw new PropertyNotFoundException();
        }
        else {
            Property propertyToEdit = property.get();
            propertyToEdit.setTitle(title);
            propertyRepository.save(propertyToEdit);
            return "Success";
        }
    }

    private List<PropertyResponseDto> getPropertyResponse(List<Property> properties) {

        List<PropertyResponseDto> propertyResponseDtos = new ArrayList<>();
        for (Property property : properties) {
            PropertyResponseDto propertyResponseDto = new PropertyResponseDto();
            propertyResponseDto.setListingType(property.getListingType().toString());
            propertyResponseDto.setId(property.getId());
            propertyResponseDto.setHostDto(HostDto.builder().hostSince(property.getHost().getHostSince()).firstName(userProfileRepository.findUserProfileByUserId(property.getHost().getUser().getId()).getFirstName()).build());
            propertyResponseDto.setDescription(property.getDescription());
            propertyResponseDto.setAmenities(property.getAmenities());
            propertyResponseDto.setTitle(property.getTitle());
            //propertyResponseDto.setAvailableDate(property.getAvailableDate);
            propertyResponseDto.setNeighbourhoodDetails(property.getNeighbourhoodDetails());
            propertyResponseDto.setGettingAround(property.getGettingAroundDetails());
            System.out.println("Property Photos:"+ property.getPhotos());
            propertyResponseDto.setPhotos(property.getPhotos().stream().map(photo->{
                System.out.println("Property Photo:"+ photo.getUrl());
                PhotoDto photoDto = new PhotoDto();
                photoDto.setUri(photo.getUrl());
                return photoDto;
            }).toList());
            propertyResponseDtos.add(propertyResponseDto);
        }

        return propertyResponseDtos;
    }

    @Override
    public Property verifyProperty(Long propertyId) {
        return null;
    }


    private Room sanitizeRoom(RoomRequest roomRequest) {
        List<Services> services = null;
        List<Amenity> amenities = null;
        List<Photo> photos = null;
        //TODO:Add Pricing
        Room room = new Room();
        if(roomRequest.getServicesList()!=null){
            services = roomRequest.getServicesList().stream().map(servicesRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();

        }
    if(roomRequest.getAmenities()!=null)
        {
            amenities = roomRequest.getAmenities().stream().map(amenityRepository::findById).filter(Optional::isPresent).map(Optional::get).toList();
        }
    if(roomRequest.getPhotoList()!=null)
        {
            photos = roomRequest.getPhotoList().stream().filter(ob -> photoRepository.findPhotoByUrl(ob.getUri())==null)
                    .map(photoDto -> new Photo(photoDto.getUri()))
                    .map(photoRepository::save).toList();
            System.out.println("Photos = " + photos);
        }
            RoomType roomType;
        roomType = switch (roomRequest.getRoomType()){
            case "Bedroom"->RoomType.BEDROOM;
            case "Kitchen"->RoomType.KITCHEN;
            case "Bathroom"->RoomType.BATHROOM;
            case "Living Room"->RoomType.LIVING_ROOM;
            case "Dining"->RoomType.DINING;
            default -> null;
        };
        System.out.println(amenities);
        room.setServices(services);
        room.setAmenities(amenities);
        room.setPhotos(photos);
        System.out.println(room.getPhotos());
        room.setRoomTitle(roomRequest.getRoomTitle());
        room.setReviews(null);
        return room;
    }

    @Override
    public Room createRoom(RoomRequest room) {

        Optional<Property> property = propertyRepository.findById(room.getPropertyId());
        if (property.isPresent()) {
            Room roomEntity = sanitizeRoom(room);
            roomEntity = roomRepository.save(roomEntity);
            var dbProperty = property.get();
            propertyRepository.save(dbProperty);
            return roomEntity;
        }

        else throw new PropertyNotFoundException();

    }

    @Override
    public Room updateRoom(RoomRequest room, Long roomId) {
        Optional<Room> roomEntity = roomRepository.findById(roomId);
        if (roomEntity.isPresent()) {
            Room roomEntity1 = modelMapper.map(room, Room.class);
            return roomRepository.save(roomEntity1);
        }
        else throw new RoomNotFoundException();

    }

    @Override
    public Room deleteRoom(RoomRequest room) {
        return null;
    }

    @Override
    public Pricing createPropertyPricing(Long propertyId, PricingRequest request) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);
        Pricing pricing;
        if(property!=null){
            pricing =  modelMapper.map(property, Pricing.class);
            return pricing;
        }
        return null;
    }

    @Override
    public Price createRoomPricing(Long roomId, PricingRequest request) {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isPresent()){
            Price pricing =  modelMapper.map(room.get(), Price.class);
            return pricingRepository.save(pricing);
        }

        else throw new RoomNotFoundException();
    }

    @Override
    public Services createService(ServicesRequest request) {

        Optional<Services> services = servicesRepository.findByName(request.getTitle());
        Services serviceEntity = new Services();
        if(services.isPresent()) {
            throw new ServiceAlreadyExistsException();
        }
        serviceEntity.setName(request.getTitle());
        serviceEntity.setIconUrl(request.getIconUrl());
        return servicesRepository.save(serviceEntity);
    }

    @Override
    public List<Services> getAllServices() {
        return servicesRepository.findAll();
    }

    @Override
    public Services updateService(Long serviceId, ServicesRequest request) {

        Optional<Services> services = servicesRepository.findById(serviceId);
        Services serviceEntity = new Services();

        if(services.isPresent()) {
            serviceEntity.setName(request.getTitle());
            serviceEntity.setIconUrl(request.getIconUrl());
            return servicesRepository.save(serviceEntity);
        }

        else {
            throw  new ServiceNotFoundException();
        }

    }

    @Override
    public Property updateProperty(PropertyCreationRequest request, Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        if (property.isEmpty()) {
            throw new PropertyNotFoundException();
        }



        return null;
    }


    private List<Room> createNewRoom(PropertyCreationRequest request,Property property){

       List<Room> rooms = request.getRooms().stream().map(
               (room)->{
                   Room roomEntity = new Room();
                   roomEntity.setRoomType(getRoomTypeFromRequest(room.getRoomType()));
                   roomEntity.setRoomTitle(room.getTitle());
                   roomEntity.setDescription(roomEntity.getDescription());
                   roomEntity.setPhotos(room.getRoomPhotos().stream().map((photo)->{
                               Photo photoEntity = new Photo();
                             photoEntity.setUrl(photo.getUri());
                             return photoRepository.save(photoEntity);
                           }).toList());
                   return roomRepository.save(roomEntity);
               }
       ).collect(toList());
       return rooms;
    }

    @Override
    public List<PropertyResponseDto> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyResponseDto> responseDtos = new ArrayList<>();
        for(Property property : properties){
            PropertyResponseDto propertyResponseDto = new PropertyResponseDto();
            propertyResponseDto = setPropertyResponse(property);
            responseDtos.add(propertyResponseDto);
        }

        return responseDtos;
    }

    private boolean filterPredicate(Property prop,PropertyQueryDto dto){
            Location location = prop.getLocation();
            boolean cityPredicate = true;
            boolean rangePredicate = true;
            boolean typePredicate = true;

            if(dto.getListingType()!=null){

            }

            if(dto.getCity()!=null){
                if(!location.getAddress().getCity().equalsIgnoreCase(dto.getCity())) {
                    cityPredicate = false;
                }
            }

            return cityPredicate && rangePredicate && typePredicate;
    }

    @Override
    public List<PropertyResponseDto> getFilteredListings(PropertyQueryDto dto) {
        List<Property> properties = propertyRepository.findAll();

        return getPropertyResponse(properties.
                stream().
                filter((prop)->prop.getPrice().getAmount().doubleValue()>=dto.getMin()
                        && prop.getPrice().getAmount().doubleValue()<=dto.getMax()).filter((prop)->{
                           return prop.getListingType().equals(getListingTypeFromRequest(dto.getListingType()));
                }).toList());
    }

    private RoomType getRoomTypeFromRequest(String type){
        RoomType roomType = switch (type){
            case "Bedroom"->RoomType.BEDROOM;
            case "Kitchen"->RoomType.KITCHEN;
            case "Bathroom"->RoomType.BATHROOM;
            case "Living Room"->RoomType.LIVING_ROOM;
            case "Dining"->RoomType.DINING;
            default -> null;
        };
        return roomType;
    }

    private void setCommonPropertyFields(Property property,PropertyCreationRequest dto) throws Exception {



        property.setPhotos(dto.getPhotos().stream().map((photo)->{
            Photo photoEntity = new Photo();
            photoEntity.setUrl(photo.getUri());
            return photoRepository.save(photoEntity);
        }).toList());
        property.setHouseRules(dto.getPolicies().stream().map((rul)->{
            HouseRule ruleEntity = new HouseRule();

            return houseRuleRepository.save(ruleEntity);
        }).toList());
        Location propertyLocation = new Location();
        propertyLocation.setAddress(Address.builder().address(dto.getAddress())
                        .city(dto.getCity()).street(dto.getStreet()).state(dto.getProvince()).
                build());
        locationRepository.save(propertyLocation);
        property.setLocation(
            propertyLocation
        );
        property.setApprovalStatus(ApprovalStatus.PENDING);
        property.setRooms(createNewRoom(dto,property));
        property.setCreatedAt(new Date(System.currentTimeMillis()));
        property.setUpdatedAt(new Date(System.currentTimeMillis()));
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setNeighbourhoodDetails(dto.getNeighbourhoodDetails());
        property.setGettingAroundDetails(dto.getGettingAround());
        System.out.println("Before Error");
        List<Amenity> amenities = dto.getAmenities().stream().map(amenityRepository::findByName).filter((amenity -> amenity.isPresent())).map((it)->it.get()).collect(toList());
        System.out.println("After Error");
        System.out.println("Property Amenities:"+amenities);

        property.setAmenities(amenities);
        property.setApprovalStatus(ApprovalStatus.PENDING);
        sanitizeProperty(dto,property);
    }

    private void setRentalTypeSpecificProperties(Rental rental,PropertyCreationRequest dto){
        rental.setListingType(ListingType.RENTAL);
        rental.setRentalType(getRentalTypeFromRequest(dto.getRentalType()));
    }
    private <T extends Property> T getPropertyType(ListingType listingType,Class<T> tClass){

        //TODO: Get the appropriate property typezaees
        //TODO: CREATE THE APPROPRIATE TYPE
        //TODO: SET THE CORRECT DETAILS
        Property property =   switch(listingType){
            case RENTAL -> new Rental();
            case HOTEL -> new Hotel();
            case HOME_STAYERS_EXPERIENCE -> new HomeStay();
            case LODGE -> new Lodge();
            case BED_AND_BREAKFAST -> new BedAndBreakfast();
            default -> throw new IllegalArgumentException("Invalid listing type");
        };

        if(tClass.isInstance(property)){
            return tClass.cast(property);
        }
        else throw new ClassCastException("Unable to cast property to type:"+ tClass.getName());
    }


    private void sanitizeProperty(PropertyCreationRequest request,Property dbProperty) throws Exception {
        Optional<Host> host = hostRepository.findById(request.getHostId());
        if(host.isEmpty()) {
            throw new HostNotFoundException();
        }
        Host hostEntity = host.get();

        dbProperty.setHost(host.get());
    }


    private RoomType getRoomTypeFromDto(String dtoType){

        RoomType roomType;

        roomType = switch (dtoType) {
            case "Dining"->
                RoomType.DINING;
            case "Bedroom"->
                 RoomType.BEDROOM;
            case "Kitchen"->
                RoomType.KITCHEN;
            case "Bathroom"->
                RoomType.BATHROOM;
            case "Living Room"->
                RoomType.LIVING_ROOM;
            case "Single Room"->
                RoomType.SINGLE_ROOM;
            case "Double Room"->
                RoomType.DOUBLE_ROOM;
            case "Suite"->
                RoomType.SUITE;
            case "Family Room"->
                RoomType.FAMILY_ROOM;
            case "Accessible Room"->
                RoomType.ACCESSIBLE_ROOM;
            case "Standard Room"->
                RoomType.STANDARD_ROOM;
            case "Standard Lodge Room"->
                RoomType.STANDARD_LODGE_ROOM;
            case "Deluxe Lodge Room"->
                RoomType.DELUXE_LODGE_ROOM;
            case "Private Cabin"->
                RoomType.PRIVATE_CABIN;
            case "Luxury Suite"->
                RoomType.LUXURY_SUITE;
            case "Deluxe"->
                RoomType.DELUXE_ROOM;
            case "Twin Room"->
                RoomType.TWIN_ROOM;
            case "Studio Room"->
                RoomType.STUDIO_ROOM;
            case "Luxury Room"->
                RoomType.LUXURY_ROOM;
            case "Themed Room"->
                RoomType.THEMED_ROOM;
            default->
                throw new IllegalArgumentException("Unknown room type: " + dtoType);
        };

        return roomType;
    }

    private AttachmentType getAttachmentTypeFromDto(String type){

        return switch (type){
            case "Bathroom"->
                AttachmentType.BATHROOM;
            case "Balcony"->
                 AttachmentType.BALCONY;
            case "Kitchenette"->
                 AttachmentType.KITCHENETTE;
            case "Living Room"->
                AttachmentType.LIVING_ROOM;
            case "Closet"->
                 AttachmentType.CLOSET;
            case "Office Space"->
                 AttachmentType.OFFICE_SPACE;
            case "Mini Bar"->
                AttachmentType.MINI_BAR;
            case "Patio"->
                AttachmentType.PATIO;
            case "Private Entrance"->
                 AttachmentType.PRIVATE_ENTRANCE;
            case "Sitting Area"->
                AttachmentType.SITTING_AREA;
            case "Jacuzzi"->
                AttachmentType.JACUZZI;
            case "Storage Room"->
                AttachmentType.STORAGE_ROOM;
            case "Additional Bedroom"->
                 AttachmentType.ADDITIONAL_BEDROOM;
            case "Fireplace"->
                AttachmentType.FIREPLACE;
            case "Game Room"->
                AttachmentType.GAME_ROOM;
            case "Soundproofing"->
                AttachmentType.SOUNDPROOFING;
            default -> null;
        };
    }

    private Room createSingleRoom(RoomDto roomDto){
        Room room = new Room();
        room.setRoomTitle(roomDto.getTitle());
        room.setRoomType(getRoomTypeFromDto(roomDto.getRoomType()));
        room.setDescription(roomDto.getRoomDescription());
        if(roomDto.getPrice()!=null){
            Price price = new Price();
            Currency curr = Currency.valueOf(roomDto.getPrice().getCurrency());
            price.setCurrency(curr);
            PricingModel pricingModel = switch (roomDto.getPrice().getPricingModel()){

                case "Per Night"-> PricingModel.PER_NIGHT;
                case "Per Day"-> PricingModel.PER_DAY;
                case "Per Week"-> PricingModel.PER_WEEK;
                case "Per Month"-> PricingModel.PER_MONTH;
                default -> null;
            };
            BigDecimal amount = BigDecimal.valueOf(roomDto.getPrice().getPrice()).setScale(2, RoundingMode.HALF_EVEN);
            price.setModel(pricingModel);
            price.setAmount(amount);
            room.setPrice(pricingRepository.save(price));
        }
        if(!roomDto.getRoomPhotos().isEmpty()){

            List<Photo> photos = new ArrayList<>();
            for(PhotoDto dto: roomDto.getRoomPhotos()){
                Photo photo = new Photo();
                if(dto.getUri()!=null){
                    photo.setUrl(dto.getUri());
                   photos.add(photoRepository.save(photo));
                }
            }
            room.setPhotos(photos);
            //TODO Add Photos
        }
        if(!roomDto.getAttachments().isEmpty()){
            List<RoomAttachment> roomAttachments = new ArrayList<>();
            for(AttachmentTypeDto dto :roomDto.getAttachments()){
                RoomAttachment roomAttachment = new RoomAttachment();
                AttachmentType attachmentType = getAttachmentTypeFromDto(dto.getName());
                if (attachmentType != null) {
                    roomAttachment.setDescription(attachmentType.getDescription());
                    roomAttachment.setAttachmentType(attachmentType);
                    roomAttachments.add(roomAttachmentRepository.save(roomAttachment));
                }
            }
            room.setRoomAttachments(roomAttachments);
            //TODO Add Attachments
        }
        if(!roomDto.getAmenities().isEmpty()){
            //TODO Add Amenities
            List<Amenity> roomAmenities = new ArrayList<>();
            for(String amenity:roomDto.getAmenities()){
                Optional<Amenity> roomAmenity = amenityRepository.findByName(amenity);
                roomAmenity.ifPresent(roomAmenities::add);
            }
            room.setAmenities(roomAmenities);
        }
        if(!roomDto.getServices().isEmpty()){
            List<Services> roomServices = new ArrayList<>();
            for(String service : roomDto.getServices()){
                Optional<Services> roomService = servicesRepository.findByName(service);
                roomService.ifPresent(roomServices::add);
            }
            room.setServices(roomServices);
            //TODO Add Services
        }

        return roomRepository.save(room);
    }

    private List<Room> createRooms(List<RoomDto> rooms){
        List<Room> roomList = new ArrayList<>();
        for(RoomDto roomDto : rooms){
            var room = createSingleRoom(roomDto);
            roomList.add(room);
        }

        return roomList;
    }

    @Override
    public PropertyResponseDto getProperty(Long propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        PropertyResponseDto propertyResponse = new PropertyResponseDto();
        if(property.isEmpty()){
            throw new PropertyNotFoundException();
        }
        else {
            propertyResponse  = setPropertyResponse(property.get());
            return propertyResponse;
        }
    }


    private HomeStayResponseDto setHomeStayResponse(HomeStay property){
        HomeStayResponseDto dto = new HomeStayResponseDto();
        dto.setId(property.getId());

        if(property.getDescription()!=null){
            dto.setDescription(property.getDescription());
        }

        dto.setTitle(property.getTitle());
        if(!property.getAmenities().isEmpty()){
            List<Amenity> amenities = new ArrayList<>(property.getAmenities());
            dto.setAmenities(amenities);
        }

        if(property.getSharedSpaces()!=null){
            List<String> sharedSpaces = property.getSharedSpaces().stream()
                    .map(SharedSpace::getName).toList();
            dto.setSharedSpaces(sharedSpaces);
        }
        if(property.getNeighbourhoodDetails()!=null){
            dto.setNeighbourhoodDetails(property.getNeighbourhoodDetails());
        }

        if(property.getGettingAroundDetails()!=null){
            dto.setGettingAround(property.getGettingAroundDetails());
        }


        if(!property.getServices().isEmpty()){
            List<Services> services = new ArrayList<>(property.getServices());
            dto.setServices(services);
        }

        List<RuleDto> rules = new ArrayList<>();
        if(!property.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>(property.getPhotos());
            List<PhotoDto> photoDtos = new ArrayList<>();
            for (Photo photo:photos){
                PhotoDto photoDto = new PhotoDto();
                if(photo.getUrl()!=null){
                    photoDto.setUri(photo.getUrl());
                }
                photoDtos.add(photoDto);
            }
            dto.setPhotos(photoDtos);
        }
        if(!property.getCustomHouseRules().isEmpty()){
            List<CustomHouseRule> houseRules = new ArrayList<>(property.getCustomHouseRules());
            for(CustomHouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                rules.add(ruleDto);
            }
        }
        if(!property.getHouseRules().isEmpty()){
            List<HouseRule> houseRules = new ArrayList<>(property.getHouseRules());
            for(HouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                rules.add(ruleDto);
            }
        }
        if(!rules.isEmpty()){
            dto.setRules(rules);
        }

        if(!property.getReviews().isEmpty()){
            List<Review> reviews = new ArrayList<>(property.getReviews());

            dto.setReviews(reviews.stream().map(rev->{
                ReviewDto revDto = new ReviewDto();
                revDto.setUserId(rev.getUser().getId());
                revDto.setComment(rev.getComment());
                revDto.setRating(rev.getRating());
                revDto.setId(rev.getId());
                revDto.setCreatedAt(rev.getCreatedAt());
                revDto.setUsername(rev.getUser().getUsername());
                return revDto;
            }).toList());
        }


        if(property.getLocation()!=null){
            dto.setLocation(property.getLocation());
        }
        if(property.getHost()!=null){
            HostDto hostDto = new HostDto();
            Host host = property.getHost();
            Optional<User> user  = userRepository.findById(host.getUser().getId());
            if(user.isPresent()){
                UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.get().getId());
                if(userProfile!=null){
                    if(userProfile.getPhoto()!=null){
                        hostDto.setProfileUrl(userProfile.getPhoto().getUrl());
                    }
                    System.out.println("First Name:"+ userProfile.getFirstName());
                    hostDto.setFirstName(userProfile.getFirstName());
                }
            }
            hostDto.setId(host.getId());
            hostDto.setHostSince(host.getHostSince());
            dto.setHostDto(hostDto);
        }
        String stayType = property.getStayType().getName();
        dto.setStayType(stayType);
        PriceDto priceDto= new PriceDto();
        if(property.getPrice()!=null) {
            priceDto.setCurrency(property.getPrice().getCurrency().toString());

            priceDto.setPricingModel(property.getPrice().getModel().getDescriptiveName());
            priceDto.setPrice(Double.parseDouble(property.getPrice().getAmount().toString()));
            dto.setPrice(priceDto);
        }

        return  dto;
    }

    private RentalResponseDto setRentalPropertyResponse(Rental property){

        RentalResponseDto rentalResponseDto = new RentalResponseDto();
        rentalResponseDto.setId(property.getId());

        if(property.getRentalRooms()!=null){
            List<RentalRoom> rooms = property.getRentalRooms();
            List<RentalRoomDto> roomsDto = new ArrayList<>();
            for(RentalRoom room:rooms){
                RentalRoomDto roomDto = new RentalRoomDto();
                roomDto.setRoomType(room.getRoomType().getName());
                roomsDto.add(roomDto);
            }
            rentalResponseDto.setRooms(roomsDto);
        }

        if (property.getRentalType()!=null){
            String rentalType = property.getRentalType().getName();
            rentalResponseDto.setRentalType(rentalType);
        }
        if(property.getDescription()!=null){
            rentalResponseDto.setDescription(property.getDescription());
        }

        rentalResponseDto.setTitle(property.getTitle());
        if(!property.getAmenities().isEmpty()){
            List<Amenity> amenities = new ArrayList<>(property.getAmenities());
            rentalResponseDto.setAmenities(amenities);
        }

        if(property.getSharedSpaces()!=null){
            List<String> sharedSpaces = property.getSharedSpaces().stream()
                    .map(SharedSpace::getName).toList();
            rentalResponseDto.setSharedSpaces(sharedSpaces);
        }
        if(property.getNeighbourhoodDetails()!=null){
            rentalResponseDto.setNeighbourhoodDetails(property.getNeighbourhoodDetails());
        }

        if(property.getGettingAroundDetails()!=null){
            rentalResponseDto.setGettingAround(property.getGettingAroundDetails());
        }


        if(!property.getServices().isEmpty()){
            List<Services> services = new ArrayList<>(property.getServices());
            rentalResponseDto.setServices(services);
        }

        List<RuleDto> rules = new ArrayList<>();
        if(!property.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>(property.getPhotos());
            List<PhotoDto> photoDtos = new ArrayList<>();
            for (Photo photo:photos){
                PhotoDto photoDto = new PhotoDto();
                if(photo.getUrl()!=null){
                    photoDto.setUri(photo.getUrl());
                }
                photoDtos.add(photoDto);
            }
            rentalResponseDto.setPhotos(photoDtos);
        }
        if(!property.getCustomHouseRules().isEmpty()){
            List<CustomHouseRule> houseRules = new ArrayList<>(property.getCustomHouseRules());
            for(CustomHouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                rules.add(ruleDto);
            }
        }
        if(!property.getHouseRules().isEmpty()){
            List<HouseRule> houseRules = new ArrayList<>(property.getHouseRules());
            for(HouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                rules.add(ruleDto);
            }
        }
        if(!rules.isEmpty()){
            rentalResponseDto.setRules(rules);
        }

        if(!property.getReviews().isEmpty()){
            List<Review> reviews = new ArrayList<>(property.getReviews());

            rentalResponseDto.setReviews(reviews.stream().map(rev->{
                ReviewDto revDto = new ReviewDto();
                revDto.setUserId(rev.getUser().getId());
                revDto.setComment(rev.getComment());
                revDto.setRating(rev.getRating());
                revDto.setId(rev.getId());
                revDto.setCreatedAt(rev.getCreatedAt());
                revDto.setUsername(rev.getUser().getUsername());
                return revDto;
            }).toList());
        }


        if(property.getLocation()!=null){
            rentalResponseDto.setLocation(property.getLocation());
        }
        if(property.getHost()!=null){
            HostDto hostDto = new HostDto();
            Host host = property.getHost();
            Optional<User> user  = userRepository.findById(host.getUser().getId());
            if(user.isPresent()){
                UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.get().getId());
                if(userProfile!=null){
                    if(userProfile.getPhoto()!=null){
                        hostDto.setProfileUrl(userProfile.getPhoto().getUrl());
                    }
                    System.out.println("First Name:"+ userProfile.getFirstName());
                    hostDto.setFirstName(userProfile.getFirstName());
                }
            }
            hostDto.setId(host.getId());
            hostDto.setHostSince(host.getHostSince());
            rentalResponseDto.setHostDto(hostDto);
        }
        PriceDto priceDto= new PriceDto();
        if(property.getPrice()!=null) {
            priceDto.setCurrency(property.getPrice().getCurrency().toString());

            priceDto.setPricingModel(property.getPrice().getModel().getDescriptiveName());
            priceDto.setPrice(Double.parseDouble(property.getPrice().getAmount().toString()));
            rentalResponseDto.setPrice(priceDto);
        }
        return rentalResponseDto;
        }

    private PropertyResponseDto setPropertyResponse(Property property){
        PropertyResponseDto propertyResponse = new PropertyResponseDto();

        String approvalStatus = switch(property.getApprovalStatus()){

            case PENDING -> "Pending";
            case APPROVED -> "Approved";
            case REJECTED -> "Rejected";
            default -> null;
        };
        if(property.getListingType()==ListingType.RENTAL){
            Rental rental = (Rental) property;
            propertyResponse.setRentalType(rental.getRentalType().getName());
        }

        if(property.getListingType()==ListingType.HOME_STAYERS_EXPERIENCE){
            HomeStay homeStay = (HomeStay) property;
            propertyResponse.setStayType(homeStay.getStayType().getName());
        }

        if(property.getHost()!=null){
            HostDto hostDto = new HostDto();
            Host host = property.getHost();
            Optional<User> user  = userRepository.findById(host.getUser().getId());
            if(user.isPresent()){
                UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.get().getId());
                if(userProfile!=null){
                    if(userProfile.getPhoto()!=null){
                        hostDto.setProfileUrl(userProfile.getPhoto().getUrl());
                    }
                    System.out.println("First Name:"+ userProfile.getFirstName());
                    hostDto.setFirstName(userProfile.getFirstName());
                }
            }
            hostDto.setId(host.getId());
            hostDto.setHostSince(host.getHostSince());
            propertyResponse.setHostDto(hostDto);
        }
        propertyResponse.setApprovalStatus(approvalStatus);
        propertyResponse.setId(property.getId());
        propertyResponse.setTitle(property.getTitle());
        propertyResponse.setListingType(property.getListingType().toString());
        propertyResponse.setDescription(property.getDescription());
        propertyResponse.setGettingAround(property.getGettingAroundDetails());
        propertyResponse.setNeighbourhoodDetails(property.getNeighbourhoodDetails());
        if(!property.getAmenities().isEmpty()){
            List<Amenity> amenities = new ArrayList<>(property.getAmenities());
            propertyResponse.setAmenities(amenities);
        }
        if(!property.getServices().isEmpty()){
            List<Services> services = new ArrayList<>(property.getServices());
            propertyResponse.setServices(services);
        }
        List<RuleDto> rules = new ArrayList<>();
        if(!property.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>(property.getPhotos());
            List<PhotoDto> photoDtos = new ArrayList<>();
            for (Photo photo:photos){
              PhotoDto photoDto = new PhotoDto();
              if(photo.getUrl()!=null){
                  photoDto.setUri(photo.getUrl());
              }
              photoDtos.add(photoDto);
            }
            propertyResponse.setPhotos(photoDtos);
        }
        if(!property.getCustomHouseRules().isEmpty()){
            List<CustomHouseRule> houseRules = new ArrayList<>(property.getCustomHouseRules());
            System.out.println("We have house rules:"+houseRules);
            for(CustomHouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                ruleDto.setId(Integer.parseInt(String.valueOf(rule.getId())));
                rules.add(ruleDto);
            }
        }
        if(!property.getHouseRules().isEmpty()){
            List<HouseRule> houseRules = new ArrayList<>(property.getHouseRules());
            for(HouseRule rule: houseRules){
                RuleDto ruleDto = new RuleDto();
                ruleDto.setRuleText(rule.getRuleText());
                rules.add(ruleDto);
            }
        }
        if(!rules.isEmpty()){
            System.out.println("Rules:"+rules);
            propertyResponse.setRules(rules);
        }
        if(!property.getReviews().isEmpty()){
            List<Review> reviews = new ArrayList<>(property.getReviews());

            propertyResponse.setReviews(reviews.stream().map(rev->{
                ReviewDto revDto = new ReviewDto();
                revDto.setUserId(rev.getUser().getId());
                revDto.setComment(rev.getComment());
                revDto.setRating(rev.getRating());
                revDto.setId(rev.getId());
                revDto.setCreatedAt(rev.getCreatedAt());
                revDto.setUsername(rev.getUser().getUsername());
                return revDto;
            }).toList());
        }

        if(property.getCreatedAt()!=null){
            propertyResponse.setStartDate(property.getCreatedAt());
        }

        if(!property.getRooms().isEmpty()){
            List<RoomResponseDto> roomResponseDtos = new ArrayList<>();
            List<Room> rooms = new ArrayList<>(property.getRooms());
            for(Room room: rooms){
                RoomResponseDto roomResponseDto = new RoomResponseDto();
                roomResponseDto = setRoomResponse(room,property);
                roomResponseDtos.add(roomResponseDto);
            }
        propertyResponse.setRooms(roomResponseDtos);
        }
        if(property.getLocation()!=null){
            propertyResponse.setLocation(property.getLocation());
        }
        PriceDto priceDto= new PriceDto();
        if(property.getPrice()!=null){
            System.out.println("Price"+ property.getPrice());
            priceDto.setCurrency(property.getPrice().getCurrency().toString());

            priceDto.setPricingModel(property.getPrice().getModel().getDescriptiveName());
            priceDto.setPrice(Double.parseDouble(property.getPrice().getAmount().toString()));
            propertyResponse.setPrice(priceDto);

        }
        return propertyResponse;

    }

    private RoomResponseDto setRoomResponse(Room room,Property property){
        RoomResponseDto roomResponse = new RoomResponseDto();
        roomResponse.setListingId(property.getId());
        roomResponse.setId(room.getId());
        roomResponse.setTitle(room.getRoomTitle());
        roomResponse.setDescription(room.getDescription());
        roomResponse.setRoomType(room.getRoomType().toString());
        if(!room.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>(room.getPhotos());
            List<PhotoDto> photoDtos = new ArrayList<>();
            for(Photo photo: photos){
                PhotoDto photoDto = new PhotoDto();
                if(photo.getUrl()!=null){
                    photoDto.setUri(photo.getUrl());
                    photoDtos.add(photoDto);
                }
            }
            roomResponse.setPhotos(photoDtos);
        }
        if(!room.getServices().isEmpty()){
            List<Services> services = new ArrayList<>(room.getServices());
            roomResponse.setServices(services);
        }
        if(!room.getAmenities().isEmpty()){
            List<Amenity> amenities = new ArrayList<>(room.getAmenities());
            roomResponse.setAmenities(amenities);
        }
        if(!room.getRoomAttachments().isEmpty()){
            List<RoomAttachment> roomAttachments = new ArrayList<>(room.getRoomAttachments());
            List<AttachmentTypeDto> attachmentTypeDtos = new ArrayList<>();
            for(RoomAttachment roomAttachment: roomAttachments){
                AttachmentTypeDto attachmentTypeDto = new AttachmentTypeDto();
                attachmentTypeDto.setName(roomAttachment.getAttachmentType().getName());
                attachmentTypeDtos.add(attachmentTypeDto);
            }
            if(!attachmentTypeDtos.isEmpty()){
                roomResponse.setAttachments(attachmentTypeDtos);
            }

        }
        if(!room.getReviews().isEmpty()){
            List<Review> reviews = new ArrayList<>(room.getReviews());
            room.setReviews(reviews);
        }
        if(room.getPrice()!=null){
            PriceDto priceDto= new PriceDto();
            priceDto.setCurrency(room.getPrice().getCurrency().toString());
            priceDto.setPricingModel(room.getPrice().getModel().getDescriptiveName());
            priceDto.setPrice(Double.parseDouble(room.getPrice().getAmount().toString()));
            roomResponse.setPrice(priceDto);

        }
        return roomResponse;
    }

    @Override
    public List<Services> editRoomServices(Long roomId, List<String> services) {

        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        if(services.isEmpty()){
            return null;
        }
        List<Services> serv = services.stream().map((am)->{
            return servicesRepository.findByName(am).orElseThrow(ServiceNotFoundException::new);
        }).toList();
        List<Services> allServices = room.getServices();
        allServices.addAll(serv);
        room.setServices(allServices);
        System.out.println("Here");
        System.out.println("Here Again");
        roomRepository.flush();
        return room.getServices();

    }

    @Override
    public List<Amenity> editRoomAmenities(Long roomId, List<String> amenities) {


        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        if(amenities.isEmpty()){
            return null;
        }
        List<Amenity> ams = amenities.stream().map((am)->{
            return amenityRepository.findByName(am).orElseThrow(AmenityNotFoundException::new);
        }).toList();
        List<Amenity> allAmenities = room.getAmenities();
        allAmenities.addAll(ams);
        room.setAmenities(allAmenities);
        System.out.println("Here");
        System.out.println("Here Again");
        roomRepository.flush();
        return room.getAmenities();

    }

    @Override
    public List<Services> editPropertyServices(Long propertyId, List<String> services) {


        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);
        if(services.isEmpty()){
            return null;
        }

        List<Services> ams = services.stream().map((am)->{
            return servicesRepository.findByName(am).orElseThrow(ServiceNotFoundException::new);
        }).toList();
        List<Services> allServices = property.getServices();
        allServices.addAll(ams);
        property.setServices(allServices);
        propertyRepository.flush();
        return property.getServices();
        }

    @Override
    public List<Amenity> editPropertyAmenities(Long propertyId, List<String> amenities) {

        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);


        if(amenities.isEmpty()){
            return null;
        }

        List<Amenity> ams = amenities.stream().map((am)->{
            return amenityRepository.findByName(am).orElseThrow(AmenityNotFoundException::new);
        }).toList();
        List<Amenity> allAmenities = property.getAmenities();
        allAmenities.addAll(ams);
        System.out.println("Amenities Found:"+ ams);
        property.setAmenities(allAmenities);
        System.out.println("Here");
        System.out.println("Here Again");
        propertyRepository.flush();
        return property.getAmenities();
    }

    @Override
    public HomeStay createHomestay(HomestayCreationRequest request) {
        Host host  = hostRepository.findById(request.getHostId()).orElseThrow(HostNotFoundException::new);
        HomeStay homeStay = new HomeStay();
        StayType stayType = switch(request.getStayType()){

            case "Rural"->StayType.RURAL;
            case "Urban"->StayType.URBAN;
            default -> throw new IllegalStateException("Unexpected value: " + request.getStayType());
        };

        homeStay.setStayType(stayType);
        homeStay.setListingType(ListingType.HOME_STAYERS_EXPERIENCE);
        if(request.getSharedSpaces()!=null){
            List<SharedSpace> sharedSpaces = request
                    .getSharedSpaces()
                    .stream()
                    .map(space->{
                        return switch(space.getName()){
                            case "Bathroom"->SharedSpace.BATHROOM;
                            case "Bedroom"->SharedSpace.BEDROOM;
                            case "Living Room"->SharedSpace.LIVING_ROOM;
                            case "Kitchen"->SharedSpace.KITCHEN;
                            case "Corridor"->SharedSpace.CORRIDOR;
                            case "Garage"->SharedSpace.GARAGE;
                            default -> throw new IllegalStateException("Unexpected value: " + space.getName());
                        };
                    })
                    .toList();
            homeStay.setSharedSpaces(sharedSpaces);
        }
        homeStay.setHost(host);
        homeStay.setCreatedAt(new Date(System.currentTimeMillis()));
        homeStay.setTitle(request.getTitle());
        homeStay.setDescription(request.getDescription());
        homeStay.setGettingAroundDetails(request.getGettingAround());
        homeStay.setNeighbourhoodDetails(request.getNeighbourhoodDetails());
        homeStay.setStatus(ServiceStatus.INACTIVE);
        homeStay.setApprovalStatus(ApprovalStatus.PENDING);
        if(!request.getPolicies().isEmpty()){
            List<CustomHouseRule> customHouseRules = new ArrayList<>();
            List<HouseRule> houseRules = new ArrayList<>();
            for (HouseRuleRequest dto: request.getPolicies()){
                if (dto.getId() == 0) {
                    CustomHouseRule customHouseRule = new CustomHouseRule();
                    customHouseRule.setRuleText(dto.getRuleText());
                    customHouseRules.add(customHouseRuleRepository.save(customHouseRule));
                } else {
                    Optional<HouseRule> houseRule = houseRuleRepository.findById((long) dto.getId());
                    houseRule.ifPresent(houseRules::add);
                }
            }
            homeStay.setCustomHouseRules(customHouseRules);
            homeStay.setHouseRules(houseRules);
        }
        if(!request.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>();
            for(PhotoDto dto: request.getPhotos()){
                Photo photo = new Photo();
                if(dto.getUri()!=null){
                    photo.setUrl(dto.getUri());
                    photos.add(photoRepository.save(photo));
                }
            }
            homeStay.setPhotos(photos);

        }
        if(!request.getAmenities().isEmpty()){
            List<Amenity> propertyAmenities = new ArrayList<>();
            for(String amenity:request.getAmenities()){
                Optional<Amenity> propertyAmenity = amenityRepository.findByName(amenity);
                propertyAmenity.ifPresent(propertyAmenities::add);
            }
            homeStay.setAmenities(propertyAmenities);
        }
        if(!request.getServices().isEmpty()){
            List<Services> propertyServices = new ArrayList<>();
            for(String service : request.getServices()){
                Optional<Services> propertyService = servicesRepository.findByName(service);
                propertyService.ifPresent(propertyServices::add);
            }
            homeStay.setServices(propertyServices);
            //TODO Add Services
        }
        //TODO Set Location and Address
        Address address = Address.builder().address(request.getAddress()).street(request.getAddress()).city(request.getCity()).state(request.getProvince()).country(request.getCountry()).build();
        Location location = new Location();
        location.setAddress(address);
        homeStay.setLocation(locationRepository.save(location));
        if(request.getPrice()!=null){
            Price price = new Price();
            Currency curr = Currency.valueOf(request.getPrice().getCurrency());
            price.setCurrency(curr);
            PricingModel pricingModel = switch (request.getPrice().getPricingModel()){

                case "Per Night"-> PricingModel.PER_NIGHT;
                case "Per Day"-> PricingModel.PER_DAY;
                case "Per Week"-> PricingModel.PER_WEEK;
                case "Per Month"-> PricingModel.PER_MONTH;
                default -> null;
            };
            BigDecimal amount = BigDecimal.valueOf(request.getPrice().getPrice()).setScale(2, RoundingMode.HALF_EVEN);
            price.setModel(pricingModel);
            price.setAmount(amount);
            homeStay.setPrice(pricingRepository.save(price));
        }

        return propertyRepository.save(homeStay);
    }

    @Override
    public List<String> getHomeStayTypes() {

        List<StayType> stayTypes = Arrays.asList(StayType.values());

        return stayTypes.stream().map(StayType::getName).toList();
    }

    @Override
    public Rental createRental(RentalCreationRequest request) {
        Host host = hostRepository.findById(request.getHostId()).orElseThrow(HostNotFoundException::new);
        Rental rental = new Rental();

        if(request.getSharedSpaces()!=null){
            List<SharedSpace> sharedSpaces = request
                    .getSharedSpaces()
                    .stream()
                    .map(space->{
                        return switch(space.getName()){
                            case "Bathroom"->SharedSpace.BATHROOM;
                            case "Living Room"->SharedSpace.LIVING_ROOM;
                            case "Kitchen"->SharedSpace.KITCHEN;
                            case "Corridor"->SharedSpace.CORRIDOR;
                            case "Garage"->SharedSpace.GARAGE;
                            default -> throw new IllegalStateException("Unexpected value: " + space.getName());
                        };
                    })
                    .toList();
            rental.setSharedSpaces(sharedSpaces);
        }

        if(request.getRooms()!=null){
            List<RentalRoom> rentalRooms = request
                    .getRooms()
                    .stream()
                    .map(room->{
                        RoomType roomType = getRoomTypeFromDto(room.getName());
                        RentalRoom rentalRoom = new RentalRoom();
                        rentalRoom.setRoomType(roomType);
                        return rentalRoomRepository.save(rentalRoom);
                    }).toList();
            rental.setRentalRooms(rentalRooms);
        }

        rental.setHost(host);
        rental.setCreatedAt(new Date(System.currentTimeMillis()));
        RentalType rentalType = getRentalTypeFromRequest(request.getRentalType());
        rental.setRentalType(rentalType);
        rental.setListingType(ListingType.RENTAL);
        rental.setGettingAroundDetails(request.getGettingAround());
        rental.setNeighbourhoodDetails(request.getNeighbourhoodDetails());
        rental.setTitle(request.getTitle());
        rental.setDescription(request.getDescription());
        rental.setUpdatedAt(new Date(System.currentTimeMillis()));
        rental.setStatus(ServiceStatus.INACTIVE);
        rental.setApprovalStatus(ApprovalStatus.PENDING);
        if(!request.getPolicies().isEmpty()){
            List<CustomHouseRule> customHouseRules = new ArrayList<>();
            List<HouseRule> houseRules = new ArrayList<>();
            for (HouseRuleRequest dto: request.getPolicies()){
                if (dto.getId() == 0) {
                    CustomHouseRule customHouseRule = new CustomHouseRule();
                    customHouseRule.setRuleText(dto.getRuleText());
                    customHouseRules.add(customHouseRuleRepository.save(customHouseRule));
                } else {
                    Optional<HouseRule> houseRule = houseRuleRepository.findById((long) dto.getId());
                    houseRule.ifPresent(houseRules::add);
                }
            }
            rental.setCustomHouseRules(customHouseRules);
            rental.setHouseRules(houseRules);
        }
        if(!request.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>();
            for(PhotoDto dto: request.getPhotos()){
                Photo photo = new Photo();
                if(dto.getUri()!=null){
                    photo.setUrl(dto.getUri());
                    photos.add(photoRepository.save(photo));
                }
            }
            rental.setPhotos(photos);

        }
        if(!request.getAmenities().isEmpty()){
            List<Amenity> propertyAmenities = new ArrayList<>();
            for(String amenity:request.getAmenities()){
                Optional<Amenity> propertyAmenity = amenityRepository.findByName(amenity);
                propertyAmenity.ifPresent(propertyAmenities::add);
            }
            rental.setAmenities(propertyAmenities);
        }
        if(!request.getServices().isEmpty()){
            List<Services> propertyServices = new ArrayList<>();
            for(String service : request.getServices()){
                Optional<Services> propertyService = servicesRepository.findByName(service);
                propertyService.ifPresent(propertyServices::add);
            }
            rental.setServices(propertyServices);
            //TODO Add Services
        }
        //TODO Set Location and Address
        Address address = Address.builder().address(request.getAddress()).street(request.getAddress()).city(request.getCity()).state(request.getProvince()).country(request.getCountry()).build();
        Location location = new Location();
        location.setAddress(address);
        rental.setLocation(locationRepository.save(location));
        if(request.getPrice()!=null){
            Price price = new Price();
            Currency curr = Currency.valueOf(request.getPrice().getCurrency());
            price.setCurrency(curr);
            PricingModel pricingModel = switch (request.getPrice().getPricingModel()){

                case "Per Night"-> PricingModel.PER_NIGHT;
                case "Per Day"-> PricingModel.PER_DAY;
                case "Per Week"-> PricingModel.PER_WEEK;
                case "Per Month"-> PricingModel.PER_MONTH;
                default -> null;
            };
            BigDecimal amount = BigDecimal.valueOf(request.getPrice().getPrice()).setScale(2, RoundingMode.HALF_EVEN);
            price.setModel(pricingModel);
            price.setAmount(amount);
            rental.setPrice(pricingRepository.save(price));
        }

        return propertyRepository.save(rental);
    }

    @Override
    public Property createProperty(PropertyCreationRequest request) throws Exception{
        Optional<Host> host = hostRepository.findById(request.getHostId());

        if(host.isEmpty()){
            throw new HostNotFoundException();
        }

        var property = switch (request.getListingType()){
            case "Hotel"-> getPropertyType(ListingType.HOTEL, Hotel.class);
            case "Lodge"->getPropertyType(ListingType.LODGE, Lodge.class);
            case  "Bed and Breakfast"->getPropertyType(ListingType.BED_AND_BREAKFAST,BedAndBreakfast.class);
            default -> new Property();
        };
        ListingType listingType = getListingTypeFromRequest(request.getListingType());
        property.setListingType(listingType);
        List<RoomDto> rooms = request.getRooms();
        List<Room> roomsToSave=new ArrayList<>();
        if(!rooms.isEmpty()){
             roomsToSave = createRooms(rooms);
        }
        property.setRooms(roomsToSave);
        property.setHost(host.get());
        property.setGettingAroundDetails(request.getGettingAround());
        property.setNeighbourhoodDetails(request.getNeighbourhoodDetails());
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setCreatedAt(new Date(System.currentTimeMillis()));
        property.setUpdatedAt(new Date(System.currentTimeMillis()));
        property.setStatus(ServiceStatus.INACTIVE);
        property.setApprovalStatus(ApprovalStatus.PENDING);
        if(!request.getPolicies().isEmpty()){
            List<CustomHouseRule> customHouseRules = new ArrayList<>();
            List<HouseRule> houseRules = new ArrayList<>();
            for (HouseRuleRequest dto: request.getPolicies()){
                if (dto.getId() == 0) {
                    CustomHouseRule customHouseRule = new CustomHouseRule();
                    customHouseRule.setRuleText(dto.getRuleText());
                    customHouseRules.add(customHouseRuleRepository.save(customHouseRule));
                } else {
                    Optional<HouseRule> houseRule = houseRuleRepository.findById((long) dto.getId());
                    houseRule.ifPresent(houseRules::add);
                }
            }
            property.setCustomHouseRules(customHouseRules);
            property.setHouseRules(houseRules);
        }
        if(!request.getPhotos().isEmpty()){
            List<Photo> photos = new ArrayList<>();
            for(PhotoDto dto: request.getPhotos()){
                Photo photo = new Photo();
                if(dto.getUri()!=null){
                    photo.setUrl(dto.getUri());
                    photos.add(photoRepository.save(photo));
                }
            }
            property.setPhotos(photos);

        }
        if(!request.getAmenities().isEmpty()){
            List<Amenity> propertyAmenities = new ArrayList<>();
            for(String amenity:request.getAmenities()){
                Optional<Amenity> propertyAmenity = amenityRepository.findByName(amenity);
                propertyAmenity.ifPresent(propertyAmenities::add);
            }
            property.setAmenities(propertyAmenities);
        }
        if(!request.getServices().isEmpty()){
            List<Services> propertyServices = new ArrayList<>();
            for(String service : request.getServices()){
                Optional<Services> propertyService = servicesRepository.findByName(service);
                propertyService.ifPresent(propertyServices::add);
            }
            property.setServices(propertyServices);
            //TODO Add Services
        }
        //TODO Set Location and Address
        Address address = Address.builder().address(request.getAddress()).street(request.getAddress()).city(request.getCity()).state(request.getProvince()).country(request.getCountry()).build();
        Location location = new Location();
        location.setAddress(address);
        property.setLocation(locationRepository.save(location));
        if(request.getPrice()!=null){
            Price price = new Price();
            Currency curr = Currency.valueOf(request.getPrice().getCurrency());
            price.setCurrency(curr);
            PricingModel pricingModel = switch (request.getPrice().getPricingModel()){

                case "Per Night"-> PricingModel.PER_NIGHT;
                case "Per Day"-> PricingModel.PER_DAY;
                case "Per Week"-> PricingModel.PER_WEEK;
                case "Per Month"-> PricingModel.PER_MONTH;
                default -> null;
            };
            BigDecimal amount = BigDecimal.valueOf(request.getPrice().getPrice()).setScale(2, RoundingMode.HALF_EVEN);
            price.setModel(pricingModel);
            price.setAmount(amount);
            property.setPrice(pricingRepository.save(price));
        }
        return propertyRepository.save(property);
    }


    @Override
    public List<ListingTypeDto> getListingTypes(){

        return sanitizeListingTypes(ListingType.values());
    }

    @Override
    public List<RentalTypeDto> getRentalTypes() {
        return sanitizeRentalTypes(RentalType.values());
    }

    private RentalType getRentalTypeFromRequest(String type){

        return switch (type){
            case "Full House"->RentalType.FULL_HOUSE;
            case "Room"->RentalType.ROOM;
            case "Cottage"->RentalType.COTTAGE;
            case "FLat"->RentalType.FLAT;
            case "Student"->RentalType.STUDENT;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private List<RentalTypeDto> sanitizeRentalTypes(RentalType[] rentalTypes){
        List<RentalTypeDto> rentalTypesDto = new ArrayList<>();
        for (RentalType rentalType : rentalTypes) {
            RentalTypeDto dto = new RentalTypeDto();
            String rentalTypeString = switch (rentalType){

                case FLAT -> "Flat";
                case ROOM -> "Room";
                case COTTAGE -> "Cottage";
                case STUDENT -> "Student";
                case FULL_HOUSE -> "Full House";
            };
            dto.setTitle(rentalTypeString);
            String rentalTypeDescription = switch (rentalType){
                case FLAT -> RentalType.FLAT.getDescription();
                case ROOM ->  RentalType.ROOM.getDescription();
                case COTTAGE ->  RentalType.COTTAGE.getDescription();
                case STUDENT ->  RentalType.STUDENT.getDescription();
                case FULL_HOUSE ->  RentalType.FULL_HOUSE.getDescription();
            };
            dto.setDescription(rentalTypeDescription);
            rentalTypesDto.add(dto);
        }
        return rentalTypesDto;
    }


    private ListingType getListingTypeFromRequest(String listingTypeString){
        ListingType listingType;
        listingType = switch (listingTypeString){
            case "Bed and Breakfast"->ListingType.BED_AND_BREAKFAST;
            case "Lodge"->ListingType.LODGE;
            case "Hotel"->ListingType.HOTEL;
            case "Homestayers Experience"->ListingType.HOME_STAYERS_EXPERIENCE;
            case "Rental"->ListingType.RENTAL;
            default -> throw new IllegalStateException("Unexpected value: " + listingTypeString);
        };

        return listingType;

    }

    private List<ListingTypeDto> sanitizeListingTypes(ListingType[] listingTypes){

        List<ListingTypeDto> listingTypesDto = new ArrayList<>();
        for (ListingType listingType : listingTypes) {
            ListingTypeDto dto = new ListingTypeDto();
            String listingTypeString = switch (listingType) {
                case BED_AND_BREAKFAST -> "Bed and Breakfast";
                case LODGE -> "Lodge";
                case HOTEL -> "Hotel";
                case HOME_STAYERS_EXPERIENCE -> "Homestayers Experience";
                case RENTAL -> "Rental";
                default -> null;
            };
            String listingTypeDescription  = switch (listingType){
                case BED_AND_BREAKFAST -> "Cozy rooms with breakfast included, offering a homey atmosphere and personalized service.";
                case LODGE -> "Rustic accommodation often located in scenic areas, providing a retreat experience close to nature.";
                case HOTEL -> "Full-service accommodations with modern amenities and professional service, ideal for both leisure and business travelers.";
                case  HOME_STAYERS_EXPERIENCE -> "A unique cultural experience where guests live with hosts, enjoying local customs and personal interaction.";
                case RENTAL -> "Private homes or apartments available for short or long-term stays, offering comfort and flexibility for guests.";
                default -> null;
            };
            dto.setDescription(listingTypeDescription);
            dto.setTitle(listingTypeString);
            listingTypesDto.add(dto);
        }
        return listingTypesDto;
    }






    @Override
    public String deleteCancellationPolicy(Long cancellationId) {
        Optional<CancellationPolicy> cancellationPolicy = cancellationPolicyRepository.findById(cancellationId);
        if (cancellationPolicy.isPresent()) {
            cancellationPolicyRepository.delete(cancellationPolicy.get());
            return "Policy deleted successfully";
        }

        else{
            throw new CancellationPolicyNotFoundException();
        }
    }

    @Override
    public CancellationPolicy updatePolicy(Long policyId,CancellationPolicyRequest request){
        Optional<CancellationPolicy> cancellationPolicy = cancellationPolicyRepository.findById(policyId);
        if (cancellationPolicy.isPresent()) {
            var policy = cancellationPolicy.get();
            modelMapper.map(request, policy);
            return cancellationPolicyRepository.save(policy);
        }

        else {
            throw new CancellationPolicyNotFoundException();
        }
    }

    @Override
    public CancellationPolicy addCancellationPolicy(CancellationPolicyRequest request) {
        Optional<CancellationPolicy> cancellationPolicy = cancellationPolicyRepository
                .findByName(request.getName());

        if(cancellationPolicy.isPresent()) {
            throw new CancellationPolicyAlreadyExistsException();
        }
        CancellationPolicy cancellationPolicyEntity = new CancellationPolicy();
        cancellationPolicyEntity.setName(request.getName());
        cancellationPolicyEntity.setDescription(request.getDescription());
        cancellationPolicyEntity.setDuration(request.getDuration());
        return cancellationPolicyRepository.save(cancellationPolicyEntity);
    }

    @Override
    public List<Amenity> getAmenitiesByCategory(int categoryId) {
        return amenityRepository.getAmenitiesByCategoryId(categoryId) ;
    }

    @Override
    public Amenity createAmenity(AmenityCreationRequest request) {

        Amenity amenity = new Amenity();
        Optional<AmenityCategory> amenityCategory = amenityCategoryRepository.findById(request.getCategoryId());
        amenityCategory.ifPresent(amenity::setCategory);
        amenity.setName(request.getName());
        amenity.setIconUrl(request.getIconUrl());
        amenity.setDescription(request.getDescription());
        return amenityRepository.save(amenity);
    }

    @Override
    public AmenityCategory createAmenityCategory(AmenityCategoryCreationRequest request) {
        AmenityCategory amenityCategory = new AmenityCategory();
        amenityCategory.setName(request.getCategoryName());
        amenityCategory.setDescription(request.getDescription());
        amenityCategory.setIconUrl(request.getIconUrl());
        return amenityCategoryRepository.save(amenityCategory);
    }

    @Override
    public List<AmenityCategory> getAmenityCategories() {
        return amenityCategoryRepository.findAll();
    }
}
