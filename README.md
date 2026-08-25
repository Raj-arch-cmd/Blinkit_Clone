# 🛒 Blinkit Clone — Android App

A Blinkit-inspired Android grocery delivery application built as a learning project to explore modern Android development, Firebase authentication, UI design, navigation, and APK deployment.

> ⚠️ This is an independent learning project and is not affiliated with or endorsed by Blinkit.

---

## 📱 Download & Try the App

### 👉 [Download Android APK — v1.0.0](https://github.com/Raj-arch-cmd/Blinkit_Clone/releases/tag/v1.0.0)

Download the APK from the GitHub Release and install it on an Android device.

> **Note:** Android may display a warning when installing an APK downloaded outside Google Play. This is expected for a manually distributed APK.

---

## ✨ Features

- 🔐 Firebase Phone Number Authentication
- 🏠 Home screen with grocery categories
- 🔎 Product search
- 🛍️ Product browsing
- 📦 Product detail screens
- 🛒 Shopping cart
- ❤️ Favorites
- 📋 Orders
- 💳 Payment section UI
- 👤 User profile
- 🚪 Logout functionality
- 📱 Responsive Jetpack Compose UI
- ⚡ Optimized image loading and UI performance
- 🔄 Smooth navigation between screens

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| **Kotlin** | Primary programming language |
| **Jetpack Compose** | Modern Android UI |
| **Firebase Authentication** | Phone number + OTP authentication |
| **Firebase** | Backend services |
| **Jetpack Navigation** | Screen navigation |
| **Hilt** | Dependency injection |
| **Coil** | Image loading |
| **Coroutines / Flow** | Asynchronous operations |
| **Gradle** | Build system |

---

## 🏗️ Architecture

The application follows a structured Android architecture with separation between UI, authentication, navigation, and data-related components.

### Main flow

```text
User
 │
 ▼
Phone Authentication
 │
 ▼
Home Screen
 │
 ├── Categories
 ├── Search
 ├── Products
 ├── Product Details
 │
 ▼
Cart
 │
 ▼
Orders
 │
 ▼
Profile
 └── Logout
