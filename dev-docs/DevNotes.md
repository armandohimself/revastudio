<!-- markdownlint-disable -->

# 🛠️ Development Notes

> Test-driven development workflow for RevaStudio full-stack application

**Stack:** Spring Boot + Angular (with optional Ionic for mobile)

---

## 🚀 Quick Start Guide

Follow this checklist to set up and build the project using a test-driven approach:

### Phase 1: Project Setup

- [ ] [Set Up Project Skeletons](#1-set-up-project-skeletons)

### Phase 2: Backend Development (Inside-Out)

- [ ] [Create Entities Using Annotations (JPA + Lombok)]()
- [ ] [Create Basic Repositories (Spring Data JPA)]()
- [ ] [Write Service Layer Tests](#2-write-service-layer-tests)
- [ ] [Implement Service Layer](#3-implement-service-layer)
- [ ] [Write Repository Tests](#4-write-repository-tests)
- [ ] [Implement Repository Layer](#5-implement-repository-layer)
- [ ] [Write Controller Tests](#6-write-controller-tests)
- [ ] [Implement Controllers](#7-implement-controllers)

### Phase 3: Frontend Development

- [ ] [Write Angular Service Tests](#8-write-angular-service-tests)
- [ ] [Write Angular Component Tests](#9-write-angular-component-tests)
- [ ] [Implement Angular Components](#10-implement-angular-components)

### Phase 4: Integration & E2E

- [ ] [Write End-to-End Tests](#11-end-to-end-tests)
- [ ] [Integration Testing](#12-integration-testing)

---

## 📐 Test-Driven Development Workflow

### Testing Strategy: Innermost → Outermost

```bash
Backend:  Services → Repositories → Controllers
Frontend: Services → Components
```

**Per Feature Approach:**

1. Write all tests for one feature's service layer
2. Implement the service to pass tests
3. Move to the next layer (repository, controller)
4. Repeat for each feature

**Integration Last:**

Add end-to-end tests after unit layers are solid:

- Full login flow
- Customer viewing tracks
- Employee managing tickets

---

## 📋 Detailed Steps

### 1. Set Up Project Skeletons

> **⚠️ Important:** Ensure prerequisites are met before starting

#### Prerequisites Check

Before you begin, verify:

- ✅ **Correct JDK version** installed
- ✅ **Terminal pointing** to the right Java version
- ✅ **`JAVA_HOME`** and **`PATH`** properly configured

📖 **Reference:** [JDKs & Environment Setup](Gradle.md#-jdks--environment-management)

#### Backend Setup (Spring Boot)

**Step 1: Generate Project**

Use [Spring Initializr](https://start.spring.io/) to create `build.gradle` and place it at the server root.

**Step 2: Build & Download Dependencies**

```bash
cd server
./gradlew clean build
```

**Step 3: Verify Installation**

✅ Success indicators:

- `build/` folder created
- `.gradle/` cache created
- No errors in terminal

#### Frontend Setup (Angular)

**Step 1: Install Node.js & npm**

```bash
# Using nvm (recommended)
nvm install 18
nvm use 18

# Verify installations
node -v
npm -v
```

**Step 2: Install Angular CLI**

```bash
npm install -g @angular/cli@latest
ng version
```

**Step 3: Create Angular Project**

```bash
cd client
ng new revastudio --directory .
```

**Step 4: Install Dependencies**

```bash
npm install
```

**Step 5: Switch to Vitest**

Remove Jasmine/Karma and install Vitest:

```bash
# Install Vitest
npm i -D vitest @vitest/coverage-v8 happy-dom

# Remove old testing tools
npm rm karma karma-chrome-launcher karma-jasmine karma-jasmine-html-reporter jasmine-core
```

**Step 6: Verify Setup**

```bash
# Start dev server
npm run start

# Run tests
npm test
```

---

### Running Tests

#### Spring Boot

```bash
cd server
./gradlew test          # Run all tests
./gradlew cucumberTest  # Run BDD tests
```

#### Angular

```bash
cd client
npm test               # Run Vitest tests
npm run coverage       # Generate coverage report
```

> **Note:** Vitest config is at `client/vitest.config.mts`

---

### 2. Write Service Layer Tests

**Focus:** Business logic with mocked repository dependencies

**Test Coverage:**

- ✅ `AuthService`: Login success/failure scenarios
- ✅ `CustomerService`: Fetch tracks for specific customer
- ✅ `EmployeeService`: Calculate and retrieve sales metrics
- ✅ `SupportTicketService`: Create, view, respond, close tickets

**Example Test Structure:**

```java
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnTracksForCustomer() {
        // Arrange
        Long customerId = 1L;
        List<Track> mockTracks = Arrays.asList(/* ... */);
        when(trackRepository.findByCustomerId(customerId))
            .thenReturn(mockTracks);

        // Act
        List<Track> result = customerService.getTracksByCustomer(customerId);

        // Assert
        assertThat(result).hasSize(mockTracks.size());
        verify(trackRepository).findByCustomerId(customerId);
    }
}
```

---

### 3. Implement Service Layer

Write service code to make tests pass — **lean implementation**, avoid over-engineering.

**Guiding Principles:**

- ✅ Make tests green
- ✅ Keep it simple
- ✅ Refactor only when all tests pass
- ❌ Don't add features not covered by tests

---

### 4. Write Repository Tests

**Focus:** Database interactions with test database (H2 or SQLite)

**Test Coverage:**

- Custom query methods
- Data persistence
- Query correctness

**Example Test:**

```java
@DataJpaTest
class TrackRepositoryTest {
    @Autowired
    private TrackRepository trackRepository;

    @Test
    void shouldFindTracksByCustomerId() {
        // Arrange
        Long customerId = 1L;

        // Act
        List<Track> tracks = trackRepository.findByCustomerId(customerId);

        // Assert
        assertThat(tracks).isNotEmpty();
        assertThat(tracks).allMatch(track ->
            track.getInvoiceLines().stream()
                .anyMatch(il -> il.getInvoice().getCustomerId().equals(customerId))
        );
    }
}
```

---

### 5. Implement Repository Layer

Add repository interfaces and custom query logic.

**Example:**

```java
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("SELECT DISTINCT t FROM Track t " +
           "JOIN t.invoiceLines il " +
           "JOIN il.invoice i " +
           "WHERE i.customer.id = :customerId")
    List<Track> findByCustomerId(@Param("customerId") Long customerId);
}
```

---

### 6. Write Controller Tests

**Focus:** REST endpoints with mocked services

**Test Coverage:**

- HTTP status codes
- Request/response payloads
- Authentication guards
- Endpoint behavior

**Example Test:**

```java
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void shouldReturnTracksForAuthenticatedCustomer() throws Exception {
        // Arrange
        Long customerId = 1L;
        List<Track> mockTracks = Arrays.asList(/* ... */);
        when(customerService.getTracksByCustomer(customerId))
            .thenReturn(mockTracks);

        // Act & Assert
        mockMvc.perform(get("/api/customers/{id}/tracks", customerId)
                .header("Authorization", "Bearer " + validJwtToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$.length()").value(mockTracks.size()));
    }
}
```

---

### 7. Implement Controllers

Build REST endpoints that satisfy controller test assertions.

**Example:**

```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}/tracks")
    public ResponseEntity<List<TrackDTO>> getCustomerTracks(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        // Verify user can only access their own data
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Track> tracks = customerService.getTracksByCustomer(id);
        return ResponseEntity.ok(tracks.stream()
            .map(TrackDTO::from)
            .collect(Collectors.toList()));
    }
}
```

---

### 8. Write Angular Service Tests

**Focus:** HTTP client calls, token handling, data transformation

**Example Test:**

```typescript
describe("CustomerService", () => {
  let service: CustomerService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CustomerService],
    });
    service = TestBed.inject(CustomerService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it("should fetch customer tracks", () => {
    const mockTracks = [
      /* ... */
    ];
    const customerId = 1;

    service.getCustomerTracks(customerId).subscribe((tracks) => {
      expect(tracks.length).toBe(mockTracks.length);
    });

    const req = httpMock.expectOne(`/api/customers/${customerId}/tracks`);
    expect(req.request.method).toBe("GET");
    req.flush(mockTracks);
  });

  afterEach(() => {
    httpMock.verify();
  });
});
```

---

### 9. Write Angular Component Tests

**Focus:** UI logic, routing, user interactions

**Example Test:**

```typescript
describe("CustomerDashboardComponent", () => {
  let component: CustomerDashboardComponent;
  let fixture: ComponentFixture<CustomerDashboardComponent>;
  let mockCustomerService: jasmine.SpyObj<CustomerService>;

  beforeEach(() => {
    mockCustomerService = jasmine.createSpyObj("CustomerService", ["getCustomerTracks"]);

    TestBed.configureTestingModule({
      declarations: [CustomerDashboardComponent],
      providers: [{ provide: CustomerService, useValue: mockCustomerService }],
    });

    fixture = TestBed.createComponent(CustomerDashboardComponent);
    component = fixture.componentInstance;
  });

  it("should display tracks on load", () => {
    const mockTracks = [
      /* ... */
    ];
    mockCustomerService.getCustomerTracks.and.returnValue(of(mockTracks));

    fixture.detectChanges();

    expect(component.tracks.length).toBe(mockTracks.length);
  });
});
```

---

### 10. Implement Angular Components

Build components incrementally, test-first.

**Workflow:**

1. Write component test
2. Implement minimum code to pass
3. Refactor if needed
4. Repeat for next feature

---

### 11. End-to-End Tests

**Focus:** Full user workflows across frontend and backend

**Example Scenarios:**

- User login → View dashboard → Logout
- Customer views tracks → Sends support ticket → Sees response
- Employee views sales metrics → Responds to ticket → Closes ticket

**Tools:**

- Cucumber for BDD scenarios
- Selenium for browser automation
- REST Assured for API validation

---

### 12. Integration Testing

**Focus:** Verify all layers work together

**Test Coverage:**

- Database ↔ Repository ↔ Service ↔ Controller
- Frontend ↔ Backend API integration
- Authentication flow end-to-end
- Error handling across layers

---

## 📚 Related Documentation

- 🎯 [Project Requirements](MediaManager.md) — Feature specifications
- 📖 [User Stories](UserStories.md) — Detailed user requirements
- ⚙️ [Gradle Configuration](Gradle.md) — Build tool reference
- 📘 [Spring Boot Help](HELP.md) — Annotations and commands

---

## 💡 Development Tips

### Best Practices

✅ **Write tests first** — TDD enforces better design
✅ **One feature at a time** — Complete vertical slices
✅ **Commit often** — Small, focused commits
✅ **Run tests frequently** — Catch issues early
✅ **Refactor when green** — Improve code after tests pass

### Common Pitfalls

❌ **Skip writing tests** — Leads to fragile code
❌ **Test too late** — Harder to retrofit tests
❌ **Mock everything** — Use real objects when reasonable
❌ **Ignore failing tests** — Fix immediately
❌ **Over-engineer** — Implement only what tests require

---

## 🎯 Success Metrics

Track your progress:

- [ ] All unit tests passing (services, repositories)
- [ ] All controller tests passing
- [ ] All Angular tests passing
- [ ] Integration tests passing
- [ ] E2E scenarios passing
- [ ] Code coverage > 80%
- [ ] No critical security vulnerabilities
- [ ] Application runs end-to-end

---

> **🚀 Ready to start?** Follow the checklist from the top and work through each phase systematically!
