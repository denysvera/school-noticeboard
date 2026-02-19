# Technical Decisions

This document briefly explains the key architectural and implementation decisions made during the assessment.


⸻

### 1. Offline-First Data Flow

The UI never consumes the network directly.
All UI state is derived from the Room database.

#### Flow:
````
API → RemoteMediator → Room → PagingSource → ViewModel → UI
````
#### Reasoning

In production systems, network responses are transient and unreliable.
By persisting responses into a local database and observing it, the application gains:

• offline support

• process death recovery

• configuration change stability

• consistent UI state

Paging3 RemoteMediator was selected instead of manual pagination because it guarantees synchronization between local cache and remote source.

⸻

### 2. Clean Architecture Separation

The project is structured into presentation, domain, and data layers.

The domain layer has no Android dependencies.

#### Why this matters

It allows:

• independent unit testing

• repository replacement

• easier scaling of features

• multiple data sources in the future

ViewModels do not contain business logic.
Business logic resides in UseCases.


### 3. Repository Pattern

A NoticeRepository interface was introduced in the domain layer, with NoticeRepositoryImpl in the data layer.

The repository is responsible for:

• coordinating network and database

• exposing observable data streams

• hiding implementation details from the UI

This prevents ViewModels from depending on Retrofit, Room, or DTOs.

⸻

### 4. Paging Strategy

The API provides next/previous links rather than page numbers.
Because of this, traditional paging counters were avoided.

Instead, RemoteKeyEntity stores pagination links per item.

#### Reasoning

This mirrors real-world APIs (e.g., Firebase, GraphQL cursors, REST cursor paging) and prevents duplicate loads or skipped pages.

⸻

### 5. Legacy Date Handling

The provided LegacyDateProvider uses a static SimpleDateFormat, which is not thread-safe.

To safely integrate it:

A DateFormatter abstraction was created.

LegacyDateFormatter:

• converts UTC → local time

• serializes access via Mutex

• runs formatting on a background dispatcher

This isolates unsafe legacy behavior while keeping the domain and UI layers clean.

⸻

### 6. Dependency Injection (Hilt)

Hilt was used instead of manual factories.

Reasons:

• lifecycle-aware ViewModel injection

• simpler testing

• scalable module configuration

• avoids service locator anti-pattern


⸻

### 7. Testing Approach

Testing focused on behavior rather than UI visuals.

Covered:

• mappers

• use cases

• ViewModel state transitions

• database ordering

UI tests were kept minimal (smoke tests) because business logic correctness is more critical than pixel verification in this assessment.

⸻

### 8. Error Handling

Errors are handled at the state level rather than thrown to the UI.

The UI observes:

````
Loading
Data
Error
Empty
````
This ensures predictable rendering and prevents crashes caused by null or network failures.