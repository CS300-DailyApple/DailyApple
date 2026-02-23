# DailyApple

A **nutrition and health tracking Android app** built with Java for the CS300 course. DailyApple helps users log daily meals, track calorie and water intake, monitor body statistics over time, and receive personalized food suggestions — all backed by Firebase.

## Features

### For Users
- **Meal Logging** — Add foods and dishes to daily meals with detailed nutritional information.
- **Calorie Tracking** — Monitor daily calorie intake with history and bar chart visualizations.
- **Water Intake Tracking** — Log water consumption throughout the day with history and charts.
- **Body Statistics** — Record and update body measurements (weight, height, etc.) over time.
- **Food Suggestions** — Get personalized food recommendations based on your profile.
- **Nutritional Breakdown** — View detailed nutrition data (macros, compounds) for each food item.
- **Dish Contributions** — Contribute your own dishes and recipes to the community food database.
- **Summary Statistics** — View overall nutritional summaries and progress charts.
- **Notifications** — Scheduled reminders to log meals and stay on track.

### For Admins
- **Food Database Management** — Create, edit, and manage the food catalog with full nutritional details.
- **User Management** — View and manage registered user accounts.
- **Contributed Dish Review** — Oversee community-contributed dishes.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Platform | Android (min SDK 33, target SDK 33, compile SDK 34) |
| Backend / Database | [Firebase](https://firebase.google.com/) (Auth, Firestore, Analytics) |
| Charts | [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart), [EazeGraph](https://github.com/blackfizz/EazeGraph) |
| Navigation | [AndroidX Navigation](https://developer.android.com/guide/navigation) (Fragments) |
| UI | Material Design Components, ConstraintLayout |
| Build System | Gradle (Kotlin DSL) |
| Testing | JUnit 4, Mockito, Robolectric, Espresso |

## Project Structure

```
.
├── docs/                          # Project documentation
├── pa/                            # Course project assignments (pa1–pa4)
└── src/                           # Android application source
    ├── build.gradle.kts           # Root Gradle config
    ├── settings.gradle.kts
    ├── gradle/
    └── app/
        ├── build.gradle.kts       # App-level dependencies & config
        └── src/
            ├── main/
            │   ├── AndroidManifest.xml
            │   ├── java/com/example/cs300_dailyapple/
            │   │   ├── MainActivity.java
            │   │   ├── Fragments/     # UI screens (see below)
            │   │   ├── Models/        # Data models
            │   │   └── Services/      # Backend & utility services
            │   └── res/               # Layouts, drawables, values, etc.
            └── androidTest/           # Instrumentation tests
```

### Key Packages

**`Fragments/`** — All UI screens implemented as Android Fragments:

| Area | Screens |
|---|---|
| Auth | Login, Create Account, Reset Password, Change Password |
| Home | User Home, Admin Home |
| Meals | Meal Logging, Add Dish, Edit Food, Custom Food in Meal |
| Food | Food List, Food Detail, Suggested Food |
| Tracking | Calorie Record, Water Drinking, Water History, Charts |
| Body | Body Information, Update Body Statistics |
| Admin | Food List Management, Food CRUD, User List, User Detail |
| Community | Dish Contribution, Dish Settings |

**`Models/`** — Domain data classes:

| Model | Description |
|---|---|
| `User` | User profile and account info |
| `PersonalInformation` | User personal details |
| `BodyInformation` | Body measurements and stats |
| `Food` | Individual food item |
| `FoodCompound` | Food with compound ingredients |
| `Nutrition` | Nutritional data for a food item |
| `NutritionOverall` | Aggregated nutritional summary |
| `DailyMeal` | A logged meal for a given day |
| `SuggestedFood` | Food recommendation entry |
| `WaterInformation` | Water intake data |
| `WaterHistoryItem` | Historical water log entry |
| `GlobalApplication` | App-wide state and configuration |

**`Services/`** — Backend and system services:

| Service | Description |
|---|---|
| `AuthService` | Firebase Authentication (login, register, password reset) |
| `DataService` | Firestore CRUD operations for all data models |
| `NotificationScheduler` | Schedules meal/water reminders |
| `NotificationReceiver` | Handles triggered notification broadcasts |

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Hedgehog or newer recommended)
- Android SDK 34
- A Firebase project with Auth and Firestore enabled
- A `google-services.json` file placed in `src/app/`

### Installation

```bash
# Clone the repository
git clone https://github.com/CS300-DailyApple/DailyApple.git
cd DailyApple/src

# Open in Android Studio, or build from the command line:
./gradlew assembleDebug
```

### Running

1. Open the `src/` directory in Android Studio
2. Add your `google-services.json` to `src/app/`
3. Sync Gradle
4. Run on an emulator or physical device (API 33+)

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests (requires a connected device or emulator)
./gradlew connectedAndroidTest
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m "Add my feature"`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

## License

This project does not currently specify a license. Please contact the repository maintainers for usage terms.
