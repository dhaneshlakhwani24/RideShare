# RideShare 🚗

Hi, I'm **Dhanesh Lakhwani**, a Java Full Stack Developer passionate about building scalable applications and solving real-world problems through technology.

During my **Java Developer Internship at ResilienceSoft**, I worked extensively with **Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate, Thymeleaf, MySQL, HTML, CSS, and JavaScript**. Throughout the internship, I learned how enterprise-level applications are designed, how backend services interact with databases, and how frontend and backend work together to build complete web applications.

Instead of ending my internship with a basic CRUD project, I wanted to apply my learning to solve a practical problem. That idea led to the development of **RideShare**.

RideShare is a ride-sharing platform designed to reduce traffic congestion, fuel consumption, travel costs, and underutilized vehicle capacity by allowing users traveling in similar directions to share rides. The project is currently developed as an **MVP (Minimum Viable Product)** that demonstrates the complete ride-sharing workflow while providing a strong foundation for future enhancements.

The application follows a layered **Spring Boot MVC architecture** and includes dedicated modules for **Users**, **Drivers**, and **Superadmins**. It supports user authentication, driver onboarding, vehicle management, ride requests, dashboards, role-based access control, session management, form validation, exception handling, and MySQL database integration.

Although the core functionality has been implemented successfully, RideShare is still under active development. Future versions will include Google Maps integration, live location tracking, JWT authentication, WebSocket-based notifications, intelligent ride matching, payment gateway integration, Docker deployment, cloud hosting, and a dedicated mobile application.

This project represents my internship learning and demonstrates how I applied Java Full Stack technologies to build a solution for a real-world transportation problem rather than simply implementing CRUD operations.

---

# Tech Stack

## Backend

- Java 17
- Spring Boot 3.3.2
- Spring MVC
- Spring Data JPA
- Hibernate ORM
- Bean Validation
- Lombok
- Maven

## Frontend

- HTML5
- CSS3
- JavaScript
- Thymeleaf

## Database

- MySQL

---

# Spring Boot Concepts Implemented

- MVC Architecture
- Layered Architecture
- Dependency Injection (IoC)
- Spring Data JPA
- Hibernate ORM
- CRUD Operations
- Bean Validation
- Session-Based Authentication
- Exception Handling
- Thymeleaf Template Engine
- MySQL Database Integration

---

# Features

- User Registration & Login
- Driver Registration
- Vehicle Registration & Management
- User Dashboard
- Driver Dashboard
- Superadmin Dashboard
- Ride Request Management
- Shared Ride Management
- Nearby Vehicle Discovery
- Role-Based Access Control
- Session Management
- Form Validation
- Exception Handling
- Responsive User Interface

---

# Project Structure

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── config
├── exception
├── util
├── templates
├── static
└── resources
```

---

# Getting Started

## Prerequisites

- Java 17 or above
- Maven
- MySQL 8.x
- IntelliJ IDEA / Eclipse

---

## Clone Repository

```bash
git clone https://github.com/your-username/RideShare.git
cd RideShare
```

---

## Configure Database

Create a MySQL database.

```sql
CREATE DATABASE rideshare;
```

Update your database credentials inside:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rideshare
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Run the Application

Run the project using Maven:

```bash
mvn spring-boot:run
```

Or simply run the **RideShareApplication.java** file from your IDE.

---

## Access the Application

Once the application starts successfully, open:

```text
http://localhost:8080/login
```

---

## Demo Credentials

| Role | Mobile Number | Password |
|------|---------------|----------|
| Superadmin | 9999999999 | Pass@123 |
| Driver | 8888888888 | Pass@123 |
| User | 7777777777 | Pass@123 |

You can also register a new **User** or **Driver** account directly from the login page.

---

# Current Status

RideShare is currently in the **MVP (Minimum Viable Product)** stage and is actively being improved.

### Planned Enhancements

- Google Maps API Integration
- Live Location Tracking
- Route Visualization
- Distance Calculation
- Nearby Driver Discovery
- JWT Authentication
- WebSocket Notifications
- Ride Matching Algorithm
- Payment Gateway Integration
- Docker Containerization
- Cloud Deployment
- Mobile Application

The long-term goal is to transform RideShare into a scalable, production-ready ride-sharing platform capable of solving real-world transportation challenges.

---

# Screenshots

> Screenshots will be added soon.

- Login Page
- User Dashboard
- Driver Dashboard
- Superadmin Dashboard

---

# Contributing

This project is currently maintained by me as part of my continuous learning and portfolio development. Suggestions and feedback are always welcome.

---

# License & Copyright

**© 2026 Dhanesh Lakhwani. All Rights Reserved.**

This project was developed during my Java Developer Internship at **ResilienceSoft** and represents my original work.

The source code, project architecture, and documentation are shared for learning and portfolio purposes only. Unauthorized copying, redistribution, or presenting this project as your own without proper attribution is discouraged.

If this project inspires or helps you, please consider giving proper credit to the original author.

---