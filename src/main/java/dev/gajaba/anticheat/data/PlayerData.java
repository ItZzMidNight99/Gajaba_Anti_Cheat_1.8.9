package dev.gajaba.anticheat.data;

import dev.gajaba.anticheat.checks.CheckType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlayerData {
    private final Player player;
    private Location location;
    private Location lastLocation;
    private float lastYaw;
    private float lastPitch;
    private float deltaYaw;
    private float deltaPitch;
    private double deltaY;
    private double lastDeltaY;
    private double deltaXZ;
    private double lastDeltaXZ;
    private boolean onGround;
    private boolean lastOnGround;
    private int airTicks;

    private long lastAttackTime;
    private final List<Long> clickTimestamps = new ArrayList<Long>();
    private final List<AttackRecord> attackHistory = new ArrayList<AttackRecord>();

    private boolean pendingVelocity;
    private Vector expectedVelocity = new Vector();
    private long velocityTime;

    private int ping = 50;
    private final List<Integer> pingHistory = new ArrayList<Integer>();
    private long lastFlyingPacket;
    private long lastBlockPlace;

    private final Map<CheckType, Integer> violations = new EnumMap<CheckType, Integer>(CheckType.class);
    private final Map<CheckType, Integer> runs = new EnumMap<CheckType, Integer>(CheckType.class);
    private final Map<CheckType, Integer> fails = new EnumMap<CheckType, Integer>(CheckType.class);
    private final Map<CheckType, Double> buffer = new EnumMap<CheckType, Double>(CheckType.class);
    private final Map<CheckType, List<String>> evidence = new EnumMap<CheckType, List<String>>(CheckType.class);
    private final Map<Long, Long> keepAliveSent = new HashMap<Long, Long>();
    private double totalVl;
    private boolean autoPunished;
    private Location lastSafeLocation;
    private long frozenUntil;
    private String clientBrand = "unknown";

    public PlayerData(Player player) {
        this.player = player;
        this.location = player.getLocation().clone();
        this.lastLocation = player.getLocation().clone();
        this.onGround = player.isOnGround();
        this.lastOnGround = this.onGround;
        this.lastSafeLocation = this.location.clone();
    }

    public void updateLocation(Location to) {
        this.lastLocation = this.location;
        this.location = to.clone();

        this.lastDeltaY = this.deltaY;
        this.lastDeltaXZ = this.deltaXZ;

        this.deltaY = location.getY() - lastLocation.getY();
        double dx = location.getX() - lastLocation.getX();
        double dz = location.getZ() - lastLocation.getZ();
        this.deltaXZ = Math.sqrt(dx * dx + dz * dz);

        this.lastOnGround = this.onGround;
        this.onGround = player.isOnGround();

        if (onGround) {
            this.airTicks = 0;
            this.lastSafeLocation = this.location.clone();
        } else {
            this.airTicks++;
        }
    }

    public double getTotalAscentSinceGround() {
        if (location == null || lastLocation == null) {
            return 0.0D;
        }
        double ascent = location.getY() - lastLocation.getY();
        return Math.max(0.0D, ascent * Math.max(airTicks, 1));
    }

    public void updateRotation(float yaw, float pitch) {
        this.deltaYaw = Math.abs(yaw - lastYaw);
        this.deltaPitch = Math.abs(pitch - lastPitch);
        this.lastYaw = yaw;
        this.lastPitch = pitch;
    }

    public void addAttack(Entity entity) {
        long now = System.currentTimeMillis();
        this.lastAttackTime = now;
        this.attackHistory.add(new AttackRecord(now, entity));
        while (attackHistory.size() > 10) {
            attackHistory.remove(0);
        }
    }

    public void addClick() {
        long now = System.currentTimeMillis();
        this.clickTimestamps.add(now);
        while (clickTimestamps.size() > 120) {
            clickTimestamps.remove(0);
        }
    }

    public void addViolation(CheckType type, String detail) {
        double newBuffer = buffer.containsKey(type) ? buffer.get(type) + 1.0D : 1.0D;
        buffer.put(type, newBuffer);

        if (newBuffer >= 2.0D) {
            violations.put(type, violations.containsKey(type) ? violations.get(type) + 1 : 1);
            buffer.put(type, 0.0D);
        }

        List<String> evidenceList = evidence.get(type);
        if (evidenceList == null) {
            evidenceList = new ArrayList<String>();
            evidence.put(type, evidenceList);
        }
        evidenceList.add(detail);
        if (evidenceList.size() > 8) {
            evidenceList.remove(0);
        }
    }

    public void addVl(double amount) {
        totalVl += amount;
    }

    public void decayVl(double amount) {
        totalVl = Math.max(0.0D, totalVl - amount);
    }

    public void recordRun(CheckType type) {
        runs.put(type, runs.containsKey(type) ? runs.get(type) + 1 : 1);
    }

    public void recordFail(CheckType type) {
        fails.put(type, fails.containsKey(type) ? fails.get(type) + 1 : 1);
    }

    public void reduceBuffer(CheckType type, double value) {
        double current = buffer.containsKey(type) ? buffer.get(type) : 0.0D;
        buffer.put(type, Math.max(0.0D, current - value));
    }

    public Player getPlayer() { return player; }
    public Location getLocation() { return location; }
    public Location getLastLocation() { return lastLocation; }
    public float getDeltaYaw() { return deltaYaw; }
    public float getDeltaPitch() { return deltaPitch; }
    public double getDeltaY() { return deltaY; }
    public double getLastDeltaY() { return lastDeltaY; }
    public double getDeltaXZ() { return deltaXZ; }
    public double getLastDeltaXZ() { return lastDeltaXZ; }
    public boolean isOnGround() { return onGround; }
    public boolean isLastOnGround() { return lastOnGround; }
    public int getAirTicks() { return airTicks; }
    public long getLastAttackTime() { return lastAttackTime; }
    public List<Long> getClickTimestamps() { return clickTimestamps; }
    public List<AttackRecord> getAttackHistory() { return attackHistory; }

    public boolean hasPendingVelocity() { return pendingVelocity; }
    public void setPendingVelocity(boolean pendingVelocity) { this.pendingVelocity = pendingVelocity; }
    public Vector getExpectedVelocity() { return expectedVelocity; }
    public void setExpectedVelocity(Vector expectedVelocity) { this.expectedVelocity = expectedVelocity.clone(); }
    public long getVelocityTime() { return velocityTime; }
    public void setVelocityTime(long velocityTime) { this.velocityTime = velocityTime; }

    public int getPing() { return ping; }
    public void setPing(int ping) {
        this.ping = ping;
        pingHistory.add(ping);
        while (pingHistory.size() > 20) {
            pingHistory.remove(0);
        }
    }
    public List<Integer> getPingHistory() { return pingHistory; }

    public long getLastFlyingPacket() { return lastFlyingPacket; }
    public void setLastFlyingPacket(long lastFlyingPacket) { this.lastFlyingPacket = lastFlyingPacket; }

    public long getLastBlockPlace() { return lastBlockPlace; }
    public void setLastBlockPlace(long lastBlockPlace) { this.lastBlockPlace = lastBlockPlace; }

    public Map<CheckType, Integer> getViolations() { return violations; }
    public Map<CheckType, Integer> getRuns() { return runs; }
    public Map<CheckType, Integer> getFails() { return fails; }
    public Map<CheckType, List<String>> getEvidence() { return evidence; }
    public double getTotalVl() { return totalVl; }
    public boolean isAutoPunished() { return autoPunished; }
    public void setAutoPunished(boolean autoPunished) { this.autoPunished = autoPunished; }
    public Location getLastSafeLocation() { return lastSafeLocation; }
    public long getFrozenUntil() { return frozenUntil; }
    public void setFrozenUntil(long frozenUntil) { this.frozenUntil = frozenUntil; }
    public boolean isFrozen() { return System.currentTimeMillis() < frozenUntil; }
    public String getClientBrand() { return clientBrand; }
    public void setClientBrand(String clientBrand) { this.clientBrand = clientBrand == null ? "unknown" : clientBrand; }

    public void putKeepAliveSent(long id, long sentAt) { keepAliveSent.put(id, sentAt); }
    public Long getKeepAliveSentTime(long id) { return keepAliveSent.get(id); }
    public void removeKeepAliveSentTime(long id) { keepAliveSent.remove(id); }

    public static final class AttackRecord {
        private final long time;
        private final Entity entity;

        public AttackRecord(long time, Entity entity) {
            this.time = time;
            this.entity = entity;
        }

        public long getTime() { return time; }
        public Entity getEntity() { return entity; }
    }
}
