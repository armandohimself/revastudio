<!-- markdownlint-disable -->

# 🎵 Media Manager Project

> RevaStudio's contract to rebuild Chinook Media Company's web platform

---

## 📋 Project Overview

You and your team have been tasked with building a **media management application** for RevaStudio. The company has won a contract to rebuild the **Chinook Media Company's web platform** from the ground up, and this is where you come in.

The Chinook Media Company plans on **slowly migrating operations** to the new platform, and they want to start with implementing **core platform features**.

---

## 🎯 Application Features

### Client-Side MVP (Angular)

#### 🔐 Authentication

- **Login/Logout** feature for both customers and employees

#### 👥 Customer Dashboard

- View all **tracks owned** by the customer
- Display **album & artist data** for each track
- Show **only purchased tracks** (no catalog browsing yet)
- **Support ticket system**:
  - Send tickets to assigned support employee
  - View ticket responses

#### 👔 Employee Dashboard

- View **sales metrics**:
  - Customers assisted
  - Tracks purchased
  - Customer billing amounts
- **Support ticket management**:
  - View customer tickets
  - Respond to tickets
  - Close resolved tickets

---

## 🔧 Technical Requirements

### Server-Side MVP (Spring Boot)

#### Architecture Layers

```
┌─────────────────────────────────────┐
│         RESTful API Layer           │  ← Controllers
├─────────────────────────────────────┤
│    Authentication (JWT-based)       │  ← Security
├─────────────────────────────────────┤
│        Service Layer                │  ← Business Logic
├─────────────────────────────────────┤
│       Repository Layer              │  ← Database Access
└─────────────────────────────────────┘
```

#### Key Components

- ✅ **RESTful API** for all client interactions
- 🔒 **JWT Authentication** for secure access
- 🛠️ **Service Layer** for all business logic
- 💾 **Repository Layer** for database operations

---

## 💾 Database & Persistence

### Technology

**SQLite** database for persistence

### Database Source

Use the pre-provided [SQLite Chinook database](https://github.com/lerocha/chinook-database) and/or the startup script found in the `ChinookDatabase/DataSources` directory.

### Entity-Relationship Diagram

![Media Manager ERD](chinook-erd.png)

> **Note:** You do not need an entity for every single table — just those necessary for the Client-Server MVPs.

---

## 🚀 Stretch Goals

Optional enhancements to implement after core features:

| Priority | Goal                   | Description                             |
| -------- | ---------------------- | --------------------------------------- |
| 🔒       | **Spring Security**    | Enhanced security framework integration |
| 📱       | **Ionic Wrapper**      | Mobile app experience for Angular       |
| 🐳       | **Jenkins & Docker**   | CI/CD pipeline and containerization     |
| ☁️       | **AWS EC2 Deployment** | Cloud hosting for production server     |

---

## 📚 Related Documentation

- 📖 [User Stories](UserStories.md) — Detailed feature requirements by role
- 🛠️ [Development Workflow](DevNotes.md) — TDD process and setup instructions
- ⚙️ [Gradle Configuration](Gradle.md) — Build tool and dependency management
- 📘 [Spring Boot Reference](HELP.md) — Annotations and API documentation

---

## 🗺️ Project Roadmap

1. **Phase 1:** Set up project skeletons (Spring Boot + Angular)
2. **Phase 2:** Implement authentication (JWT-based)
3. **Phase 3:** Build customer features (tracks dashboard, support tickets)
4. **Phase 4:** Build employee features (sales metrics, ticket management)
5. **Phase 5:** Integration testing and refinement
6. **Phase 6:** (Optional) Stretch goals

---

> **💡 Quick Start:** Follow the setup instructions in [DevNotes.md](DevNotes.md) to begin development.
