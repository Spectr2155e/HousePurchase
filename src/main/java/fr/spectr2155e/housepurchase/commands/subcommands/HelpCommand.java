package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "house.help";
    }

    @Override
    public String getDescription() {
        return "Commande afin de voir toutes les commande lié au plugin HousePurchase";
    }

    @Override
    public String getUsage() {
        return "/housepurchase help";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase help.");
            return;
        }

        // Information
        final String descInfo = new InformationCommand().getDescription();
        final String usageInfo = new InformationCommand().getUsage();

        // RemoveHouse
        final String descRemoveHouse = new RemoveHouseCommand().getDescription();
        final String usageRemoveHouse = new RemoveHouseCommand().getUsage();

        // RemoveRegion
        final String descRemoveRegion = new RemoveRegionCommand().getDescription();
        final String usageRemoveRegion = new RemoveRegionCommand().getUsage();

        // SetBuyPrice
        final String descSetBuyPrice = new SetBuyPriceCommand().getDescription();
        final String usageSetBuyPrice = new SetBuyPriceCommand().getUsage();

        // SetLeasePrice
        final String descSetLeasePrice = new SetLeasePriceCommand().getDescription();
        final String usageSetLeasePrice = new SetLeasePriceCommand().getUsage();

        // Teleport
        final String descTeleport = new TeleportCommand().getDescription();
        final String usageTeleport = new TeleportCommand().getUsage();


        // Message
        player.sendMessage("§7§m|-------------§7| §6§lHousePurchase §7§m|-------------§7|");
        player.sendMessage("");
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageInfo, usageInfo+" : "+descInfo));
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageRemoveHouse, usageRemoveHouse+" : "+descRemoveHouse));
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageRemoveRegion, usageRemoveRegion+" : "+descRemoveRegion));
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageSetBuyPrice, usageSetBuyPrice+" : "+descSetBuyPrice));
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageSetLeasePrice, usageSetLeasePrice+" : "+descSetLeasePrice));
        player.spigot().sendMessage(Utils.createClickableCommand("§7Cliquez pour afficher la commande", usageTeleport, usageTeleport+" : "+descTeleport));
    }
}
