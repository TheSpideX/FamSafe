<p align="center">
  <img src="https://github.com/TheSpideX/FamSafe/assets/171694578/fa06c15b-7832-4da8-a315-2dd89db7cc14" width="100" />  
</p>
<p align="center">
  <h1 align="center">SafeTrack</h1>
</p>
<p align="center">
  *A Real-Time Location Sharing App for Groups*
</p>
<p align="center">
  <img src="https://img.shields.io/github/license/TheSpideX/FamSafe" alt="license">
  <img src="https://img.shields.io/github/last-commit/TheSpideX/FamSafe" alt="last-commit">
  <img src="https://img.shields.io/github/languages/top/TheSpideX/FamSafe" alt="repo-top-language">
  <img src="https://img.shields.io/github/languages/count/TheSpideX/FamSafe" alt="repo-language-count">
</p>

<p align="center">
  *Built with Kotlin, Jetpack Compose, Firebase, and Google Maps*
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=flat&logo=Kotlin&logoColor=white" alt="Kotlin">
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4.svg?style=flat&logo=Jetpack%20Compose&logoColor=white" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/Firebase-FFCA28.svg?style=flat&logo=Firebase&logoColor=black" alt="Firebase">
  <img src="https://img.shields.io/badge/Google_Maps-4285F4.svg?style=flat&logo=Google%20Maps&logoColor=white" alt="Google Maps">
</p>

---

## Overview

SafeTrack is a mobile app designed to help friends and family stay connected and safe. It provides real-time location sharing, SOS alerts, customizable notifications, and group management features. The app prioritizes privacy and efficiency, utilizing Firebase Realtime Database and Google Maps for its core functionality.

---

## Features

- **Real-time Location Sharing:**
    - Share your live location with selected groups.
    - View the real-time location of your group members on a map.
    - Choose between manual and automatic location sharing modes:
        - Manual:  SOS (high-frequency updates), Long Ride (hourly updates)
        - Automatic: Location shared only when outside the home zone
    - Request the location of all group members for 1 minute.
- **Group Management:**
    - Create and join groups with unique codes.
    - Add and remove members from groups.
    - Customize group settings (e.g., location sharing preferences).
- **Location History:**
    - View your own and group members' location history for the past 2 days.
    - Up to 30 days of location history can be cached locally on the device.
- **Customizable Alerts:**
    - Set custom sounds for SOS and check-in notifications.
    - Define the radius around your home location for geofencing.
- **Notifications:**
    - Receive alerts when someone triggers an SOS or enters/leaves their home zone.

---

## Repository Structure

The repository is organized as follows:

- `app`: Contains the Android app code (built with Jetpack Compose).
    - `ui`: UI composables for different screens (LoginScreen, HomeScreen, MapScreen, etc.).
    - `viewmodel`: ViewModels for managing app logic and data.
    - `service`: Background services for location updates.
    - `model`: Data classes (`UserData`, `GroupData`, etc.). 

---



# License
```xml
Copyright 2024 Kumar Satyam . All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
---
