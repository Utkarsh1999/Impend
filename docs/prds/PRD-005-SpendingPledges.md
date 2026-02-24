# PRD-005: Spending Pledges & Behavioral Friction

## 1. Objective
Introduce the "Pledge" system to help users stick to their behavioral financial goals by adding intentional friction at the point of spending.

## 2. Requirements (SMART)
- **Achievable**: Users can create a "Pledge" for a specific category and duration (e.g., 24h, 3 days, 1 week).
- **Realistic**: Pledges are stored locally and monitored during the expense entry flow.
- **Measurable**: The app tracks "Pledge Breaks" vs "Pledge Successes".
- **Doable**: Implement a new `PledgeEntity` in SQLDelight and a "Commitment Check" UI overlay.

## 3. User Experience
- **Pledge Creation**: A dedicated "New Pledge" screen with duration selection and category picking.
- **The Friction Overlay**: If a user tries to log an expense in a pledged category during the active window, a full-screen "Commitment Check" appears.
    - *Message*: "You pledged to avoid [Category] until [Time]. Are you sure you want to proceed?"
    - *Buttons*: "I broke my pledge" (Logs expense + failure) or "I'll stick to it" (Cancels entry).

## 4. Guardrails
- **No Shaming**: If a pledge is broken, the app should focus on "Resetting" rather than "Failing".
- **Short-Term Focus**: Limit pledges to a maximum of 30 days to keep them realistic and high-resolution.

## 5. Success Metrics
- Number of active pledges per user.
- "Pledge Success Rate" (Successes / Total Pledges Created).
