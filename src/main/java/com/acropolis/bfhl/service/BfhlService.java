package com.acropolis.bfhl.service;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;

public interface BfhlService {

    /**
     * Processes the input data array and returns a structured response:
     * - even/odd numbers, alphabets (uppercased), special characters
     * - numeric sum as string
     * - concat_string: all alpha chars concatenated → reversed → alternating caps
     */
    BfhlResponse processData(BfhlRequest request);
}
