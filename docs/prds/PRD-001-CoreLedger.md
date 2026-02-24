# PRD-001: Core Ledger (Expense Tracking)

## 1. Objective
Enable users to record and track their financial transactions manually in an offline-first environment.

## 2. Requirements (SMART)
- **Achievable**: Implement a form to capture:
    - Amount (Numeric, required)
    - Category (Dropdown: Food, Transport, Rent, Shopping, Entertainment, Others)
    - Timestamp (Automatic or manual selection)
- **Realistic**: Use local storage (SQLDelight) to ensure data persists across sessions.
- **Measurable**: Users should be able to view a list of recent transactions on the Dashboard.
- **Doable**: Leverage existing KMP architecture and SQLDelight setup.

## 3. User Experience
- Tapping "Log Expense +" opens a clean entry screen.
- Swipe-to-delete functionality for erroneous entries.
- Clear validation messages for zero or negative amounts.

## 4. Constraints
- Manual entry only (No bank scraping in Phase 1).
- Single currency support for the "Minimal" version.
