package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;

public final class ClientTypeManager implements PluginMessageListener {

    private final GajabaLegacy plugin;

    public ClientTypeManager(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!"MC|Brand".equals(channel)) {
            return;
        }

        String brand = new String(message, StandardCharsets.UTF_8).toLowerCase();
        PlayerData data = plugin.getPlayerDataManager().getData(player);
        data.setClientBrand(brand);
    }

    public String classify(PlayerData data) {
        String b = data.getClientBrand().toLowerCase();
        if (b.contains("badlion")) return "Badlion";
        if (b.contains("lunar")) return "Lunar";
        if (b.contains("silent")) return "Silent Client";
        if (b.contains("forge") || b.contains("fabric")) return "Modded Client";
        if (b.contains("vanilla")) return "Vanilla";
        if (b.contains("feather")) return "Feather";
        return "Unknown/Masked";
    }
}
