package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.commands.CommandManager;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager {
    public void createCommand(String str, Object command){
        HousePurchase.instance.getCommand(str).setExecutor((CommandExecutor) command);
    }
    public void initCommands(){
        createCommand("housepurchase", new CommandManager());
        HousePurchase.instance.getCommand("housepurchase").setTabCompleter(new CommandManager());
        List<String> list = new ArrayList<>();
        list.add("houses");
        list.add("house");
        HousePurchase.instance.getCommand("housepurchase").setAliases(list);
    }
}
