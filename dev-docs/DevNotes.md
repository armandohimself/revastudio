<!-- markdownlint-disable -->

# 🛠️ RevaStudio Development Notes (Sequential TDD Playbook)

> This is your organized, reusable, plain-English guide for how TDD is **supposed to be done** in this project style.
>
> It keeps all major details from your original notes + mentoring Q&A (JPA relationships, DTOs, testing ladder, nullability, MockMvc, RestAssured, etc.) and puts them in the right learning + build order.

**Stack:** Spring Boot + Angular (Vitest) (+ optional Playwright / RestAssured / Cucumber)

---

## 🧭 How to Use This File

1. Read **Part A (Learning Blocks)** once before coding.
2. Follow **Part B (Sequential TDD Steps)** feature-by-feature.
3. Use **Part C (Cheat Sheets)** while coding tests/controllers/entities.
4. Use **Part D (Question Bank)** when you return later and want quick recall.

---

# Part A — Learning Blocks (Prerequisite Concepts in Plain English)

## Block 0 — Relationship you are modeling

### Plain English

- One `Customer` can have one support rep (`Employee`) (or no rep if optional).
- One `Employee` can support many customers.

### Technical terms

- Many-to-One: many customers → one employee.
- One-to-Many: one employee → many customers.

### Visual

```text
Customer.SupportRepId  --->  Employee.EmployeeId
```

---

## Block 1 — FK column in DB vs object relationship in Java

### Option A: scalar FK only

```java
@Column(name = "SupportRepId")
private UUID supportRepId;
```

✅ Simple

❌ No direct navigation (`customer.getSupportRep().getTitle()` is impossible)

### Option B: ORM object relationship (common in Spring/JPA)

```java
@ManyToOne(fetch = FetchType.LAZY, optional = true)
@JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")
private Employee supportRep;
```

✅ Natural object navigation (`customer.getSupportRep().getTitle()`)

⚠️ Must understand LAZY, owning side, recursion, DTO boundaries

---

## Block 2 — Annotation breakdown (piece by piece)

### `@ManyToOne(fetch = FetchType.LAZY, optional = true)`

- `@ManyToOne`: many customers can point to one employee.
- `fetch = LAZY`: load employee only when first accessed.
- `optional = true`: customer may have no support rep.

### `@JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")`

- `name`: FK column in **Customer** table.
- `referencedColumnName`: target column in **Employee** table.
- If referenced target is PK, `referencedColumnName` is often optional.

### `@OneToMany(mappedBy = "supportRep", fetch = FetchType.LAZY)`

- Inverse/mirror collection on employee.
- `mappedBy = "supportRep"` points to Java field name in `Customer`.
- `mappedBy` is **not** a DB column name.

### `@JoinColumn` vs `@JoinColumns`

- `@JoinColumn`: one FK column.
- `@JoinColumns`: multiple columns (composite key join).

---

## Block 3 — Owning side vs inverse side (critical)

```text
Customer (owning side) --@ManyToOne + @JoinColumn--> Employee
Employee (inverse side) --@OneToMany(mappedBy="supportRep")--> List<Customer>
```

- Owning side updates FK in DB (`Customer.supportRep`).
- Inverse side does not persist FK by itself (`Employee.supportedCustomers`).

### Practical truth

```java
customer.setSupportRep(employee);              // owning side -> updates FK
employee.getSupportedCustomers().add(customer); // inverse side -> memory sync only
```

Do both for in-memory consistency. Usually add a helper method.

---

## Block 4 — Why `new ArrayList<>()` matters

```java
private List<Customer> supportedCustomers = new ArrayList<>();
```

- Prevents null list reference.
- Safe `.add(...)` immediately.
- Avoids `NullPointerException` from uninitialized collection.

### List vs ArrayList

- `List`: interface/contract (“ordered collection”).
- `ArrayList`: concrete implementation (resizable array).

---

## Block 5 — Lazy loading in plain English

### What

- Hibernate gives a placeholder/proxy first.
- Actual related row is loaded when you touch it.

### Example flow

```text
Load Customer -> supportRep is proxy
call customer.getSupportRep().getTitle()
-> Hibernate runs SELECT Employee ...
```

### Why used

- Avoid loading huge object graphs unless needed.

### Risks

- Hidden SQL queries.
- `LazyInitializationException` if accessed after session closes.

### Alternatives

1. `EAGER` fetch (simpler, but can over-fetch heavily)
2. Keep `LAZY` + explicit fetch join/query per endpoint (common best practice)

---

## Block 6 — Recursion in JSON serialization

### Plain English

If you return entities directly:

```text
Employee -> supportedCustomers[] -> supportRep -> supportedCustomers[] -> ...
```

Jackson can recurse forever.

### “No recursion / lazy loading surprises” means

- No accidental infinite JSON loops.
- No unexpected lazy-triggered SQL / session errors during serialization.

### Fixes (ranked)

1. ✅ Best: return DTOs from controllers.
2. ✅ Quick: `@JsonIgnoreProperties` on one side.
3. ✅ Alternative: `@JsonManagedReference` / `@JsonBackReference`.

### Why ignoring either side works

- Recursion needs both arrows. Remove one arrow, loop breaks.

---

## Block 7 — Serialize in plain English

Serialize = convert Java object → JSON text in HTTP response.

Example:

```java
Employee e = ...
```

becomes

```json
{
  "employeeId": "...",
  "title": "Sales Support Agent"
}
```

---

## Block 8 — Where `application.properties` goes

If missing, create:

```text
src/main/resources/application.properties
```

Useful debug settings:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE
```

---

# Part B — Sequential TDD Steps (What to do, in order)

## 1) Set up project skeletons first

### Prerequisites check

- ✅ Correct JDK version installed.
- ✅ Terminal points to right Java version.
- ✅ `JAVA_HOME` and `PATH` are correct.

Reference: [Gradle.md](Gradle.md)

### Backend setup (Spring Boot)

1. Generate project (`build.gradle` at server root).
2. Build and download dependencies:

```bash
cd server
./gradlew clean build
```

3. Verify success:

- `build/` exists
- `.gradle/` cache exists
- no terminal errors

### Frontend setup (Angular)

```bash
# using nvm
nvm install 18
nvm use 18
node -v
npm -v

npm install -g @angular/cli@latest
ng version

cd client
ng new revastudio --directory .
npm install
```

### Switch frontend testing to Vitest

```bash
npm i -D vitest @vitest/coverage-v8 happy-dom jsdom
npm rm karma karma-chrome-launcher karma-jasmine karma-jasmine-html-reporter jasmine-core
```

### Verify frontend

```bash
npm run start
npm test
```

---

## 2) Define test-running routine before writing features

### Spring Boot

```bash
cd server
./gradlew test
./gradlew cucumberTest
```

### Angular

```bash
cd client
npm test
npm run coverage
```

Vitest config path: `client/vitest.config.mts`

---

## 3) TDD sequence per feature (core rule)

```text
Backend: Service tests -> Service impl -> Repo tests -> Repo impl -> Controller tests -> Controller impl
Frontend: Service tests -> Component tests -> Component impl
Integration/E2E: last
```

### Why this order

- Inner logic stabilizes first.
- Outer layers become simpler/faster to test.
- Integration failures become easier to isolate.

---

## 4) Write Service Layer tests first (RED)

Focus: business logic with mocked repositories.

Examples covered:

- `AuthService`: login success/failure
- `CustomerService`: fetch tracks for customer
- `EmployeeService`: sales metrics
- `SupportTicketService`: ticket lifecycle

Example structure:

```java
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private TrackRepository trackRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnTracksForCustomer() {
        Long customerId = 1L;
        List<Track> mockTracks = Arrays.asList();
        when(trackRepository.findByCustomerId(customerId)).thenReturn(mockTracks);

        List<Track> result = customerService.getTracksByCustomer(customerId);

        assertThat(result).hasSize(mockTracks.size());
        verify(trackRepository).findByCustomerId(customerId);
    }
}
```

---

## 5) Implement Service Layer (GREEN)

Guidelines:

- ✅ Write only enough to pass tests.
- ✅ Keep implementation lean.
- ✅ Refactor only after green.
- ❌ Don’t add untested features.

---

## 6) Write Repository tests next

Use `@DataJpaTest` for DB mapping/query truth.

Coverage:

- derived query methods
- data persistence
- relationship behavior
- query correctness

Example:

```java
@DataJpaTest
class TrackRepositoryTest {
    @Autowired
    private TrackRepository trackRepository;

    @Test
    void shouldFindTracksByCustomerId() {
        Long customerId = 1L;
        List<Track> tracks = trackRepository.findByCustomerId(customerId);

        assertThat(tracks).isNotEmpty();
        assertThat(tracks).allMatch(track ->
            track.getInvoiceLines().stream()
                .anyMatch(il -> il.getInvoice().getCustomerId().equals(customerId))
        );
    }
}
```

---

## 7) Implement Repository layer

### What you get by default from `JpaRepository<T, ID>`

- save/update: `save`, `saveAll`, `saveAndFlush`, `flush`
- read: `findById`, `findAll`, `findAllById`, `getReferenceById`
- delete: `delete`, `deleteById`, `deleteAll`, batch deletes
- existence/count: `existsById`, `count`
- paging/sort: `findAll(Pageable)`, `findAll(Sort)`

### What custom methods to add

Only add methods when it is a real domain question or fetch strategy need.

Examples:

```java
List<Customer> findByCompany(String company);
List<Customer> findBySupportRepEmployeeId(UUID employeeId);
long countBySupportRepEmployeeId(UUID employeeId);
Page<Customer> findByCompanyContainingIgnoreCase(String company, Pageable pageable);
```

Use `@Query` when method names get too long or you need fetch join behavior.

---

## 8) Write Controller tests (then controller)

### Controller test slice (`@WebMvcTest`)

- fast
- no DB
- service mocked
- validates route/status/json/validation behavior

### Why `@WebMvcTest(CustomerController.class)`

It limits context to that controller and avoids extra noise/beans.

### Correct mocking annotation in this setup

- use `@MockBean` for Spring slice tests
- use `@Mock + @InjectMocks` for pure Mockito unit tests

### Common import pitfall

Correct import:

```java
import org.springframework.boot.test.mock.mockito.MockBean;
```

### Why test method often has `throws Exception`

`mockMvc.perform(...)` can throw checked exceptions; test signature keeps code clean.

---

## 9) Implement Controllers

Return DTOs as API contracts (best practice), not entities directly.

```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}/tracks")
    public ResponseEntity<List<TrackDTO>> getCustomerTracks(@PathVariable Long id) {
        List<Track> tracks = customerService.getTracksByCustomer(id);
        return ResponseEntity.ok(tracks.stream().map(TrackDTO::from).toList());
    }
}
```

---

## 10) Frontend TDD sequence

1. Angular service tests
2. Angular component tests
3. Angular implementation

### Service test focus

- HTTP method/path
- token handling
- mapping/transform correctness

### Component test focus

- UI logic
- rendering based on data
- user interaction and routing behavior

---

## 11) Integration + E2E last

### Integration

- `@SpringBootTest + @AutoConfigureMockMvc`
- validates layer wiring: DB ↔ repo ↔ service ↔ controller

### API integration tests

- RestAssured + running app (`RANDOM_PORT`) for real HTTP contract checks

### E2E

- Playwright (UI/browser)
- Cucumber/Selenium optional for BDD/browser automation

---

# Part C — Cheat Sheets You Asked For

## 1) Entity mapping example (recommended relationship model)

### Customer (owning side)

```java
@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "CustomerId", nullable = false)
    private UUID customerId;

    @Embedded
    private PII personalIdentifiableInformation;

    @Embedded
    private Address address;

    @Column(name = "Company")
    private String company;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")
    private Employee supportRep;
}
```

### Employee (inverse side)

```java
@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "EmployeeId", nullable = false)
    private UUID employeeId;

    @Embedded
    private PII personalIdentifiableInformation;

    @Embedded
    private Address address;

    @Column(name = "Title")
    private String title;

    @OneToMany(mappedBy = "supportRep", fetch = FetchType.LAZY)
    private List<Customer> supportedCustomers = new ArrayList<>();

    @Column(name = "ReportsTo")
    private String reportsTo;

    @Column(name = "BirthDate")
    private String birthDate;

    @Column(name = "HireDate")
    private String hireDate;
}
```

### If you keep both `supportRepId` + relationship

```java
@Column(name = "SupportRepId", insertable = false, updatable = false)
private UUID supportRepId;

@ManyToOne(fetch = FetchType.LAZY, optional = true)
@JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")
private Employee supportRep;
```

---

## 2) Helper methods for bidirectional sync

```java
public void addSupportedCustomer(Customer customer) {
    supportedCustomers.add(customer); // inverse list sync
    customer.setSupportRep(this);     // owning side FK source
}

public void assignSupportRep(Employee employee) {
    this.setSupportRep(employee);
    employee.getSupportedCustomers().add(this);
}
```

---

## 3) DTO design flow (industry standard)

### Core rule

A DTO should match one endpoint/screen need (not your entire entity graph).

### 3 common DTO types

1. **List/Card DTO** (small): id + display fields
2. **Detail DTO** (screen shape): one level of nested summaries
3. **Write DTOs** (`CreateRequest` / `UpdateRequest`): only client-editable fields

### Include in DTOs

- ids needed by UI
- display fields
- flattened relation fields (`supportRepName`)
- small summary objects
- useful derived values (`supportedCustomerCount`)

### Avoid in DTOs

- full nested entity graphs
- recursive collections by default
- internal server-only fields

### Relationship patterns

- forms: `supportRepId`
- lists: `supportRepName`
- details: summary object `{id,name,title}`

---

## 4) DTO naming guidance

### Good patterns

- `CustomerCreateRequest`
- `CustomerUpdateRequest`
- `CustomerListItem` / `CustomerSummary`
- `CustomerDetail` / `CustomerDetailResponse`

`Dto/DTO` suffix is allowed, but purpose-based names are clearer.

### Your provided DTO shape

```java
public record CustomerDto() {
    record CreateCustomerDto(String company, UUID supportRepId, AddressDto address, PIIDto pii) {}
    record UpdateCustomerDto(String company, UUID supportRepId, AddressDto address) {}
}
```

- Valid Java, but outer empty wrapper record is usually not preferred.
- Better:
  - one DTO per file (most common), or
  - `final class CustomerDtos { record Create... }` namespace pattern.

### Note from your discussion

If update endpoint should allow PII edits, include `pii` in update request.

---

## 5) Nulls, Optional, and NPEs

### Why NPE is bad

- user-facing 500 errors
- hidden bugs and unpredictability
- hard debugging
- production instability

### Null risk checklist

High risk null source:

- request input / external API / DB nullable columns
- lookups (`findById`, map.get)
- optional relations (`optional = true`)
- uninitialized collections

### 3-state model

- absent (`null`)
- present but empty (`""`, empty list)
- present with real value

### Optional mental model

`Optional<T>` = box that may or may not hold a value.

```java
Customer found = customerRepo.findById(c.getCustomerId()).orElseThrow();
```

Breakdown:

- `findById(...)` returns `Optional<Customer>`
- `orElseThrow()` unwraps if present
- if empty, throws immediately (in tests this is often desired)

Supplier variant:

```java
.orElseThrow(() -> new NotFoundException("Customer not found"))
```

### Should we catch thrown exceptions here?

- In tests: usually no, let test fail loudly.
- In services/controllers: throw domain exceptions and map to proper HTTP responses.

---

## 6) AssertJ vs JUnit assertions

- JUnit assertions are fine.
- AssertJ gives fluent style + readable failures.
- Choose one style per module for consistency.

---

## 7) `<T>` generics quick decode

- `<` `>`: type parameter container
- `T`: placeholder type (Type)
- `List<Customer>`: list of customers
- `Optional<Customer>`: optional customer
- `JpaRepository<Customer, UUID>`: repo for `Customer` with `UUID` id

---

## 8) `@Nullable` / `@NonNull` quick guidance

- Nullability annotations are generally not deprecated as a concept.
- Put on method params/returns/fields where it improves clarity.
- In JPA entities, rely heavily on JPA/db constraints too:
  - `optional = true/false`
  - `@Column(nullable = false)`

Common annotations vary by library (`Spring`, `JetBrains`, `Lombok`).

---

## 9) Testing ladder with your tool stack

```text
FASTEST
- Frontend unit: Vitest
- Backend unit: JUnit + Mockito (@Mock/@InjectMocks)
- Backend slices: @DataJpaTest, @WebMvcTest
- Backend integration/API: @SpringBootTest + MockMvc or RestAssured
- Full E2E: Playwright
SLOWEST / MOST REALISTIC
```

### WebMvcTest vs RestAssured

- `@WebMvcTest` + MockMvc: controller contract, fast, no DB.
- RestAssured + running server: realistic HTTP pipeline.
- Industry standard: use both (many fast slice tests + a few critical integration tests).

---

## 10) MockMvc chain explained

```java
mockMvc.perform(get("/customers/{id}", customerId))
  .andExpect(status().isOk())
  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
  .andExpect(jsonPath("$.customerId").value(customerId.toString()));

verify(customerService).getCustomerDetail(customerId);
```

- `perform(...)`: fake HTTP request in same JVM
- `get(...)`: build request
- `andExpect(...)`: assertions
- `jsonPath(...)`: assert JSON fields
- `verify(...)`: assert service was actually called

Stubbing pattern:

```java
when(customerService.getCustomerDetail(customerId)).thenReturn(dto);
```

- `when(...)`: intercept call
- `thenReturn(...)`: return prepared object
- `thenAnswer(...)`: advanced dynamic return behavior when needed

---

## 11) Multi-file create command (Mac/Linux)

```bash
touch file1 file2 file3
```

Example:

```bash
mkdir -p src/main/java/com/revastudio/revastudio/dto/customer
touch src/main/java/com/revastudio/revastudio/dto/customer/CustomerCreateRequest.java \
      src/main/java/com/revastudio/revastudio/dto/customer/CustomerUpdateRequest.java \
      src/main/java/com/revastudio/revastudio/dto/customer/CustomerListItem.java \
      src/main/java/com/revastudio/revastudio/dto/customer/CustomerDetail.java
```

---

## 12) Why test names include `roundTrip`

“Round trip” = save to DB, read back, values survive full trip.

Equivalent names are fine:

- `save_then_findById_returnsSameCustomer`
- `saveCustomer_thenCanLoadIt`

---

## 13) About `company` field confusion

Your concern is valid.

- In music app domain, `company` may feel odd.
- It might come from schema legacy/sample design.
- Keep if schema requires it; otherwise phase out in API/UI DTOs.

---

# Part D — Progress + Notes You Explicitly Mentioned

## Confirmed test progress note

From your update:

- `EmployeeRepositoryTest` updated with:
  - `supportRep_customer_relationship_persists_when_both_sides_synced()`
  - `supportRep_customer_relationship_persists_after_flush_and_clear()`
- First test demonstrates in-memory bidirectional sync + owning-side persistence check.
- Second demonstrates owning-side persistence + flush/clear + reload truth.
- Result: **5 passed, 0 failed**.

---

## Annotation pairing cheat sheet (quick lookup)

- Repository slice: `@DataJpaTest` + `@Autowired repositories`
- Controller slice: `@WebMvcTest(Controller.class)` + `@MockBean service`
- Full integration: `@SpringBootTest` + `@AutoConfigureMockMvc` (or RestAssured)
- Unit test (no Spring): `@ExtendWith(MockitoExtension.class)` + `@Mock` + `@InjectMocks`

---

## Your original quick exception notes (kept and organized)

- `NullPointerException`: unexpected null usage (validation earlier is better)
- `IllegalArgumentException`: invalid method input
- `IllegalStateException`: invalid state for operation
- `RuntimeException`: generic fallback (prefer specific when possible)
- Custom domain exceptions (e.g., `CustomerNotFoundException`) preferred

---

# Part E — Minimal Reusable TDD Loop (copy/paste for future projects)

```text
For each feature:
1) Write service test (RED)
2) Implement service (GREEN)
3) Refactor service (REFACTOR)
4) Write repository test (RED)
5) Implement repository query/mapping (GREEN)
6) Refactor repository code/test data
7) Write controller test (RED)
8) Implement controller + DTO mapping (GREEN)
9) Refactor controller/contracts
10) Add/update frontend service/component tests
11) Implement frontend behavior
12) Add 1-2 integration tests for critical path
13) Add E2E scenario once flow is stable
```

Definition of done per feature:

- service tests green
- repository tests green
- controller tests green
- frontend tests green (if touched)
- no recursion/lazy surprises in API responses
- one focused commit sequence (`test -> impl -> refactor`)

---

## 📚 Related docs

- [MediaManager.md](MediaManager.md)
- [UserStories.md](UserStories.md)
- [Gradle.md](Gradle.md)
- [HELP.md](HELP.md)

---

> Keep this as your return-to-project anchor: read Block 0–6, then execute Part B in order.
