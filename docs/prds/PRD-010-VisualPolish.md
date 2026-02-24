# PRD-010: Visual Polish & Chart Stability

## 1. Objective
Refine the visual language of the application and ensure critical data visualizations are reliable and aesthetically premium.

## 2. Problem Statement
- **Chart Visibility**: The Behavioral Growth graph (TrendChart) is currently not rendering or is invisible to the user.
- **Navigation Aesthetics**: The current Bottom Navigation icons feel dated and lack clear visual feedback for the selected state.

## 3. SMART Requirements
- **Chart Fix**: 
  - Debug `TrendChart.kt` rendering logic to ensure paths are drawn with correct coordinates and visibility.
  - Implement a fallback "No Data" state for the chart to prevent empty UI blocks.
- **Modern Navigation**:
  - Replace current emoji-based icons with modern, sleek vector-style icons or refined design tokens.
  - Implement a "selected" highlight effect (e.g., active color tint, subtle scale-up, or indicator dot).
  - Use a more vibrant color palette for the active state to improve scannability.

## 4. Constraint & Guardrails
- **Performance**: Canvas-based charts must not cause UI jank during scrolling.
- **Accessibility**: Active/Inactive states must have sufficient contrast ratio (> 4.5:1).

## 5. Success Metrics
- 100% visibility of progress charts when data exists.
- "Selected" state is immediately obvious to 100% of tested users.

## 6. Handoff to Architecture
- Review `TrendChart.kt` for potential Canvas coordinate issues in scrollable containers.
- Define a standard `BottomNavItem` UI spec with transition animations.
