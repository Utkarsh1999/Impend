# ARCH-012: Global Context (Localization & Multi-Currency)

## 1. Overview
This architectural plan detailing how Impend will support multi-lingual text and flexible currency formatting across the Compose Multiplatform ecosystem.

## 2. Localization Strategy (String Externalization)

### 2.1 Technology Choice
We will use **Compose Multiplatform Resources** (`compose.components.resources`). This is already configured in the `build.gradle.kts` and provides a native, type-safe API (`Res.string.app_name`) for resolving strings across iOS and Android.

### 2.2 Directory Structure
Strings will be stored in XML files within the `composeApp/src/commonMain/composeResources/values/` directory:
- `values/strings.xml` (Default / English)
- `values-es/strings.xml` (Spanish)

### 2.3 Refactoring Approach
We will systematically replace hardcoded strings in UI components (e.g., `Text("Your Overview")`) with `stringResource(Res.string.title_overview)`.

## 3. Multi-Currency Specification

### 3.1 Preference Storage
We will utilize the existing `multiplatform-settings` library (or custom KMM implementation) to create a `PreferencesRepository`.
**Schema:**
- `key_currency_code`: String (Default: "USD")
- `key_language_code`: String (Default: "system" or "en")

### 3.2 Currency Formatter Utility
Create a `CurrencyFormatter` singleton/class in the `shared` module that:
- Accepts an `amount: Double` and a `currencyCode: String`.
- Returns a formatted string (e.g., "$10.00", "€10,00", "₹10.00").
- We will implement a custom mapping for a core set of currencies (USD, EUR, GBP, JPY, INR) since KMP lacks a unified `java.text.NumberFormat`.

### 3.3 Data Layer Impact
- **V1 Constraints**: The SQLite `expenses` table will continue to store amounts as `REAL`. The display currency applied to historical data will simply match the currently selected preference. Real-time exchange rate storage is deferred to V2.

## 4. UI Layer Updates
- **SettingsScreen**: Add dropdowns/dialogs for selecting Language and Currency.
- **ViewModels**: Inject `PreferencesRepository` as a `StateFlow` so the UI reacts instantly to currency/language changes without a reboot.
