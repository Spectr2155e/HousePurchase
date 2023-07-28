package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConfigHouseManager {
    static File houseFolder = new File(HousePurchase.instance.getDataFolder(), "Houses");

    public static void createHouse(int id, Location location, int priceOfBuy, int priceOfLease){
        checkDir();
        createIdFile(id);
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
        file.set("house.id", id);
        file.set("house.location", location);
        file.set("house.isOwned", false);
        file.set("house.owner", null);
        file.set("house.isBuy", false);
        file.set("house.dateOfBuy", null);
        file.set("house.isLease", false);
        file.set("house.leaseDate", null);
        file.set("house.priceOfBuy", priceOfBuy);
        file.set("house.priceOfLease", priceOfLease);
        saveConfigIdFile(file, id);
        Houses.houses.put(id, new Houses(id, Utils.getLocationToJSON(location), false, null, false, null, false, null, priceOfBuy, priceOfLease, true));
    }

    public static void createRegion(int id, Location loc1, Location loc2, String name){
        checkDir();
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
        file.set("region.id", id);
        file.set("region.loc1", loc1);
        file.set("region.loc2", loc2);
        file.set("region.name", name);
        saveConfigIdFile(file, id);
    }

    public static void initHouses(){
        for(Integer id : getListOfHouses()){
            FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
            Houses.houses.put(id, new Houses(id,
                    Utils.getLocationToJSON((Location) file.get("house.location")),
                    file.getBoolean("isOwned"),
                    file.getString("house.owner"),
                    file.getBoolean("house.isBuy"),
                    (Timestamp) file.get("house.dateOfBuy"),
                    file.getBoolean("house.isLease"),
                    (Timestamp) file.get("house.leaseDate"),
                    file.getInt("house.priceOfBuy"),
                    file.getInt("house.priceOfLease"),
                    true));
        }
    }

    public static void initRegions(){
        for (Integer id : getListOfHouses()){
            FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
            if(file.get("region.id") != null){
                HouseRegion.regions.put(id, new HouseRegion(id,
                        (Location) file.get("region.loc1"),
                        (Location) file.get("region.loc2"),
                        file.getString("region.name")));
            }
        }
    }

    public static void removeHouse(int id){
        removeIdFile(id);
    }

    private static void checkDir(){
        if(!houseFolder.exists()){
            houseFolder.mkdir();
            try {new File(houseFolder, "list.yml").createNewFile();}
            catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    private static List<Integer> getListOfHouses(){
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile("list"));
        return (List<Integer>) file.getList("list");
    }

    private static void createIdFile(int id){
        File idFile = new File(houseFolder, id + ".yml");
        if(!idFile.exists()){
            try {idFile.createNewFile();}
            catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    private static void removeIdFile(int id){
        File idFile = new File(houseFolder, id + ".yml");
        if(idFile.exists()){idFile.delete();}
        List<Integer> list = getListOfHouses();
        list.remove(id);
        YamlConfiguration.loadConfiguration(getIdFile("list")).set("list", list);
    }

    public static File getIdFile(int id){return new File(houseFolder, id + ".yml");}

    public static File getIdFile(String str){return new File(houseFolder, str + ".yml");}

    private static void saveConfigIdFile(FileConfiguration file, int id){
        try {file.save(getIdFile(id));}
        catch (IOException e) {e.printStackTrace();}
    }
}
