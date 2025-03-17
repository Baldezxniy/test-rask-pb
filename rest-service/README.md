# Rest Service

## Service Documentation

This service is responsible for processing incoming REST requests,
checking the `Authorization` header and create initial event for
collecting data from various services. Events are queued **ActiveMQ**.

## Workflow

1. **Received REST request**
    - The `Authorization` header is checked in the filter for the required `sid`.

2. **Authentication check**
    - If the `sid` does not match the expected value, the request is rejected (401 Unauthorized returns).
    - If the check is successful, the request is passed on to processing.

3. **Event Create**
    - After successful authentication, the service creates a collection event.
    - The event contains information about the required data and context of the request.

4. **Send event to queue in ActiveMQ**
    - The event is serialized in and sent to **ActiveMQ**.
    - Other services subscribed to `users.collect-full-name.queue` queue and.

## Tests

1. Added an integration test with `ActiveMQ` broker, which checks if the event
   has been sent correctly to the queue with necessary date
    - The test uses an in-memory version of ActiveMQ 5.18, not 6+, as specified in the requirements.
    - Ensures that the event is correctly placed in the queue and contains the expected data.

## Logs

The service includes log messages at different processing stages using **slf4j**, ensuring better observability:

- **Receiving event from queue** – Logs when a request is received from http request.
- **Validating SID** – Logs the validation of the `Authorization` header (SID check), including success or failure.
- **Calling external service** – Logs the request to an external service to fetch the user's full name.
- **Creating event** – Logs when the object was created.
- **Forwarding event** – Logs when the updated event is sent to `users.collect-full-name.queue`.
- **Error handling and retries** – Logs any issues during event processing.
