package com.acropolis.bfhl.controller;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;
import com.acropolis.bfhl.service.BfhlService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * GET /health
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {

        Map<String, Object> response = new HashMap<>();

        response.put("status", "running");
        response.put("message", "BFHL API is live");

        return ResponseEntity.ok(response);
    }

    /**
     * POST /bfhl
     * Main API endpoint
     */
    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> processData(
            @Valid @RequestBody BfhlRequest request) {

        return ResponseEntity.ok(
                bfhlService.processData(request)
        );
    }
}