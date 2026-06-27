package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpaCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length != 1) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Usage: /tpa <player>")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Player not found.")));
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("You cannot send a request to yourself.")));
            return true;
        }

        manager.sendRequest(player.getUniqueId(), target.getUniqueId(), TpaManager.RequestType.TPA);
        player.sendMessage(SimpleTps.getInstance().prefix().append(Component.text("Teleport request sent.")));

        Component accept = Component.text("ACCEPT")
                .color(NamedTextColor.GREEN)
                .decorate(TextDecoration.BOLD)
                .clickEvent(ClickEvent.runCommand("/tpaccept " + player.getName()));

        Component deny = Component.text("DENY")
                .color(NamedTextColor.RED)
                .decorate(TextDecoration.BOLD)
                .clickEvent(ClickEvent.runCommand("/tpdeny " + player.getName()));

        Component separator = Component.text(" | ").color(NamedTextColor.WHITE);

        Component message = Component.empty()
                .append(SimpleTps.getInstance().prefix().append(Component.text(player.getName() + " wants to teleport to you.\n")))
                .append(SimpleTps.getInstance().prefix().append(Component.text("To teleport, type /tpaccept " + player.getName() + "\n")))
                .append(SimpleTps.getInstance().prefix().append(Component.text("To deny, type /tpadeny " + player.getName() + "\n")))
                .append(SimpleTps.getInstance().prefix().append(Component.text("This request will timeout after 60 seconds.\n")))
                .append(SimpleTps.getInstance().prefix().append(accept).append(separator).append(deny));

        target.sendMessage(message);
        return true;
    }
}