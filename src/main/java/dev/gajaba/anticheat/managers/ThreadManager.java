package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadManager {

    private final ExecutorService pool;

    public ThreadManager(GajabaLegacy plugin) {
        int size = Math.max(2, plugin.getConfig().getInt("performance.worker-threads", 2));
        this.pool = Executors.newFixedThreadPool(size, r -> {
            Thread t = new Thread(r, "gajaba-worker");
            t.setDaemon(true);
            return t;
        });
    }

    public void submit(Runnable task) {
        pool.submit(task);
    }

    public void shutdown() {
        pool.shutdownNow();
    }
}
