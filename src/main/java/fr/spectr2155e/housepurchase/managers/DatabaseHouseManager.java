package fr.spectr2155e.housepurchase.managers;

import com.google.gson.Gson;
import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.database.Query;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import fr.spectr2155e.housepurchase.region.RegionMaker;
import fr.spectr2155e.housepurchase.region.manager.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHouseManager {

    public static void createHouse(int id, Location location, int priceOfBuy, int priceOfLease) {
        Houses.housesList.add(id);
        final String sqlRequest = "INSERT INTO house_purchase VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, Utils.getLocationToJSON(location));
                    preparedStatement.setBoolean(3, false);
                    preparedStatement.setString(4, null);
                    preparedStatement.setBoolean(5, false);
                    preparedStatement.setTimestamp(6, null);
                    preparedStatement.setBoolean(7, false);
                    preparedStatement.setTimestamp(8, null);
                    preparedStatement.setInt(9, priceOfBuy);
                    preparedStatement.setInt(10, priceOfLease);
                    preparedStatement.setString(11, null);
                    preparedStatement.execute();
                    Houses.houses.put(id, new Houses(id, Utils.getLocationToJSON(location), false, null, false, null, false, null, priceOfBuy, priceOfLease, true, null));
                } catch (SQLException e) {throw new RuntimeException(e);}
            }
        }.runTaskAsynchronously(HousePurchase.instance);
    }

    public static void initHouses(){
        final String sqlRequest = "SELECT ID, LOCATION, IS_OWNED, OWNER, IS_BUY, DATE_OF_BUY, IS_LEASE, LEASE_DATE, PRICE_OF_BUY, PRICE_OF_LEASE, TRUSTED_PLAYERS FROM house_purchase";
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    final Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlRequest);
                    while(resultSet.next()){
                        Houses.houses.put(resultSet.getInt("ID"), new Houses(resultSet.getInt("ID"),
                                resultSet.getString("LOCATION"),
                                resultSet.getBoolean("IS_OWNED"),
                                resultSet.getString("OWNER"),
                                resultSet.getBoolean("IS_BUY"),
                                resultSet.getTimestamp("DATE_OF_BUY"),
                                resultSet.getBoolean("IS_LEASE"),
                                resultSet.getTimestamp("LEASE_DATE"),
                                resultSet.getInt("PRICE_OF_BUY"),
                                resultSet.getInt("PRICE_OF_LEASE"),
                                true,
                                resultSet.getString("TRUSTED_PLAYERS")));
                        Houses.housesList.add(resultSet.getInt("ID"));
                    }
                } catch (SQLException e) {throw new RuntimeException(e);}
            }
        }.runTaskAsynchronously(HousePurchase.instance);
    }

    public static void initRegions(){
        final String sqlRequest = "SELECT ID, LOC1, LOC2, NAME FROM house_regions";
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    final Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sqlRequest);
                    while(resultSet.next()){
                        HouseRegion.regions.put(resultSet.getInt("ID"), new HouseRegion(resultSet.getInt("ID"),
                                Utils.getJSONToLocation(resultSet.getString("LOC1")),
                                Utils.getJSONToLocation(resultSet.getString("LOC2")),
                                resultSet.getString("NAME")));
                        RegionMaker.getInstance().getRegionManager().registerRegion(HousePurchase.instance, new Location(Bukkit.getWorld("world"), Utils.getJSONToLocation(resultSet.getString("LOC1")).getX(), Utils.getJSONToLocation(resultSet.getString("LOC1")).getY(), Utils.getJSONToLocation(resultSet.getString("LOC1")).getZ()), new Location(Bukkit.getWorld("world"), Utils.getJSONToLocation(resultSet.getString("LOC2")).getX(), Utils.getJSONToLocation(resultSet.getString("LOC2")).getY(), Utils.getJSONToLocation(resultSet.getString("LOC2")).getZ()), resultSet.getString("NAME"), true);
                    }
                } catch (SQLException e) {throw new RuntimeException(e);}
            }
        }.runTaskAsynchronously(HousePurchase.instance);
    }

    public static void createRegion(int id, Location loc1, Location loc2, String name){
        new BukkitRunnable(){
            @Override
            public void run() {
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
        }.runTaskAsynchronously(HousePurchase.instance);
    }

    public static void removeRegion(int id){
        Query.requestUpdate("DELETE FROM house_regions WHERE ID = "+id);
    }

    public static String getJsonFromArray(List<String> arrayList){
        return new Gson().toJson(arrayList);
    }

    public static List<String> getArrayFromJson(String json){
        json = json.replace("\\", "").replaceFirst("\"", "");
        json = json.substring(0, json.length() - 1);
        json = json.replace("[","");
        json = json.replace("]","");
        json = json.replace("\"","");
        List<String> myList = new ArrayList<String>(Arrays.asList(json.split(",")));
        return myList;
    }
}
