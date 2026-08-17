# Performance Audit Report - Blinkit Clone

## A. Top 5 Lag Sources Ranked by Impact

1.  **Distributed State Collection & DI in ProductCard**: Every `ProductCard` collects the entire `cartItems` map and calls `hiltViewModel()`.
2.  **Broken Laziness in AllCategoryScreen**: Wrapping multiple grids inside a single `item` of a `LazyColumn`.
3.  **Direct Scroll Offset Reads**: Triggering recomposition on every pixel of scroll for header animations.
4.  **Static List Re-allocation**: Recreating UI data lists on every recomposition.
5.  **Nested LazyVerticalGrid with Fixed Height**: Defeats laziness and adds composition overhead.

## B. Exact Files and Line Ranges

1.  **ProductCard.kt [Lines 31-40]**: `hiltViewModel()` and `cartItems.collectAsState()` inside the Composable.
2.  **AllCategoryScreen.kt [Lines 30-140]**: Recreating `categoryList` and `simpleProductItems` without `remember`.
3.  **CategoryScreen.kt [Lines 69-80]**: Reading `firstVisibleItemScrollOffset` directly for `animateDpAsState` and `offset`.
4.  **AllCategoryScreen.kt [Lines 159-220]**: Multiple `LazyVerticalGrid` instances inside a `Column` (which is inside a `LazyColumn` item).
5.  **HomeScreen.kt [Lines 57-65]**: `categories` list recreation and similar scroll offset reading.

## C. Why Each Causes Jank

1.  **State Distribution**: When the cart changes, `cartItems` (a Map) is emitted as a new object. Every `ProductCard` observing this map will recompose to check if its `quantity` changed. With 50 products, that's 50+ recompositions for a single button click. `hiltViewModel()` adds lookup overhead during list layout.
2.  **Composition Overload**: `LazyColumn` items are supposed to be small. By wrapping the entire `AllCategoryScreen` (multiple grids) in one `item`, Compose is forced to compose the entire screen content at once when the first pixel enters the view.
3.  **Recomposition Cycles**: Accessing `.value` of a `derivedStateOf` that tracks scroll offset inside a Composable body (like for `animateDpAsState`) causes the Composable to recompose on every scroll event (every few milliseconds), even if the animation is just moving a view.
4.  **GC Pressure**: Re-allocating lists of data objects on every frame (during animation or scroll) increases memory churn and triggers Garbage Collection hitches.
5.  **Layout Cost**: `LazyVerticalGrid` with `userScrollEnabled = false` and a fixed height inside a `LazyColumn` is effectively just a heavy `Column` with extra overhead.

## D. Minimal Patch for Each

### 1. Optimize ProductCard (ProductCard.kt)
```diff
-fun ProductCard(
-    product: ProductItem,
-    modifier: Modifier = Modifier,
-    navController: NavHostController,
-    cartViewModel: CartViewModel = hiltViewModel()
-) {
-    val quantity by cartViewModel.cartItems.collectAsState()
-    val itemQuantity = quantity[product] ?: 0
+fun ProductCard(
+    product: ProductItem,
+    itemQuantity: Int,
+    onAdd: () -> Unit,
+    onRemove: () -> Unit,
+    modifier: Modifier = Modifier,
+    navController: NavHostController
+) {
```

### 2. Remember Lists (AllCategoryScreen.kt)
```diff
-    val categoryList = listOf<BestSellerData>(...)
+    val categoryList = remember { listOf<BestSellerData>(...) }
```

### 3. GraphicsLayer Offset (CategoryScreen.kt)
```diff
-    val topContentOffset by animateDpAsState(...)
     Column(
         modifier = Modifier
-            .offset(y = topContentOffset)
+            .graphicsLayer { translationY = -scrollOffset.value.toFloat() }
```

### 4. Flatten Laziness (HomeScreen.kt / CategoryScreen.kt)
Instead of `item { AllCategoryScreen() }`, move the grids directly into `LazyColumn` items or use a `LazyVerticalGrid` as the root.

## E. Estimated FPS Improvement

- **Scroll Smoothness**: 25-30% improvement (reduction in dropped frames during scroll).
- **Interaction Latency**: 50% improvement (Cart "Add" button response time will feel instant as only 1 card recomposes instead of 50).
- **Startup/Nav Latency**: 15% improvement due to better laziness.
