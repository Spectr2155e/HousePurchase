package fr.spectr2155e.housepurchase.commands.subcommands;

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
        return "Commande afin de supprimer une maison.";
    }

    @Override
    public String getUsage() {
        return "/housepurchase removeHouse <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length != 2){
            player.sendMessage("§8§l(§4§lERREUR§8§l) §cVeuillez utiliser la commande /housepurchase removeHouse <id>.");
            return;
        }
        final int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage("§8§l(§4§lERREUR§8§l) §cLa maison comportant cet id est introuvable.");
            return;
        }
        Houses.removeHouse(id);
        HouseRegion.removeRegion(id);
        player.sendMessage("§8§l(§6§lHousePurchase§8§l) §fLa maison sous l'id "+id+" a été supprimé.");
    }
}
