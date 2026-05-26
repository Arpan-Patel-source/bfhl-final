# BFHL API — Acropolis Campus Hiring

REST API built with Java 17 + Spring Boot 3.  
**POST /bfhl** — processes an array of strings and returns categorized results.

---

## ⚠️ Before deploying — update YOUR details

Set these four **Environment Variables** in the Render dashboard:

| Variable | Example value |
|---|---|
| `APP_USER_FULL_NAME` | `priya_sharma` ← lowercase, underscore |
| `APP_USER_DOB` | `15032003` ← ddmmyyyy |
| `APP_USER_EMAIL` | `priya@college.edu` |
| `APP_USER_ROLL_NUMBER` | `CS2021001` |

---

## Deploy on Render (Docker)

1. Push this folder to a **GitHub repo** (public or private).
2. Go to [render.com](https://render.com) → **New** → **Web Service**.
3. Connect your GitHub repo.
4. Configure:
   - **Environment**: `Docker`  ← important, select Docker not Java
   - **Branch**: `main`
5. Add the four environment variables above.
6. Click **Deploy**.
7. Your API will be live at:  `https://your-app-name.onrender.com/bfhl`

---

## Run locally

```bash
# Requires Java 17+ and Maven 3.8+

# (optional) set your details
export APP_USER_FULL_NAME=your_name
export APP_USER_DOB=ddmmyyyy
export APP_USER_EMAIL=you@email.com
export APP_USER_ROLL_NUMBER=ROLL123

mvn spring-boot:run
```

Test it:
```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data":["a","1","334","4","R","$"]}'
```

Run tests:
```bash
mvn test
```

---

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
