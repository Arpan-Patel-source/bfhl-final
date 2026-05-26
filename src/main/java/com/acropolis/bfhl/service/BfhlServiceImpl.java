package com.acropolis.bfhl.service;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${app.user.full-name}")
    private String fullName;

    @Value("${app.user.dob}")
    private String dob;

    @Value("${app.user.email}")
    private String email;

    @Value("${app.user.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> data = request.getData();

        List<String> evenNumbers      = new ArrayList<>();
        List<String> oddNumbers       = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long         numericSum       = 0;
        StringBuilder allAlphas       = new StringBuilder();

        for (String item : data) {
            if (isNumeric(item)) {
                long num = Long.parseLong(item);
                numericSum += num;
                if (Math.abs(num) % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlpha(item)) {
                String upper = item.toUpperCase();
                alphabets.add(upper);
                allAlphas.append(upper);
            } else {
                specialChars.add(item);
            }
        }

        return BfhlResponse.builder()
                .success(true)
                .userId(fullName.toLowerCase() + "_" + dob)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(numericSum))
                .concatString(alternatingCapsReversed(allAlphas.toString()))
                .build();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** True when the entire token parses as a long integer. */
    private boolean isNumeric(String token) {
        if (token == null || token.isEmpty()) return false;
        try {
            Long.parseLong(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** True when every character in the token is a letter (a-z / A-Z). */
    private boolean isAlpha(String token) {
        if (token == null || token.isEmpty()) return false;
        for (char c : token.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the concat_string:
     *   1. Take all alpha chars already concatenated & uppercased  →  e.g. "AYB"
     *   2. Reverse                                                 →  "BYA"
     *   3. Alternating caps (even index = upper, odd = lower)      →  "ByA"
     *
     * Examples from the spec:
     *   ["a","R"]        → "AR" → "RA" → "Ra"
     *   ["a","y","b"]    → "AYB" → "BYA" → "ByA"
     *   ["A","ABCD","DOE"] → "AABCDDOE" → "EODDCBAA" → "EoDdCbAa"
     */
    private String alternatingCapsReversed(String allAlphas) {
        if (allAlphas == null || allAlphas.isEmpty()) return "";
        String reversed = new StringBuilder(allAlphas).reverse().toString();
        StringBuilder result = new StringBuilder(reversed.length());
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            result.append(i % 2 == 0 ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return result.toString();
    }
}
