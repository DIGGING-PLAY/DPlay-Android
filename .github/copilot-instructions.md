# Runtime Code Review Rules (for Copilot)

Scope: The following rules apply to all code review comments.

- Review language: Respond in Korean.
- Architecture: Verify compliance with Clean Architecture and MVI principles.
- Side-effect APIs
- Accessibility
- When implementing components in `core:designsystem`, review whether they are well designed in the Compose component style.
- Analyze Compose layout
  - State Hoisting: Move state up to the lowest common ancestor that needs access to it. This improves testability and makes components more reusable.
  - Unidirectional Data Flow: Data flows down, events flow up. This makes debugging and reasoning about your UI easier. Use remember to store state, and pass lambdas for events.
  - Immutable Data: Use data classes and Immutable annotations where possible. This helps Compose optimize recompositions.
  - Using 'remember' to cache results, but only for values that should survive recompositions (as this could cause a memory leak)
  - Using the 'key' parameter in lazy layouts to avoid unnecessary recompositions
  - Using 'derivedStateOf' for rapidly changing states
  - Avoiding backwards writes, changing state after it has been in a composable to prevent recomposition loops
  - Ensuring breaking down UI into smaller composables that do one thing well
  - Proper state management, hoisting state to parent composables and using lifecycle-aware coroutine scopes like 'viewModelScope' or 'lifecycleScope' for async operations
  - Adhering to Jetpack Compose API guidelines for naming, layering components, and ensuring accessibility
  - Using Baseline Profiles and R8 optimizations
  - Passing a 'Modifier' parameter in composables to allow customization and maintain consistency
  - Not excessively overusing modifiers, resulting in reduced readability and clutter
  - Unnecessary use of WebView inside of composables
- Refactor the Kotlin code below to be more idiomatic, efficient, and readable. Focus on
  using Kotlin's features like extension functions, data classes, sealed classes, and
  coroutines where appropriate. Explain the changes you made and why they improve the
  code. Also, suggest potential performance optimizations.
- Analyze the code to identify the specific performance bottlenecks.
  Suggest and implement optimizations to address these bottlenecks.
  Include comments explaining the changes and their expected impact on performance.
  If applicable, add logging or other performance metrics to measure the improvements.