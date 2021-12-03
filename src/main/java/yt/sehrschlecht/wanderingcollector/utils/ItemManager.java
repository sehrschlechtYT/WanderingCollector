package yt.sehrschlecht.wanderingcollector.utils;

import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.wanderingcollector.WanderingCollector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ItemManager {
    private static FileConfiguration itemsConfig;

    static {
        File file = new File("plugins/" + WanderingCollector.getPlugin().getName() + "/items.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                WanderingCollector.getPlugin().getLogger().log(Level.SEVERE, "Error creating items.yml file!");
                Bukkit.getPluginManager().disablePlugin(WanderingCollector.getPlugin());
            }
        }
        itemsConfig = YamlConfiguration.loadConfiguration(file);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(WanderingCollector.getPlugin(), ItemManager::save, 0L, 400L);
    }

    public static List<ItemStack> getItems() {
        List<String> stringItems = itemsConfig.getStringList("items");
        List<ItemStack> items = new ArrayList<>();
        for (String string : stringItems) {
            items.add(ItemSerializer.deserialize(new JsonParser().parse(string).getAsJsonObject()));
        }
        return items;
    }

    public static void addItemStack(ItemStack stack) {
        List<String> stringItems = itemsConfig.getStringList("items");
        stringItems.add(ItemSerializer.serialize(stack));
        itemsConfig.set("items", stringItems);
    }

    public static void removeItemStack(ItemStack stack) {
        List<String> stringItems = itemsConfig.getStringList("items");
        String string = ItemSerializer.serialize(stack);
        stringItems.remove(string);
        itemsConfig.set("items", stringItems);
    }

    public static void save() {
        try {
            itemsConfig.save(new File("plugins/" + WanderingCollector.getPlugin().getName() + "/items.yml"));
        } catch (IOException e) {
            WanderingCollector.getPlugin().getLogger().log(Level.SEVERE, "Could not save items!");
        }
    }
}
