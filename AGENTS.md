# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java/com/strategicgains/aclaid/` contains the core library code (AccessControl, domain models, builders, and rewrite rules).
- `src/test/java/com/strategicgains/aclaid/` holds JUnit 4 tests and examples (e.g., `*Test.java`).
- `docs/images/` contains diagrams and namespace reference assets.
- `pom.xml` defines the Maven build, Java 11 target, and publishing plugins.

## Build, Test, and Development Commands
- `mvn test` runs the JUnit 4 test suite.
- `mvn package` builds the JAR in `target/`.
- `mvn verify` runs tests and attaches sources/javadocs; it also triggers artifact signing via GPG.
- `mvn -DskipTests package` builds quickly without running tests.

## Coding Style & Naming Conventions
- Java 11, package naming follows `com.strategicgains.aclaid`.
- Indentation uses tabs, and braces generally start on a new line (Allman style).
- Class names use `UpperCamelCase`; methods and variables use `lowerCamelCase`.
- No formatter or linter is enforced in the build; keep changes consistent with existing style.

## Testing Guidelines
- Tests use JUnit 4 (`org.junit` in `pom.xml`).
- Name tests with the `*Test.java` suffix and keep them in the matching package under `src/test/java/`.
- Run focused tests with `mvn -Dtest=ClassNameTest test`.

## Commit & Pull Request Guidelines
- Commit messages are short, imperative sentences without prefixes (e.g., "Updated diagrams", "Added throws InvalidTupleException").
- PRs should include a clear description, testing notes (`mvn test` or rationale if skipped), and link related issues when applicable.
- Add screenshots only when changing docs images or diagrams in `docs/images/`.

## Security & Configuration Notes
- `mvn verify` enables GPG signing; ensure your local GPG setup is ready if publishing artifacts.
- Avoid committing generated outputs from `target/`.
