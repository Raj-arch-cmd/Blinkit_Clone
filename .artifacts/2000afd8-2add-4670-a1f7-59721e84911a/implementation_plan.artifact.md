# Performance Audit & Optimization Plan - Blinkit Clone

As a Senior Android Performance Engineer, I have audited the codebase for Jetpack Compose performance pitfalls. The following plan identifies the most critical lag sources and provides surgical fixes to ensure a smooth 60/120 FPS experience on real devices.

## Top 5 Lag Sources Ranked by Impact

1.  **Distributed State Collection in ProductCard**: Every `ProductCard` collects the entire `cartItems` map. A single cart update triggers recomposition for ALL product cards in a list.
2.  **Expensive `hiltViewModel()` calls in Lazy items**: `ProductCard` obtains `CartViewModel` via Hilt internally. Doing this for every item in a scrolling list adds significant overhead.
3.  **Broken Laziness in AllCategoryScreen**: Wrapping multiple `LazyVerticalGrid` components inside a single `item` of a `LazyColumn` defeats the scrolling optimization for that entire section.
4.  **Static Data Re-allocation**: Lists like `categories` and `productItems` are hardcoded inside Composables without `remember`, causing unnecessary allocations and potential stability issues during recomposition.
5.  **Main-Thread Heavy Header Animations**: Reading scroll offsets directly in the Composable body to drive layout offsets (`Modifier.offset`) triggers expensive recomposition cycles on every pixel of scroll.

## Proposed Changes

### 1. Optimize State Collection & DI in ProductCard
Move `hiltViewModel()` and `collectAsState()` out of `ProductCard`. Pass only the specific quantity and callback to the card.

#### [MODIFY] [ProductCard.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/ProductCard.kt)
- Remove `hiltViewModel()` call.
- Remove `collectAsState()` call.
- Add `quantity: Int` and action lambdas to parameters.

### 2. Fix Broken Laziness in AllCategoryScreen
Convert the `LazyColumn` item containing `AllCategoryScreen` into individual items or use a `LazyVerticalGrid` for the whole screen if possible. At minimum, move the grids into their own `item` blocks if they must remain nested.

#### [MODIFY] [AllCategoryScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/CategoryScreen/AllCategoryScreen.kt)
- Wrap static lists in `remember`.
- (Optimization) Recommend flattening the layout structure.

### 3. Smooth Header Animations
Switch from `Modifier.offset` to `Modifier.graphicsLayer { translationY = ... }` to offload scroll-driven animations to the GPU and avoid recomposition.

#### [MODIFY] [CategoryScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/CategoryScreen.kt)
#### [MODIFY] [HomeScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/HomeScreen.kt)

### 4. General Cleanup of Static Lists
Apply `remember` to all hardcoded UI data lists.

## Verification Plan
### Automated Tests
- Build the project to ensure no breaking changes in parameter signatures.
- `gradlew assembleDebug`

### Manual Verification
- Deploy to a physical device.
- Use **Profile GPU Rendering** (Settings > Developer Options) to observe the "Jank bars" before and after.
- Verify cart updates no longer cause a noticeable hitch in long lists.
