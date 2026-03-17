### HotelShredder Backend

Spring Boot backend for the HotelShredder booking system. Provides REST APIs for authentication, rooms, bookings, payments, and user profiles. Uses PostgreSQL for persistence, JWT for authentication, and Stripe for payments. Deployed on Railway.

---

### What it does

- **Auth & users** – Register, log in, and issue JWTs for `CUSTOMER` and `ADMIN` roles.
- **Rooms** – Admins can create, update, delete rooms and upload photos; all users can list and view room details.
- **Search & availability** – Find available rooms given check‑in/check‑out dates and optional room type.
- **Bookings** – Customers can book rooms, view their bookings; admins can view and manage all bookings.
- **Payments** – Integrates with Stripe to create payment intents and update booking payment status.
- **Images** – Stores room photos on disk in a Railway volume and serves them via `/product-image/**`.

---

### Tech stack

| Layer      | Stack                                                                 |
|-----------|------------------------------------------------------------------------|
| Language  | Java 21                                                                |
| Framework | Spring Boot 4, Spring MVC, Spring Security, Spring Data JPA, Hibernate |
| Database  | PostgreSQL                                                             |
| Auth      | JWT (jjwt API / impl / jackson)                                       |
| Images    | File storage on Railway volume, served via `WebMvcConfigurer`         |
| Mail      | JavaMail (Spring Mail)                                                |
| Other     | ModelMapper, Lombok, HikariCP                                         |

---

### Project structure

- `src/main/java/com/leshredder/hotel_booking/`
  - `controllers/` – REST controllers (`AuthController`, `RoomController`, `BookingController`, etc.).
  - `services/` & `services/impl/` – Business logic for rooms, bookings, users, notifications, payments.
  - `repositories/` – Spring Data JPA repositories for `Room`, `Booking`, `User`, etc.
  - `security/` – CORS config, JWT filter, guards, and `CustomUserDetailsService`.
  - `config/WebConfig.java` – Static resource mapping for `/product-image/**`.
  - `dtos/` & `entities/` – Data transfer objects and JPA entities.
- `src/main/resources/`
  - `application.properties` – Spring Boot configuration (imports `.env`).
- `.env` – Backend runtime configuration (not committed).

---

### Configuration

`application.properties` imports a `.env` file:

```properties
spring.config.import=optional:file:.env[.properties]

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

secretJwtString=${JWT_SECRET}

stripe.api.public.key=${STRIPE_PUBLIC_KEY}
stripe.api.secret.key=${STRIPE_SECRET_KEY}
```

Example `.env` for local dev:

```env
DB_URL=jdbc:postgresql://localhost:5432/hotel
DB_USERNAME=postgres
DB_PASSWORD=postgres

JWT_SECRET=change_me_to_a_long_random_secret

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@example.com
MAIL_PASSWORD=app-password-or-test-value

STRIPE_PUBLIC_KEY=pk_test_...
STRIPE_SECRET_KEY=sk_test_...
```

> **Never** commit real secrets. In production (Railway), set these as environment variables.

---

### Image storage

Room images are saved by `RoomServiceImpl`:

- Files are written under a directory like `/data/product-image/` in the container.
- `saveImage` returns a relative web path: `/product-image/<filename>.jpeg`.
- `WebConfig` maps `/product-image/**` to the physical image directory so the frontend can load images via HTTP.

Example JSON from `/api/rooms/all`:

```json
{
  "status": 200,
  "message": "success",
  "rooms": [
    {
      "id": 4,
      "roomNumber": 124,
      "type": "SINGLE",
      "pricePerNight": 123.00,
      "capacity": 123,
      "description": "123",
      "imageUrl": "/product-image/1405d584-bf03-4ae5-a2be-0d1ebe061f9c_photo.jpeg"
    }
  ]
}
```

The frontend prepends the backend base URL (e.g. `https://hotel-shredder-backend-production.up.railway.app`) to `imageUrl` to render images.

---

### Running locally

#### Prerequisites

- Java 21
- Maven
- PostgreSQL

#### Steps

```bash
cd HotelBooking

# Create .env as shown above

mvn spring-boot:run
```

The API will be available at `http://localhost:9090/api`.

---

### Deployment (Railway)

- Create a new Railway service from this repo.
- Configure environment variables:
  - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
  - `JWT_SECRET`
  - `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`
  - `STRIPE_PUBLIC_KEY`, `STRIPE_SECRET_KEY`
- Mount a persistent volume at `/data` so room images survive restarts.

---

### License

See `LICENSE` for licensing details.
