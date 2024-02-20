package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.entity.Player;

public class RemoveHouseCommand extends SubCommand {
    @Override
    public String getName() {
        return "removeHouse";
    }

    @Override
    public String getPermission() {
        return "house.removeHouse";
    }

    @Override
    public String getDescription() {
        return "§7Commande afin de supprimer une maison.";
    }

    @Override
    public String getUsage() {
        return "§6§l/housepurchase removeHouse §e§l<id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length != 2){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase removeHouse <id>.");
            return;
        }
        final int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage(HousePurchase.prefixError+"La maison comportant cet id est introuvable.");
            return;
        }
        Houses.removeHouse(id);
        HouseRegion.removeRegion(id);
        player.sendMessage(HousePurchase.prefixHousePurchase+"La maison sous l'id "+id+" a été supprimé.");
    }
}
