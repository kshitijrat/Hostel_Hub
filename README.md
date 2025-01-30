# Hostel Hub

A comprehensive web application for managing hostel operations, including room allocation, student management, booking management, and reporting.

## Features

- **User Authentication and Authorization**: Secure login for both admin and student roles with role-based access control using Spring Security.
- **Student Registration and Management**: Register and manage student details, including booking history and fee status.
- **Room Allocation**: Admins can allocate rooms to students based on availability.
- **Booking Management**: Manage and track room bookings and booking status.
- **Complaint System**: Students can submit complaints, and admins can resolve them.
- **Auto-generated Reports**: Admins can generate reports for bookings, fees, and complaints.

## Tech Stack

### Backend
- **Frameworks**: Spring Boot, Hibernate, Spring Security
- **Database**: MySQL

### Frontend
- **Languages**: HTML, CSS, JavaScript
- **Build Tool**: Maven

## Installation

### Prerequisites
1. JDK 8 or higher
2. MySQL
3. Maven

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/kshitijrat/Hostel_Hub.git
2. Update application properties with your MySQL credentials
   ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
 3. Build & Run
    ```bash
    mvn spring-boot:run
4. Access the backend at http://localhost:8080
  


