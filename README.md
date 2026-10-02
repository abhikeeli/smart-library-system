# smart-library-system
# 📚 Smart Library System - Backend API

> 🔗 **Frontend Repository:** Looking for the user interface? Check out the [Smart Library System Frontend Repo](https://github.com/abhikeeli/smart-library-system_frontend.git).

A robust RESTful API service built with **Spring Boot** and **PostgreSQL** to automate library inventory management, book borrowing lifecycles, and transaction histories.

---

## 🌟 Key Features

- **Copy-Level Physical Inventory Tracking:** Tracks every physical book unit independently using unique barcode IDs and individual status lifecycles (`AVAILABLE`, `ISSUED`,`RESERVED`,
  `DAMAGED`), preventing concurrency race conditions and enabling physical item auditing.
- **Borrowing & Return Workflows:** Handles automated checkout, return, and overdue status management through transactional API services.
- **Database Seeding & Schema Management:** Relational database setup configured with Spring Data JPA and PostgreSQL.
- **RESTful Architecture:** Clean separation of concerns using Data Transfer Objects (DTOs), Service layers, and Controller endpoints.

---

## 🛠️ Tech Stack

- **Framework:** Java 23, Spring Boot (Spring Web, Spring Data JPA)
- **Database:** PostgreSQL
- **Build Tool:** Maven 
- **Testing:** Postman 

---

## 🏛️ Key Architectural Decision

Unlike traditional library apps that maintain a single `available_copies` counter integer on a book record, this backend explicitly models **individual physical copies** (`BookCopy` entities) linked to a parent `Book` metadata entity.


+------------------+         1 : N         +-------------------+
|       Book       | --------------------> |     BookCopy      |
|------------------|                       |-------------------|
| - id (ISBN/UUID) |                       | - copy_barcode_id |
| - title          |                       | - status (ENUM)   |
| - author         |                       | - condition       |
| - total copies   |                       | - isBorrowed      |
|    available     |                       | - currentBorrower |
+------------------+                       +-------------------+

**Why this matters:**
1. **Concurrency Safety:** Prevents double-booking race conditions when multiple users attempt to borrow the same title simultaneously.
2. **Itemized Auditing:** Tracks exactly which physical book copy was damaged, lost, or sent for repairs.

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 23
- PostgreSQL database instance
- Maven

### Local Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/smart-library-backend.git](https://github.com/your-username/smart-library-backend.git)
   cd smart-library-backend