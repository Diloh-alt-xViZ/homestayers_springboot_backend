package com.developer.homestayersbackend.repository;


import com.developer.homestayersbackend.entity.RentalRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRoomRepository extends JpaRepository<RentalRoom, Long> {
}
