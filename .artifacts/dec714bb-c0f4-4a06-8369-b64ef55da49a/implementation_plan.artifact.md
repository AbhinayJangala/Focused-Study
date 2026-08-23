# Implementation Plan - Focus Session & App Blocking

Enhance the Focus flow by adding app selection for blocking and implementing the Timer/Stopwatch screen.

## Proposed Changes

### Navigation

#### [MODIFY] [screen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/navigation/screen.kt)
- Add `Timer` route to the `Screen` sealed class.

#### [MODIFY] [AppNavigation.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/navigation/AppNavigation.kt)
- Add a composable for `TimerScreen` that receives the selected time and apps.

---

### Data & State Management

#### [NEW] [FocusViewModel.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/viewmodel/FocusViewModel.kt)
- Manage `selectedMinutes`.
- Manage a list of `AppInfo` (mocked list for selection).
- Handle navigation to the Timer screen.

---

### UI Components

#### [MODIFY] [FocusScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/focus/FocusScreen.kt)
- Add a "Select Apps to Block" section with a scrollable list of apps and checkboxes.
- Add a "Start Session" button at the bottom.
- Visual feedback for the selected time button.

#### [MODIFY] [TimerScreen.kt](file:///C:/Users/abhin/AndroidStudioProjects/FoucsedStudyApp/app/src/main/java/com/example/foucsedstudyapp/ui/screens/focus/TimerScreen.kt)
- Implement a countdown timer/stopwatch based on the selected duration.
- Display a list of "Blocked Apps" during the session.
- Add a "Quit" or "Finish" button.

## Verification Plan

### Automated Tests
- N/A for this phase (manual verification preferred for UI flow).

### Manual Verification
- Navigate from Home -> Focus.
- Select a time and toggle some apps.
- Click "Start Session" and verify navigation to Timer.
- Verify the timer counts down correctly.
