package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataManager {

    private final Map<UUID, PlayerData> dataMap = new HashMap<UUID, PlayerData>();

    public PlayerData getData(Player player) {
        PlayerData data = dataMap.get(player.getUniqueId());
        if (data == null) {
            data = new PlayerData(player);
            dataMap.put(player.getUniqueId(), data);
        }
        return data;
    }

    public PlayerData getData(UUID uuid) {
        return dataMap.get(uuid);
    }

    public void removeData(UUID uuid) {
        dataMap.remove(uuid);
    }
}
