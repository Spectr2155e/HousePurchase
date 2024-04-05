package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.objects.database.DatabaseManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class ConfigManager {

    public void initConfig(){
        HousePurchase.instance.getConfig().addDefault("database.host", "localhost");
        HousePurchase.instance.getConfig().addDefault("database.user", "root");
        HousePurchase.instance.getConfig().addDefault("database.password", "password");
        HousePurchase.instance.getConfig().addDefault("database.dbName", "housepurchase");
        HousePurchase.instance.getConfig().addDefault("database.port", 3306);
        HousePurchase.instance.getConfig().addDefault("config.methodOfStorage", "file");
        HousePurchase.instance.getConfig().addDefault("config.economy", "vault");
        HousePurchase.instance.getConfig().addDefault("config.discordLogs", "false");
        HousePurchase.instance.getConfig().addDefault("config.version", "1.3");
        HousePurchase.instance.getConfig().options().copyDefaults(true);
        HousePurchase.instance.saveConfig();
    }

    public void saveCustomConfig(FileConfiguration file, String str){
        try {file.save(HousePurchase.fileManager.getFile(str));}
        catch (IOException event) {event.printStackTrace();}
    }

    public void setCustomConfig(FileConfiguration file, String str, String path, Object value){
        file.set(path, value);
        saveCustomConfig(file, str);
    }

    public static String getVersion(){
        return HousePurchase.instance.getConfig().getString("config.version");
    }

    public static void initMethodOfStorage(){
        String methodOfStorage;
        String configMethodOfStorage = HousePurchase.instance.getConfig().getString("config.methodOfStorage");
        if(!configMethodOfStorage.equals("database") && !configMethodOfStorage.equals("file")){
            System.out.println("§4Erreur de l'initialisation de la méthode de stockage, veillez à bien avoir préciser file ou mysql dans la config du plugin.");
            HousePurchase.instance.getServer().shutdown();
            return;
        }
        methodOfStorage = configMethodOfStorage;
        if(methodOfStorage.equals("database")) {
            HousePurchase.databaseManager = new DatabaseManager();
            HousePurchase.instance.initAllTables();
        }
        HousePurchase.methodOfStorage = methodOfStorage;
    }

}
