# JalSanchay Tracker 🌧️💧

An Android application developed as part of the **VTU Internship 2026 – Android App Development using Generative AI**.

JalSanchay Tracker helps users measure and visualize rainwater harvesting effectiveness by calculating water savings based on roof area, rainfall, and roof type.

The application promotes **water conservation awareness**, sustainable living, and household-level rainwater management through an interactive and user-friendly Android interface.

---

# 📱 Features

- 🌧️ Rainfall logging and water harvesting calculation
- 🏠 Roof type selection with runoff coefficient logic
- 💧 Water tank visual progress indicator
- 📊 Dashboard showing:
  - Liters Saved Today
  - Total Water Savings
  - Impact Score (Household Water Days)
- 🗂️ Monthly reports using Room Database
- 📚 Water conservation tips section
- 🎮 Interactive and user-friendly UI
- 📱 Optimized for Android devices
- ⚡ Real-time calculation updates
- 🧠 Input validation for incorrect/non-numeric values

---

# 🧮 Formula Used

The application calculates harvested rainwater using:

```text
Water Harvested = Roof Area × Rainfall × 0.0929 × Runoff Coefficient
```

Where:
- Roof Area → in square feet
- Rainfall → in millimeters
- 0.0929 → sq.ft to sq.m conversion factor
- Runoff Coefficient → based on roof type

---

# 🏠 Roof Types Supported

| Roof Type | Runoff Coefficient |
|---|---|
| Concrete Roof | 0.85 |
| Metal Roof | 0.90 |
| Tile Roof | 0.75 |
| Asphalt Roof | 0.80 |

---

# 🛠️ Tech Stack

## Frontend
- Kotlin
- Jetpack Compose / XML Layouts
- Material Design Components

## Backend / Storage
- Room Database
- SharedPreferences

## Development Tools
- Android Studio
- Gradle
- GitHub

---

# 📂 Project Structure

```text
JalSanchayTracker/
│
├── app/
│   ├── src/main/java/
│   │   ├── activities/
│   │   ├── database/
│   │   ├── ui/
│   │   ├── utils/
│   │   └── adapters/
│   │
│   ├── res/
│   │   ├── layout/
│   │   ├── drawable/
│   │   ├── values/
│   │   └── mipmap/
│   │
│   └── AndroidManifest.xml
│
├── screenshots/
├── README.md
└── build.gradle
```

---

# 📸 Screenshots

## Splash Screen

```md
![Splash Screen](screenshots/Home_Screen.png)
```

## Setup / Input Screen

```md
![Setup Screen](screenshots/Setup_screen.png)
```

## Dashboard Screen

```md
![Dashboard Screen](screenshots/dashboard_screen.png)
```

## Reports Screen

```md
![Reports Screen](screenshots/reports_screen.png)
```

## Knowledge Hub / Tips Screen

```md
![Tips Screen](screenshots/tips_screen.png)
```

## Tank Progress Visualization

```md
![Tank Visualization](screenshots/tank_visualization.png)
```

---

# 🗄️ Room Database

The application uses **Room Database** to store:

- Rainfall history
- Water calculation history
- Monthly reports
- Saved water records

## Database Features

- Offline storage
- Fast local data access
- Monthly history tracking
- Report generation

---

# 📊 Dashboard Features

The dashboard provides:

- Current rainfall data
- Water saved calculation
- Total accumulated savings
- Tank fill percentage
- Household water impact score

---

# 📈 Monthly Reports

Users can track:
- Daily water collection
- Monthly total savings
- Historical rainfall entries
- Water conservation trends

---

# 📚 Knowledge Hub

The application includes educational content related to:
- Rainwater harvesting
- Water conservation methods
- Sustainable household practices
- Efficient water usage tips

---

# 🎯 Objectives

- Promote sustainable water management
- Create awareness about rainwater harvesting
- Help households measure water conservation impact
- Encourage eco-friendly practices using technology

---

# ⚙️ Installation

## Clone Repository

```bash
git clone https://github.com/your-username/JalSanchayTracker.git
```

## Open in Android Studio

1. Open Android Studio
2. Select **Open Existing Project**
3. Choose the project folder
4. Sync Gradle files
5. Run the application

---

# ▶️ Running the App

- Connect Android device or emulator
- Click **Run ▶**
- Launch the application

---

# ✅ Requirements

- Android Studio Hedgehog or above
- Android SDK 24+
- Kotlin support enabled
- Gradle installed

---

# 🧪 Testing

The application was tested on:
- Android Emulator (Pixel)
- Real Android Device
- Different screen sizes
- Offline and online scenarios

---

# 🚀 Future Enhancements

- Firebase cloud sync
- AI-based rainfall prediction
- Weather API integration
- Push notifications
- Water usage analytics
- Gamification and rewards

---

# 👩‍💻 Developed By

**Suraksha Ganesh**  
Final Year Computer Science & Cyber Security Student  
VTU Internship 2026

---

# 📄 License

This project is developed for educational and internship purposes under the VTU Internship Programme.

---

# ⭐ Acknowledgements

- VTU Internship Programme
- MindMatrix.io
- Android Developers Documentation
- Kotlin Documentation
- Open-source Android community