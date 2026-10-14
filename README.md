<div align="center">


  # 📚 Focused Study

  ### Focus Better. Study Smarter. Stay Consistent.

  An Android productivity application designed to help students eliminate digital distractions, build consistent study habits, and track their academic progress.

  <p>
    <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
    <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
    <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
    <img src="https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" alt="Firebase"/>
  </p>

  <p>
    <a href="#-features">Features</a> •
    <a href="#-tech-stack">Tech Stack</a> •
    <a href="#-architecture">Architecture</a> •
    <a href="#-getting-started">Getting Started</a> •
    <a href="#-contributing">Contributing</a>
  </p>

</div>

---

## 📌 Overview

**Focused Study** is an Android application that helps students stay focused by limiting access to distracting applications during study sessions.

Users can customize their focus sessions, select applications to block, monitor their daily study activity, maintain study streaks, review session history, and compare their weekly study time through a leaderboard.

Built with **Kotlin, Jetpack Compose, MVVM, and Firebase**, the application combines modern Android development practices with cloud-backed progress tracking.

### 🎯 Project Goals

- Minimize distractions during study sessions.
- Encourage consistent study habits through streaks and statistics.
- Help users understand their study patterns.
- Introduce friendly competition through a weekly leaderboard.
- Provide a clean, intuitive, and modern Android experience.

---

## ✨ Features

### 🔒 Smart App Blocking

- Select distracting applications from the installed apps list.
- Use Android's Accessibility Service to detect when a selected application is opened.
- Redirect users to the focus timer when they attempt to access a blocked application during an active session.
- Help maintain focus throughout a study session.

### ⏱️ Customizable Focus Timer

- Configure study session durations.
- Start, pause, resume, and end focus sessions.
- Display a visual countdown timer.
- Confirm before ending an active session.
- Distinguish between active and paused states through UI feedback.

### 📊 Study Statistics & Progress

- Track daily study time.
- Monitor completed study sessions.
- Maintain consecutive-day study streaks.
- Review previous focus sessions.
- View study progress through dedicated statistics screens.

### 🏆 Weekly Leaderboard

- Compare weekly study time across users.
- Display the top three performers using a podium layout.
- Highlight the current user's position.
- Encourage consistent study habits through friendly competition.

### 🔍 Application Selection

- Browse installed applications.
- Search for specific applications.
- Select or deselect all available applications.
- View the number of applications selected for blocking.

### 🔐 Authentication

- Email and password registration.
- Email and password login.
- Google Sign-In using Android's Credential Manager integration.
- Password strength guidance during registration.
- Password reset functionality.

### 🌐 Connectivity Handling

- Monitor network connectivity.
- Display an appropriate offline screen when internet access is unavailable.
- Respond to connectivity changes.
- Support Firebase-dependent operations when connectivity is available.

> **Note:** Some cloud-backed operations require an internet connection. The behavior of focus sessions and session synchronization depends on the application's implementation.

---

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Programming Language | Kotlin |
| UI Development | Jetpack Compose |
| Design System | Material 3 |
| Navigation | Navigation Compose |
| Architecture | MVVM |
| Data Access | Repository Pattern |
| Authentication | Firebase Authentication |
| Cloud Database | Cloud Firestore |
| Google Authentication | Credential Manager / Google Identity |
| Local Storage | SharedPreferences |
| Asynchronous Programming | Kotlin Coroutines and Flow |
| Android Services | Accessibility Service |
| Build System | Gradle with Kotlin DSL |
| Code Generation | KSP |

---

## 🏗️ Architecture

Focused Study follows the **Model–View–ViewModel (MVVM)** architectural pattern, separating UI components from application logic and data access.

### Architectural Responsibilities

| Component | Responsibility |
|---|---|
| UI / View | Displays screens and collects user interactions using Jetpack Compose. |
| ViewModel | Manages screen state and coordinates application operations. |
| Repository | Provides a consistent interface for data access. |
| Service | Encapsulates Firebase operations and other platform-specific functionality. |
| Model | Represents application data, including users and focus sessions. |

### Project Structure

The following structure represents the main architectural organization of the application.

```text
Focused-Study/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── ...
│   │       │       ├── data/
│   │       │       │   └── Models
│   │       │       │
│   │       │       ├── navigation/
│   │       │       │   ├── NavHost
│   │       │       │   ├── Screen
│   │       │       │   └── BottomNavBar
│   │       │       │
│   │       │       ├── repository/
│   │       │       │   ├── AuthRepository
│   │       │       │   ├── FocusRepository
│   │       │       │   ├── HomeRepository
│   │       │       │   └── LeaderboardRepository
│   │       │       │
│   │       │       ├── service/
│   │       │       │   ├── AuthService
│   │       │       │   ├── HomeService
│   │       │       │   ├── LeaderboardService
│   │       │       │   └── AppBlockingService
│   │       │       │
│   │       │       ├── ui/
│   │       │       │   ├── screens/
│   │       │       │   └── theme/
│   │       │       │
│   │       │       ├── utils/
│   │       │       │   ├── FocusManager
│   │       │       │   ├── NetworkUtils
│   │       │       │   └── AccessibilityUtils
│   │       │       │
│   │       │       └── viewmodel/
│   │       │
│   │       ├── AndroidManifest.xml
│   │       └── res/
│   │
│   ├── build.gradle.kts
│   └── google-services.json  # Local configuration; not committed
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

> **Note:** The tree illustrates the intended organization of the major components. Adjust directory and class names to match the actual repository if they differ.

---

## 📱 Screenshots

Screenshots will be added here to demonstrate the application's interface and major features.

Recommended screens:

| Screen | Description |
|---|---|
| Home Dashboard | Daily study statistics and progress |
| Focus Timer | Active and paused study sessions |
| App Selection | Searching and selecting applications to block |
| Leaderboard | Weekly rankings and top performers |
| Statistics | Study history and streak information |
| Authentication | Login and registration screens |

<!--
After adding screenshots to the screenshots/ directory,
replace this section with the following format:

<div align="center">

| Home Dashboard | Focus Timer |
|:---:|:---:|
| ![Home](screenshots/home.png) | ![Timer](screenshots/timer.png) |

| App Selection | Leaderboard |
|:---:|:---:|
| ![App Selection](screenshots/app-selection.png) | ![Leaderboard](screenshots/leaderboard.png) |

</div>
-->

---

## 🚀 Getting Started

Follow these steps to configure and run Focused Study locally.

### Prerequisites

Ensure that you have:

- [Android Studio](https://developer.android.com/studio) installed.
- A compatible Android device or emulator running Android 7.0 (API 24) or later.
- A [Firebase project](https://console.firebase.google.com/) with Authentication and Cloud Firestore configured.
- Git installed.
- A compatible JDK configured for the project's Gradle build.

### 1. Clone the Repository

```bash
git clone https://github.com/AbhinayJangala/Focused-Study.git
cd Focused-Study
```

### 2. Configure Firebase

The Firebase configuration file is intentionally excluded from version control.

1. Open the [Firebase Console](https://console.firebase.google.com/).
2. Select your Firebase project.
3. Open **Project Settings → General**.
4. Register an Android app using the package name defined in the project's Android configuration.
5. Download `google-services.json`.
6. Place the file in the `app/` directory.

Your local project should contain:

```text
app/google-services.json
```

**Important:** Do not commit this file to the repository. Ensure it remains excluded by `.gitignore`.

### 3. Enable Firebase Services

#### Firebase Authentication

Open **Firebase Console → Authentication → Sign-in method** and enable the providers used by the application:

- Email/Password
- Google

For Google Sign-In, configure the required SHA fingerprints and OAuth settings for your Android application.

#### Cloud Firestore

1. Open Cloud Firestore in the Firebase Console.
2. Create a database if one does not already exist.
3. Configure Firestore Security Rules for authenticated users and the application's data-access requirements.
4. Verify that the rules protect user data and prevent unauthorized modifications.

> Never use unrestricted production database rules. Authentication alone does not replace database access controls.

### 4. Build the Application

**Windows:**

```powershell
.\gradlew.bat assembleDebug
```

**Linux / macOS:**

```bash
./gradlew assembleDebug
```

Alternatively, open the project in Android Studio and click **Run ▶**.

### 5. Configure Device Permissions

On the first launch:

1. Sign in or create an account.
2. Follow the in-app instructions for enabling the Accessibility Service.
3. Grant the required Android permissions.
4. Select applications to block.
5. Start a focus session and verify the timer and blocking behavior.

---

## 🔑 Permissions

The following permissions may be used by the application, depending on its Android manifest and service configuration.

| Permission | Purpose |
|---|---|
| `INTERNET` | Communicate with Firebase and other network services. |
| `ACCESS_NETWORK_STATE` | Monitor network connectivity. |
| `BIND_ACCESSIBILITY_SERVICE` | Declare the accessibility service in the manifest; the system controls binding to the service. |
| `RECEIVE_BOOT_COMPLETED` | Receive boot-completed broadcasts if reboot-related behavior is implemented. |
| `QUERY_ALL_PACKAGES` | Query installed applications for the application selector, where required. |

> Android permissions should be declared only when required by the implementation. Accessibility access must be explicitly enabled by the user in Android Settings.

---

## 🗄️ Firestore Data Model

The following is an illustrative representation of the user data maintained by the application. Actual field names, types, and collection structures should match the implementation.

```text
users/
└── {uid}/
    ├── name                 : String
    ├── email                : String
    ├── totalStudySeconds    : Long
    ├── sessionsCompleted    : Long
    ├── currentStreak        : Int
    ├── lastStudyDate        : Timestamp
    └── weeklyStudySeconds   : Long
```

### Data Management

- Firebase Authentication provides user identity.
- Cloud Firestore stores user progress and leaderboard-related data.
- Repositories coordinate data access.
- ViewModels expose screen state to the Compose UI.

Configure database rules according to the application's actual ownership, leaderboard, and write-access requirements.

---

## 🧠 Engineering Highlights

Focused Study brings together several important Android development concepts:

- **Modern Android UI:** Declarative interfaces built with Kotlin and Jetpack Compose.
- **MVVM Architecture:** Separation of UI state, application logic, and data access.
- **Firebase Integration:** Authentication and cloud-backed user data.
- **Android Accessibility APIs:** Application-interruption behavior during active focus sessions.
- **Asynchronous Programming:** Coroutines and Flow for asynchronous operations and reactive state.
- **State Management:** Timer state, selected applications, and focus-session status.
- **Cloud Data Operations:** Study statistics, session tracking, and weekly leaderboard functionality.

These implementations demonstrate practical experience building a multi-screen Android application with authentication, platform services, and backend integration.

---

## 🔐 Security Considerations

- Keep `google-services.json` out of version control.
- Never commit service-account credentials, private keys, or other server-side secrets.
- Apply appropriate API restrictions where supported.
- Configure Firebase Authentication and Firestore Security Rules.
- Validate permissions and data-access requirements.
- Review Git history if sensitive credentials were previously committed.

**Important:** Removing a file from the latest commit does not remove it from earlier Git history. Previously exposed secrets should be assessed and rotated when necessary.

---

## 🤝 Contributing

Contributions, suggestions, and bug reports are welcome.

### Contribution Workflow

1. Fork the repository.
2. Create a feature branch.

   ```bash
   git checkout -b feature/your-feature
   ```

3. Make your changes and test them.
4. Commit your changes.

   ```bash
   git commit -m "feat: describe your change"
   ```

5. Push your branch.

   ```bash
   git push origin feature/your-feature
   ```

6. Open a Pull Request describing the changes.

For substantial changes, consider opening an issue first to discuss the proposed implementation.

---

## 📄 License

This project is distributed under the MIT License.

See the [LICENSE](LICENSE) file for the complete license text.

---

## 👨‍💻 Author

<div align="center">

### Abhinay Jangala

Android Developer | Kotlin | Jetpack Compose

Building Android applications focused on usability, clean architecture, and practical problem-solving.

[![GitHub](https://img.shields.io/badge/GitHub-AbhinayJangala-181717?style=for-the-badge&logo=github)](https://github.com/AbhinayJangala)

</div>

---

<div align="center">

**Built with Kotlin and Jetpack Compose.**

If you find Focused Study useful, consider giving the repository a ⭐.

</div>
