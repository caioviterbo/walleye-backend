package com.walleye.walleye_backend.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.DashboardResponseDto;
import com.walleye.walleye_backend.services.DashboardService;
import com.walleye.walleye_backend.services.JwtService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final JwtService jwtService; 

    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboard(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        UUID userId = jwtService.extractUserId(jwt);

        DashboardResponseDto response = dashboardService.getDashboard(userId);
        return ResponseEntity.ok(response);
    }
}
