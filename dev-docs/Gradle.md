<!-- markdownlint-disable MD025 -->
<!-- markdownlint-disable MD032 -->
<!-- markdownlint-disable MD033 -->
<!-- markdownlint-disable MD060 -->

# What is Gradle?

`Gradle` is an open source build system for Java, Android, and Kotlin.

`Gradle Build Tool` is a (there words not mine) fast, dependable, and adaptable open source build automation tool with an elegant and extensible declarative build language.

Gradle supports Android, Java, Kotlin Multiplatform, Groovy, Scala, Javascript, and C/C++.

# Gradle Wrapper

# JDKs (Java Development Kits)

You can have multiple JDKs installed on your machine.

When you run:

- `java`
- `javac`
- `gradle`
- `./gradlew`

your my MacBook picks a Java version based on: <br>
(a) what your shell environment says (JAVA_HOME, PATH) and <br>
(b) what the tool itself prefers/embeds/configures.

> [!NOTE] My Scenario
>
> Homebrew (Homebrew package manager) installed OpenJDK 25 when I ran `brew install gradle`
>
> but my terminal still points to JDK 23 because my JAVA_HOME/PATH still points there.


I was trying to update VS Code settings at:

```json
// ~/Library/Application Support/Code/User/profiles/-2c624891/settings.json

"java.configuration.runtimes": [
    {
      "name": "JavaSE-25",
      "path": "/Library/Java/JavaVirtualMachines/" // <- But I don't have JDK 25 in here when trying to point to it...
    }
  ],
```




# Prerequisites

- JDK 17+

# Commands

|                                           Command                                            |                         What is does                          | Link to resources, documentation, etc |
| :------------------------------------------------------------------------------------------- | :------------------------------------------------------------ | :------------------------------------: |
|                                       `java -version`                                        |  See which version of Java's JDK we are running on machine.   | [jenv repo](https://github.com/jenv/jenv) |
|                                         `gradle -v`                                          |         See which version of gradle you are running.          | |
|                                    `brew install gradle`                                     |                AORN, 6Feb2026 -> version 9.3.1                | |
|                                    `./gradlew --version`                                     |                 See version of gradle wrapper                 | |
|                          `./gradlew wrapper --gradle-version X.X.X`                          |         Installs specific version of gradle wrapper.          | |
|                                   `./gradlew dependencies`                                   |        See the full dependency tree (who pulled what)         | |
|                               `./gradlew dependencies --scan`                                |      A full web-based, searchable dependency tree report      | |
| `./gradlew dependencyInsight --dependency jackson-databind --configuration runtimeClasspath` | Zoom in on one dependency and see why that version got chosen | |
|                                              `brew install jenv`                                              | Helps manage JDK versions and switch quickly between them | |
|                                              `brew shellenv`                                              |                Shows me all the                                                | |
|                                              ``                                              |                                                               | |
|                                              ``                                              |                                                               | |

## Markdown Newlines

To start a new paragraph, leave a blank line between blocks.

To force a line break inside the same paragraph, end the line with two spaces or use `<br>`.
