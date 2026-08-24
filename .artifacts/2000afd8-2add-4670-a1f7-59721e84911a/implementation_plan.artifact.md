# Phase 3.2 Performance Optimization Plan

This plan implements the final minimal fixes to eliminate the remaining 800ms startup stall and the synchronous decoding of large JPEGs on the main thread.

## Proposed Changes

### 1. Carousel Simplification
Remove high-cost rendering effects from the background carousel.
- **[MODIFY] [ProductAutoScrolling.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/ProductAutoScrolling.kt)**:
    - Set `elevation = 0.dp` for `ProductCardForAutoScroll`. Shadows on 15-27 items are extremely expensive for initial layout.
    - Remove the diagnostic log lines once verified.

### 2. Strategic Delay (Animation-First)
Increase the loading deferral to ensure navigation animations finish before the CPU is hammered by image decodes.
- **[MODIFY] [HomeScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/HomeScreen.kt)**: Change `withFrameNanos` to `delay(600)`.
- **[MODIFY] [PhoneNumberInputScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/PhoneNumberInputScreen.kt)**: Change `withFrameNanos` to `delay(600)`.

### 3. Kill Synchronous Decodes
Replace `Image(painterResource)` with `AsyncImage` for all large assets to move their decoding to background threads.
- **[MODIFY] [PrintScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/PrintScreen.kt)**:
    - Use `AsyncImage` for `fastdelivery.jpg`, `doorstep.jpg`, `form.jpg`, `passport.jpg`, and `rentform.jpg`.
- **[MODIFY] [ProductScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/ProductScreen.kt)**:
    - Ensure the main product image uses `AsyncImage`.

## Verification Plan

### Manual Verification
- **Flow**: Login -> Skip -> Home.
- **Metrics**:
    - `Davey!` durations: Targeting <300ms.
    - `SkJpegCodec`: Should only appear in background threads (not followed by `Davey!` logs).
- **Visuals**: Navigation should be perfectly fluid. Images will fade in 600ms later.
