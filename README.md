# Kisaan Sathi (किसान साथी) - Android Application

Kisaan Sathi is a native Android application built with **Kotlin** and **Jetpack Compose** (Material 3) designed to empower farmers with smart agricultural advisory, precision irrigation intelligence, soil health management, government welfare schemes, and an AI-powered farming assistant.

This application has been rewritten from the original web application into a complete, modern Android application, preserving all core domain features and business logic.

---

## 📱 Download & Install APK

You can download and install the pre-built APK directly onto any Android phone (Android 7.0 / API 24 or newer):

- **📥 Download APK**: [`kisaan-sathi.apk`](./kisaan-sathi.apk) (18 MB)
- **Install Instructions**:
  1. Download `kisaan-sathi.apk` to your phone.
  2. Tap the downloaded file and select **Install** (allow "Install from Unknown Sources" if prompted).
  3. Open **Kisaan Sathi** and select your language.

---

## 🌟 Core Features

1. **🌾 Crop Consultation & Agronomic Planning**
   - Detailed advisories for major Indian crops: Wheat (गेहूं), Paddy/Rice (धान), Mustard (सरसों), Cotton (कपास), and Pulses (चना).
   - Scientific recommendations for sowing windows, soil fit, row-to-row spacing, seed rate, and critical growth stages.
   - Field-ready checklist to spot symptoms early and plan crop rotation.

2. **💧 Smart Irrigation Planner & Water Calculator**
   - Interactive farm water requirement calculator based on crop type, soil texture (loamy, sandy, clayey), and field acreage.
   - Computes daily water requirements in liters and recommended irrigation intervals.
   - Comparative analysis of Drip Irrigation, Sprinkler Systems, and Traditional Flood Irrigation, highlighting 45–55% subsidies under PMKSY.

3. **🛡️ Soil Health & Integrated Pest Management (IPM)**
   - Complete guide to the 12 vital parameters of the Government Soil Health Card (NPK, Sulfur, Zinc, Iron, pH, Organic Carbon).
   - Identification and symptom catalog for prevalent crop diseases (Yellow Rust, Pink Bollworm, Blast, Aphids, Pod Borer).
   - Proven organic bio-formulation recipes: Neem Seed Kernel Extract (NSKE 5%), Sour Buttermilk (छाछ), and Dashparni Ark.
   - Strict safety guidelines for agricultural chemical application.

4. **🏛️ Government Scheme Guidance**
   - Searchable directory of central & state welfare schemes:
     - **PM-KISAN**: Direct ₹6,000/year income support.
     - **PMFBY**: Pradhan Mantri Fasal Bima Yojana crop insurance.
     - **Kisan Credit Card (KCC)**: 4% effective interest agricultural loan.
     - **PMKSY (Per Drop More Crop)**: Micro-irrigation equipment subsidy.
     - **Soil Health Card Scheme**: Free periodic soil testing.
   - Step-by-step application instructions, eligibility criteria, and required document checklists.

5. **🤖 AI Agri Assistant**
   - Conversational agricultural advisor adhering to strict agronomic guidelines.
   - Answers questions regarding crop stages, fertilization, pest vigilance, and government programs.
   - Quick prompt suggestion chips and voice query simulator.

6. **📋 Expert Consultation Booking & Ticket Management**
   - Request consultation directly with Project Coordinator Dr. Pushpendra Tyagi and the Group 11 IDT team.
   - Generates unique ticket tracking numbers (e.g. `KS-XXXXXX`) with status updates.

7. **🚨 Emergency Kisan Call Center Support**
   - One-tap direct dialer for the Government of India's toll-free Kisan Call Center: **1800-180-1551** (6:00 AM – 10:00 PM).

8. **🌐 Multi-lingual Support & Theming**
   - Supports 8 major Indian languages: English, Hindi (हिंदी), Punjabi (ਪੰਜਾਬੀ), Bengali (বাংলা), Gujarati (ગુજરાતી), Marathi (मराठी), Tamil (தமிழ்), Telugu (తెలుగు).
   - Full dark and light mode theming adhering to Material Design 3.

9. **🗓️ Crop Plans & Reminders**
   - Save a crop and sowing date, then schedule a local field-task reminder. Notifications can be enabled in Android settings.
10. **💰 Farm Expense & Profit Tracker**
    - Record crop expenses and sales, and view net totals for the whole farm and each crop. Records stay on the device.
11. **📈 Live Mandi Prices**
    - Query official AGMARKNET records through data.gov.in by commodity and state. Live results require an API key; the app does not substitute sample prices.
12. **⛅ Weather & Field Alerts**
    - Highlights heat, wind, rain, and humidity thresholds from the latest available weather reading. These are current-condition alerts, not a forecast.
13. **📔 Farm Diary**
    - Save dated crop observations and photos on the device.
14. **✅ Scheme Eligibility Helper**
    - Suggests official scheme portals to check based on farmer-selected answers; it does not determine or guarantee eligibility.
15. **📷 Crop Photo & Symptom Guide**
    - Lets farmers note symptoms and attach a photo to an expert consultation. It does not automatically diagnose crop images.

---

## Live Mandi Price Setup

Create a local `.env` file from `.env.example` and set `DATA_GOV_IN_API_KEY` to an API key issued by [data.gov.in](https://data.gov.in/). Rebuild the Android app after setting the key. Without it, live market prices are reported as unavailable; no sample rates are shown as current prices. Do not commit the local `.env` file. Android build configuration embeds this key in the APK, so use a restricted key or a server-side proxy for public distribution.

## 🏗️ Architecture & Tech Stack

- **Platform**: Android (minSdk 24, targetSdk 36, compileSdk 36)
- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture
- **State Management**: Kotlin Coroutines & `MutableStateFlow` / `StateFlow`
- **Gradle**: Kotlin DSL (`build.gradle.kts`) with Version Catalog (`gradle/libs.versions.toml`)
- **Assets**: Adaptive vector launcher icon with golden wheat & leaf sprout motif.
