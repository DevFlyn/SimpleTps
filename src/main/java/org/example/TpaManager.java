package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class TpaManager {

    public enum RequestType { TPA, TPAHERE }

    private final HashMap<UUID, UUID> requests = new HashMap<>(); // target -> sender
    private final HashMap<UUID, RequestType> types = new HashMap<>(); // target -> type
    private final JavaPlugin plugin;

    public TpaManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void sendRequest(UUID sender, UUID target, RequestType type) {
        requests.put(target, sender);
        types.put(target, type);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (requests.containsKey(target) && requests.get(target).equals(sender)) {
                requests.remove(target);
                types.remove(target);
                Player senderPlayer = Bukkit.getPlayer(sender);
                Player targetPlayer = Bukkit.getPlayer(target);

                if (senderPlayer != null) {
                    senderPlayer.sendMessage(SimpleTps.getInstance().prefix()
                            .append(Component.text("Your teleport request to " +
                                    (targetPlayer != null ? targetPlayer.getName() : "Unknown") + " has expired.")));
                }
            }
        }, 20 * 60L);
    }

    public boolean hasRequest(UUID target) {
        return requests.containsKey(target);
    }

    public UUID getRequester(UUID target) {
        return requests.get(target);
    }

    public RequestType getRequestType(UUID target) {
        return types.get(target);
    }

    public void removeRequest(UUID target) {
        requests.remove(target);
        types.remove(target);
    }

    public void cancelRequest(UUID sender) {
        requests.values().removeIf(uuid -> uuid.equals(sender));
    }
}