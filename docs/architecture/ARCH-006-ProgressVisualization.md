# ARCH-006: Visualization Engine Architecture

## 1. Technical Context
This architecture addresses [PRD-006-ProgressVisualization](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-006-ProgressVisualization.md). It focuses on data aggregation and on-device chart rendering.

## 2. Data Transformation Layer: AnalyticsTransformer
A new component `AnalyticsTransformer` in the `shared` module will handle the heavy lifting:
- `getTrendData(timeframe: Timeframe)`: Aggregates expenses by day/week.
- `getMoodSpendCorrelationData()`: Pairs mood scores with spending amounts for scatter/line charts.
- `getPledgeSummary()`: Summarizes `PledgeEntity` stats (Success vs Broken).

## 3. Storage Additions
No new tables required. We will leverage existing `ExpenseEntity` and `PledgeEntity`. We may add complex SQL queries to optimized broad data fetching.

### SQL Extensions (in `ExpenseEntity.sq`):
```sql
getExpensesByTimeframe:
SELECT * FROM ExpenseEntity 
WHERE dateMillis >= ? AND dateMillis <= ? 
ORDER BY dateMillis ASC;

getPledgeSummaryStats:
SELECT status, COUNT(*) as count 
FROM PledgeEntity 
GROUP BY status;
```

## 4. UI Layer: Progress Dashboard

### 4.1 Technologies
- **Compose Canvas**: For custom-drawn, high-performance trend lines.
- **Impend Chart Components**: Modular UI pieces for "Risk Gauge", "Trend Line", and "Pledge Circle".

### 4.2 Screens
- `ProgressScreen`: Host for the visualization widgets.
- `DetailsView`: Drill-down for specific trend spikes.

## 5. Implementation Roadmap
1. [MODIFY] `shared/.../ExpenseEntity.sq`: Add aggregation queries.
2. [NEW] `shared/.../analytics/AnalyticsTransformer.kt`.
3. [NEW] `composeApp/.../presentation/ProgressViewModel.kt`.
4. [NEW] `composeApp/.../ui/screens/ProgressScreen.kt`.
5. [NEW] `composeApp/.../ui/components/TrendChart.kt`.
