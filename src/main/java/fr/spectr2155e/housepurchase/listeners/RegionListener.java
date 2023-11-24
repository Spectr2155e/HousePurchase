package fr.spectr2155e.housepurchase.listeners;


import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.region.events.RegionBlockBreakEvent;
import fr.spectr2155e.housepurchase.region.events.RegionBlockPlaceEvent;
import fr.spectr2155e.housepurchase.region.events.RegionPlayerEnterEvent;
import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RegionListener implements Listener {
    @EventHandler
    public void onRegionEnter(RegionPlayerEnterEvent e){
        if(e.getRegion().getRegionName().equalsIgnoreCase("test")){
            e.getPlayer().sendMessage("Vous entrez dans une maison");
        }
    }

    @EventHandler
    public void onBlockBreak(RegionBlockBreakEvent e){
        if(HouseRegion.getHouse(e.getRegion()).getOwner() == null){
            e.setCancelled(true);
            return;
        }
        if(HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())){
            Bukkit.broadcastMessage("AHAHHAHAHA");
        } else {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(RegionBlockPlaceEvent e){
        if(HouseRegion.getHouse(e.getRegion()).getOwner() == null){
            e.setCancelled(true);
            return;
        }
        if(HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())){
            Bukkit.broadcastMessage("AHAHHAHAHA");
        } else {
            e.setCancelled(true);
        }
    }
}
