package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.entity.Player;

public class RemoveRegionCommand extends SubCommand {
    @Override
    public String getName() {
        return "removeRegion";
    }

    @Override
    public String getPermission() {
        return "house.removeRegion";
    }

    @Override
    public String getDescription() {
        return "Commande afin de supprimer une region.";
    }

    @Override
    public String getUsage() {
        return "/housepurchase removeRegion <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length == 2)){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase removeRegion <id>.");
            return;
        }
        final int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage(HousePurchase.prefixError+"La maison comportant cet id est introuvable.");
            return;
        }
        HouseRegion.removeRegion(id);
        player.sendMessage(HousePurchase.prefixHousePurchase+"La region de la maison sous l'id "+id+" a été supprimé.");
    }
}
