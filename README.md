# BFHL API 

REST API built with Java 17 + Spring Boot 3.  
**POST /bfhl** — processes an array of strings and returns categorized results.


## API Reference

**POST** `/bfhl`

Request body:
```json
{ "data": ["a", "1", "334", "4", "R", "$"] }
```

Response (HTTP 200):
```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```
