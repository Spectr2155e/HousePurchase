package fr.spectr2155e.housepurchase.listeners;


import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.region.events.RegionBlockBreakEvent;
import fr.spectr2155e.housepurchase.region.events.RegionBlockPlaceEvent;
import fr.spectr2155e.housepurchase.region.events.RegionInteractEvent;
import fr.spectr2155e.housepurchase.region.events.RegionPlayerEnterEvent;
import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// Cette classe implémente l'interface Listener, ce qui lui permet d'écouter et de répondre aux événements du jeu.
public class RegionListener implements Listener {

    @EventHandler
    public void onBlockBreak(RegionBlockBreakEvent e) {
        if(e.getPlayer().hasPermission("housepurchase.op")){
            return;
        }
        //if (((HouseRegion.getHouse(e.getRegion()).getOwner() != null && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()))
        //        && (HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() != null && !DatabaseHouseManager.getArrayFromJson(HouseRegion.getHouse(e.getRegion()).getTrustedPlayers()).contains(e.getPlayer().getName()))
        //        || HouseRegion.getHouse(e.getRegion()).getOwner() == null && HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null)
        //        || HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()))
        //    e.setCancelled(true);
        if(((HouseRegion.getHouse(e.getRegion()).getOwner() != null && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()))
        && (HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() != null && !DatabaseHouseManager.getArrayFromJson(HouseRegion.getHouse(e.getRegion()).getTrustedPlayers()).contains(e.getPlayer().getName()) && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())))
        || HouseRegion.getHouse(e.getRegion()).getOwner() == null && HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(RegionBlockPlaceEvent e) {
        if(e.getPlayer().hasPermission("housepurchase.op")){
            return;
        }
        if((HouseRegion.getHouse(e.getRegion()).getOwner() != null && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()) && (HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() != null && !DatabaseHouseManager.getArrayFromJson(HouseRegion.getHouse(e.getRegion()).getTrustedPlayers()).contains(e.getPlayer().getName())) || HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null)
                || (HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() != null && !DatabaseHouseManager.getArrayFromJson(HouseRegion.getHouse(e.getRegion()).getTrustedPlayers()).contains(e.getPlayer().getName()) && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()))
                || HouseRegion.getHouse(e.getRegion()).getOwner() == null && HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteract(RegionInteractEvent e) {
        if(e.getPlayer().hasPermission("housepurchase.op")){
            return;
        }
        if(((HouseRegion.getHouse(e.getRegion()).getOwner() != null && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName()))
                && (HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() != null && !DatabaseHouseManager.getArrayFromJson(HouseRegion.getHouse(e.getRegion()).getTrustedPlayers()).contains(e.getPlayer().getName()) && !HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())))
                || HouseRegion.getHouse(e.getRegion()).getOwner() == null && HouseRegion.getHouse(e.getRegion()).getTrustedPlayers() == null){
            e.setCancelled(true);
        }
    }
}
