# Performance Optimization Task List

- `[x]` Optimize `ProductCard` (State & DI)
    - `[x]` Update `ProductCard` signature to accept `itemQuantity` and lambdas
    - `[x]` Update callers (e.g., `ProductScreen`, `VerticalTabProductScreen`) to pass state and callbacks
- `[x]` Fix Broken Laziness in `AllCategoryScreen`
    - `[x]` Wrap static lists in `remember`
    - `[x]` Flatten `LazyColumn` items if possible (Optimized components instead)
- `[x]` Optimize Header Animations
    - `[x]` Switch to `graphicsLayer` in `CategoryScreen`
    - `[x]` Switch to `graphicsLayer` in `HomeScreen`
- `[x]` General Data Optimization
    - `[x]` Apply `remember` to static lists in all screen composables
- `[x]` Verification
    - `[x]` Build project
    - `[x]` Manual check of jank in critical screens
