# ACL-Aid-Z

ACL-Aid-Z is a Java library for modeling relationship-based access control (ReBAC) in the style of Zanzibar. It lets you define object types and relations, store tuples, and evaluate access checks using rewrite rules such as unions and tuple-to-userset traversal.

## Installation

Build and install the JAR to your local Maven repository:

```sh
mvn install
```

Then depend on it from your application:

```xml
<dependency>
  <groupId>com.strategicgains</groupId>
  <artifactId>acl-aid-z</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Quick Start

```java
import static com.strategicgains.aclaid.builder.rewrite.Rewrites._this;
import static com.strategicgains.aclaid.builder.rewrite.Rewrites.computedUserSet;
import static com.strategicgains.aclaid.builder.rewrite.Rewrites.union;

import com.strategicgains.aclaid.AccessControl;
import com.strategicgains.aclaid.builder.AccessControlBuilder;

AccessControlBuilder builder = new AccessControlBuilder();
builder
  .object("doc")
    .relation("owner")
    .relation("editor")
      .rewrite(union(_this(), computedUserSet("owner")))
  .tuple("app:user/kim", "owner", "app:doc/roadmap");

AccessControl acl = builder.build();
boolean canEdit = acl.check("app:user/kim", "editor", "app:doc/roadmap");
```

## Concepts & Data Formats

- Object IDs follow `namespace:type/id`, for example `app:doc/roadmap`.
- Usersets are `object#relation`, for example `app:group/admins#member`.
- Tuples represent `object#relation@userset` and are added via `.tuple(userOrUserset, relation, object)`.
- Wildcards are supported in userset user IDs (e.g., `app:user/*`), but tuple resource wildcards are invalid and raise `InvalidTupleException`.

## Rewrite Rules

Use the builder DSL in `com.strategicgains.aclaid.builder.rewrite.Rewrites` to express union, computed usersets, and tuple-to-userset relationships (see `ZanzibarAcademyTest` for richer examples).

## More Examples

- `src/test/java/com/strategicgains/aclaid/AccessControlTest.java`
- `src/test/java/com/strategicgains/aclaid/ZanzibarAcademyTest.java`

## Contributor Guidance

See `AGENTS.md` for contributor-focused guidelines (project layout, build/test commands, style, and PR expectations) to keep changes consistent and reviewable.

## Compatibility

- Java 11 (configured in `pom.xml`)
- JUnit 4 for tests
