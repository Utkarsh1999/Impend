# ARCH-003: Privacy Audit & Enforcement

## 1. Technical Context
This architecture addresses [PRD-003-PrivacyEnforcement](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-003-PrivacyEnforcement.md). The mission is to prove and enforce that no financial data ever leaves the device.

## 2. Zero-Leak Audit Results (2026-02-24)

### 2.1 Dependencies
- **Status**: ✅ CLEAN
- **Audit**: `libs.versions.toml` reviewed. No instances of Firebase, Mixpanel, Segment, or other tracking SDKs.

### 2.2 Network Layer
- **Status**: ⚠️ ENFORCEMENT REQUIRED
- **Audit**: `AuthApi.kt` exists but is currently decoupled from the main app flow. It targets `api.impend.finance`. 
- **Risk**: Potential for future developers to add telemetry.
- **Fix**: Implement a `PrivacyGateway` interceptor.

### 2.3 Data Persistence
- **Status**: ✅ SECURE
- **Audit**: `AndroidSqliteDriver` and `NativeSqliteDriver` are used. These are local-first and store data in the app's private sandbox (`/data/data/...` or iOS Container).

## 3. Enforcement Protocol

### 3.1 The "Air-Gap" Network Client
We will implement a `NetworkGuard` that wraps Ktor. 
- **Rule**: Any request containing keywords like "amount", "category", or "mood" in the payload will be blocked by the interceptor at the code level.
- **Whitelist**: Only the `AuthApi` base URL is allowed.

### 3.2 Anonymization Guard
- Auth requests must use a random `SessionID` generated on-device, never tied to the hardware ID (IMEI/Serial) to prevent fingerprinting.

### 3.3 Analytics Isolation
- All analytics (ImpulseRisk, Behavioral) MUST remain in the `shared` module with zero network dependencies. 

## 4. Verification Proof
The QA phase for this PRD will involve:
1.  **Static Analysis**: Grep for network sinks in all Expense-related UseCases.
2.  **Proxy Check**: Running the app through a proxy (Charles/Fiddler) and verifying zero hits during expense entry.
