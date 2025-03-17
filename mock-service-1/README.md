# Mock Service 1

## Service Documentation

This service is responsible for **processing user data** by consuming events from **ActiveMQ**
`users.collect-full-name.queue` queue, enriching them with
additional information, and forwarding them to  `users.collect-address.queue` queue.

## Workflow

1. **Receive Event from Queue**
    - The service listens to the `users.collect-full-name.queue` queue in **ActiveMQ**.
    - When an event arrives, it is deserialized and processed.

2. **Call External Service**
    - The extracted user ID is used to call an external service to retrieve the user's full name.
    - If the external service responds successfully, the full name is added to the event.
    - If the request fails, a retry mechanism is triggered, making up to **three attempts**
    - If the all attempts fails, appropriate error handling is applied (logging, retries, etc.).

3. **Forward Enriched Event**
    - After enriching the event object with the user's full name, it is forwarded to the `users.collect-address.queue`
      queue in **ActiveMQ**.
    - This ensures that subsequent services receive updated event data.

## Tests

1. **Integration Test with ActiveMQ**
    - The test verifies that the service correctly processes an event from `users.collect-full-name.queue`, calls the
      external service, and forwards the updated event to `users.collect-address.queue`.
    - The test uses an **in-memory version of ActiveMQ 5.18**, not 6+, as specified in the requirements.
    - Ensures that the event is correctly modified before being forwarded.

## Logs

The service includes log messages at different processing stages using **slf4j**, ensuring better observability:

- **Receiving event** – Logs the event received from the queue.
- **Calling external service** – Logs the attempt to fetch user data, success, or failure.
- **Enriching event** – Logs when the event is updated with the full name.
- **Forwarding event** – Logs when the modified event is sent to the next queue.
- **Error handling** – Logs issues during event processing or external service calls.  
