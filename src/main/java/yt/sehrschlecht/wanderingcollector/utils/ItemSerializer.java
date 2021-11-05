package yt.sehrschlecht.wanderingcollector.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ItemSerializer {
    private static Gson gson = new Gson();

    public static String serialize(ItemStack item) {
        String base64 = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitOut = new BukkitObjectOutputStream(out);
            bukkitOut.writeObject(item);
            bukkitOut.close();
            base64 = Base64Coder.encodeLines(out.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObject object = new JsonObject();
        object.addProperty("item-data", base64);
        return gson.toJson(object);
    }

    public static ItemStack deserialize(JsonObject object) {
        ItemStack result = null;
        String itemData = object.get("item-data").getAsString();
        ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(itemData));
        try {
            BukkitObjectInputStream bukkitIn = new BukkitObjectInputStream(in);
            result = (ItemStack) bukkitIn.readObject();
            bukkitIn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}

