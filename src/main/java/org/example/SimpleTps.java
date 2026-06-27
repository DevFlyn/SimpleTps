package me.yourname.simpletps;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleTps extends JavaPlugin {

    private static SimpleTps instance;
    private TpaManager tpaManager;
    private final MiniMessage mm = MiniMessage.miniMessage();
    private String prefix;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        prefix = getConfig().getString("prefix");

        tpaManager = new TpaManager(this);

        getCommand("tpa").setExecutor(new TpaCommand(tpaManager));
        getCommand("tpahere").setExecutor(new TpaHereCommand(tpaManager));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(tpaManager));
        getCommand("tpadeny").setExecutor(new TpaDenyCommand(tpaManager));
        getCommand("tpacancel").setExecutor(new TpCancelCommand(tpaManager));
    }

    public static SimpleTps getInstance() {
        return instance;
    }

    public Component prefix() {
        return mm.deserialize(prefix);
    }
}