package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;

public class ConfigManager {
    public void createDefaultConfig(){
        HousePurchase.instance.getConfig().addDefault("config.storageMethod", "file");
        HousePurchase.instance.getConfig().addDefault("config.database.host", "localhost");
        HousePurchase.instance.getConfig().addDefault("config.database.username", "minecraft");
        HousePurchase.instance.getConfig().addDefault("config.database.password", "yourPassword");
        HousePurchase.instance.getConfig().addDefault("config.database.dbName", "yourDbName");
        HousePurchase.instance.getConfig().addDefault("config.database.port", 3306);
        HousePurchase.instance.getConfig().options().copyDefaults(true);
        HousePurchase.instance.saveConfig();
    }
}
