package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.entity.Player;

public class SetLeasePriceCommand extends SubCommand {
    @Override
    public String getName() {
        return "setLeasePrice";
    }

    @Override
    public String getPermission() {
        return "house.setLeasePrice";
    }

    @Override
    public String getDescription() {
        return "Commande afin de changer le prix de location de la maison";
    }

    @Override
    public String getUsage() {
        return "/housepurchase setBuyLease <prix>/j <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length == 3)){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase setBuyLease <prix>/j <id>.");
            return;
        }
        final int id = Integer.parseInt(args[2]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage(HousePurchase.prefixError+"La maison comportant cet id est introuvable.");
            return;
        }
        if(!HousePurchase.utils.isInt(args[1])){
            player.sendMessage(HousePurchase.prefixError+"Veuillez préciser un prix correct (Il faut qu'il soit un nombre valide).");
            return;
        }
        final int price = Integer.parseInt(args[1]);
        Houses.houses.get(id).setPriceOfLease(price);
        player.sendMessage(HousePurchase.prefixHousePurchase+"Le prix de la maison à la location sous l'id §e"+id+" §fest maintenant de §a"+price);
    }
}
