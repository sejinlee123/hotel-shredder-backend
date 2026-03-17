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

### License

See `LICENSE` for licensing details.
