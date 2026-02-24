# ARCH-004: Coaching Layer Architecture

## 1. Technical Context
This architecture addresses [PRD-004-BehavioralInsights](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-004-BehavioralInsights.md). The goal is to bridge the gap between complex analytics and the user's daily experience.

## 2. Shared Logic: Insight Pipeline

### 2.1 Insight Data Model
```kotlin
data class Insight(
    val title: String,
    val recommendation: String,
    val type: InsightType,
    val importance: Float // 0.0 to 1.0
)

enum class InsightType {
    RISK_WARNING,
    MOOD_CORRELATION,
    SPENDING_PATTERN,
    POSITIVE_STREAK
}
```

### 2.2 InsightGenerator (New Component)
A pure Kotlin class in the `shared` module that:
- Ingests `Double` metrics (avgRisk, moodCorrelation).
- Applies a set of `HeuristicRules` to generate `Insight` objects.
- *Example Rule*: If `avgImpulseRisk > 0.7` → Generate `RISK_WARNING`.
- *Example Rule*: If `moodCorrelation < -0.6` → Generate `MOOD_CORRELATION` (Stress Spending).

## 3. Presentation Layer: Surfacing Coaching

### 3.1 HomeUiState Update
Add `insights: List<Insight>` to the `HomeUiState.Success` data class.

### 3.2 Dashboard UI: InsightCarousel
- **Component**: `InsightCarousel` (New).
- **Placement**: Above the "Recent Activity" list, as a horizontal pager or a scrollable row of cards.
- **Design**: Vivid, high-contrast cards with micro-animations.

## 4. Guardrails
- **Privacy Enforcement**: The `InsightGenerator` has zero context of the user's identity. It ONLY sees raw doubles/integers.
- **Computation Frequency**: Insights are regenerated whenever the list of expenses changes (reactive via `StateFlow`).

## 5. Implementation Roadmap
1. [NEW] `shared/.../analytics/InsightGenerator.kt`
2. [MODIFY] `shared/.../presentation/HomeViewModel.kt`
3. [NEW] `composeApp/.../ui/components/InsightCard.kt`
4. [MODIFY] `composeApp/.../ui/screens/DashboardScreen.kt`
