# ⚙️ Gradle Build Configuration

> Build tool documentation for RevaStudio Spring Boot backend

---

## 📖 What is Gradle?

**Gradle** is an open-source build automation tool for Java, Android, Kotlin, and more.

**Gradle Build Tool** is a fast, dependable, and adaptable build system with an elegant and extensible declarative build language.

### Supported Languages

- ☕ Java
- 📱 Android
- 🎯 Kotlin Multiplatform
- 🔷 Groovy
- 📊 Scala
- 🌐 JavaScript
- ⚡ C/C++

---

## 🎁 Gradle Wrapper

The Gradle Wrapper (`./gradlew`) ensures everyone uses the same Gradle version, regardless of what's installed locally.

### Why Use the Wrapper?

✅ **Consistent builds** across different machines
✅ **No global Gradle installation** required
✅ **Version-locked** to project requirements

### Common Wrapper Commands

```bash
# Check wrapper version
./gradlew --version

# Upgrade wrapper to specific version
./gradlew wrapper --gradle-version 8.5

# Build project
./gradlew build

# Clean build artifacts
./gradlew clean
```

---

## ☕ JDKs & Environment Management

### Managing Multiple JDKs with `jenv`

You can have multiple JDKs installed on your machine. When you run commands like `java`, `javac`, `gradle`, or `./gradlew`, your system picks a Java version based on:

1. **Shell environment variables** (`JAVA_HOME`, `PATH`)
2. **Tool-specific configurations** (embedded/configured versions)

#### Install jenv (macOS)

```bash
# Install jenv
brew install jenv

# Add to shell (zsh)
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
echo 'eval "$(jenv init -)"' >> ~/.zshrc
source ~/.zshrc

# Verify installation
jenv --version
```

#### Using jenv

```bash
# List available Java versions
jenv versions

# Set global Java version
jenv global 21

# Set local (project) Java version
jenv local 21

# Add a JDK to jenv
jenv add /Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home
```

---

## 🛠️ Essential Commands

| Command                                                                            | Description                             | Notes                                    |
| ---------------------------------------------------------------------------------- | --------------------------------------- | ---------------------------------------- |
| `java -version`                                                                    | Check active JDK version                | Shows which Java is currently in use     |
| `gradle -v`                                                                        | Check globally installed Gradle version | May differ from wrapper version          |
| `./gradlew --version`                                                              | Check wrapper Gradle version            | Project-specific version                 |
| `./gradlew dependencies`                                                           | View full dependency tree               | Shows all dependencies and their sources |
| `./gradlew dependencies --scan`                                                    | Web-based dependency report             | Interactive, searchable tree             |
| `./gradlew dependencyInsight --dependency <name> --configuration runtimeClasspath` | Investigate specific dependency         | Shows version resolution details         |
| `brew install gradle`                                                              | Install Gradle globally (macOS)         | Latest version via Homebrew              |
| `brew install jenv`                                                                | Install Java environment manager        | For managing multiple JDKs               |

---

## 📦 Understanding `build.gradle`

### Plugin Configuration

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.1'
    id 'io.spring.dependency-management' version '1.1.7'
}
```

### Project Metadata

```gradle
group = 'com.revastudio'
version = '0.0.1-SNAPSHOT'
description = 'RevaStudio Media Manager'
```

### Java Toolchain

```gradle
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

### Repositories

```gradle
repositories {
    mavenCentral()
}
```

---

## 📚 Dependency Scopes Explained

Understanding when dependencies are used:

| Scope                 | When Used                  | Included in JAR |
| --------------------- | -------------------------- | --------------- |
| `implementation`      | Compile + Runtime          | ✅ Yes          |
| `compileOnly`         | Compile only               | ❌ No           |
| `annotationProcessor` | Code generation at compile | ❌ No           |
| `testImplementation`  | Test compile + runtime     | ❌ No           |
| `testRuntimeOnly`     | Test runtime only          | ❌ No           |
| `runtimeOnly`         | Runtime only               | ✅ Yes          |

---

## 🔧 Project Dependencies

### Core Application Dependencies

#### Spring Boot Starters

```gradle
// Spring Web + MVC (controllers, @RestController, routing)
implementation 'org.springframework.boot:spring-boot-starter-webmvc'

// Spring Data JPA (repositories, EntityManager, ORM)
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

// Production-ready features (health checks, metrics)
implementation 'org.springframework.boot:spring-boot-starter-actuator'

// JSON processing (ObjectMapper)
implementation 'org.springframework.boot:spring-boot-starter-json'
```

#### Database

```gradle
// SQLite dialect for Hibernate
implementation "org.hibernate.orm:hibernate-community-dialects:7.1.8.Final"

// SQLite JDBC driver
implementation 'org.xerial:sqlite-jdbc:3.51.1.0'
```

#### Security & Authentication

```gradle
// JWT API (compile-time interfaces)
implementation 'io.jsonwebtoken:jjwt-api:0.13.0'

// JWT implementation (runtime)
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.13.0'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.13.0'

// Password encryption (BCrypt)
implementation 'org.springframework.security:spring-security-crypto'
```

### Compile-Time Code Generation

```gradle
// Lombok annotations (@Getter, @Setter, @Builder)
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```

### Test Dependencies

#### Core Testing Frameworks

```gradle
// Spring Boot test bundle (JUnit Jupiter, MockMvc, etc.)
testImplementation "org.springframework.boot:spring-boot-starter-test"

// In-memory database for testing
testImplementation 'com.h2database:h2:2.4.240'

// AssertJ fluent assertions
testImplementation "org.assertj:assertj-core:3.26.3"

// JUnit Platform launcher
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```

#### BDD Testing (Cucumber)

```gradle
// Cucumber step definitions (@Given, @When, @Then)
testImplementation 'io.cucumber:cucumber-java:7.33.0'

// JUnit Platform integration
testImplementation 'io.cucumber:cucumber-junit-platform-engine:7.33.0'

// Spring integration for Cucumber
testImplementation 'io.cucumber:cucumber-spring:7.33.0'

// Suite support for @Suite annotation
testImplementation 'org.junit.platform:junit-platform-suite'
```

#### API & Browser Testing

```gradle
// REST Assured for API testing
testImplementation 'io.rest-assured:rest-assured:6.0.0'

// Selenium for browser automation
testImplementation 'org.seleniumhq.selenium:selenium-java:4.39.0'
```

> **⚠️ Warning:** Selenium drivers may not close properly with many tests — monitor resource usage.

---

## 🧪 Custom Gradle Tasks

### Cucumber Test Task

```gradle
tasks.register("cucumberTest", Test) {
    description = "Runs Cucumber BDD tests"
    group = "verification"

    testClassesDirs = sourceSets.test.output.classesDirs
    classpath = sourceSets.test.runtimeClasspath
    useJUnitPlatform()

    include("**/CucumberRunnerTest.class")
    testLogging { showStandardStreams = true }

    systemProperty "cucumber.filter.tags", "not @wip"
}
```

**Usage:**

```bash
./gradlew cucumberTest
```

### Open Cucumber Report

```gradle
tasks.register("openCucumberHtmlReport", Exec) {
    group = "verification"
    description = "Opens cucumberTest HTML report in default browser (macOS)"

    onlyIf { file("$buildDir/reports/tests/cucumberTest/index.html").exists() }

    commandLine "open", "$buildDir/reports/tests/cucumberTest/index.html"
}
```

### Combined Task: Test + Open Report

```gradle tasks.register("cucumberTestAndOpen") {
    group = "verification"
    description = "Runs cucumberTest and opens the HTML report"
    dependsOn("cucumberTest")
    finalizedBy("openCucumberHtmlReport")
}
```

**Usage:**

```bash
./gradlew cucumberTestAndOpen
```

### Enhanced Test Logging

```gradle
tasks.named('test') {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false
}

test {
    testLogging {
        showStandardStreams = true  // Print console logs
    }
}
```

---

## ⚠️ Common Pitfalls & Solutions

### 1. Dependency Conflicts

**Problem:** Mixing manually specified versions with Spring Boot's managed versions.

**Solution:** Let Spring Boot manage dependency versions — only specify versions for non-Boot dependencies.

```gradle
// ❌ BAD - Overrides Boot's managed version
testImplementation 'org.junit.jupiter:junit-jupiter:5.14.0'

// ✅ GOOD - Uses Boot's managed version
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

### 2. Duplicate Dependencies

**Problem:** Including dependencies already provided by starter packages.

```gradle
// ❌ AVOID - Already included in spring-boot-starter-test
testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa-test'
testImplementation 'org.springframework.boot:spring-boot-starter-webmvc-test'
```

### 3. REST Assured Order

**Problem:** REST Assured must be declared before JUnit dependencies.

```gradle
// ✅ CORRECT ORDER
testImplementation 'io.rest-assured:rest-assured:6.0.0'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

---

## 📚 Related Documentation

- 🛠️ [Development Workflow](DevNotes.md) — Setup and testing process
- 📘 [Spring Boot Annotations](HELP.md) — JPA and Lombok reference
- 🎯 [Project Overview](MediaManager.md) — Requirements and architecture

---

## 💡 Quick Tips

- **Always use `./gradlew`** instead of `gradle` for consistent builds
- **Check dependency tree** with `./gradlew dependencies --scan` when debugging conflicts
- **Use `--info` flag** for detailed build output: `./gradlew build --info`
- **Clear cache** if builds behave unexpectedly: `./gradlew clean build --refresh-dependencies`
