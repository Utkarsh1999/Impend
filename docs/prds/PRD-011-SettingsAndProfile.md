# PRD-011: User Profile & Settings

## 1. Objective
Provide a centralized hub for account management and application preferences, enhancing user control and personalization.

## 2. Problem Statement
- The app lacks a place for users to manage their profile, change settings (language, notifications), or logout securely.

## 3. SMART Requirements
- **Settings Screen**: Create a new destination `SettingsScreen` accessible via the navigation bar.
- **Feature Set**:
  - **Profile**: Display user information (Anonymous ID/Account).
  - **Preferences**: Language selection (English/Other), Notification toggles.
  - **Security**: "Logout" / "Clear All Data" action.
- **UI Integration**: Add a 'More' or 'Settings' tab to the Bottom Navigation Bar.

## 4. Constraint & Guardrails
- **Privacy-First**: "Clear All Data" must physically delete the local SQL database.
- **Anonymous Session**: Logout should reset the `AnonymousId` if not tied to a permanent account.

## 5. Success Metrics
- Navigation to Settings completion in < 2 taps.
- Successful data wipe verified by empty database check.

## 6. Handoff to Architecture
- Define a new `Screen.Settings` state.
- Design the Data Retention / Wipe workflow.
