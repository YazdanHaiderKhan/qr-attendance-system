# QR Attendance System

A Spring Boot-based QR Attendance System for secure, role-based attendance tracking using QR codes. Supports Admin and Student roles, JWT authentication, and both H2 (dev) and MySQL (prod) databases.

## Features
- User authentication (Admin & Student)
- JWT-based API security
- QR code generation for classes
- Attendance scan & logging
- H2 database for development
- MySQL compatibility for production

## Getting Started

### Prerequisites
- Java 17 or later
- Maven
- (Optional) MySQL server for production

### Development Setup (H2)
1. **Clone the repository:**
   ```bash
   git clone https://github.com/<your-username>/qr-attendance-system.git
   cd qr-attendance-system
   ```
2. **Run the app:**
   ```bash
   mvn spring-boot:run
   ```
3. **Access H2 console:**
   - [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`

### Production Setup (MySQL)
1. Configure `src/main/resources/application.yml` with your MySQL credentials.
2. Ensure MySQL server is running.
3. Run the app as above.

## API Endpoints
- `/api/auth/login` - User login (returns JWT)
- `/api/admin/generate-qr` - Admin: Generate QR code for class
- `/api/student/scan` - Student: Scan QR for attendance
- `/api/admin/scan-logs` - Admin: View attendance logs

## Testing Users
Insert test users via H2 console or SQL scripts. Use the `/api/test/hash` endpoint (if enabled) to generate BCrypt hashes for passwords.

## Security
- Never commit real secrets or production `application.yml` to the repo.
- Use `.env` or `application-example.yml` for sharing config templates.

## License
MIT
