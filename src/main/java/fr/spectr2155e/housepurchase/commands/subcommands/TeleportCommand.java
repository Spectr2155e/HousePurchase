package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand extends SubCommand {
    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public String getPermission() {
        return "house.teleport";
    }

    @Override
    public String getDescription() {
        return "§7Commande afin de se téléporter à une maison avec l'id.";
    }

    @Override
    public String getUsage() {
        return "§6§l/housepurchase teleport §e§l<id>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length == 2)){
            player.sendMessage(HousePurchase.prefixError+"Veuillez utiliser la commande /housepurchase teleport <id>.");
            return;
        }
        final int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage(HousePurchase.prefixError+"La maison comportant cet id est introuvable.");
            return;
        }
        Location location = Utils.getJSONToLocation(Houses.houses.get(id).getLocation());
        location.setWorld(Bukkit.getWorld("world"));
        location.setY(location.getY() - 1);
        player.teleport(location);
        player.sendMessage(HousePurchase.prefixHousePurchase+"Vous avez été téléporté(e) à la maison avec l'id: §e"+id);
    }
}
