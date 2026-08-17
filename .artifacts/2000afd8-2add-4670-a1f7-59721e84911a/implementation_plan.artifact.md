# Logout Functionality Fix Plan

This plan fixes the issue where the "Logout" option on the Profile screen is not working. The root cause is likely an unreliable navigation transition in the root `AppNavigation` and potential backstack conflicts.

## Root Cause Analysis
1.  **Dynamic `startDestination` Issues**: Changing the `startDestination` of a `NavHost` dynamically based on state can lead to inconsistent behavior. The `NavHost` does not automatically transition to the new `startDestination` if it's already active.
2.  **Unreliable `popUpTo`**: The use of `popUpTo(navController.graph.id)` with `inclusive = true` can sometimes invalidate the navigation graph itself, causing the transition to fail or the screen to go blank.
3.  **Navigation Race Conditions**: When `isLoggedIn` changes, both the `NavHost` re-composition and the `LaunchedEffect` trigger simultaneously, which can lead to the `navigate` call being ignored if the graph is in a transitional state.

## Proposed Changes

### 1. Navigation Logic
#### [MODIFY] [AppNavigation.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/AppNavigation.kt)
- **Robust Logout Transition**: Update the `LaunchedEffect` to use a more standard and reliable way to clear the backstack. Instead of popping to the root graph ID, we will pop everything up to the first destination.
- **Fixed `startDestination`**: Ensure the `NavHost` handles the state transition smoothly.
- **Navigation Guard**: Add a check to ensure we only trigger navigation if the current destination is actually part of the authenticated flow.

### 2. ViewModel State
#### [MODIFY] [PhoneAuthViewModel.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/auth/PhoneAuthViewModel.kt)
- No changes required, as the `signOut` method correctly updates the `StateFlow`.

## Verification Plan

### Manual Verification
- **Logout Action**: Click the "Log Out" button on the Profile screen. Verify the app instantly transitions back to the Phone Authentication screen.
- **Back Button Check**: After logging out, press the system back button. Verify the app exits instead of going back into the Profile screen.
- **Fresh Login**: Verify that after logging out, you can log back in successfully.
