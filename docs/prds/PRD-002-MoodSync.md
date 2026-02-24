# PRD-002: Emotional Context (Mood Sync)

## 1. Objective
Enable users to quantify the emotional state associated with their spending to facilitate behavioral awareness.

## 2. Requirements (SMART)
- **Achievable**: Implement an emoji-based slider (1-5 scale) within the expense entry flow.
- **Realistic**: Store mood scores as integer values linked directly to the Expense entity.
- **Measurable**: Provide a "Mood Summary" on the Dashboard showing the average mood for the last 5 expenditures.
- **Doable**: Update the existing `Expense` model and SQLDelight schema.

## 3. User Experience
- Non-intrusive mood selector appearing immediately after amount entry.
- Visual feedback (color changing emojis) reflecting the mood.

## 4. Constraints
- Mood logging is optional (users can skip).
- Historical mood trends are out of scope for the "Minimal" version.
