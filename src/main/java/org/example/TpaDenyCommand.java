package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaDenyCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpaDenyCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 1) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Usage: /tpdeny <player>")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Player not found.")));
            return true;
        }

        if (!manager.hasRequest(player.getUniqueId()) || !manager.getRequester(player.getUniqueId()).equals(target.getUniqueId())) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("No request from this player.")));
            return true;
        }

        target.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Your teleport request was denied.")));
        player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("You denied " + target.getName() + "'s teleport request.")));

        manager.removeRequest(player.getUniqueId());
        return true;
    }
}