# Dev Notes

This is a full-stack web application using Spring Boot + Angular with a stretch goal to integrate Ionic for a mobile experience.

## How To Start

- [ ] [Set Up Project Skeletons](#set-up-project-skeletons)
- [ ] [Write API Layer Tests](#write-api-layer-tests)
- [ ] [Pass API Layer](#pass-api-layer)
- [ ] [Write Repository Test](#write-repository-test)
- [ ] [Pass Repository Layer](#pass-repository-layer)
- [ ] [Write Controller Tests with Mocked Services](#write-controller-tests-with-mocked-services)
- [ ] [Pass Controller Tests](#pass-controller-tests)
- [ ] [Write Angular Service Tests](#write-angular-service-tests)
- [ ] [Write Angular Component Tests](#write-angular-component-tests)
- [ ] [Pass Angular Components to Pass Tests](#pass-angular-components-to-pass-tests)

### Test Order & Workflow

- Innermost → Outermost: Services → Repositories → Controllers (backend); Services → Components (frontend)
- Per feature: Write all tests for one feature's service, then implement; move to next layer
- Integration: Add end-to-end tests last (e.g., full login flow, customer viewing tracks) after unit layers are solid

---

### `Set Up Project Skeletons`

Add testing dependencies to Gradle (JUnit 5, Mockito, Spring Boot Test) and package.json (Jasmine, Karma for Angular); configure test runners and coverage reporting.

### `Write API Layer Tests`

Create test classes for AuthService, CustomerService, EmployeeService, SupportTicketService with mock Repository dependencies; test business logic paths (login success/failure, fetch tracks for customer, calculate sales metrics).

### `Pass API Layer`

Write actual service code to make tests green; lean implementation without over-engineering.

### `Write Repository Test`

Create integration tests for Spring Data repositories using an in-memory H2 database or test SQLite; test custom query methods (e.g., findTracksByCustomerId(), findSalesMetricsByEmployeeId()).

### `Pass Repository Layer`

Add repository interfaces and custom query logic.

### `Write Controller Tests with Mocked Services`

Create REST controller tests using MockMvc; mock service layer and verify endpoints, HTTP status codes, request/response payloads, and authentication guards.

### `Pass Controller Tests`

Build REST endpoints that satisfy controller test assertions.

### `Write Angular Service Tests`

Create unit tests for HttpClient calls, token handling, and data transformation; mock HTTP responses.

### `Write Angular Component Tests`

Create component tests for login, dashboards, ticket views; mock service calls and test UI logic, routing, and user interactions.

### `Pass Angular Components to Pass Tests`

Build components incrementally, test-first.
