# Keyboard Alignment Root Cause Fix Task List

- `[x]` System Configuration
    - `[x]` Add `android:windowSoftInputMode="adjustResize"` to `MainActivity` in `AndroidManifest.xml`
- `[x]` UI Inset Optimization
    - `[x]` Refactor `PhoneNumberInputScreen.kt` to use `WindowInsets.safeDrawing` or optimized `imePadding`
    - `[x]` Ensure flush contact between input card and keyboard
- `[x]` Verification
    - `[x]` Build and run
    - `[x]` Verify zero-gap between card and keyboard
