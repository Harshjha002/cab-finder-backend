# 🚖 Cab Finder – Spring Boot Backend

A modular backend for a cab booking platform built using Spring Boot. It supports user management, cab listings, and secure ownership-based operations with clean REST APIs.

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

---

## ✅ Features

### 👤 User Module
- Register new users
- Sign in with credentials
- Update user profile
- Promote user to cab **Owner**

### 🚗 Cab Module
- Owners can add, update, and delete their own cabs
- Public can view cab details by ID
- Ownership checks to restrict unauthorized updates/deletions

---

## 🔗 API Endpoints

### `/api/user`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/register` | Register a new user |
| `POST` | `/sign-in` | Authenticate and sign in |
| `GET`  | `/{id}` | Get user details by ID |
| `PUT`  | `/{id}` | Update user profile |
| `PUT`  | `/{id}/upgrade-to-owner` | Promote user to cab owner |

---

### `/api/cab`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`    | `/{cabId}` | Get cab details by ID |
| `POST`   | `/{userId}/add-cab` | Add a new cab (only for owners) |
| `PUT`    | `/{userId}/{cabId}/update-cab` | Update a cab (must be owner of cab) |
| `DELETE` | `/{userId}/{cabId}/delete-cab` | Delete a cab (must be owner of cab) |

---

## 🛡️ Ownership Validation

- Cab creation, update, and delete operations are restricted to users with `owner` role.
- Only the owner of a cab can modify or delete it.

---

## 🧪 How to Use

- Configure PostgreSQL credentials in `application.properties`
- Run the app with:  
  ```bash
  mvn spring-boot:run

## 📝 Author
Harsh Jha  
Full stack Developer | Java | Spring Boot

---

