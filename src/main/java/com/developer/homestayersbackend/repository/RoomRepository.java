package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
