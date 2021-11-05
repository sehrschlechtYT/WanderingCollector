package yt.sehrschlecht.wanderingcollector.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import yt.sehrschlecht.wanderingcollector.WanderingCollector;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Config {
    private static Config config = null;

    private final List<String> itemBlacklist;
    private final boolean clearMeta;

    private final int buyLimit;
    private final int minTradeCount;
    private final int maxTradeCount;
    private final int minPrice;
    private final int maxPrice;
    private final String tradeItem;

    public Config(List<String> itemBlacklist, boolean clearMeta, int buyLimit, int minTradeCount, int maxTradeCount, int minPrice, int maxPrice, String tradeItem) {
        this.itemBlacklist = itemBlacklist;
        this.clearMeta = clearMeta;
        this.buyLimit = buyLimit;
        this.minTradeCount = minTradeCount;
        this.maxTradeCount = maxTradeCount;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.tradeItem = tradeItem;
        config = this;
    }

    public static Config getInstance() {
        if(config == null) {
            reload();
        }
        return config;
    }

    public static void reload() {
        FileConfiguration configuration = WanderingCollector.getPlugin().getConfig();
        config = new Config(
                configuration.getStringList("items.blacklist"),
                configuration.getBoolean("items.clear-meta"),

                configuration.getInt("trades.buy-limit"),
                configuration.getInt("trades.trade-count.min"),
                configuration.getInt("trades.trade-count.max"),
                configuration.getInt("trades.price.min"),
                configuration.getInt("trades.price.max"),
                configuration.getString("trades.price.item"));
    }

    public List<Material> getItemBlacklist() {
        return itemBlacklist.stream().map(material -> {
            try {
                return Material.valueOf(material.toUpperCase(Locale.ENGLISH));
            } catch (Exception exception) {
                WanderingCollector.getPlugin().getLogger().log(Level.SEVERE, "The specified material \"" + material + "\" is invalid!");
                return null;
            }
        }).collect(Collectors.toList());
    }

    public boolean shouldClearMeta() {
        return clearMeta;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public int getMinTradeCount() {
        return minTradeCount;
    }

    public int getMaxTradeCount() {
        return maxTradeCount;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public Material getTradeItem() {
        return Material.valueOf(tradeItem);
    }
}
