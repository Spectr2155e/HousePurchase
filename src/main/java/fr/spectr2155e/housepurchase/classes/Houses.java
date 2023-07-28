package fr.spectr2155e.housepurchase.classes;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.managers.ConfigHouseManager;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.database.Query;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Houses {

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

    public Houses(int id, String location, boolean isOwned, String owner, boolean isBuy, Timestamp dateOfBuy, boolean isLease, Timestamp leaseDate, int priceOfBuy, int priceOfLease, boolean locked) {
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
    }

    public static void initHouses(){
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.initHouses();
                break;
            case "database":
                DatabaseHouseManager.initHouses();
                break;
        }
    }

    public static void createHouse(Location location, int priceOfBuy, int priceOfLease){
        int randomNumber = Utils.getRandomNumberInRange(100000, 1000000);
        while(houses.containsKey(randomNumber)){randomNumber = Utils.getRandomNumberInRange(100000, 1000000);}
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.createHouse(randomNumber, location, priceOfBuy, priceOfLease);
                break;
            case "database":
                DatabaseHouseManager.createHouse(randomNumber, location, priceOfBuy, priceOfLease);
                break;
        }
    }

    public static void removeHouse(int id){
        if(houses.containsKey(id)){houses.remove(id);}
        switch (HousePurchase.methodOfStorage){
            case "file":
                ConfigHouseManager.removeHouse(id);
                break;
            case "database":
                Query.requestUpdate("DELETE FROM house_purchase WHERE ID = "+id);
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
    public String getLocation() {return location;}
    public void setLocation(String newLocation, String location) {
        this.location = location;
        Query.requestUpdate("UPDATE house_purchase SET LOCATION = "+newLocation+" WHERE LOCATION = "+location);
    }
    public boolean isOwned() {
        return isOwned;
    }
    public void setOwned(boolean owned) {
        isOwned = owned;
        Query.requestUpdate("UPDATE house_purchase SET IS_OWNED = "+owned+" WHERE ID = "+this.id);
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
        try {
            final Connection connection = DatabaseManager.getHouseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET OWNER = ? WHERE ID = ?");
            preparedStatement.setString(1, owner);
            preparedStatement.setInt(2, this.id);
            preparedStatement.execute();
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
    public boolean isBuy() {
        return isBuy;
    }
    public void setBuy(boolean buy) {
        isBuy = buy;
        Query.requestUpdate("UPDATE house_purchase SET IS_BUY = "+buy+" WHERE ID = "+this.id);
    }
    public Timestamp getDateOfBuy() {
        return dateOfBuy;
    }
    public void setDateOfBuy(Timestamp dateOfBuy) {
        this.dateOfBuy = dateOfBuy;
        try {
            final Connection connection = DatabaseManager.getHouseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET DATE_OF_BUY = ? WHERE ID = ?");
            preparedStatement.setTimestamp(1, dateOfBuy);
            preparedStatement.setInt(2, this.id);
            preparedStatement.execute();
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
    public boolean isLease() {
        return isLease;
    }
    public void setLease(boolean lease) {
        isLease = lease;
        Query.requestUpdate("UPDATE house_purchase SET IS_LEASE = "+lease+" WHERE ID = "+this.id);
    }
    public Timestamp getLeaseDate() {
        return leaseDate;
    }
    public void setLeaseDate(Timestamp leaseDate) {
        this.leaseDate = leaseDate;
        try {
            final Connection connection = DatabaseManager.getHouseConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE house_purchase SET LEASE_DATE = ? WHERE ID = ?");
            preparedStatement.setTimestamp(1, leaseDate);
            preparedStatement.setInt(2, this.id);
            preparedStatement.execute();
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
    public int getPriceOfBuy() {
        return priceOfBuy;
    }
    public void setPriceOfBuy(int priceOfBuy) {
        this.priceOfBuy = priceOfBuy;
        Query.requestUpdate("UPDATE house_purchase SET PRICE_OF_BUY = "+priceOfBuy+" WHERE ID = "+this.id);
    }
    public int getPriceOfLease() {
        return priceOfLease;
    }
    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
        Query.requestUpdate("UPDATE house_purchase SET PRICE_OF_LEASE = "+priceOfLease+" WHERE ID = "+this.id);
    }
    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
