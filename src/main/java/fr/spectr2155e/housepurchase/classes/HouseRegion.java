package fr.spectr2155e.housepurchase.classes;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.managers.ConfigHouseManager;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.HashMap;

public class HouseRegion {

    public static HashMap<Integer, HouseRegion> regions = new HashMap<>();

    private int id;
    private Location loc1;
    private Location loc2;
    private String name;

    public HouseRegion(int id, Location loc1, Location loc2, String name) {
        this.id = id;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.name = name;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public Location getLoc1() {return loc1;}
    public void setLoc1(Location loc1) {this.loc1 = loc1;}
    public Location getLoc2() {return loc2;}
    public void setLoc2(Location loc2) {this.loc2 = loc2;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    // Initialisation Region - Au démarrage du serveur.
    public static void initRegions(){
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.initRegions();
                break;
            case "database":
                DatabaseHouseManager.initRegions();
                break;
        }
    }

    // Register Region dans le Gui
    public static void registerRegion(Player player, String options, @Nullable Location location, @Nullable String name){
        switch (options) {
            case "loc1":
                if(!CreationRegion.creationRegionHashMap.containsKey(player)) {CreationRegion.creationRegionHashMap.put(player, new CreationRegion(null, location, null, null));}
                CreationRegion.creationRegionHashMap.get(player).setLoc1(location);
                break;
            case "loc2":
                if(!CreationRegion.creationRegionHashMap.containsKey(player)){CreationRegion.creationRegionHashMap.put(player, new CreationRegion(null, null, location, null));}
                CreationRegion.creationRegionHashMap.get(player).setLoc2(location);
                break;
            case "name":
                if(!CreationRegion.creationRegionHashMap.containsKey(player)){CreationRegion.creationRegionHashMap.put(player, new CreationRegion(null, null, null, name));}
                CreationRegion.creationRegionHashMap.get(player).setName(name);
                break;
        }
    }

    // Création de la region pour un id de porte
    public static void createRegion(int id, Location loc1, Location loc2, String name) {
        //todo RegionMaker Plugin
        regions.put(id, new HouseRegion(id, loc1, loc2, name));
        switch (HousePurchase.methodOfStorage) {
            case "file":
                ConfigHouseManager.createRegion(id, loc1, loc2, name);
                break;
            case "database":
                DatabaseHouseManager.createRegion(id, loc1, loc2, name);
                break;
        }
    }
}
