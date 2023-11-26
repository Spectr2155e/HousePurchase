package fr.spectr2155e.housepurchase.objects.managers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(now);
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isOnline(String player){
        if(Bukkit.getPlayerExact(player) != null){
            return Bukkit.getPlayerExact(player).isOnline();
        } else {
            return false;
        }
    }

    public Object getDefaultConfig(String path){
        return HousePurchase.instance.getConfig().get(path);
    }

    public ItemStack getItem(Material item, String name, int id, String... lore){
        ItemStack it = new ItemStack(item);
        ItemMeta it_meta = it.getItemMeta();
        it_meta.setDisplayName(name);
        it_meta.setLore(Arrays.asList(lore));
        it.setDurability((short) id);
        it.setItemMeta(it_meta);
        return it;
    }
    public ItemStack getPlayerHead(String player, String name, String... lore){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
        meta.setLore(Arrays.asList(lore));
        meta.setDisplayName(name);
        skull.setItemMeta(meta);
        return skull;
    }
    public ItemStack getItema(Material item, String name, ArrayList lore, int id){
        ItemStack it = new ItemStack(item);
        ItemMeta it_meta = it.getItemMeta();
        it_meta.setDisplayName(name);
        it_meta.setLore(lore);
        it.setDurability((short) id);
        it.setItemMeta(it_meta);
        return it;
    }

    public String getFinalStringArgsCommand(String[] args){
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            builder.append(args[i]);
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public String getHumanReadablePriceFromNumber(int number){

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

        if(number >= 1000000000){
            return String.format("%.2fMard", number/ 1000000000.0);
        }

        if(number >= 1000000){
            return String.format("%.2fMil", number/ 1000000.0);
        }

        return numberFormat.format(number);

    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getLocationToJSON(Location location){
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("world", location.getWorld().getName());
        jsonObject.put("x", location.getX());
        jsonObject.put("y", location.getY());
        jsonObject.put("z", location.getZ());
        jsonObject.put("pitch", location.getPitch());
        jsonObject.put("yaw", location.getYaw());
        return jsonObject.toString();
    }

    public static Location getJSONToLocation(String json){
        JsonObject jsonString = new JsonParser().parse(json).getAsJsonObject();
        Location location = new Location(Bukkit.getWorld(String.valueOf(jsonString.get("world"))),
                Float.valueOf(String.valueOf(jsonString.get("x"))),
                Float.valueOf(String.valueOf(jsonString.get("y"))),
                Float.valueOf(String.valueOf(jsonString.get("z"))),
                Float.valueOf(String.valueOf(jsonString.get("pitch"))),
                Float.valueOf(String.valueOf(jsonString.get("yaw"))));
        return location;
    }

    public static Timestamp addDays(Timestamp timestamp, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        cal.add(Calendar.HOUR, days*24);
        timestamp.setTime(cal.getTime().getTime());
        return timestamp;
    }

}
