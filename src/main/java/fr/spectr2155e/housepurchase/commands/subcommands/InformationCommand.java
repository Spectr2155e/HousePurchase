package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class InformationCommand extends SubCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getPermission() {
        return "house.information";
    }

    @Override
    public String getDescription() {
        return "Commande afin de voir les informations d'une maison";
    }

    @Override
    public String getUsage() {
        return "/housepurchase info <id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length != 2){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase info <id>.");
            return;
        }
        final int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage(HousePurchase.prefixError+"La maison comportant cet id est introuvable.");
            return;
        }

        // Initialisation House
        final Houses house = Houses.houses.get(id);

        // Owner
        final String owner;
        if(house.isOwned()){ owner = house.getOwner(); }
        else { owner = "§cAucun"; }

        // DateOfBuy
        final String dateOfBuy;
        if(house.isBuy()){ dateOfBuy = house.getDateOfBuy().toString(); }
        else { dateOfBuy = "§cPas acheté"; }

        // LeaseDate
        final String leaseDate;
        if(house.isLease()){ leaseDate = house.getLeaseDate().toString(); }
        else { leaseDate = "§cPas loué"; }

        final int priceOfBuy = house.getPriceOfBuy();
        final int priceOfLease = house.getPriceOfLease();
        String friends = house.getTrustedPlayers();
        if(friends == null){
            friends = "§cPas d'ami";
        } else {
            friends = house.getTrustedPlayers();
        }

        player.sendMessage("§7§lID: §b"+ id);
        player.sendMessage("§7§lPropriétaire: §e"+ owner);
        player.sendMessage("§7§lDate d'achat: §3"+ dateOfBuy);
        player.sendMessage("§7§lDate de location: §3"+ leaseDate);
        player.sendMessage("§7§lPrix d'achat: §a"+ priceOfBuy);
        player.sendMessage("§7§lPrix de location: §a"+ priceOfLease);
        player.sendMessage("§7§lAmis: §a"+ friends);
    }
}
