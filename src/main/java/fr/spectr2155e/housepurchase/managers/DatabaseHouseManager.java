package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;

import java.sql.*;

public class DatabaseHouseManager {

    public static void createHouse(int id, Location location, int priceOfBuy, int priceOfLease) {
        final String sqlRequest = "INSERT INTO house_purchase VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.execute();
            Houses.houses.put(id, new Houses(id, Utils.getLocationToJSON(location), false, null, false, null, false, null, priceOfBuy, priceOfLease, true));
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    public static void initHouses(){
        final String sqlRequest = "SELECT ID, LOCATION, IS_OWNED, OWNER, IS_BUY, DATE_OF_BUY, IS_LEASE, LEASE_DATE, PRICE_OF_BUY, PRICE_OF_LEASE FROM house_purchase";
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
                        true));
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}