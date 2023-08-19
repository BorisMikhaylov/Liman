# Liman Annotations

**Liman Annotations** is an innovative Java project designed to revolutionize the way annotations are used and
interpreted in your codebase. With the ability to annotate other annotations, and the inclusion of specialized
annotations like `@Once` and `@ForceStatic`, Liman Annotations empowers developers to enhance code clarity,
maintainability, and functionality.

## Features

### Annotate the Annotations

Liman Annotations takes a unique approach by allowing you to annotate other annotations. This meta-annotation capability
gives you unprecedented flexibility in configuring the behavior of your annotations. By applying annotations to
annotations, you can create powerful combinations and control how your code is processed.

### `@Once`

Use `@Once` annotation is a game-changer when it comes to enforcing code structure. It restricts each class to have only
one method marked with the `@Once` annotation. This promotes a clear and focused design where a specific method's role
stands out, enhancing the readability and understanding of your codebase.

### `@ForceStatic` and `@ForceNonStatic`

The `@ForceStatic` annotation addresses the need for static methods in certain scenarios. By applying this annotation to
a method, you ensure that the method must be static, eliminating accidental non-static method creation. This is
particularly useful in utility classes and scenarios where static methods are preferred for performance or design
reasons.

## Benefits

- **Code Clarity:** The ability to annotate annotations itself provides a new layer of clarity. Annotations can now be
  categorized, grouped, and their behavior modified systematically.
- **Structural Enforcement:** The `@Once` annotation encourages a clean design by limiting a class to a single
  designated method, enhancing the structure of your code.
- **Static Method Integrity:** With the `@ForceStatic` annotation, you can guarantee that specific methods are static,
  avoiding inconsistencies and potential runtime errors.
- **Enhanced Expressiveness:** The combination of annotations allows for expressive and precise configuration of your
  code's behavior without cluttering it with excessive logic.

## How to Get Started

Getting started with Liman Annotations is straightforward:

1. Download and integrate the Liman Annotations library into your Java project.
2. Begin annotating your annotations to modify their behavior or create new combinations.
3. Utilize the `@Once` annotation to emphasize single-responsibility methods and improve code organization.
4. Employ the `@ForceStatic` annotation to enforce static method usage where required.

Liman Annotations empowers you to take control of your annotations and optimize your codebase's structure and
functionality like never before.

Discover the new dimension of annotation usage with Liman Annotations! Your code will thank you.

For documentation, examples, and support, visit our [official website](https://www.limanannotations.com).
