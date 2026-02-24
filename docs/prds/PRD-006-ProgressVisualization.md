# PRD-006: Behavioral Progress Visualization

## 1. Objective
Enable users to visualize their long-term behavioral trends and celebrate progress in financial discipline.

## 2. Requirements (SMART)
- **Achievable**: Implement a "Progress" tab with trend charts using Compose Multiplatform Canvas/Charts.
- **Realistic**: Data is sourced from existing `ExpenseEntity` and `PledgeEntity` tables.
- **Measurable**: Users can see "Average Risk Score" per week and "Pledge Mastery" (success rate).
- **Doable**: Use on-device data processing to generate chart data points.

## 3. User Experience
- **Progress Screen**: A new top-level screen or section.
- **Spend vs Mood Chart**: A double-line chart showing spending volume vs average mood score over time.
- **Risk Trend**: A bar chart showing the daily `ImpulseRiskScore` average.
- **Pledge Mastery Card**: A visual indicator of "Continuous Streak" and "Total Pledges Kept".

## 4. Guardrails
- **Privacy**: All visualization data is generated and rendered strictly on-device.
- **UX**: Use simplified, clean charts to avoid overwhelming the user (Aesthetic First).

## 5. Success Metrics
- Average time spent on the Progress screen.
- Positive correlation between "Mastery Score" and "Spending Reduction".
