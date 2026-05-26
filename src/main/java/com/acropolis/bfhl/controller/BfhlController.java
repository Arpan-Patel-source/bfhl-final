package com.acropolis.bfhl.controller;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;
import com.acropolis.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl
     * Accepts { "data": [...] } and returns the processed result.
     * Returns HTTP 200 on success, 400 on bad input.
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody BfhlRequest request) {
        return ResponseEntity.ok(bfhlService.processData(request));
    }
    @GetMapping("/health")
public ResponseEntity<?> health() {

    Map<String, Object> response = new HashMap<>();

    response.put("status", "running");
    response.put("message", "BFHL API is live (GET)");

    return ResponseEntity.ok(response);
}
}
