package com.developer.homestayersbackend.repository;

import com.developer.homestayersbackend.entity.Host;
import com.developer.homestayersbackend.entity.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findHostByUserId(Long userId);


}
