# 🎬 MovieZone App

A professional Android application built to explore movies using real-time data from the TMDB API. This project follows modern Android development practices and clean architecture.

## 📱 App Features
- ✅ **Popular Movies List** – Real-time data from TMDB API.
- ✅ **Movie Detail Page** – Detailed view with Poster, Title, Rating, Overview, and Release Year.
- ✅ **Favourite Movies** – Option to save and manage a personalized list of favourite movies.
- ✅ **Search Feature** – Powerful search functionality to find any movie.
- ✅ **Offline Support** – Seamless experience without internet using **Room DB** caching.
- ✅ **Dark/Light Mode** – Full support for system theme toggling.
- ✅ **Modern UI** – Bottom Navigation, Top App Bar, and Splash Screen.
- ✅ **User Experience** – Loading indicators for API calls and Empty States for favourites.

## 🛠️ Technologies Used
- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Networking:** Retrofit & OkHttp
- **Database:** Room Persistence Library (Local Storage)
- **Reactive UI:** LiveData & Observer Pattern
- **Concurrency:** Kotlin Coroutines
- **Navigation:** Jetpack Navigation Component
- **Image Loading:** Glide
- **Design:** Material Design Components & ViewBinding

## 📁 Project Structure
The project is organized into logical packages to ensure scalability and maintainability:

com.am.moviesfix/
├── model/           # Data classes for API and Database
├── network/         # Retrofit interfaces and Client
├── database/        # Room Database, DAO and Entities
├── adapter/         # RecyclerView Adapters for lists
├── ui/              # Fragments for different screens
├── viewmodel/       # Business logic and State management
└── utils/           # Helper classes and Preferences
