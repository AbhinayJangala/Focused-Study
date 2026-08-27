# Professional UI Overhaul Plan

Upgrade the entire app's user interface to a high-quality, professional standard using Material 3, custom theming, and refined layouts.

## Proposed Changes

### 1. Global Theming & Branding

#### [MODIFY] [Color.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/theme/Color.kt)
- Define a professional brand palette:
    - Primary: Deep Blue (`0xFF0052CC`)
    - Secondary: Success Green (`0xFF2EBD6B`)
    - Tertiary: Soft Purple (`0xFF9133FF`)
    - Backgrounds: Clean Grays and Whites (`0xFFF8FAFC`)

#### [MODIFY] [Theme.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/theme/Theme.kt)
- Update `LightColorScheme` and `DarkColorScheme` to use the brand colors.
- Ensure consistent surface and background colors.

---

### 2. Authentication & Onboarding

#### [MODIFY] [WelcomeScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/WelcomeScreen.kt)
- Replace hardcoded colors with `MaterialTheme.colorScheme`.
- Implement a modern "Get Started" button with consistent branding.
- Refine feature cards with better typography and iconography.

#### [MODIFY] [LoginScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/login/LoginScreen.kt) & [SignupScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/login/SignupScreen.kt)
- Standardize `OutlinedTextField` styling.
- Improve button states (Loading, Success).
- Refine the overall layout for better focus on input fields.

---

### 3. Core Experience

#### [MODIFY] [HomeScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/home/HomeScreen.kt)
- Enhance the "Today's Focus" card with a subtle gradient and better padding.
- Standardize "Quick Stats" and "Leaderboard" items using consistent Material 3 surface components.

#### [MODIFY] [FocusScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/focus/FocusScreen.kt)
- Refine `TimeButton` states (Selected vs Unselected) using theme-aware colors.
- Improve the `AppBlockItem` with app icons and a cleaner selection toggle.

#### [MODIFY] [TimerScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/focus/TimerScreen.kt)
- Implement a premium-feeling timer UI.
- Use a thicker, more modern `CircularProgressIndicator`.
- Add subtle background elements to make the countdown more engaging.

## Verification Plan

### Manual Verification
- Deploy to an emulator/device.
- Verify color consistency across all screens.
- Check responsive behavior (padding and spacing) on different screen sizes.
- Test button interactions and navigation transitions.
