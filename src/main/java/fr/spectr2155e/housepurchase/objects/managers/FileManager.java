package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public void createFile(String Filename){
        if(!HousePurchase.instance.getDataFolder().exists()){HousePurchase.instance.getDataFolder().mkdir();}
        File file = new File(HousePurchase.instance.getDataFolder(), Filename + ".yml");
        if(!file.exists()){
            try {file.createNewFile();}
            catch (IOException e){e.printStackTrace();}
        }
    }
    public File getFile(String fileName){return new File(HousePurchase.instance.getDataFolder(), fileName + ".yml");}
    public void saveConfig(FileConfiguration file, String str){
        try {file.save(getFile(str));}
        catch (IOException e) {e.printStackTrace();}
    }
    public void initFiles(){
        createFile("houses");
    }

}
