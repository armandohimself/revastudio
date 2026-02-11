# 🎨 RevaStudio Client

> Angular frontend for the RevaStudio Media Management Platform

Built with [Angular CLI](https://github.com/angular/angular-cli) v13.0.4 • Testing with [Vitest](https://vitest.dev/)

---

## 🚀 Quick Start

### Development Server

```bash
npm run start
# or
ng serve
```

Navigate to **http://localhost:4200/** — the app auto-reloads on file changes.

### Testing

```bash
npm test          # Run Vitest tests
npm run coverage  # Generate coverage report
```

> **Note:** This project uses **Vitest** instead of Karma/Jasmine for faster, more modern testing.

---

## 📦 Project Structure

```
src/
├── app/
│   ├── app-routing.module.ts    # Route configuration
│   ├── app.component.ts         # Root component
│   └── app.routes.ts            # Standalone route definitions
├── assets/                      # Static assets
├── environments/                # Environment configs
│   ├── environment.ts           # Development
│   └── environment.prod.ts      # Production
└── styles.scss                  # Global styles
```

---

## 🛠️ Angular CLI Commands

### Code Generation

```bash
# Components
ng generate component feature/component-name
ng g c feature/component-name

# Services
ng generate service services/service-name
ng g s services/service-name

# Other artifacts
ng g directive|pipe|guard|interface|enum|module <name>
```

### Build

```bash
# Development build
ng build

# Production build (optimized)
ng build --configuration production
```

Build artifacts are stored in the `dist/` directory.

---

## 📚 Key Technologies

| Technology     | Purpose               |
| -------------- | --------------------- |
| **Angular 13** | Frontend framework    |
| **TypeScript** | Type-safe development |
| **Vitest**     | Unit testing          |
| **SCSS**       | Styling               |
| **RxJS**       | Reactive programming  |

---

## 🔗 Useful Links

- 📖 [Angular CLI Documentation](https://angular.io/cli)
- 📖 [Angular Style Guide](https://angular.io/guide/styleguide)
- 🧪 [Vitest Documentation](https://vitest.dev/)
- 🎯 [Project Requirements](../dev-docs/MediaManager.md)
- 📝 [Development Workflow](../dev-docs/DevNotes.md)

---

## 💡 Tips

- Use `ng help` for quick CLI reference
- Run `ng lint` to check code quality (if configured)
- Check [../dev-docs/DevNotes.md](../dev-docs/DevNotes.md) for the development workflow
- See [../dev-docs/UserStories.md](../dev-docs/UserStories.md) for feature requirements
