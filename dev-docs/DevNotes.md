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

**ACTION ITEMS:**

- [ ] 1. Create `build.gradle` file at server root level if you don't have one.
- [ ] 2. In `server/` -> add testing dependencies to Gradle (JUnit 5, Mockito, Spring Boot Test).
- [ ] 3. Add `client/` -> add package.json dependencies (Jasmine, Karma for Angular).
- [ ] 4. Configure test runners and coverage reporting.

> [!IMPORTANT] DEV NOTE
>
> Make sure you have the right JDK version installed, your terminal pointing to the version you want to use, and correct `JAVA_HOME`/`PATH` are set up.
>
> You can reference [JDKs (Java Development Kits)](./Gradle.md#jdks-java-development-kits) for more information on your prerequisites.


[Notion Notes1]()
[Notion Notes2]()
[Notion Notes3]()
[Notion Notes4]()
[Notion Notes5]()






Add JUnit 5 & Mockito to dependencies — In the dependencies {} block, add:

testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
testImplementation 'org.mockito:mockito-core:5.2.0'
testImplementation 'org.mockito:mockito-junit-jupiter:5.2.0'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'com.h2database:h2:2.1.214' (for in-memory test database)
Run Gradle download — Open terminal in server and run: gradle build or ./gradlew build (this downloads all dependencies listed).

Verify installation — You should see a build/ folder and .gradle/ cache created; no errors means success.

Steps for Angular Frontend
Locate or create package.json — Navigate to /Users/armandoarteaga/Github Repos/ARevatureLearning/revastudio/client/ directory; check if package.json exists.

Check for existing test dependencies — If Angular CLI created the project, Jasmine and Karma should already be there (search package.json for @angular/core, jasmine, karma).

Add missing dependencies (if needed) — If not present, add to devDependencies in package.json:

"jasmine-core": "^4.5.0"
"karma": "^6.4.0"
"karma-jasmine": "^5.1.0"
"karma-chrome-launcher": "^3.1.1"
Run npm install — Open terminal in client and run: npm install (this downloads all dependencies).

Verify installation — You should see a node_modules/ folder created; no errors means success.

How to Run Tests Once Installed
Spring Boot: In server, run gradle test or ./gradlew test
Angular: In client, run ng test or npm test
Further Considerations
Gradle vs Maven — Spring Boot can use either Gradle or Maven as build tools. Are you committed to Gradle, or open to Maven?

Angular project initialization — Has the Angular CLI already scaffolded the client folder, or is it completely empty? If empty, you need to run ng new client first.

Version compatibility — What Java version and Spring Boot version are you targeting? Versions above assume Spring Boot 3.x and Java 17+; adjust if different.











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
