package yt.sehrschlecht.wanderingcollector.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.wanderingcollector.config.Config;
import yt.sehrschlecht.wanderingcollector.utils.ItemManager;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(event.getDrops().isEmpty()) return;
        Config config = Config.getInstance();
        if(!config.shouldTradeDeathItems()) return;
        for (ItemStack drop : event.getDrops()) {
            if(config.getItemBlacklist().contains(drop.getType())) continue;
            ItemManager.addItemStack(drop);
        }
        event.getDrops().clear();
        ItemManager.save();
    }
}
