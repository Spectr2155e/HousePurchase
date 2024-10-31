package fr.spectr2155e.housepurchase;


import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import fr.spectr2155e.housepurchase.managers.EconomyHouseManager;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import fr.spectr2155e.housepurchase.objects.managers.*;
import fr.spectr2155e.housepurchase.region.RegionMaker;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

public final class HousePurchase extends JavaPlugin {
    
    public static final String prefixHousePurchase = "§8§l(§6§lHousePurchase§8§l) §f";
    public static final String prefixError = "§8§l(§4§lErreur§8§l) §c";

    public static HousePurchase instance;
    public static FileManager fileManager = new FileManager();
    public static CommandsManager commandsManager = new CommandsManager();
    public static ListenerManager listenerManager = new ListenerManager();
    public static ConfigManager configManager = new ConfigManager();
    public static RegionMaker regionMaker = new RegionMaker();
    public static Utils utils = new Utils();
    public static HashMap<String, ItemStack[]> inventories = new HashMap<>();
    public static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
    public static DatabaseManager databaseManager;
    public static DatabaseManager getDatabaseManager() {return databaseManager;}
    public static String methodOfStorage;
    public static net.milkbowl.vault.economy.Economy econ;

    @Override
    public void onEnable() {
        instance = this;
        configManager.initConfig();
        ConfigManager.initMethodOfStorage();
        if(EconomyHouseManager.initEconomyMode() != null) econ = EconomyHouseManager.initEconomyMode().getProvider();
        regionMaker.enable();
        listenerManager.initListeners();
        commandsManager.initCommands();
        try {Houses.initHouses();} catch (ParseException e) {throw new RuntimeException(e);}
        HouseRegion.initRegions();
        LeaseHouse.initTimer();
        a
        System.out.println("Plugin créé par Spectr2155e (HousePurchase) activé version "+ConfigManager.getVersion());
    }

    @Override
    public void onDisable() {
        if(methodOfStorage.equals("database"))
            getDatabaseManager().close();
        regionMaker.disable();
    }

    public void initAllTables(){
        try {getDatabaseManager().initTables();}
        catch (SQLException e) {throw new RuntimeException(e);}
    }

}
