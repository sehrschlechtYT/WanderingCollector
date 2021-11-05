package yt.sehrschlecht.wanderingcollector.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import yt.sehrschlecht.wanderingcollector.config.Config;
import yt.sehrschlecht.wanderingcollector.utils.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WanderingTraderListeners implements Listener {
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if(!event.getEntityType().equals(EntityType.WANDERING_TRADER)) return;
        Config config = Config.getInstance();
        WanderingTrader trader = (WanderingTrader) event.getEntity();
        List<MerchantRecipe> recipes = new ArrayList<>();
        int tradeCount = config.getMinTradeCount() == config.getMaxTradeCount()
                ? config.getMinTradeCount()
                : ThreadLocalRandom.current().nextInt(config.getMinTradeCount(), config.getMaxTradeCount());
        int buyLimit = config.getBuyLimit();
        List<ItemStack> stacks = ItemManager.getItems();
        if(stacks.isEmpty()) return;
        for (int i = 0; i < tradeCount; i++) {
            ItemStack stack = stacks.get(ThreadLocalRandom.current().nextInt(stacks.size() - 1));
            if(config.shouldClearMeta()) {
                stack = new ItemStack(stack.getType());
            }
            MerchantRecipe recipe = new MerchantRecipe(stack, buyLimit);
            ItemStack ingredient = new ItemStack(config.getTradeItem());
            ingredient.setAmount(ThreadLocalRandom.current().nextInt(config.getMinPrice(), config.getMaxPrice()));
            recipe.addIngredient(ingredient);
            recipes.add(recipe);
        }
        trader.setRecipes(recipes);
    }
}
