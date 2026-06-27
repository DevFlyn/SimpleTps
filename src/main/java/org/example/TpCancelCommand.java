package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpCancelCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpCancelCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        manager.cancelRequest(player.getUniqueId());

        player.sendMessage(SimpleTps.getInstance().prefix()
                .append(Component.text("Request Cancelled.")));

        return true;
    }
}