# ARCH-001: Core Ledger Architecture

## 1. Technical Context
This architecture addresses [PRD-001-CoreLedger](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-001-CoreLedger.md). We leverage the existing KMP Clean Architecture.

## 2. Data Schema (SQLDelight)
The `Expense` table in `ImpendDatabase.sq` is already established, but we must ensure strict nullability for the optional `moodScore`.

```sql
CREATE TABLE ExpenseEntity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    amount REAL NOT NULL,
    category TEXT NOT NULL,
    dateMillis INTEGER NOT NULL,
    moodScore INTEGER AS Int DEFAULT NULL
);
```

## 3. Component Interaction
- **UI**: `AddExpenseScreen` (Compose) -> `HomeViewModel` (Presentation).
- **Domain**: `AddExpenseUseCase` -> `ExpenseRepository` (Interface).
- **Data**: `ExpenseRepositoryImpl` -> `ExpenseEntityQueries` (SQLDelight).

## 4. UI/UX Blueprint
- **Composition**: Use the `AppCard` glassmorphism component for ledger items.
- **State Management**: `HomeViewModel` observes a `Flow<List<Expense>>` to ensure real-time UI updates on the Dashboard.

## 5. Risk Assessment
- **Concurrency**: SQLite writes must be handled via the SQLDelight background driver. Current Koin implementation ensures a single driver instance.
- **Validation**: Amount must be validated in the ViewModel before reaching the Use Case.
