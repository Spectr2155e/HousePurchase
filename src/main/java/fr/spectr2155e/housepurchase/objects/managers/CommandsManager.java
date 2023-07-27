package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import org.bukkit.command.CommandExecutor;

public class CommandsManager {
    public void createCommand(String str, Object command){
        HousePurchase.instance.getCommand(str).setExecutor((CommandExecutor) command);
    }
    public void initCommands(){
    }
}
