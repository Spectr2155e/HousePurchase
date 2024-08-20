package fr.spectr2155e.housepurchase.listeners;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.managers.ConfigHouseManager;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGUINotOwner;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGUIOwnerBuy;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGuiNotOwnerUnRegistered;
import fr.spectr2155e.housepurchase.systems.crochetage.CrochetageGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ClickDoor implements Listener {

    @EventHandler
    public void onClickDoor(PlayerInteractEvent e){
        // Verification du block cliqué
        if(e.getClickedBlock() == null)  return;
        if (e.getClickedBlock().getType() == null) return;

        // Check si la porte est une porte normal ou dark_oak
        if (e.getClickedBlock().getType().equals(Material.WOODEN_DOOR) || e.getClickedBlock().getType().equals(Material.DARK_OAK_DOOR)) {

            // Récupérer la localisation de la porte
            Location location = e.getClickedBlock().getLocation();

            // Vérification de la demi portion de la porte
            if(HousesManager.checkHouse(e.getClickedBlock().getLocation())){

                // Récupérer la partie supérieur de la porte
                location.setY(location.getY()+1);
            }

            // Vérification de la disponibilité de la maison
            if(!HousesManager.isHouseExist(location)) {
                if(!e.getPlayer().isSneaking()) return;

                // Vérification de la permission du joueur
                if(!e.getPlayer().hasPermission("housepurchase.createHouse")) {return;}

                // Est ce que la porte est présente à la position.
                if(e.getClickedBlock().getLocation().add(0,1,0).getBlock().getType().equals(Material.WOODEN_DOOR)) {
                    location.setY(location.getY()+1);
                }

                // Appel du menu de création de maison pour un admin
                new HouseGuiNotOwnerUnRegistered().openInventoryWithLocation(e.getPlayer(), location);
                return;
            }

            if (((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).isOwned()) {
                if (e.getPlayer().isSneaking()) {
                    if (e.getPlayer().getItemInHand().getType().equals(Material.FEATHER)) {
                        if (e.getHand() == EquipmentSlot.HAND)
                            return;
                        if (((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).isLocked()) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage("§8§l(§4§lErreur§8§l) §ccette porte est verouillé");
                            if (((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).getOwner().equals(e.getPlayer().getName()) || DatabaseHouseManager.getArrayFromJson(((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).getTrustedPlayers()).contains(e.getPlayer().getName())) {
                                e.getPlayer().sendMessage("§8§l(§4§lErreur§8§l) §cVous ne pouvez pas crocheter votre maison ainsi que celle ou vous y etes ajouté.");
                                return;
                            }
                            (new CrochetageGUI()).openInventoryWithLocation(e.getPlayer(), location);
                            return;
                        }
                        (new HouseGUIOwnerBuy()).openInventoryWithLocation(e.getPlayer(), location);
                        e.setCancelled(true);
                    } else {
                        if (((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).isLocked()) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage("§8§l(§6§lHousePurchase§8§l) §ccette porte est verouille");
                        }
                        (new HouseGUIOwnerBuy()).openInventoryWithLocation(e.getPlayer(), location);
                        e.setCancelled(true);
                    }
                } else if (((Houses)Houses.houses.get(Integer.valueOf(Houses.getId(location)))).isLocked()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§8§l(§6§lHousePurchase§8§l) §ccette porte est verouille");
                }
                return;
            }
            if (e.getPlayer().isSneaking()) {
                (new HouseGUINotOwner()).openInventoryWithLocation(e.getPlayer(), location);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        // Boucle pour envoyer la date d'expiration de la maison
        switch (HousePurchase.methodOfStorage){
            case "file":
                for(Integer id : ConfigHouseManager.getListOfHouses()){
                    // Est ce que la maison a été loué
                    if(Houses.houses.get(id).isLease()){

                        // Is there a player who have the house
                        if(Houses.houses.get(id).getOwner().equals(e.getPlayer().getName())){
                            e.getPlayer().sendMessage("§8§l(§a§lGouvernement§8§l) §fLa location de votre bien sous l'id §e"+id+" §fexpire le §a"
                                    + HousePurchase.utils.timeStampToStringDate(Houses.houses.get(id).getLeaseDate()));
                        }
                    }
                }
                break;
            case "database":
                for(Integer id : Houses.housesList){
                    // Est ce que la maison a été loué
                    if(Houses.houses.get(id).isLease()){

                        // Is there a player who have the house
                        if(Houses.houses.get(id).getOwner().equals(e.getPlayer().getName())){
                            e.getPlayer().sendMessage("§8§l(§a§lGouvernement§8§l) §fLa location de votre bien sous l'id §e"+id+" §fexpire le §a"
                                    + HousePurchase.utils.timeStampToStringDate(Houses.houses.get(id).getLeaseDate()));
                        }
                    }
                }
        }
    }
}
