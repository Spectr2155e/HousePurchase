package fr.spectr2155e.housepurchase.commands.subcommands;

import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.commands.SubCommand;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportHouse extends SubCommand {
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
        return "Commande afin de se téléporter à une maison avec l'id.";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length >= 2)){
            player.sendMessage("§8§l(§4§lERREUR§8§l) §cVeuillez utiliser la commande /housepurchase teleport <id>.");
            return;
        }
        int id = Integer.parseInt(args[1]);
        if(!HousesManager.isHouseExist(id)){
            player.sendMessage("§8§l(§4§lERREUR§8§l) §cLa maison comportant cet id n'est pas trouvable.");
            return;
        }
        Location location = Utils.getJSONToLocation(Houses.houses.get(id).getLocation());
        location.setWorld(Bukkit.getWorld("world"));
        location.setY(location.getY() - 1);
        player.teleport(location);
        player.sendMessage("§8§l(§6§lHousePurchase§8§l) §fVous avez été téléporter à la maison avec l'id: §e"+id);
    }
}
