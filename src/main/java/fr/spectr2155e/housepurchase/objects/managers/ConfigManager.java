package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class ConfigManager {

    public void initConfig(){
        HousePurchase.instance.getConfig().addDefault("database.host", "localhost");
        HousePurchase.instance.getConfig().addDefault("database.user", "everloremc");
        HousePurchase.instance.getConfig().addDefault("database.password", "&741630rp&");
        HousePurchase.instance.getConfig().addDefault("database.dbName", "everlorerp");
        HousePurchase.instance.getConfig().addDefault("database.port", 3306);
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

}
