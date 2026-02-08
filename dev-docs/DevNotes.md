<!-- markdownlint-disable MD042 -->

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

> [!IMPORTANT] DEV NOTE
>
> Make sure you have the right JDK version installed, your terminal pointing to the version you want to use, and correct `JAVA_HOME`/`PATH` are set up.
>
> You can reference [JDKs (Java Development Kits)](./Gradle.md#jdks-java-development-kits-jenv) for more information on your prerequisites.

**ACTION ITEMS:**

- [ ] 1. [Have spring initializer website](https://start.spring.io/) create `build.gradle` for you and place it at root level if you don't have one.
- [ ] 2. Run `./gradlew <clean> build` or `gradle build` command in terminal at server root level to build project and download all dependencies listed in `build.gradle` file.
- [ ] 3. Verify installation. You should see a `build/` folder and `.gradle/` cache created; no errors means success.
- [ ] X. Make sure you have Node.js installed on your machine (npm comes with Node.js). I'm using `nvm` as a node manager to install and switch between versions.
- [ ] X. Install npm to make sure you can install Angular CLI with `npm install -g npm` and `npm -v` to verify you have it installed globally on your machine.
- [ ] X. Install Angular CLI if not already installed with `npm install -g @angular/cli@latest`.
- [ ] X. Install new Angular project in client using `ng new <project name> --directory .`.
- [ ] X. Run `npm install` to install packages.
- [ ] X. Run `npm run start` to run frontend and start to see angular project.
- [ ] X. Run `npm i -D vitest @vitest/coverage-v8 happy-dom` because we no longer use Jasmine or Karma for testing and instead use Vitest.
- [ ] X. Run `npm rm karma karma-chrome-launcher karma-jasmine karma-jasmine-html-reporter jasmine-core`

#### How to Run Tests Once Installed

**Spring Boot:** In server, run `gradle test` or `./gradlew test`.
**Angular:** In client, run `npm test`.

Note: The Vitest config uses ESM and lives at `client/vitest.config.mts`.

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
