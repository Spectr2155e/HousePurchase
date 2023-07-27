package fr.spectr2155e.housepurchase.classes;

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
        final String sqlRequest = "SELECT ID, LOC1, LOC2, NAME FROM house_regions";
        try {
            final Connection connection = DatabaseManager.getHouseConnection().getConnection();
            final Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while(resultSet.next()){
                regions.put(resultSet.getInt("ID"), new HouseRegion(resultSet.getInt("ID"),
                                Utils.getJSONToLocation(resultSet.getString("LOC1")),
                                Utils.getJSONToLocation(resultSet.getString("LOC2")),
                                resultSet.getString("NAME")));
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
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
        try {
            final Connection connection = DatabaseManager.getHouseConnection().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO house_regions VALUES(?, ?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, Utils.getLocationToJSON(loc1));
            preparedStatement.setString(3, Utils.getLocationToJSON(loc2));
            preparedStatement.setString(4, name);
            preparedStatement.execute();
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
