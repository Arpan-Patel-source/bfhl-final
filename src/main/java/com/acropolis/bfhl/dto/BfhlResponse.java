package com.acropolis.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response DTO.
 *
 * NOTE: The boolean field is named 'success' (not 'isSuccess').
 * Naming a boolean field 'isSuccess' causes Java to generate a getter called
 * isSuccess() that conflicts with the bean convention, breaking compilation.
 * The JSON key is still "is_success" via @JsonProperty.
 */
public class BfhlResponse {

    @JsonProperty("is_success")
    private boolean success;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    @JsonProperty("alphabets")
    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    @JsonProperty("sum")
    private String sum;

    @JsonProperty("concat_string")
    private String concatString;

    public BfhlResponse() {}

    private BfhlResponse(Builder b) {
        this.success          = b.success;
        this.userId           = b.userId;
        this.email            = b.email;
        this.rollNumber       = b.rollNumber;
        this.oddNumbers       = b.oddNumbers;
        this.evenNumbers      = b.evenNumbers;
        this.alphabets        = b.alphabets;
        this.specialCharacters = b.specialCharacters;
        this.sum              = b.sum;
        this.concatString     = b.concatString;
    }

    public static Builder builder() { return new Builder(); }

    // Getters
    public boolean isSuccess()                  { return success; }
    public String getUserId()                   { return userId; }
    public String getEmail()                    { return email; }
    public String getRollNumber()               { return rollNumber; }
    public List<String> getOddNumbers()         { return oddNumbers; }
    public List<String> getEvenNumbers()        { return evenNumbers; }
    public List<String> getAlphabets()          { return alphabets; }
    public List<String> getSpecialCharacters()  { return specialCharacters; }
    public String getSum()                      { return sum; }
    public String getConcatString()             { return concatString; }

    // Setters
    public void setSuccess(boolean success)                          { this.success = success; }
    public void setUserId(String userId)                             { this.userId = userId; }
    public void setEmail(String email)                               { this.email = email; }
    public void setRollNumber(String rollNumber)                     { this.rollNumber = rollNumber; }
    public void setOddNumbers(List<String> oddNumbers)               { this.oddNumbers = oddNumbers; }
    public void setEvenNumbers(List<String> evenNumbers)             { this.evenNumbers = evenNumbers; }
    public void setAlphabets(List<String> alphabets)                 { this.alphabets = alphabets; }
    public void setSpecialCharacters(List<String> specialCharacters) { this.specialCharacters = specialCharacters; }
    public void setSum(String sum)                                   { this.sum = sum; }
    public void setConcatString(String concatString)                 { this.concatString = concatString; }

    public static class Builder {
        private boolean success;
        private String userId;
        private String email;
        private String rollNumber;
        private List<String> oddNumbers;
        private List<String> evenNumbers;
        private List<String> alphabets;
        private List<String> specialCharacters;
        private String sum;
        private String concatString;

        public Builder success(boolean v)                          { this.success = v;           return this; }
        public Builder userId(String v)                            { this.userId = v;            return this; }
        public Builder email(String v)                             { this.email = v;             return this; }
        public Builder rollNumber(String v)                        { this.rollNumber = v;        return this; }
        public Builder oddNumbers(List<String> v)                  { this.oddNumbers = v;        return this; }
        public Builder evenNumbers(List<String> v)                 { this.evenNumbers = v;       return this; }
        public Builder alphabets(List<String> v)                   { this.alphabets = v;         return this; }
        public Builder specialCharacters(List<String> v)           { this.specialCharacters = v; return this; }
        public Builder sum(String v)                               { this.sum = v;               return this; }
        public Builder concatString(String v)                      { this.concatString = v;      return this; }
        public BfhlResponse build()                                { return new BfhlResponse(this); }
    }
}
