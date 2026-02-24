# ARCH-011: Settings & Profile

## 1. Overview
Introduction of a centralized settings module for user control and personalization.

## 2. Navigation State
A new `Screen.Settings` will be added to the global navigation state in `App.kt`.

## 3. Feature Architecture

### 3.1 Profile Module
- **Identity Display**: Show the `AnonymousId` from `AnonymousIdProvider`.
- **Local Persistence**: Settings will be stored using `multiplatform-settings` (if present) or a new local table.

### 3.2 Data Management
- **Security Wipe**: Implementation of a `deleteAllData()` method in the `shared` module that:
  1. Closes the SQL driver.
  2. Deletes the database file from disk.
  3. Resets the `AnonymousId`.

## 4. UI Components
- **SettingRow**: A reusable component for toggles, action buttons, and labels.
- **SettingsScreen**: A scrollable list of configurations organized by category (Account, Preferences, Legal).
