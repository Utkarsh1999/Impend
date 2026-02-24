# PRD-004: AI Behavioral Insights (Coaching Layer)

## 1. Objective
Transform raw financial and emotional data into actionable behavioral insights using on-device analytics, providing the user with a "Financial Coach" experience.

## 2. Requirements (SMART)
- **Achievable**: Surface specific reasons for Impulse Risk scores (e.g., "Frequency Anomaly").
- **Realistic**: Implement a "Weekly Pattern" card that correlates mood and spending categories.
- **Measurable**: Display at least 2 unique "Insight Nuggets" on the dashboard.
- **Doable**: Leverage existing `ImpulseRiskEngine` and `BehavioralAnalyticsEngine`.

## 3. User Experience
- **Insight Cards**: Premium, swipeable carousel of insights on the dashboard.
- **Natural Language**: Insights should be phrased as helpful coaching tips, not just numbers.
    - *Example*: "You tend to spend 30% more on Coffee when feeling 'Stressed'. Try a 5-minute walk next time!"

## 4. Guardrails
- **Privacy**: All insight generation MUST happen 100% on-device (Zero-Leak).
- **Non-Judgmental**: The tone must be supportive and "Antigravity" (lifting the user up), never critical.

## 5. Success Metrics
- User views the "Pattern Detail" screen.
- Average Impulse Risk score decreases over time (long-term).
