
# 📝 ITNotes — A Feature-Rich Note-Taking Android App

ITNotes is a clean, modern, and scalable Android note-taking app built using **Jetpack Compose**, **MVVM architecture**, **Kotlin Coroutines & Flow**, and **Hilt** for Dependency Injection. Inspired by a [Figma design](https://www.figma.com/community/file/1247577276453656954) with minimalist and neubrutalist UI principles, the app offers a delightful and efficient user experience across all screen sizes and themes.

---

## ✨ Features

- 📝 Create, edit, and delete notes
- 🏷️ Add tags to categorize notes
- 🔍 Search notes with a dynamic search bar
- 🎨 Supports Light and Dark themes
- 📱 Responsive UI for various screen sizes
- 📅 Each note displays:
  - Title
  - Description
  - Tags (optional)
  - Timestamps (Created, Last Modified)

---

## 🛠️ Tech Stack

### 💡 Architecture

- **MVVM (Model-View-ViewModel)**
- **Clean Architecture** layered as:
  - `data` – local storage and repositories
  - `domain` – business logic, and use cases
  - `ui` – viewmodels and composables

### 🧰 Tools & Libraries

| Tool/Library |
|--------------|
| **Jetpack Compose** |
| **Room** |
| **Kotlin Coroutines + Flow** |
| **Hilt** |
| **StateFlow** |
| **JUnit & Mockito/Kotlinx.coroutines.test** |

---

## 📐 Project Structure

```
MinimalNotes/
├── data/
│   ├── local/
│   ├── repository/
│   └── converter/
├── domain/
│   ├── usecase/
│   └── repository/
├── ui/
│   ├── components/
│   ├── screen/
│   ├── viewModel/
│   └── theme/
├── di/
└── navigation/
```

---

## 🧪 Testing

✅ Unit tests for:
- UseCases
- ViewModels  
Using `kotlinx.coroutines.test`, `turbine`, and standard `JUnit` for test coverage and reliability.

---

## 🔍 Design Inspiration

The UI is inspired by [Mihir Vaghani's Minimalist Neubrutalism UI Kit](https://www.figma.com/community/file/1247577276453656954). Special attention was given to color schemes, spacing, and typography to maintain visual clarity while adhering to Material Design 3 principles.

---

## 🚀 Getting Started

Clone the repo and build the app:

```bash
git clone https://github.com/your-username/ITNotes.git
cd ITNotes
./gradlew assembleDebug
```
---

