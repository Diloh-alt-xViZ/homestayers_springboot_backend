package com.developer.homestayersbackend.controller;

import com.developer.homestayersbackend.dto.HostRequest;
import com.developer.homestayersbackend.entity.Host;
import com.developer.homestayersbackend.entity.User;
import com.developer.homestayersbackend.service.api.UserService;
import com.developer.homestayersbackend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class HostController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{userId}/hosts")
    public ResponseEntity<Long> addHost(@PathVariable("userId") Long userId) {
        System.out.println("Trying to add host");
        Host host = userService.createHost(userId);
        if(host == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(host.getId());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{userId}/hosts/create")
    public ResponseEntity<Long> createHost(@PathVariable("userId") Long userId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewHost(userId));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hosts")
    public ResponseEntity<List<Host>> getAllHosts(@RequestParam String verificationStatus) {
        return ResponseEntity.ok(userService.getAllHosts(verificationStatus));
    }

}
