package fr.spectr2155e.housepurchase.objects.database;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private DbConnection housePurchase;

    public DatabaseManager() {
        FileConfiguration config = HousePurchase.instance.getConfig();
        this.housePurchase = new DbConnection(new DbCredentials(config.getString("database.host"), config.getString("database.user"), config.getString("database.password"), config.getString("database.dbName"), config.getInt("database.port")));
    }

    public DbConnection getHousePurchase() {
        return housePurchase;
    }
    public void close(){
        try {this.housePurchase.close();}
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void initTables() throws SQLException {
        System.out.println("Initialisation des bases de donées...");
        DatabaseMetaData dbm = getHouseConnection().getConnection().getMetaData();

        // House Purchase
        ResultSet house_table = dbm.getTables(null, null, "house_purchase", null);
        if(!house_table.next()){
            System.out.println("Création de la base de donnée House Purchase !");
            Statement statement = getHouseConnection().getConnection().createStatement();
            String createQuery = "CREATE TABLE house_purchase(ID INT, LOCATION VARCHAR(200), IS_OWNED BOOLEAN, OWNER VARCHAR(20), IS_BUY BOOLEAN, DATE_OF_BUY TIMESTAMP, " +
                    "IS_LEASE BOOLEAN, LEASE_DATE TIMESTAMP, PRICE_OF_BUY INT, PRICE_OF_LEASE INT, TRUSTED_PLAYERS TEXT)";
            statement.execute(createQuery);
            statement.close();
        }

        // House Regions
        ResultSet house_region_table = dbm.getTables(null, null, "house_regions", null);
        if(!house_region_table.next()){
            System.out.println("Création de la base de donnée House Region !");
            Statement statement = getHouseConnection().getConnection().createStatement();
            String createQuery = "CREATE TABLE house_regions(ID INT, LOC1 VARCHAR(200), LOC2 VARCHAR(200), NAME VARCHAR(200))";
            statement.execute(createQuery);
            statement.close();
        }
    }

    public static DbConnection getHouseConnection(){return HousePurchase.getDatabaseManager().getHousePurchase();}
}
