package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpAcceptCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpAcceptCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 1) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Usage: /tpaccept <player>")));
            return true;
        }

        Player requester = Bukkit.getPlayer(args[0]);
        if (requester == null) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Player not found.")));
            return true;
        }

        if (!manager.hasRequest(player.getUniqueId()) || !manager.getRequester(player.getUniqueId()).equals(requester.getUniqueId())) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("No request from this player.")));
            return true;
        }

        // Determine request type
        TpaManager.RequestType type = manager.getRequestType(player.getUniqueId());
        if (type == TpaManager.RequestType.TPA) {
            requester.teleport(player.getLocation());
        } else if (type == TpaManager.RequestType.TPAHERE) {
            player.teleport(requester.getLocation());
        }

        requester.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Your teleport request was accepted.")));
        player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("You accepted " + requester.getName() + "'s teleport request.")));

        manager.removeRequest(player.getUniqueId());
        return true;
    }
}