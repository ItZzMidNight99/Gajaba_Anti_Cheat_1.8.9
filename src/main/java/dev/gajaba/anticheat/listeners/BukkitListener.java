package dev.gajaba.anticheat.listeners;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class BukkitListener implements Listener {

    private final GajabaLegacy plugin;

    public BukkitListener(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getPlayerDataManager().getData(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getTestSessionManager().stopSession(event.getPlayer().getUniqueId(), "player quit");
        plugin.getPlayerDataManager().removeData(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) {
            return;
        }
        PlayerData data = plugin.getPlayerDataManager().getData(event.getPlayer());

        if (data.isFrozen()) {
            event.setTo(event.getFrom());
            return;
        }

        data.updateLocation(event.getTo());
        data.updateRotation(event.getTo().getYaw(), event.getTo().getPitch());
        data.setLastFlyingPacket(System.currentTimeMillis());

        if (!plugin.getConfig().getBoolean("test.hacktest-only", false)
                || plugin.getTestSessionManager().isPlayerUnderTest(event.getPlayer().getUniqueId())) {
            plugin.getCheckManager().runGeneralChecks(data);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAnimation(PlayerAnimationEvent event) {
        PlayerData data = plugin.getPlayerDataManager().getData(event.getPlayer());
        data.addClick();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        PlayerData data = plugin.getPlayerDataManager().getData(event.getPlayer());
        data.setLastBlockPlace(System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Entity target = event.getEntity();
        PlayerData data = plugin.getPlayerDataManager().getData(damager);

        if (data.isFrozen() && plugin.getConfig().getBoolean("setback.cancel-combat", true)) {
            event.setCancelled(true);
            return;
        }

        if (plugin.getHoneypotManager().isDecoy(target)) {
            event.setCancelled(true);
            plugin.getHoneypotManager().handleDecoyHit(damager, target);
            return;
        }

        data.addAttack(target);
        data.setPendingVelocity(true);
        data.setExpectedVelocity(target.getVelocity());
        data.setVelocityTime(System.currentTimeMillis());

        if (!plugin.getConfig().getBoolean("test.hacktest-only", false)
                || plugin.getTestSessionManager().isPlayerUnderTest(damager.getUniqueId())) {
            plugin.getCheckManager().runCombatChecks(data, target);
        }
    }
}
