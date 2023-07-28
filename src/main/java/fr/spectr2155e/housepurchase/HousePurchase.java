package fr.spectr2155e.housepurchase;

import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.managers.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public final class HousePurchase extends JavaPlugin {

    public static HousePurchase instance;
    public static FileManager fileManager = new FileManager();
    public static CommandsManager commandsManager = new CommandsManager();
    public static ListenerManager listenerManager = new ListenerManager();
    public static ConfigManager configManager = new ConfigManager();
    public static Utils utils = new Utils();
    public static HashMap<String, ItemStack[]> inventories = new HashMap<>();
    public static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
    private static DatabaseManager databaseManager;
    public static DatabaseManager getDatabaseManager() {return databaseManager;}

    @Override
    public void onEnable() {
        instance = this;
        configManager.initConfig();
        System.out.println("HousePurchase activé ! Version 1.0");
        listenerManager.initListeners();
        commandsManager.initCommands();
        databaseManager = new DatabaseManager();
        initAllTables();
        Houses.initHouses();
        HouseRegion.initRegions();
        LeaseHouse.initTimer();
    }

    @Override
    public void onDisable() {
        databaseManager.close();
    }

    private void initAllTables(){
        try {
            getDatabaseManager().initTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
