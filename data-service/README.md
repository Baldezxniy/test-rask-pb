# Data Service

## Service Documentation

This service is responsible for **storing user data** by consuming events from the **ActiveMQ** queue  
`users.collect-storage.queue` and persisting the received information into the database.

---

## Workflow

1. **Receive Event from Queue**
    - The service listens to the `users.collect-storage.queue` queue in **ActiveMQ**.
    - When an event arrives, it is deserialized and processed.

2. **Save Data to Database**
    - The received event contains user information (ID, full name, address, credit card numbers).
    - The service **persists the received data** into the database.

---

## Tests

1. **Integration Test with ActiveMQ**
    - The test verifies that the service correctly processes an event from `users.collect-storage.queue`.
    - Ensures that the **event is successfully saved to the database** with all expected attributes.
    - The test uses an **in-memory version of ActiveMQ 5.18**, not 6+, as specified in the requirements.
      and h-2 database for storing data

---

## Logs

The service includes log messages at different processing stages using **slf4j**, ensuring better observability:

- **Receiving event** – Logs the event received from `users.collect-storage.queue`.
- **Processing event** – Logs when the event data is extracted for persistence.
- **Saving to database** – Logs when user data is successfully stored or updated.
