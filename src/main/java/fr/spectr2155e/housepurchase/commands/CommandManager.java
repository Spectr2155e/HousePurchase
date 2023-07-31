package fr.spectr2155e.housepurchase.commands;

import fr.spectr2155e.housepurchase.commands.subcommands.*;
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
            for (SubCommand subCommand : getSubCommands()) {
                completionResult.add(subCommand.getName());
            }
            return completionResult;
        }
        return null;
    }
}
