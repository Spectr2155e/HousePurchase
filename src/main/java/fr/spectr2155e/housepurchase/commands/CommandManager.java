package fr.spectr2155e.housepurchase.commands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.subcommands.*;
import fr.spectr2155e.housepurchase.managers.ConfigHouseManager;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(){
        subCommands.add(new TeleportCommand());
        subCommands.add(new InformationCommand());
        subCommands.add(new SetBuyPriceCommand());
        subCommands.add(new SetLeasePriceCommand());
        subCommands.add(new RemoveHouseCommand());
        subCommands.add(new RemoveRegionCommand());
        subCommands.add(new HelpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!(args.length > 0)){
                sender.sendMessage("§8§l(§4§lERREUR§8§l) §cVeuillez utiliser la commande /housepurchase help");
                return false;
            }
            for (SubCommand subCommand : getSubCommands()) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.perform(p, args);
                }
            }
        }
        return false;
    }

    public List<SubCommand> getSubCommands(){
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equals("houses") || command.getName().equals("house") || command.getName().equals("housepurchase")) {
            List<String> completionResult = new ArrayList<>();
            List<String> result = new ArrayList<>();
            if(args.length == 1) {
                for (SubCommand subCommand : getSubCommands()) {
                    completionResult.add(subCommand.getName());
                }
                for (String str : completionResult) {
                    if (str.toLowerCase().startsWith(args[0].toLowerCase())) {
                        result.add(str);
                    }
                }
            } else if(args.length >= 2){
                if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("removeHouse") || args[0].equalsIgnoreCase("removeRegion") || args[0].equalsIgnoreCase("teleport") || (args[0].equalsIgnoreCase("setBuyprice") && args.length >= 3) || (args[0].equalsIgnoreCase("setLeasePrice") && args.length >= 3)){
                    if(HousePurchase.methodOfStorage.equals("file")) {
                        for (Integer id : ConfigHouseManager.getListOfHouses()) {
                            completionResult.add(String.valueOf(id));
                        }
                    } else {
                        for (Integer id : Houses.housesList) {
                            if (id.toString().toLowerCase().startsWith(args[1].toLowerCase())) {
                                completionResult.add(String.valueOf(id));
                            } else {
                                return null;
                            }
                        }
                    }
                    for (String str : completionResult) {
                        if (str.toLowerCase().startsWith(args[1].toLowerCase()) || str.toLowerCase().startsWith(args[2].toLowerCase())) {
                            result.add(str);
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }
}
