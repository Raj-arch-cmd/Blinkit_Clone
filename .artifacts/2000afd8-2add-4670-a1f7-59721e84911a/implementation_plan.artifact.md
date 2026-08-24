# Implementation Plan - Professional Launcher Icon

This plan describes how to add a professional Android launcher icon to the Blinkit Clone project using the provided logo and following adaptive icon best practices.

## User Review Required

> [!IMPORTANT]
> The new icon will use the provided Blinkit logo on a solid yellow background (`#F7CB45`). This ensures the app looks professional and consistent with the Blinkit brand across all modern Android devices.

## Proposed Changes

### [Component] Resources

#### [MODIFY] [colors.xml](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/res/values/colors.xml)
- Add `blinkitYellow` color resource with value `#F7CB45`.

#### [MODIFY] [ic_launcher_background.xml](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/res/drawable/ic_launcher_background.xml)
- Replace the existing vector path with a simple solid color using the new `blinkitYellow`.

#### [MODIFY] [ic_launcher_foreground.xml](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/res/drawable/ic_launcher_foreground.xml)
- Replace the existing vector data with a centered and scaled version of the `blinkit_logo` image.
- The logo will be scaled to ensure it fits within the "safe zone" of the adaptive icon (center 66dp).

### [Component] Manifest

#### [MODIFY] [AndroidManifest.xml](file:///Users/rajsingh/AndroidStudioProjects/Blinkit_Clone/app/src/main/AndroidManifest.xml)
- Ensure the `android:icon` and `android:roundIcon` correctly point to the adaptive icon resources. (Already configured, but I will verify).

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to ensure no resource errors were introduced.

### Manual Verification
- Verify the icon appears correctly in the launcher (if I could run it on a device with a screen, but I will rely on the build success and visual logic).
- Check that the `blinkit_logo` is properly centered and not clipped on rounded or squircle icon shapes.
