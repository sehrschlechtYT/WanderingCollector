package yt.sehrschlecht.wanderingcollector;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.wanderingcollector.commands.WanderingCollectorCommand;
import yt.sehrschlecht.wanderingcollector.listeners.ItemListeners;
import yt.sehrschlecht.wanderingcollector.listeners.PlayerListeners;
import yt.sehrschlecht.wanderingcollector.listeners.WanderingTraderListeners;
import yt.sehrschlecht.wanderingcollector.utils.ItemManager;

public final class WanderingCollector extends JavaPlugin {
    private static WanderingCollector plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        getCommand("wanderingcollector").setExecutor(new WanderingCollectorCommand());
        getCommand("wanderingcollector").setTabCompleter(new WanderingCollectorCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListeners(), this);
        pluginManager.registerEvents(new WanderingTraderListeners(), this);
        pluginManager.registerEvents(new ItemListeners(), this);
    }

    @Override
    public void onDisable() {
        ItemManager.save();
    }

    public static WanderingCollector getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return "§7[§bWanderingCollector§7] ";
    }
}
