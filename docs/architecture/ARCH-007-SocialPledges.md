# ARCH-007: Anonymous Social Pledges Architecture

## 1. Technical Context
This architecture addresses [PRD-007-SocialPledges](file:///Users/utkarsh/.gemini/antigravity/brain/7a7fa7ec-4c82-4d70-9076-c8b316bebf47/prds/PRD-007-SocialPledges.md). It focuses on "Blind Accountability"—synergetic motivation without identity.

## 2. Infrastructure Layer: Success Relay
We will introduce a `SocialRelay` component in the `shared` module:
- **Success Tokens**: Small, encrypted payloads sent when a pledge interval completes successfully.
- **Oblivious Aggregation**: The server (or relay) only stores counts, not individual mappings.
- **Local Cache**: Store the circle stats locally to ensure the UI remains snappy and works offline.

## 3. Storage Additions
- `CircleEntity`: To store joined circles and cached aggregate stats.
- `PledgeEntity`: Add `circleId` (nullable) to link a pledge to a social circle.

### SQL Extensions (in `CircleEntity.sq`):
```sql
CREATE TABLE CircleEntity (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    memberCount INTEGER DEFAULT 0,
    groupSuccessRate REAL DEFAULT 0.0
);

joinCircle:
INSERT OR REPLACE INTO CircleEntity (id, name, description) VALUES (?, ?, ?);
```

## 4. UI Layer: Circles Dashboard
- `CirclesScreen`: Discovery and status view.
- `MomentumWidget`: Real-time "Strength in Numbers" indicator on the Dashboard.

## 5. Security Guardrails
- **Rate Limiting**: Prevent token spamming.
- **Anonymity Audit**: Ensure the Relay cannot reconstruct a user's spending habits from token frequency.
