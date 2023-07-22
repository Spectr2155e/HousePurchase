package fr.spectr2155e.housepurchase;

import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public final class HousePurchase extends JavaPlugin {

    public static HousePurchase instance;
    public static String storageMethod;

    @Override
    public void onEnable() {
        initStorageMethod();
        instance = this;
        System.out.println("Plugin HousePurchase créé par Spectr2155e activé !");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initStorageMethod(){
        storageMethod = getConfig().getString("config.storageMethod");
        if (!storageMethod.equalsIgnoreCase("file") && !storageMethod.equalsIgnoreCase("mysql"))
            System.out.println(Color.RED + "Problème de configuration, veuillez insérer une méthode de stockage correct file/mysql.");
    }
}
