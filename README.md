# Resumer – Customizable Text Resume Generator

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-blue)
[![Watch the Video](https://img.shields.io/badge/Watch%20Demo-YouTube-red)](https://youtu.be/rDU3raweREo)

**Resumer** is a modern Android 
application built with **Jetpack Compose**. It allows users to view and customize resume data in real time with a highly interactive UI. The app features a custom-built 3D design system for color selection and dynamic content formatting.

![resumer_ss](https://github.com/user-attachments/assets/fc02672f-8a21-4414-9841-d11f6f27a399)
---

## Key Features

- **Real-Time Preview**  
  Resume updates instantly as styling changes are applied.

- **Interactive Control Panel**
  - Adjust font size (12sp – 32sp)
  - Change text color & background color

- **Location Integration**  
  Automatically fetches and formats the user’s current location via Google Location Services.

---

## Tech Stack

- **Language:** Kotlin  
- **UI Framework:** Jetpack Compose (Material 3)  
- **Architecture:** MVVM (Model–View–ViewModel)  
- **Dependency Injection:** Dagger Hilt  
- **Asynchronous:** Coroutines & StateFlow  

---

## Project Structure

The project follows clean architecture principles:

```text
com.example.resumer
├── data
|   ├── model/
│   │   └── ResumeResponse.kt  // Data class matching the JSON API [cite: 17]
│   ├── remote/
│   │   ├── ResumeApi.kt        // Retrofit Interface definitions
│   │   └── RetrofitClient.kt  // Singleton to create the Retrofit instance
│   └── repository/
│       └── ResumeRepository.kt // Calls API and passes data to ViewModel 
│
├── di/             
│   └── AppModule.kt  
│
├── ui
│   ├── components/  // Reusable UI parts
│   │   ├── ColorPicker.kt     // Custom color selection buttons [cite: 25, 26]
│   │   ├── ControlPanel.kt    // Sliders & settings container [cite: 23]
│   │   └── LocationBadge.kt   // Top-right GPS display [cite: 3]
│   ├── screens/
│   │   └── HomeScreen.kt      // The main screen assembling all components
│   ├── theme/       // Standard Compose Theme folder
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/
│   │   ├── ResumeViewModel.kt
│   │   └── ResumeUiState.kt 
```

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/YourUsername/Resumer-Compose-App.git
```

### Open in Android Studio
```
1. Go to **File > Open**
2. Select the cloned folder
```

### Sync Gradle

Allow Android Studio to download dependencies.

### Run the app
```
1. Connect a physical device or start an emulator
2. Press the **Run** button
```
> **Note:** Location permissions are required for the location feature to work.


