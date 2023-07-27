package fr.spectr2155e.housepurchase.objects.database;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.SQLException;

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

    public static DbConnection getHouseConnection(){return HousePurchase.getDatabaseManager().getHousePurchase();}
}
