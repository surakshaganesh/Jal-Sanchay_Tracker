# 💧 Jal-Sanchay Tracker

**Android App for Rainwater Harvesting Tracking**  
MindMatrix VTU Internship Program — Project Title #86

---

## 📱 Features

| Screen | Description |
|--------|-------------|
| Welcome | Animated splash with water drop |
| Setup | Roof area, tank capacity, rooftop type selection |
| Dashboard | Animated water tank with wave, stats, quick actions |
| Data Entry | Log rainfall, live liter calculation preview |
| Alerts | 4-level alert system (Normal/Stable/Warning/Overflow) |
| Impact | Convert liters → days, showers, flushes, CO₂ |
| Reports | Tabular log + bar chart of last 7 entries |
| Knowledge Hub | 13 filterable water harvesting tips |
| Gamification | 6 badge achievements with unlock tracking |

---

## 🏗️ Architecture

```
com.jalsanchay.tracker/
├── MainActivity.kt
├── model/
│   └── Models.kt          (AppState, RainfallEntry, Badge, AlertLevel)
├── viewmodel/
│   └── JalSanchayViewModel.kt  (ViewModel with StateFlow)
├── navigation/
│   └── NavGraph.kt        (NavHost with all routes)
└── ui/
    ├── theme/
    │   └── Theme.kt       (Water-inspired color palette)
    ├── components/
    │   └── SharedComponents.kt (JalTopBar, InfoCard, GradientBackground)
    └── screens/
        ├── WelcomeScreen.kt
        ├── SetupScreen.kt
        ├── DashboardScreen.kt  (animated Canvas water tank)
        ├── DataEntryScreen.kt
        ├── AlertsScreen.kt
        ├── ImpactScreen.kt
        ├── ReportsScreen.kt
        ├── KnowledgeHubScreen.kt
        └── GamificationScreen.kt
```

---

## 🚀 Setup in Android Studio 2025.3.1

1. **Open Android Studio** → File → New → Import Project
2. Select the `JalSanchayTracker` folder
3. Wait for Gradle sync to complete
4. Run on emulator (API 24+) or physical device

### Requirements
- Android Studio 2025.3.1 (Meerkat)
- Kotlin 2.0.21
- compileSdk 35, minSdk 24
- Jetpack Compose BOM 2024.12.01

---

## 💧 Water Calculation Formula

```
Liters = Roof Area (sq ft) × Rainfall (mm) × 0.0929 × 0.85
```

- `0.0929` → converts sq ft to sq meters
- `0.85` → runoff coefficient (15% loss to evaporation/absorption)

---

## 🎨 Color Scheme

| Level | % Fill | Color |
|-------|--------|-------|
| Normal | < 50% | Blue |
| Stable | ≥ 50% | Yellow |
| Warning | ≥ 80% | Orange |
| Overflow | 100% | Red |

---

## 🏆 Badge System

| Badge | Unlock Condition |
|-------|-----------------|
| First Drop | Log 1 entry |
| Water Warrior | Save 100L |
| Hydro Hero | Save 500L |
| Rain Master | 10 entries |
| Conservationist | Save 1000L |
| Full Tank | 100% tank fill |

---

## 📦 Dependencies (from libs.versions.toml)

- `androidx.navigation:navigation-compose:2.8.5`
- `androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7`
- `androidx.compose.material:material-icons-extended`
- Standard Compose BOM 2024.12.01
