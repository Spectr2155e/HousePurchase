package fr.spectr2155e.housepurchase.classes;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.managers.ConfigHouseManager;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.database.Query;
import fr.spectr2155e.housepurchase.objects.managers.ConfigManager;
import fr.spectr2155e.housepurchase.objects.managers.FileManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Houses {

    public static ArrayList<Integer> housesList = new ArrayList<>();

    public static HashMap<Integer, Houses> houses = new HashMap<>();
    private int id;
    private String location;
    private boolean isOwned;
    private String owner;
    private boolean isBuy;
    private Timestamp dateOfBuy;
    private boolean isLease;
    private Timestamp leaseDate;
    private int priceOfBuy;
    private int priceOfLease;
    private boolean locked;
    private String trustedPlayers;

    public Houses(int id, String location, boolean isOwned, String owner, boolean isBuy, Timestamp dateOfBuy, boolean isLease, Timestamp leaseDate, int priceOfBuy, int priceOfLease, boolean locked, String trustedPlayers) {
        this.id = id;
        this.location = location;
        this.isOwned = isOwned;
        this.owner = owner;
        this.isBuy = isBuy;
        this.dateOfBuy = dateOfBuy;
        this.isLease = isLease;
        this.leaseDate = leaseDate;
        this.priceOfBuy = priceOfBuy;
        this.priceOfLease = priceOfLease;
        this.locked = locked;
        this.trustedPlayers = trustedPlayers;
    }

    public static void initHouses() throws ParseException {
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.initHouses();
                break;
            case "database":
                DatabaseHouseManager.initHouses();
                break;
        }
    }

    public static void createHouse(Location location, int priceOfBuy, int priceOfLease, Player creator) throws IOException {
        int randomNumber = Utils.getRandomNumberInRange(100000, 1000000);
        while(houses.containsKey(randomNumber)){randomNumber = Utils.getRandomNumberInRange(100000, 1000000);}
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.createHouse(randomNumber, location, priceOfBuy, priceOfLease, creator);
                break;
            case "database":
                DatabaseHouseManager.createHouse(randomNumber, location, priceOfBuy, priceOfLease, creator);
                break;
        }
    }

    public static void removeHouse(int id, Player deleter) throws IOException {
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.removeHouse(id, deleter);
                break;
            case "database":
                DatabaseHouseManager.removeHouse(id, deleter);
                break;
        }
    }

    public static int getId(Location location) {
        int idOfHouse = 0;
        for (Map.Entry<Integer, Houses> entry : houses.entrySet()) {
            if (entry.getValue().location.equals(Utils.getLocationToJSON(location))) {idOfHouse = entry.getKey();}
        }
        return idOfHouse;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getLocation() {return location;}
    public void setLocation(String newLocation, String location) {
        this.location = location;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(Houses.getId(Utils.getJSONToLocation(location))));
                file.set("house.location", Utils.getJSONToLocation(newLocation));
                ConfigHouseManager.saveConfigIdFile(file, Houses.getId(Utils.getJSONToLocation(location)));
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET LOCATION = "+newLocation+" WHERE LOCATION = "+location);
                break;
        }
    }
    public boolean isOwned() {
        return isOwned;
    }
    public void setOwned(boolean owned) {
        isOwned = owned;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.isOwned", owned);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET IS_OWNED = "+owned+" WHERE ID = "+this.id);
                break;
        }
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.owner", owner);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id)).set("house.owner", owner);
                break;
            case "database":
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET OWNER = ? WHERE ID = ?");
                    preparedStatement.setString(1, owner);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.execute();
                } catch (SQLException e) {throw new RuntimeException(e);}
                break;
        }
    }
    public boolean isBuy() {
        return isBuy;
    }
    public void setBuy(boolean buy) {
        isBuy = buy;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.isBuy", buy);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET IS_BUY = "+buy+" WHERE ID = "+this.id);
                break;
        }
    }
    public Timestamp getDateOfBuy() {
        return dateOfBuy;
    }
    public void setDateOfBuy(Timestamp dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.dateOfBuy", dateOfBuy);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET DATE_OF_BUY = ? WHERE ID = ?");
                    preparedStatement.setTimestamp(1, dateOfBuy);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.execute();
                } catch (SQLException e) {throw new RuntimeException(e);}
                break;
        }
    }
    public boolean isLease() {
        return isLease;
    }
    public void setLease(boolean lease) {
        isLease = lease;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.isLease", lease);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET IS_LEASE = "+lease+" WHERE ID = "+this.id);
                break;
        }
    }
    public Timestamp getLeaseDate() {
        return leaseDate;
    }
    public void setLeaseDate(Timestamp leaseDate) {
        this.leaseDate = leaseDate;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.leaseDate", leaseDate);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET LEASE_DATE = ? WHERE ID = ?");
                    preparedStatement.setTimestamp(1, leaseDate);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.execute();
                } catch (SQLException e) {throw new RuntimeException(e);}
                break;
        }
    }
    public int getPriceOfBuy() {
        return priceOfBuy;
    }
    public void setPriceOfBuy(int priceOfBuy) {
        this.priceOfBuy = priceOfBuy;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.priceOfBuy", priceOfBuy);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET PRICE_OF_BUY = "+priceOfBuy+" WHERE ID = "+this.id);
                break;
        }
    }
    public int getPriceOfLease() {
        return priceOfLease;
    }
    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.priceOfLease", priceOfLease);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                Query.requestUpdate("UPDATE house_purchase SET PRICE_OF_LEASE = "+priceOfLease+" WHERE ID = "+this.id);
                break;
        }
    }
    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public String getTrustedPlayers() {
        return trustedPlayers;
    }
    public void setTrustedPlayers(String trustedPlayers) {
        switch (HousePurchase.methodOfStorage){
            case "file":
                FileConfiguration file = YamlConfiguration.loadConfiguration(ConfigHouseManager.getIdFile(this.id));
                file.set("house.trustedPlayers", trustedPlayers);
                ConfigHouseManager.saveConfigIdFile(file, this.id);
                break;
            case "database":
                try {
                    final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET TRUSTED_PLAYERS = ? WHERE ID = ?");
                    preparedStatement.setString(1, trustedPlayers);
                    preparedStatement.setInt(2, this.id);
                    preparedStatement.execute();
                } catch (SQLException e) {throw new RuntimeException(e);}
                break;
        }
        this.trustedPlayers = trustedPlayers;
    }

    public void removeTrustedPlayer(String trustedPlayers){
        List<String> list = DatabaseHouseManager.getArrayFromJson(getTrustedPlayers());
        list.remove(trustedPlayers);
        if(list.isEmpty()){
            setTrustedPlayers(null);
        } else {
            setTrustedPlayers(DatabaseHouseManager.getJsonFromArray(list));
        }
    }

}
