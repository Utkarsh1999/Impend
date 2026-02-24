# ARCH-002: Mood Sync Architecture

## 1. Technical Context
This architecture addresses [PRD-002-MoodSync](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-002-MoodSync.md). The Goal is to integrate emotional state logging into the existing expense flow.

## 2. Infrastructure Baseline
- **Data Model**: `Expense` already contains `moodScore: Int?`.
- **Persistence**: `ExpenseEntity` in SQLDelight already supports `moodScore`.
- **Analytics**: `BehavioralAnalyticsEngine` already uses mood for correlation; this feature will provide high-quality data for it.

## 3. Proposed UI Changes

### 3.1 Mood Selection Component
- **Component**: `MoodSelector` (New).
- **Behavior**: A custom `Slider` or a set of 5 `IconButton`s with emojis.
- **Visuals**:
    - 1 (😢): Red highlight
    - 3 (😐): Yellow highlight
    - 5 (🤩): Green highlight
- **Implementation**: Compose `Slider` with custom `thumb` and `track` colors based on value.

### 3.2 AddExpenseScreen Integration
- Inject the `MoodSelector` between "Category" and the "Save" button.
- Default state: No selection (null).
- UI Feedback: Display the selected emoji label (e.g., "Feeling: 🤩 Great!") below the slider.

### 3.3 Dashboard Summary
- **Logic**: `HomeViewModel` will compute the average mood of the first 5 items in the expense list.
- **UI**: Display a "Mood Pulse" card on the Dashboard with the average emoji and a trend label (e.g., "Trending: Positive").

## 4. Component Interaction
- **AddExpenseScreen**: Updates local `var selectedMood by remember { ... }`.
- **HomeViewModel**: Receives `moodScore` in `onAddExpenseClicked`.
- **StateFlow**: The `HomeUiState.Success` will be updated to include `avgMoodScore: Double`.

## 5. Guardrails
- **Optionality**: The user must be able to hit "Save" without touching the mood slider. Default value in DB remains NULL.
- **Accessibility**: Ensure emoji labels are readable by screen readers.
