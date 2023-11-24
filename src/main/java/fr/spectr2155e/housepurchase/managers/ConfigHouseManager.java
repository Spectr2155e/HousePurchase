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
        file.set("house.location", Utils.getLocationToJSON(location));
        file.set("house.isOwned", false);
        file.set("house.owner", null);
        file.set("house.isBuy", false);
        file.set("house.dateOfBuy", null);
        file.set("house.isLease", false);
        file.set("house.leaseDate", null);
        file.set("house.priceOfBuy", priceOfBuy);
        file.set("house.priceOfLease", priceOfLease);
        addIdToList(id);
        saveConfigIdFile(file, id);
        Houses.houses.put(id, new Houses(id, Utils.getLocationToJSON(location), false, null, false, null, false, null, priceOfBuy, priceOfLease, true));
    }

    public static void createRegion(int id, Location loc1, Location loc2, String name){
        checkDir();
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
        file.set("region.id", id);
        file.set("region.loc1", Utils.getLocationToJSON(loc1));
        file.set("region.loc2", Utils.getLocationToJSON(loc2));
        file.set("region.name", name);
        saveConfigIdFile(file, id);
    }

    public static void removeRegion(int id){
        checkDir();
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
        file.set("region", null);
        saveConfigIdFile(file, id);
    }

    public static void initHouses(){
        if(getListOfHouses() == null) return;
        for(Integer id : getListOfHouses()){
            FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
            Houses.houses.put(id, new Houses(id,
                    file.getString("house.location"),
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
        if(getListOfHouses() == null) return;
        for (Integer id : getListOfHouses()){
            FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile(id));
            if(file.get("region.id") != null){
                HouseRegion.regions.put(id, new HouseRegion(id,
                        Utils.getJSONToLocation(file.getString("region.loc1")),
                        Utils.getJSONToLocation(file.getString("region.loc2")),
                        file.getString("region.name")));
            }
        }
    }

    public static void removeHouse(int id){removeIdFile(id);}

    private static void checkDir(){
        if(!houseFolder.exists()){
            houseFolder.mkdir();
            try {new File(houseFolder, "list.yml").createNewFile();}
            catch (IOException e) {throw new RuntimeException(e);}
        }
    }

    public static List<Integer> getListOfHouses(){
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
        FileConfiguration listFile = YamlConfiguration.loadConfiguration(getIdFile("list"));
        if(idFile.exists()){idFile.delete();}
        List<Integer> list = getListOfHouses();
        list.remove((Integer) id);
        if(!list.isEmpty()) {listFile.set("list", list);}
        else {listFile.set("list", null);}
        saveConfigIdFile(listFile, "list");
    }

    public static File getIdFile(int id){return new File(houseFolder, id + ".yml");}

    public static File getIdFile(String str){return new File(houseFolder, str + ".yml");}

    private static void addIdToList(int id){
        FileConfiguration file = YamlConfiguration.loadConfiguration(getIdFile("list"));
        List<Integer> list = new ArrayList<>();
        if(file.get("list") != null){
            list = getListOfHouses();
            list.add(id);
            file.set("list", list);
            saveConfigIdFile(file, "list");
            return;
        }
        list.add(id);
        file.set("list", list);
        saveConfigIdFile(file, "list");
    }

    private static void saveConfigIdFile(FileConfiguration file, int id){
        try {file.save(getIdFile(id));}
        catch (IOException e) {e.printStackTrace();}
    }

    private static void saveConfigIdFile(FileConfiguration file, String str){
        try {file.save(getIdFile(str));}
        catch (IOException e) {e.printStackTrace();}
    }
}
