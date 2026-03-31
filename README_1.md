# 🎓 Student Course Registration & Management System

A full-stack web application built with **Spring Boot** that allows administrators to manage students, courses, and course registrations through a clean, browser-based interface.

---

## 👥 Group Members

| Name | Registration Number |
|------|-------------------|
| Roy Kibiwott | CS/MK/0693/09/23 |
| Sam Kibet | CS/M/0681/09/23 |
| Caleb Kiragu | CS/M/0708/09/23 |
| Rodney Chirchir | CS/MK/1040/09/23 |

---

## 📋 Features

- **Student Management** — Add, edit, view profiles, and delete students
- **Course Management** — Create and manage courses with code, lecturer, and credit info
- **Course Registration** — Register students into courses per semester
- **Student Profiles** — View all courses a student is enrolled in
- **Dashboard** — Central landing page with navigation to all sections
- **Validation** — Form-level input validation with user-friendly error messages
- **H2 In-Memory Database** — Runs out of the box with no database setup required
- **MySQL Support** — Optional MySQL configuration for persistent data

---

## 🛠️ Tech Stack

| Layer        | Technology                        |
|--------------|-----------------------------------|
| Backend      | Java 17, Spring Boot 3.2.0        |
| Web Layer    | Spring MVC                        |
| Templating   | Thymeleaf                         |
| Persistence  | Spring Data JPA / Hibernate       |
| Database     | H2 (default) / MySQL (optional)   |
| Validation   | Jakarta Bean Validation           |
| Build Tool   | Maven                             |

---

## 📁 Project Structure

```
student-course-registration/
├── src/
│   ├── main/
│   │   ├── java/com/example/studentapp/
│   │   │   ├── StudentApplication.java          # App entry point
│   │   │   ├── controller/
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── StudentController.java
│   │   │   │   ├── CourseController.java
│   │   │   │   ├── RegistrationController.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── model/
│   │   │   │   ├── Student.java
│   │   │   │   ├── Course.java
│   │   │   │   └── Registration.java
│   │   │   ├── repository/
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── RegistrationRepository.java
│   │   │   └── service/
│   │   │       ├── StudentService.java
│   │   │       ├── CourseService.java
│   │   │       └── RegistrationService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── index.html                   # Dashboard
│   │           ├── error.html
│   │           ├── fragments/navbar.html
│   │           ├── students/
│   │           │   ├── list.html
│   │           │   ├── form.html
│   │           │   └── profile.html
│   │           ├── courses/
│   │           │   ├── list.html
│   │           │   └── form.html
│   │           └── registrations/
│   │               ├── register.html
│   │               └── student-courses.html
│   └── test/
│       └── java/com/example/studentapp/
│           └── StudentApplicationTests.java
└── pom.xml
```

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- **Java 17+** → [Download here](https://adoptium.net/)
- **Maven 3.6+** → [Download here](https://maven.apache.org/download.cgi)
- **Git** → [Download here](https://git-scm.com/)

> You do **not** need to install a database — the app uses H2 in-memory by default.

---

### Running Locally (H2 In-Memory Database)

1. **Clone the repository**
   ```bash
   git clone https://github.com/wealeyroy-design/student-course-registration.git
   cd student-course-registration
   ```

2. **Run the application**
   ```bash
   mvn clean spring-boot:run
   ```

3. **Open in your browser**
   ```
   http://localhost:9090
   ```

The app will start immediately. Data is stored in-memory and resets on each restart.

---

### (Optional) Using MySQL Instead of H2

1. Create a MySQL database:
   ```sql
   CREATE DATABASE studentdb;
   ```

2. Open `src/main/resources/application.properties` and:
   - **Comment out** the H2 section
   - **Uncomment** the MySQL section and fill in your credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/studentdb?createDatabaseIfNotExist=true
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   ```

3. Run the app as normal:
   ```bash
   mvn clean spring-boot:run
   ```

---

## 🔗 Application Routes

| URL                               | Description                          |
|-----------------------------------|--------------------------------------|
| `GET /`                           | Dashboard / Home                     |
| `GET /students`                   | List all students                    |
| `GET /students/new`               | Add new student form                 |
| `GET /students/{id}/edit`         | Edit student form                    |
| `GET /students/{id}/profile`      | View student profile & courses       |
| `POST /students/save`             | Save student (create or update)      |
| `POST /students/{id}/delete`      | Delete a student                     |
| `GET /courses`                    | List all courses                     |
| `GET /courses/new`                | Add new course form                  |
| `GET /courses/{id}/edit`          | Edit course form                     |
| `POST /courses/save`              | Save course (create or update)       |
| `POST /courses/{id}/delete`       | Delete a course                      |
| `GET /registrations/register`     | Register a student to a course       |
| `POST /registrations/save`        | Save registration                    |
| `POST /registrations/{id}/delete` | Remove a registration                |
| `GET /h2-console`                 | H2 database console (dev only)       |

---

## 🗃️ Data Models

### Student
| Field      | Type    | Constraints                         |
|------------|---------|-------------------------------------|
| id         | Long    | Auto-generated primary key          |
| firstName  | String  | Required, 2–50 characters           |
| lastName   | String  | Required, 2–50 characters           |
| email      | String  | Required, valid email, unique        |
| program    | String  | Required, 2–100 characters          |
| studentYear| int     | Required, between 1 and 6           |

### Course
| Field      | Type    | Constraints                         |
|------------|---------|-------------------------------------|
| id         | Long    | Auto-generated primary key          |
| courseName | String  | Required, 2–100 characters          |
| courseCode | String  | Required, 2–20 characters, unique   |
| lecturer   | String  | Required                            |
| credits    | int     | Required, between 1 and 6           |

### Registration
| Field    | Type    | Constraints                                        |
|----------|---------|----------------------------------------------------|
| id       | Long    | Auto-generated primary key                         |
| student  | Student | Many-to-one relationship                           |
| course   | Course  | Many-to-one relationship                           |
| semester | String  | Required; unique per student+course+semester combo |

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📦 Building a JAR

To package the application as a runnable JAR:

```bash
mvn clean package
java -jar target/studentapp-0.0.1-SNAPSHOT.jar
```

---

## 🔗 Repository

[https://github.com/wealeyroy-design/student-course-registration](https://github.com/wealeyroy-design/student-course-registration)

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).
