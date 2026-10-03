# GajabaLegacy (Spigot 1.8.9 AntiCheat + HackTest)

GajabaLegacy works in two modes:
1. **Background anti-cheat mode** (always on) like Vulcan-style continuous monitoring.
2. **Timed hack-test mode** (`/hacktest test <player>`) for deep staff investigations.

---

## What this plugin can do

### Core detection systems
- Combat checks: Aura (A-F), AutoBlock, AutoClicker (A-E), Criticals, Reach (A-C), Velocity (A-C)
- Movement checks: Fly (B-E), Speed (B-D), NoFall (A-B), NoSlow (A-B), Phase (A-B), Jesus, Spider, Step
- Extra movement modules: **BunnyHop**, **Crouch speed**, **Prediction/simulation mismatch**
- Packet/network checks: BadPackets (A-C), PingSpoof, Blink
- World checks: FastPlace, InvMove
- Honeypot decoy check: **HONEYPOT_A**

### Enforcement + prevention
- Weighted VL (Violation Level) scoring
- Auto-punishment commands from config (`kick`, `ban`, custom commands)
- Setback/freeze system:
  - teleports flagged player to safe location
  - freezes movement for configured duration
  - can cancel combat so cheaters cannot land hits while frozen

### Staff + network visibility
- Staff alerts in real time (`gajaba.staff`)
- BungeeCord broadcast alerts (cross-server)
- Session-end report with:
  - verdict
  - total VL
  - per-check true/false
  - likely client signatures (Raven/Wurst/LiquidBounce/Doomsday-style)
  - detected client type from brand heuristics (Badlion, Vanilla, Silent-like, etc.)

---

## Staff alert preview

```text
[AC] Steve REACH_A » Raw reach 3.72 (VL: 18.40)
[AC] Setback applied to Steve for REACH_A
[AC] AutoPunish executed on Steve (VL: 35.10)
```

---

## Math model (how scoring works)

### 1) Per-check buffering
Each check uses a small buffer to avoid one-tick false positives:
- flag => buffer increases
- reward/pass => buffer decreases
- when buffer crosses check threshold, violation count increments

### 2) VL weights
Every check has a configurable VL weight in `config.yml`:
- Example: `HONEYPOT_A` contributes more VL than lightweight checks
- VL accumulates on flags and decays slowly on normal behavior

### 3) Action thresholds
- **Setback threshold** (default 15 VL): immediate movement/cross-check prevention
- **Autopunish threshold** (default 35 VL): execute configured punishment commands

---

## Commands
- `/hacktest test <player>`
- `/hacktest use <player>`
- `/hacktest stop <player>`

---

## Permissions
- `gajaba.staff` — receive alerts and run hack tests
- `gajaba.bypass` — bypass checks/punishment

---

## Important note
No anti-cheat can guarantee literal 100% certainty against all client spoofing/masking techniques.
GajabaLegacy is designed as a layered, high-pressure anti-cheat system with prevention + evidence.
