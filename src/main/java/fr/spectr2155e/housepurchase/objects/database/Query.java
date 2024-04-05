package fr.spectr2155e.housepurchase.objects.database;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
    public static void requestUpdate(String sqlRequest){
        System.out.println("test");
        Bukkit.getScheduler().runTaskAsynchronously(HousePurchase.instance, () -> {
            try {
                final Connection connection = DatabaseManager.getHouseConnection().getConnection();
                final Statement statement = connection.createStatement();
                statement.execute(sqlRequest);
            } catch (SQLException e) {throw new RuntimeException(e);}
        });
    }
}
