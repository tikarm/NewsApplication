# NewsApplication

An Android app built in Kotlin that fetches and displays news from various sources using the [NewsAPI](https://newsapi.org).

---

## Screens & Features

### 1️⃣ Homepage
- Lists all available news sources via `/v2/top-headlines/sources`
- Sorted by default API order
- Automatically refreshes every 1 minute
- Maintains scroll position on refresh

### 2️⃣ News Source Screen
- Lists articles from a selected news source via `/v2/top-headlines`
- Pagination (5 articles per page, I set it to 5 because most articles are less than 10, so in this case pagination is visible) 
- Search functionality to find article by text

### 3️⃣ News Detail Page
- Displays selected article’s full details
- Uses already fetched data which is saved in local database

---

## 📐 Architecture

Built with **Clean Architecture** principles:


---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **DI**: Hilt
- **Network**: Retrofit, Moshi
- **Local Database**: Room
- **State**: ViewModel, StateFlow
- **Navigation**: Navigation Compose
- **Threading**: Coroutines

