package fr.spectr2155e.housepurchase.listeners;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGUINotOwner;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGUIOwnerBuy;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.HouseGuiNotOwnerUnRegistered;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
                if(!e.getPlayer().hasPermission("house.createHouse")) {return;}

                // Est ce que la porte est présente à la position.
                if(e.getClickedBlock().getLocation().add(0,1,0).getBlock().getType().equals(Material.WOODEN_DOOR)) {
                    location.setY(location.getY()+1);
                }

                // Appel du menu de création de maison pour un admin
                new HouseGuiNotOwnerUnRegistered().openInventoryWithLocation(e.getPlayer(), location);
                return;
            }

            // Vérification si la maison est priorisé par une personne
            if(Houses.houses.get(Houses.getId(location)).isOwned()){

                // Vérification si la porte est vérouillé
                if(Houses.houses.get(Houses.getId(location)).isLocked()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cCette porte est verouillé");
                }
                if(e.getPlayer().isSneaking()) {

                    // Ouverture du menu de Maison acheté
                    new HouseGUIOwnerBuy().openInventoryWithLocation(e.getPlayer(), location);
                    e.setCancelled(true);
                }
                return;
            }

            // Si la maison n'est pas priorisé et qu'elle a été créé
            if(e.getPlayer().isSneaking()) {

                // Ouverture du menu d'achat de maison
                new HouseGUINotOwner().openInventoryWithLocation(e.getPlayer(), location);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        // Boucle pour envoyer la date d'expiration de la maison
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
