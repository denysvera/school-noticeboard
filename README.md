# 📱 School Noticeboard – Android (Kotlin)

This project implements a School Noticeboard module as part of the d6 Senior Android Developer Challenge.

The goal of the application is to provide a reliable, offline-ready communication feed where parents can view school notices even under poor connectivity, malformed data, and high-resolution media conditions.

The implementation focuses on:

• Clean architecture

• Offline-first data flow

• Defensive programming

• Scalable state management

• Testability

The application is designed as if it were a production feature inside a real communicator app rather than a demo application.

⸻

## ✨ Features

• 📄 Paginated notice feed

• 🧭 Notice detail screen

• 📡 Remote API integration

• 💾 Offline-first caching (Room database)

• 🔁 Automatic pagination via RemoteMediator

• 🕒 Friendly localized date formatting (legacy compatibility)

• ⚠️ Loading, empty and error UI states

• 🧪 Unit + instrumentation tests

⸻

## 🧱 Tech Stack & Architecture

### Language & UI

• Kotlin

• Jetpack Compose

• Material 3

⸻

### Architecture

The project follows a Clean Architecture + MVVM approach with strict layer separation.
````
UI (Compose)
↓
ViewModel (state + orchestration)
↓
UseCases (business logic)
↓
Repository (data abstraction)
↓
Local DB (Room) + Remote API (Retrofit)
````
The database is treated as the Single Source of Truth.

Key principles:

• Unidirectional data flow

• Separation of concerns

• Testability-first design

• No Android framework dependencies in domain layer

⸻

### Dependency Injection

• Hilt

Hilt is used across the entire app:
	
  •	ViewModels
	
  •	Repository
	
  •	Database
	
  •	Retrofit API
	
  •	DateFormatter abstraction

Manual factories and service locators were intentionally avoided.

⸻

### Data Layer

#### Offline-first architecture

The app never loads UI directly from the network.

Instead:
````
API → RemoteMediator → Room Database → UI
````
The UI always observes the database.
Network requests only update the database.

This guarantees:

• offline support

• process death recovery

• configuration change safety

• consistent state

⸻

### Pagination

• Paging 3

• RemoteMediator

• Room PagingSource

The API provides paged links.
RemoteMediator handles:

  •	initial load
	
  •	append
	
  •	refresh
	
  •	cache invalidation

⸻

### Networking

• Retrofit

• OkHttp

• Moshi

⸻

### Local Storage

• Room Database

Entities:
	
  •	NoticeEntity
	
  •	RemoteKeyEntity

Remote keys track pagination state.

⸻

### Date Handling (Important Design Choice)

The API provides UTC ISO timestamps, but the project includes a legacy date formatter (LegacyDateProvider) that is:

    • not thread-safe

    • requires a specific local time format

To safely integrate it:

A DateFormatter abstraction was introduced.

LegacyDateFormatter:
	
  •	converts UTC → local time
	
  •	serializes access using Mutex
	
  •	runs formatting on a background dispatcher

This prevents crashes and keeps legacy compatibility without polluting domain or UI layers.

⸻

### Image Loading

• Coil (Compose)

Images are:
	
  •	memory cached
	
  •	disk cached
	
  •	resized before display

This prevents scrolling jank with large (12MP) images.

⸻

## 📦 Package Structure

````
com.nativkod.schoolnoticeboard

core/
    database/
    network/
    security/
    util/

data/
    local/
    remote/
    paging/
    mapper/
    repository/

domain/
    model/
    repository/
    usecase/

presentation/
    ui/
    state/
    vm/

di/
    Hilt modules

legacy/
    LegacyDateProvider

app.navigation/
    NavGraph & routes
````
The structure enforces:
	•	Domain independence
	•	UI unaware of data source
	•	Replaceable API or DB

⸻

## 🧪 Testing Strategy

### Unit Tests

Located in:
````
src/test
````
Covered components:

• DateFormatter behavior

• Notice mapping validation

• Use cases

• ViewModel state flows

Focus:
	
  •	business logic
	
  •	state transitions
	
  •	error handling

⸻

### Database Tests

Located in:
````
src/androidTest
````
• Room DAO behavior

• Ordering by publish date

⸻

### UI Tests (Smoke Tests)

• Compose component rendering

• App launch verification

⸻

## 📊 What is Tested

• Mapper validation (null + trimming cases)

• Correct chronological ordering of notices

• ViewModel Loading → Data → Error transitions

• Paging exposure to UI


⸻

## 🚀 How to Run


  1.	Clone repository
	
  2.	Open in Android Studio (Giraffe+ / Koala recommended)
	
  3.	Sync Gradle
	
  4.	Run on emulator or device (API 28+)

No API keys required.

⸻

## ⚙️ Build & Test

Run unit tests:
• Date formatting safety
````
./gradlew testDebugUnitTest
````
Run instrumentation tests:
````
./gradlew connectedDebugAndroidTest
````

⸻

## 📌 Design Considerations

### Why RemoteMediator?

Ensures UI observes a single source of truth (database) instead of network responses.

### Why UseCases?

Keeps ViewModel lightweight and business logic reusable/testable.

### Why DateFormatter abstraction?

Prevents legacy formatting code from leaking into data/domain layers and guarantees thread safety.

### Why Hilt?

Removes manual dependency wiring and improves scalability and testability.

⸻

## 👤 Author

### Denys Vera
Senior Android Engineer / Technical Lead

⸻
