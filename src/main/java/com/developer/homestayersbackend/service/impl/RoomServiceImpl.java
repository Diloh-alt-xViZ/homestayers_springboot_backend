package com.developer.homestayersbackend.service.impl;

import com.developer.homestayersbackend.dto.AttachmentTypeDto;
import com.developer.homestayersbackend.dto.PhotoDto;
import com.developer.homestayersbackend.dto.RoomRequest;
import com.developer.homestayersbackend.dto.RoomResponseDto;
import com.developer.homestayersbackend.entity.*;
import com.developer.homestayersbackend.exception.PropertyNotFoundException;
import com.developer.homestayersbackend.repository.*;
import com.developer.homestayersbackend.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.developer.homestayersbackend.service.impl.PropertyServiceImpl.getAttachmentTypeFromDto;


@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final PricingRepository pricingRepository;
    private final PhotoRepository photoRepository;
    private final RoomAttachmentRepository roomAttachmentRepository;
    private final AmenityRepository amenityRepository;
    private final ServicesRepository servicesRepository;


    @Override
    public List<RoomResponseDto> getPropertyRooms(Long propertyId) {

        return List.of();
    }

    @Override
    public Room editRoom(RoomRequest request) {

        Optional<Room> room = roomRepository.findById(request.getRoomId());
        Property property = propertyRepository.findById(request.getPropertyId()).orElseThrow(PropertyNotFoundException::new);
        if (room.isPresent()) {
            //TODO: Edit Room and Flush Property as well
            Room getRoom = room.get();

            sanitizeRoom(request, getRoom);
            roomRepository.flush();
            return getRoom;
        }

        else {
            //TODO: Add new Room and flush Property as well
            Room newRoom = new Room();
            sanitizeRoom(request, newRoom);
            return roomRepository.save(newRoom);

        }
    }
    private void sanitizeRoom(RoomRequest request, Room getRoom){
        if(request.getTitle()!=null){
            getRoom.setRoomTitle(request.getTitle());
        }
        if(request.getDescription()!=null){
            getRoom.setDescription(request.getDescription());
        }
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
            getRoom.setPrice(pricingRepository.save(price));
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
            getRoom.setPhotos(photos);
            //TODO Add Photos
        }
        if(!request.getAttachments().isEmpty()){
            List<RoomAttachment> roomAttachments = new ArrayList<>();
            for(AttachmentTypeDto dto :request.getAttachments()){
                RoomAttachment roomAttachment = new RoomAttachment();
                AttachmentType attachmentType = getAttachmentTypeFromDto(dto.getName());
                if (attachmentType != null) {
                    roomAttachment.setDescription(attachmentType.getDescription());
                    roomAttachment.setAttachmentType(attachmentType);
                    roomAttachments.add(roomAttachmentRepository.save(roomAttachment));
                }
            }
            getRoom.setRoomAttachments(roomAttachments);
            //TODO Add Attachments
        }
        if(!request.getAmenities().isEmpty()){
            //TODO Add Amenities
            List<Amenity> roomAmenities = new ArrayList<>();
            for(String amenity:request.getAmenities()){
                Optional<Amenity> roomAmenity = amenityRepository.findByName(amenity);
                roomAmenity.ifPresent(roomAmenities::add);
            }
            getRoom.setAmenities(roomAmenities);
        }
        if(!request.getServices().isEmpty()){
            List<Services> roomServices = new ArrayList<>();
            for(String service : request.getServices()){
                Optional<Services> roomService = servicesRepository.findByName(service);
                roomService.ifPresent(roomServices::add);
            }
            getRoom.setServices(roomServices);
            //TODO Add Services
        }

    }
}
