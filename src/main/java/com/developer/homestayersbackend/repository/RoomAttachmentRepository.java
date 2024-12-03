package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.RoomAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAttachmentRepository extends JpaRepository<RoomAttachment, Long> {
}
