# 🎓 Student Management System

A full-stack **Student Management System** developed using **Spring Boot**, **Spring Data JPA**, **Hibernate**, **MySQL**, **HTML**, **CSS** and **JavaScript**. The application provides an efficient way to manage students, departments, courses, marks, and academic results through a responsive web interface.

## 🚀 Live Demo

🔗 https://studentmanagementsystem-d163.onrender.com

## 📂 GitHub Repository

🔗 https://github.com/sharathchikkagoudra/StudentManagementSystem

---

## 📌 Features

### Authentication
- Admin Login
- Admin Registration
- Logout

### Student Management
- Add Student
- View Students
- Update Student
- Delete Student
- Search Student by:
  - Name
  - USN

### Department Management
- Add Department
- Update Department
- Delete Department
- View Departments

### Course Management
- Add Course
- Update Course
- Delete Course
- View Courses

### Marks Management
- Add Student Marks
- Update Marks
- Delete Marks
- View Marks

### Academic Reports
- Student Result Generation
- Rank List
- Topper Identification
- Failed Students Report
- Pass Percentage Calculation

---

## 🛠️ Technologies Used

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

### Frontend
- HTML
- CSS
- JavaScript

### Database
- MySQL

### Tools
- Spring Tool Suite (STS)
- Swagger UI
- Git
- GitHub
- Render
- Railway

---

## 📁 Project Structure

```
StudentManagementSystem
│
├── src
│   ├── main
│   │   ├── java
│   │   ├── resources
│   │   └── static
│   └── test
│
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🏗️ Architecture

```
Frontend (HTML, CSS, JavaScript)
            │
            ▼
Spring Boot REST Controllers
            │
            ▼
Service Layer
            │
            ▼
Repository Layer (Spring Data JPA)
            │
            ▼
MySQL Database
```

---

## 📊 Database Entities

- Student
- Department
- Course
- Marks
- Admin

---

## 🔑 REST API Features

- CRUD Operations
- Validation
- Exception Handling
- DTO Pattern
- RESTful API Design
- Swagger API Documentation

---

## ⚙️ Local Setup

### Clone the Repository

```bash
git clone https://github.com/sharathchikkagoudra/StudentManagementSystem.git
```

### Navigate to the Project

```bash
cd StudentManagementSystem
```

### Configure Database

Create a MySQL database and update:

```
src/main/resources/application.properties
```

Or configure environment variables:

```
DB_URL
DB_USERNAME
DB_PASSWORD
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will be available at:

```
http://localhost:8085
```

---

## ☁️ Deployment

### Backend
- Render

### Database
- Railway MySQL

---

## 📚 Learning Outcomes

This project helped me gain practical experience in:

- Spring Boot Development
- REST API Development
- Spring Data JPA
- Hibernate ORM
- MySQL Database Design
- Exception Handling
- Input Validation
- Cloud Deployment
- Git & GitHub Version Control

---

## 👨‍💻 Developer

**Sharath Chikkagoudra**

- GitHub: https://github.com/sharathchikkagoudra
- LinkedIn: https://www.linkedin.com/in/sharath-chikkagoudra-966192308/

---

## 📄 License

This project is developed for learning and portfolio purposes.
