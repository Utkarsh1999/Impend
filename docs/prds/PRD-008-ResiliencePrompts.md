# PRD-008: Resilience Prompts (Cognitive Reframing)

## 1. Objective
Introduce active intervention during the spending lifecycle. When the `ImpulseRiskEngine` detects a high-risk scenario, the app should prompt the user with a cognitive reframing exercise before final log entry.

## 2. User Stories
- **High-Risk Intervention**: As a user, I want to be prompted with a "Breath Check" or "Value Alignment" question when I'm about to log an expense that matches my impulse patterns.
- **Micro-Reflection**: As a user, I want a 5-second "Pause Timer" to give my prefrontal cortex time to override the impulse.
- **Insight Loop**: As a user, I want to see how many impulses I successfully "reframed" today.

## 3. SMART Requirements
- **Trigger Logic**: Intervention triggers if `ImpulseRiskScore > 0.7`.
- **Content Library**: At least 5 distinct reframing prompts (e.g., "Will this matter in 10 minutes?", "Does this buy align with my 'Tech Fast' pledge?").
- **Persistence**: Log whether an expense was "proceeded with" or "abandoned" after a prompt.
- **UI Interaction**: Full-screen modal or high-priority overlay during the `AddExpense` flow.

## 4. Privacy Guardrails
- All reflections are local-only.
- No emotional data from the prompts is synced via the Social Relay.

## 5. Success Metrics
- 20% reduction in high-risk expense volume for users who engage with prompts.
- User sentiment (via optional mood sync post-reframing).

## 6. Handoff to Architecture
- Needs a `ResilienceEngine` to manage the content library.
- Integration with `AddExpenseViewModel` to intercept the saving flow.
