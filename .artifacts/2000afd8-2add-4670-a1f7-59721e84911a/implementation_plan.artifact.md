# Keyboard Alignment Root Cause Fix

This plan addresses the persistent white gap between the phone input card and the keyboard by correctly managing window insets and system-level soft input modes.

## Root Cause Analysis
1.  **Missing `adjustResize`**: Without `android:windowSoftInputMode="adjustResize"` in the manifest, the system may default to `adjustPan`, which moves the whole window and often conflicts with Compose's `imePadding()`, resulting in unpredictable white spaces.
2.  **Inset Stacking**: Applying `navigationBarsPadding()` and `imePadding()` on the same container can cause "double padding" on some devices because the IME inset often already includes the area occupied by the navigation bar.
3.  **Inset Consumption**: The root `Box` might not be consuming the insets, leading to children recalculating them incorrectly.

## Proposed Changes

### 1. System Configuration
#### [MODIFY] [AndroidManifest.xml](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/AndroidManifest.xml)
- Explicitly set `android:windowSoftInputMode="adjustResize"` for `MainActivity`. This is a prerequisite for reliable keyboard handling in Compose.

### 2. UI Inset Optimization
#### [MODIFY] [PhoneNumberInputScreen.kt](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/java/com/example/blinkit_clone/presentation/screens/CategoryScreen/PhoneNumberInputScreen.kt)
- **Simplify Root Paddings**: Remove individual `navigationBarsPadding()` and `imePadding()` from the main `Column`.
- **Use `safeDrawing`**: Apply `WindowInsets.safeDrawing` or a combination that automatically handles the transition between the navigation bar and the keyboard.
- **Flush Container**: Ensure the bottom `Surface` (the login card) has **zero bottom padding** in its internal layout when the keyboard is open, allowing it to sit perfectly flush against the IME.

## Verification Plan

### Manual Verification
- **Visual Inspection**: Open the phone input screen and trigger the keyboard. The "Terms & Conditions" text should be immediately above the keyboard with no white gap.
- **Hide Keyboard**: Dismiss the keyboard and ensure the card returns to its position above the navigation bar without jumping.
