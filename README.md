# 🎮 GameVault

GameVault is a modern Android application designed for collectors and gamers to manage their library of video games and board games. Built with Kotlin and Material 3, it provides a seamless and visually appealing experience for tracking your gaming collection.

## ✨ Features

-   **Dual Library Support**: Track both Video Games (with platform details) and Board Games (with player counts).
-   **Dynamic Views**: Toggle between a classic **List view** and a modern **Grid (Table) view** at the touch of a button.
-   **Real-time Search**: Quickly find any game in your collection by title or genre using the integrated search bar.
-   **Full CRUD Operations**: Easily add, view, edit, and delete items in your library.
-   **Modern UI/UX**:
    -   **Material 3**: Utilizing the latest Google design system for a premium look.
    -   **Edge-to-Edge**: Full immersion with content flowing under system bars.
    -   **Responsive Layouts**: Full support for both Portrait and Landscape orientations.
-   **Persistent Storage**: Your collection is safely stored using SharedPreferences, ensuring data persists across app restarts.

## 🛠 Tech Stack

-   **Language**: [Kotlin](https://kotlinlang.org/)
-   **UI Framework**: Jetpack ViewBinding, ConstraintLayout, RecyclerView
-   **Design System**: Material Design 3 (M3)
-   **Architecture**: Container-based Dependency Injection (DI)
-   **Storage**: Shared Preferences (JSON Serialization)
-   **Gradle**: Kotlin DSL (kts) with AGP 9.0+

## 📸 Screenshots

| Main List (Light) | Grid View | Edit Item |
| :---: | :---: | :---: |
| ![List](https://via.placeholder.com/200x400?text=Main+List) | ![Grid](https://via.placeholder.com/200x400?text=Grid+View) | ![Edit](https://via.placeholder.com/200x400?text=Edit+Form) |

## 🚀 Getting Started

1.  Clone the repository:
    ```bash
    git clone https://github.com/LeonidMotolko/GameVault.git
    ```
2.  Open the project in **Android Studio (Ladybug or newer)**.
3.  Sync Gradle and run the app on an emulator or physical device (API 23+).

## 📋 Requirements Compliance

This project was developed as part of an academic examination and fulfills the following requirements:
-   [x] **Kotlin Based**: 100% Kotlin source code.
-   [x] **Multi-screen**: MainActivity, DetailActivity, and EditItemActivity.
-   [x] **Edge-to-Edge**: Implemented using `WindowInsetsCompat`.
-   [x] **Dual Types**: Support for `VideoGame` and `BoardGame` polymorphic types.
-   [x] **Persistent Storage**: Data survives process death.
-   [x] **Orientation Support**: Optimized layouts for `port` and `land` modes.

## 📄 License

This project is for educational purposes. All rights reserved.
