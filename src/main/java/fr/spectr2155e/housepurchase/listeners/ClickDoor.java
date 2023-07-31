package fr.spectr2155e.housepurchase.listeners;

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

public class ClickDoor implements Listener {

    @EventHandler
    public void onClickDoor(PlayerInteractEvent e){
        if(e.getClickedBlock() == null)  return;
        if (e.getClickedBlock().getType() == null) return;
        if (e.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
            Location location = e.getClickedBlock().getLocation();
            if(HousesManager.checkHouse(e.getClickedBlock().getLocation())){
                location.setY(location.getY()+1);
            }
            if(!HousesManager.isHouseExist(location)) {
                if(!e.getPlayer().isSneaking()) return;
                if(!e.getPlayer().hasPermission("house.createHouse")) {return;}
                new HouseGuiNotOwnerUnRegistered().openInventoryWithLocation(e.getPlayer(), location);
                return;
            }
            if(Houses.houses.get(Houses.getId(location)).isOwned()){
                if(Houses.houses.get(Houses.getId(location)).isLocked()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cCette porte est verouillé");
                }
                if(e.getPlayer().isSneaking()) {
                    new HouseGUIOwnerBuy().openInventoryWithLocation(e.getPlayer(), location);
                    e.setCancelled(true);
                }
                return;
            }
            if(e.getPlayer().isSneaking()) {
                new HouseGUINotOwner().openInventoryWithLocation(e.getPlayer(), location);
                e.setCancelled(true);
            }
        }
    }
}
