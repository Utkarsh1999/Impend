# PRD-009: UX Refinement & Navigation

## 1. Objective
Stabilize the user experience by resolving critical layout issues and introducing a persistent navigation system. The goal is to ensure accessibility of all features and improve the visual hierarchy of the dashboard.

## 2. Problem Statement
- **Scrolling**: The home screen uses a fixed layout that hides content on smaller screens or when the list grows.
- **Navigation**: Users lack a global navigation mechanism to switch between Dashboard, Add Expense, Progress, and Circles.
- **Visual Distortion**: Fixed font sizes and unconstrained layouts cause buttons (like Circles) to break the UI.
- **Information Density**: The dashboard "wastes real estate" with redundant or poorly prioritized information.

## 3. SMART Requirements
- **Global Navigation**: Implement a Bottom Navigation Bar with icons for: Dashboard, Progress, Add (Primary Action), Circles.
- **Scrollable Dashboard**: Convert the static dashboard Column into a scrollable container (e.g., `LazyColumn` or `VerticalScroll`).
- **Layout Optimization**: 
  - Condense the "Mood Pulse" and "Insight Carousel" to occupy less vertical space.
  - Implement dynamic font scaling or text wrapping for the "Circles" entry point.
- **Frictionless Entry**: Move the "Add Expense" trigger to the center of the Bottom Navigation.

## 4. Constraint & Guardrails
- **Responsive Design**: UI must scale across different device heights (Small, Medium, Large).
- **Navigation Consistency**: Navigation state must persist during screen transitions.

## 5. Success Metrics
- 100% visibility of all dashboard components on min-spec devices.
- Interaction clicks to change screens reduced by 30% via persistent navigation.

## 6. Handoff to Architecture
- Transition `App.kt` from a simple state-based screen switcher to a more robust navigation container.
- Define the `NavHost` equivalent or systematic screen management.
