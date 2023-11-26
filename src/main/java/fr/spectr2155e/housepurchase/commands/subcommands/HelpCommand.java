package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.commands.SubCommand;
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
        player.sendMessage(usageInfo + ": " + descInfo);
        player.sendMessage(usageRemoveHouse + ": " + descRemoveHouse);
        player.sendMessage(usageRemoveRegion + ": " + descRemoveRegion);
        player.sendMessage(usageSetBuyPrice + ": " + descSetBuyPrice);
        player.sendMessage(usageSetLeasePrice + ": " + descSetLeasePrice);
        player.sendMessage(usageTeleport + ": " + descTeleport);
    }
}
