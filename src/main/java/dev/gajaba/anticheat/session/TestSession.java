package dev.gajaba.anticheat.session;

import java.util.UUID;

public final class TestSession {
    private final UUID tester;
    private final UUID target;
    private final long start;
    private final long end;

    public TestSession(UUID tester, UUID target, long start, long end) {
        this.tester = tester;
        this.target = target;
        this.start = start;
        this.end = end;
    }

    public UUID getTester() { return tester; }
    public UUID getTarget() { return target; }
    public long getStart() { return start; }
    public long getEnd() { return end; }
}
