package dev.gajaba.anticheat;

import dev.gajaba.anticheat.commands.HackTestCommand;
import dev.gajaba.anticheat.listeners.BukkitListener;
import dev.gajaba.anticheat.managers.AlertManager;
import dev.gajaba.anticheat.managers.CheckManager;
import dev.gajaba.anticheat.managers.ClientProfileManager;
import dev.gajaba.anticheat.managers.HoneypotManager;
import dev.gajaba.anticheat.managers.PlayerDataManager;
import dev.gajaba.anticheat.managers.PunishmentManager;
import dev.gajaba.anticheat.managers.SetbackManager;
import dev.gajaba.anticheat.managers.TestSessionManager;
import dev.gajaba.anticheat.managers.ThreadManager;
import dev.gajaba.anticheat.managers.ClientTypeManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class GajabaLegacy extends JavaPlugin {

    private static GajabaLegacy instance;

    private PlayerDataManager playerDataManager;
    private AlertManager alertManager;
    private CheckManager checkManager;
    private TestSessionManager testSessionManager;
    private PunishmentManager punishmentManager;
    private ThreadManager threadManager;
    private HoneypotManager honeypotManager;
    private ClientProfileManager clientProfileManager;
    private SetbackManager setbackManager;
    private ClientTypeManager clientTypeManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        playerDataManager = new PlayerDataManager();
        alertManager = new AlertManager(this);
        checkManager = new CheckManager(this);
        punishmentManager = new PunishmentManager(this);
        threadManager = new ThreadManager(this);
        clientProfileManager = new ClientProfileManager();
        setbackManager = new SetbackManager(this);
        clientTypeManager = new ClientTypeManager(this);
        testSessionManager = new TestSessionManager(this);
        honeypotManager = new HoneypotManager(this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "MC|Brand", clientTypeManager);

        getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

        HackTestCommand command = new HackTestCommand(this);
        getCommand("hacktest").setExecutor(command);
        getCommand("hacktest").setTabCompleter(command);

        getLogger().info("GajabaLegacy AntiCheat enabled. Checks: " + checkManager.getChecks().size());
    }

    @Override
    public void onDisable() {
        if (testSessionManager != null) {
            testSessionManager.shutdown();
        }
        if (threadManager != null) {
            threadManager.shutdown();
        }
    }

    public static GajabaLegacy getInstance() {
        return instance;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public TestSessionManager getTestSessionManager() {
        return testSessionManager;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public HoneypotManager getHoneypotManager() {
        return honeypotManager;
    }

    public ClientProfileManager getClientProfileManager() {
        return clientProfileManager;
    }

    public SetbackManager getSetbackManager() {
        return setbackManager;
    }

    public ClientTypeManager getClientTypeManager() {
        return clientTypeManager;
    }
}
