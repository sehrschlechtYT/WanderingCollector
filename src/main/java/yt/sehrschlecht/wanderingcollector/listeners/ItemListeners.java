package yt.sehrschlecht.wanderingcollector.listeners;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.wanderingcollector.config.Config;
import yt.sehrschlecht.wanderingcollector.utils.ItemManager;

public class ItemListeners implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onDespawn(ItemDespawnEvent event) {
        Config config = Config.getInstance();
        if(!config.shouldTradeDespawnedItems()) return;
        Item item = event.getEntity();
        ItemStack stack = item.getItemStack();
        if(config.isWhitelistEnabled() && !config.getItemWhitelist().contains(stack.getType())) return;
        if(config.getItemBlacklist().contains(stack.getType())) return;
        ItemManager.addItemStack(stack);
        ItemManager.save();
    }
}
