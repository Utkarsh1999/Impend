---
description: Collaborative Product Lifecycle Workflow (PM -> Architect -> Engineer -> QA)
---

# Collaborative Product Lifecycle Workflow

This workflow ensures top-notch quality through a strictly modular and documented lifecycle. Every feature must progress through these sequential stages.

## 1. Product Management (PM) Phase
**Objective**: Define SMART Product Requirements.
- Create a standalone Feature PRD (e.g., `PRD-XXX-FeatureName.md`) in `brain/prds/`.
- **SMART Goals**: Ensure the requirement is Achievable, Realistic, Measurable, and Doable.
- **Constraints**: No implementation details; focus on "What" and "Why".
- **Handoff**: PM calls for Architect Review.

## 2. Architecture Phase
**Objective**: Design the "How".
- Update `architecture_review.md` or create a feature-specific architecture doc.
- Define API contracts, database schema changes, and system diagrams (Mermaid).
- **Guardrail**: No code writing. Architecture must be reviewed against the PRD.
- **Handoff**: Architect signals Engineer to begin implementation.

## 3. Engineering Phase
**Objective**: Build the Tech.
- Implement the feature following the approved architecture.
- Write code that is clean, modular (KMP standard), and documented.
- **Unit Testing**: Engineer MUST write unit tests as part of the implementation.
- **Handoff**: Engineer provides a `walkthrough.md` or technical summary for QA.

## 4. QA & Verification Phase
**Objective**: Quality Assurance.
- QA reviews the PRD, Architecture, and Code.
- Execute automated tests and perform manual verification.
- Document results in `qa_test_report.md`.
- **Guardrail**: Only QA can mark a feature as "Verified" in `task.md`.

## Organization Guardrails
- **Zero Assumption Policy**: Every decision must be referenced in a document.
- **Context Integrity**: Every artifact MUST be updated before moving to the next stage.
- **Fail Fast**: If a requirement is found to be unrealistic during Architecture or Engineering, it must be sent back to PM for revision.
- **Minimalistic First**: Prioritize core utility over complex features to ensure speed-to-market.
