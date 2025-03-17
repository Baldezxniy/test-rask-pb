# **Project Documentation**

## **Overview**

This project consists of multiple **microservices** that interact using **ActiveMQ** as a message broker. The system
processes user data step by step by **retrieving user details from external services**, handling retries for possible
failures, and **storing the collected information in a PostgreSQL database**.

### **Technologies Used**

- **Spring Boot 3.2.5** – Core framework for building microservices.
- **ActiveMQ 6+** – Message broker for asynchronous communication.
- **PostgreSQL 15** – Database for storing user data.
- **Spring Retry** – Handles retries for external service failures.
- **Spring Data JPA** – ORM for database operations.
- **JUnit & Mockito** – Used for unit and integration tests.

### **Notes**

- The **in-memory ActiveMQ broker (5.18)** is used in tests, while the actual system runs on **ActiveMQ 6+** (**a known
  deviation from the requirements**).
- **Messages are configured to ensure reliable delivery** – they remain in the queue until consumed by another service.

---

## **Microservices Overview**

### **1. `rest-service` – Handles Incoming Requests**

**Function:**

- Accepts **HTTP requests** at `localhost:8080/api/v1/users/init/{id}`.
- Requires an **Authorization** header with a **valid SID** (`sid1`, `sid2`, `sid3`).
- If the request is valid, **publishes the user ID** to **ActiveMQ queue** `users.collect-full-name.queue`.

**Technologies:**

- **Spring Boot (Web, Security)**
- **ActiveMQ (JMS, Message Broker)**
- **Spring slf4j (Logging)**

---

### **2. `mock-service-1` – Fetches Full Name**

**Function:**

- Consumes messages from **`users.collect-full-name.queue`**.
- Calls an **external service** to fetch the **user’s full name**.
- The external service has **a 10% chance of failing**, but **Spring Retry** ensures up to **3 attempts**.
- Sends the enriched event to **`users.collect-address.queue`**.

**Technologies:**

- **Spring Boot**
- **Spring Retry (Retry)**
- **ActiveMQ (JMS, Message Broker)**
- **Spring slf4j (Logging)**

---

### **3. `mock-service-2` – Fetches Address**

**Function:**

- Consumes messages from **`users.collect-address.queue`**.
- Calls an **external service** to fetch the **user’s address**.
- Also has a **10% chance of failure**, with **Spring Retry** handling up to **3 retries**.
- Sends the updated event to **`users.collect-credit-card.queue`**.

**Technologies:**

- **Spring Boot**
- **Spring Retry (Retry)**
- **ActiveMQ (JMS, Message Broker)**
- **Spring slf4j (Logging)**

---

### **4. `mock-service-3` – Fetches Credit Cards**

**Function:**

- Consumes messages from **`users.collect-credit-card.queue`**.
- Calls an **external service** to fetch the **user’s credit card list**.
- Same **10% failure chance** with **3 retries** using **Spring Retry**.
- Sends the fully populated event to **`users.collect-store.queue`**.

**Technologies:**

- **Spring Boot**
- **Spring Retry (Retry)**
- **ActiveMQ (JMS, Message Broker)**
- **Spring slf4j (Logging)**

---

### **5. `data-service` – Stores Data in Database**

**Function:**

- Consumes messages from **`users.collect-store.queue`**.
- Saves the **user and their credit cards** to the PostgreSQL database.
- Uses **one-to-many relationship** (one user → multiple credit cards).
- Hibernate ddl mode - `create-drop` each application restart table will delete and recreate

**Technologies:**

- **Spring Boot (JPA, PostgreSQL)**
- **Spring Data JPA**
- **Spring slf4j (Logging)**
- **ActiveMQ (JMS, Message Broker)**

---

## **Data Flow Summary**

1. **User sends an HTTP request to `rest-service`.**
2. **User ID is placed into `users.collect-full-name.queue`.**
3. **Each `mock-service` fetches additional details step by step:**
    - `mock-service-1` → Fetches **full name**.
    - `mock-service-2` → Fetches **address**.
    - `mock-service-3` → Fetches **credit cards**.
4. **Each service forwards the enriched event to the next queue.**
5. **Final event reaches `data-service`, which stores the complete user data.**

---

## **Reliability & Fault Tolerance**

**Spring Retry** ensures that failures in mock services are retried **up to 3 times**.  
**ActiveMQ is configured** so that messages **are not removed** until successfully processed.

## **Start & Build command**

**`docker-compose up --build`**
