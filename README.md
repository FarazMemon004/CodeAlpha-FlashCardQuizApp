# 📚 Flashcard Quiz App | FlaseWise App

A simple and interactive Android application designed to help students learn, revise, and remember information using digital flashcards. Create flashcards with questions and answers, review them at your own pace, and test your knowledge in a convenient mobile app.

## ✨ Features

- **Create Flashcards** — Add custom questions and answers.
- **View Answers** — Reveal answers when you're ready.
- **Navigate Cards** — Move between flashcards using previous and next controls.
- **Edit Flashcards** — Update existing questions and answers.
- **Delete Flashcards** — Remove cards you no longer need.
- **Local Data Storage** — Store flashcards on the device using Room Database.
- **Modern Android UI** — Built with Jetpack Compose.
- **Custom App Branding** — Supports a personalized app icon and splash screen.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Primary programming language |
| Android Studio | Android development environment |
| Jetpack Compose | UI development |
| Material 3 | UI components and styling |
| Room Database | Local database storage |
| Kotlin Coroutines & Flow | Asynchronous operations and reactive data |
| Gradle | Build automation and dependency management |

## 📱 App Overview

The application allows users to manage their own collection of study flashcards. Each flashcard contains a question and its corresponding answer. Users can reveal answers, navigate through cards, add new cards, edit existing cards, and delete unwanted cards.

Flashcards are stored locally using Room Database, so they can remain available after closing and reopening the application.

## 📋 Requirements

- Android Studio
- Android SDK 35 or a compatible configured SDK
- JDK 11 or a compatible Gradle JDK configuration
- Android device or emulator running Android 8.0 (API 26) or later
- Internet connection for the initial Gradle dependency download

## 🚀 Getting Started

### 1. Clone the Repository

Open your terminal and run:

```bash
git clone https://github.com/FarazMemon004/CodeAlpha-FlashCardQuizApp/tree/main
```


### 2. Open the Project

1. Open Android Studio.
2. Select **Open**.
3. Choose the cloned `FlashcardQuizApp` folder.
4. Wait for Gradle synchronization to complete.

### 3. Run the Application

1. Connect an Android device with USB debugging enabled, or start an Android emulator.
2. Select the device in Android Studio.
3. Click **Run ▶**.
4. Start creating and reviewing your flashcards.

## 📂 Project Structure

```text
FlashcardQuizApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── flashlearn/
│   │       │               ├── Flashcard.kt
│   │       │               ├── FlashcardDao.kt
│   │       │               ├── FlashcardDatabase.kt
│   │       │               └── MainActivity.kt
│   │       ├── res/
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

*Note: The structure above represents the expected main files. Your actual folder structure may differ depending on your implementation.*

## 🗄️ Database

The app uses Room Database to manage flashcard data locally.

- **Flashcard entity:** Represents each question-and-answer card.
- **Flashcard DAO:** Defines database operations, including inserting, updating, deleting, and retrieving flashcards.
- **Flashcard database:** Provides the Room database instance used by the application.

## 🎨 Customization

You can personalize the app by changing:

- The application name in `res/values/strings.xml`.
- The launcher icon using Android Studio's **Image Asset Studio**.
- The splash screen logo and background in the app's theme resources.
- Colors, typography, layouts, and components in your Jetpack Compose UI.

## 🔮 Future Improvements

- Add categories and subjects.
- Implement multiple-choice quizzes.
- Track learning progress and accuracy.
- Add spaced repetition for better long-term retention.
- Support flashcard search and filtering.
- Add import and export functionality.
- Introduce optional cloud backup and synchronization.

## 🎯 Learning Objectives

This project provides practical experience with:

- Native Android development using Kotlin.
- Declarative UI development with Jetpack Compose.
- Local data persistence using Room.
- CRUD operations for application data.
- Reactive state management using Kotlin Flow.
- Android project configuration and Gradle dependencies.

## 🤝 Contributing

Contributions and suggestions are welcome.

1. Fork the repository.
2. Create a feature branch.
3. Make your changes.
4. Submit a pull request.

## 📄 License

This project is available for educational and personal use. Add a `LICENSE` file to the repository if you want to publish it under a specific open-source license.

---

**Developed with Kotlin and Jetpack Compose ❤️**

*A simple way to learn, revise, and remember.*
