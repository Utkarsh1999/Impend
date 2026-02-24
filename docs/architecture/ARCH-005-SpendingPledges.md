# ARCH-005: Pledge Engine & Friction Architecture

## 1. Technical Context
This architecture addresses [PRD-005-SpendingPledges](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-005-SpendingPledges.md). The goal is to enforce "Behavioral Friction" by monitoring active spending commitments.

## 2. Data Layer: Pledge Schema
We will add a new table to `ImpendDatabase.sq`:

```sql
CREATE TABLE PledgeEntity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    category TEXT NOT NULL,
    endTime INTEGER NOT NULL, -- Epoch millis
    status TEXT NOT NULL -- 'ACTIVE', 'COMPLETED', 'BROKEN'
);
```

## 3. Business Logic: PledgeService
A new component in the `shared` module:
- `getActivePledge(category: String)`: Returns the current active pledge for a category, if any.
- `markPledgeBroken(id: Long)`: Updates status to 'BROKEN'.
- `checkCommitment(category: String)`: A use-case used by the UI to decide whether to show the friction overlay.

## 4. UI Layer: Friction Overlay

### 4.1 Integration Point
The `AddExpenseUseCase` will NOT be blocked at the domain level (since we shouldn't prevent data entry). Instead, the **Presentation Layer** (`AddExpenseViewModel`) will call `checkCommitment` before confirming the save.

### 4.2 UI Flow
1. User enters amount and category.
2. User taps "Save".
3. `AddExpenseViewModel` checks for active pledges.
4. If pledge exists: Show `CommitmentDialog`.
5. If user confirms: `markPledgeBroken` + `saveExpense`.
6. If user cancels: Dismiss dialog + stay on entry screen.

## 5. Implementation Roadmap
1. [MODIFY] `shared/.../ExpenseEntity.sq`: Add `PledgeEntity`.
2. [NEW] `shared/.../domain/model/Pledge.kt`.
3. [NEW] `shared/.../domain/repository/PledgeRepository.kt`.
4. [MODIFY] `composeApp/.../presentation/AddExpenseViewModel.kt`.
5. [NEW] `composeApp/.../ui/components/CommitmentCheckDialog.kt`.
