# Kisaan Sathi (किसान साथी) - Android Application

Kisaan Sathi is a native Android application built with **Kotlin** and **Jetpack Compose** (Material 3) designed to empower farmers with smart agricultural advisory, precision irrigation intelligence, soil health management, government welfare schemes, and an AI-powered farming assistant.

This application has been rewritten from the original web application into a complete, modern Android application, preserving all core domain features and business logic.

---

## 📱 Download & Install APK

You can download and install the pre-built APK directly onto any Android phone (Android 7.0 / API 24 or newer):

- **📥 Download APK**: [`Kisaan-Sathi.apk`](./Kisaan-Sathi.apk) (18 MB)
- **Install Instructions**:
  1. Download `Kisaan-Sathi.apk` to your phone.
  2. Tap the downloaded file and select **Install** (allow "Install from Unknown Sources" if prompted).
  3. Open **Kisaan Sathi** and select your language.

---

## 🌟 Core Features

- **New in this version**: first-launch intro, MSP price guide (reference rates, not live mandi prices), crop calendar, soil health helper, "My Requests" history, voice input and read-aloud in the AI assistant, text-size control, WhatsApp contact, offline weather copy and a remembered language.

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
   - View member photos and contact Group 11 IDT at **+91 8081392456** or **group11idt@outlook.com**.
   - Generates unique ticket tracking numbers (e.g. `KS-XXXXXX`) with status updates.

7. **📞 Group 11 IDT Support**
   - One-tap direct dialer for Group 11 IDT at **+91 8081392456** and email support at **group11idt@outlook.com**.
   - Consultation requests are emailed to the team through FormSubmit, the same service as the website form (name, email, phone, financial aid, address, issue, optional photo/video up to 4 MB). The recipient must complete FormSubmit's first-time email verification before requests can be delivered.
   - Website: [kissansathiindia.vercel.app](https://kissansathiindia.vercel.app/), also reachable from the app's Home screen.

8. **🌐 Multi-lingual Support & Theming**
   - Supports 8 major Indian languages: English, Hindi (हिंदी), Punjabi (ਪੰਜਾਬੀ), Bengali (বাংলা), Gujarati (ગુજરાતી), Marathi (मराठी), Tamil (தமிழ்), Telugu (తెలుగు).
   - Dark mode preference is remembered between app launches, with Material Design 3 adaptive colors.

---

## 🏗️ Architecture & Tech Stack

- **Platform**: Android (minSdk 24, targetSdk 36, compileSdk 36)
- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture
- **State Management**: Kotlin Coroutines & `MutableStateFlow` / `StateFlow`
- **Gradle**: Kotlin DSL (`build.gradle.kts`) with Version Catalog (`gradle/libs.versions.toml`)
- **Assets**: Single-image adaptive launcher icon using the supplied Kisaan Sathi icon artwork.
