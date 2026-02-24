# Implementation Plan - PRD-012: Global Context

This plan outlines the steps to introduce string localization and multi-currency formatting using Compose Resources and a custom currency utility.

## Proposed Changes

### [Component] Shared: Preferences Storage
Adding storage for user localization preferences.

#### [NEW] [PreferencesRepository.kt](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/shared/src/commonMain/kotlin/com/impend/shared/domain/repository/PreferencesRepository.kt)
- Create interface and implementation using `multiplatform-settings`.
- Methods: `getCurrencyCode()`, `setCurrencyCode()`, `getLanguageCode()`, `setLanguageCode()`.
- Add to Koin `AppModule.kt`.

### [Component] Shared: Currency Formatting
Creating a utility to handle currency symbols.

#### [NEW] [CurrencyFormatter.kt](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/shared/src/commonMain/kotlin/com/impend/shared/util/CurrencyFormatter.kt)
- Create a `CurrencyFormatter` object.
- Functions: `format(amount: Double, currencyCode: String): String`.
- Support mappings for: "USD" (`$`), "EUR" (`€`), "GBP" (`£`), "JPY" (`¥`), "INR" (`₹`).

---

### [Component] UI: String Localization Setup
Integrating Compose Resources.

#### [NEW] [composeResources/values/strings.xml](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/composeApp/src/commonMain/composeResources/values/strings.xml)
- Define base English strings:
  - `app_name`: "Impend"
  - `nav_home`: "Home"
  - `nav_trends`: "Trends"
  - ...all other hardcoded UI strings.

#### [NEW] [composeResources/values-es/strings.xml](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/composeApp/src/commonMain/composeResources/values-es/strings.xml)
- Define Spanish translations for the base strings.

---

### [Component] UI: Refactoring Screens
Replacing hardcoded values with dynamic resources.

#### [MODIFY] [App.kt](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/composeApp/src/commonMain/kotlin/com/impend/app/App.kt)
- Update Bottom Navigation labels to use `stringResource(Res.string.nav_home)`.

#### [MODIFY] [DashboardScreen.kt](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/composeApp/src/commonMain/kotlin/com/impend/app/ui/screens/DashboardScreen.kt)
- Update titles, subtitles.
- Use `CurrencyFormatter.format()` for expense amounts and totals instead of prefixed `$`.

#### [MODIFY] [SettingsScreen.kt](file:///Users/utkarsh/Desktop/Projects/Mobile Projects/Impend/composeApp/src/commonMain/kotlin/com/impend/app/ui/screens/SettingsScreen.kt)
- Add dropdowns/dialogs for selecting Currency (USD, EUR, GBP, JPY, INR).
- Connect selections to `PreferencesRepository` via the ViewModel.

---

## Verification Plan

### Automated Tests
- Unit test `CurrencyFormatter` to ensure it applies the correct symbols (e.g., `format(10.5, "EUR")` -> `€10.50`).

### Manual Verification
1. **Locale Overlay**: Change device language to Spanish and verify `App.kt` and `DashboardScreen.kt` text translates automatically.
2. **Currency Toggle**: Navigate to Settings, change currency to GBP. Verify `DashboardScreen` now shows `£` instead of `$`.
