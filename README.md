# Langio

Langio is an Android language learning application built with **Kotlin** and **Jetpack Compose**.
The app focuses on learning Spanish vocabulary through structured levels, interactive exercises, and exams, combined with basic game mechanics such as lives, hints, and rewards.

---

## Project Overview

The application is designed as a single-player educational game where the user progresses through chapters and levels.
Each level introduces new vocabulary and ends with a final exam required to unlock the next level.

User progress is stored locally and restored between app launches.

---

## Main Features

* Vocabulary learning using word lists and flashcards
* Multiple exam types:

  * Multiple-choice questions
  * Translation input
  * Word matching
* Level and chapter progression system
* Interactive map for level selection
* Daily rewards with streak tracking
* User profile with learning statistics
* Hint and lives system
* Local data persistence using JSON files

---

## Architecture

The project follows a simple modular structure separating logic, data models, and UI.

### Controllers

* **GameController**
  Acts as the central application controller.
  Responsible for:

  * Screen navigation
  * Game state management
  * Level and exam logic
  * User progress, lives, hints, and daily rewards
  * Loading and saving user data

* **DataController**
  Handles loading vocabulary data from files and provides word sets for specific levels.

---

### Data Models

* **Level**
  Represents a single level, including chapter information and assigned words.

* **WordInstance**
  Stores vocabulary data such as translations, incorrect answers, and sample sentences.

* **UserData**
  Stores user-related information including progress, statistics, and reward streaks.
  Data is persisted locally in JSON format.

---

## User Interface

* Fully implemented using Jetpack Compose
* Separate screens for:

  * Home and map navigation
  * Level menu
  * Vocabulary learning
  * Exams
  * Daily rewards
  * User profile
* Custom UI components and animations (e.g. flashcard flipping)
* Canvas-based UI for word-matching exams

---

## Data Persistence

* User data is saved locally as JSON
* Automatically loaded on application start
* Default data is created if no previous save exists

---

## Gameplay Flow

1. User selects a chapter and level from the map
2. Learns vocabulary using word lists or flashcards
3. Completes a final exam
4. Unlocks the next level upon passing
5. Collects daily rewards and tracks progress in the profile

---

## Technologies Used

* Kotlin
* Android SDK
* Jetpack Compose
* JSON (local storage)
* MediaPlayer (audio playback)

---

## Application Appearance

<img width="230" height="512" alt="image" src="https://github.com/user-attachments/assets/7578559b-b117-4b60-923f-2f3366cd4a10" />
<img width="230" height="512" alt="image" src="https://github.com/user-attachments/assets/f6e6cb65-5633-45ec-8008-822fd4606843" />
<img width="230" height="512" alt="image" src="https://github.com/user-attachments/assets/f9c5b7c7-49e2-460d-9017-6749bedec3f6" />
<img width="230" height="512" alt="image" src="https://github.com/user-attachments/assets/7c96ee2e-0036-40ce-8e91-6b9b08deaf3b" />


