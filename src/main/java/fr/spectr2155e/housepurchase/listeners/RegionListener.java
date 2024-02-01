package fr.spectr2155e.housepurchase.listeners;


import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.region.events.RegionBlockBreakEvent;
import fr.spectr2155e.housepurchase.region.events.RegionBlockPlaceEvent;
import fr.spectr2155e.housepurchase.region.events.RegionPlayerEnterEvent;
import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// Cette classe implémente l'interface Listener, ce qui lui permet d'écouter et de répondre aux événements du jeu.
public class RegionListener implements Listener {

    // Cette méthode est un gestionnaire d'événements pour RegionPlayerEnterEvent, qui est déclenché lorsqu'un joueur entre dans une région.
    @EventHandler
    public void onRegionEnter(RegionPlayerEnterEvent e) {
        // Vérifie si le joueur est entré dans la région "test".
        if (e.getRegion().getRegionName().equalsIgnoreCase("test")) {
            // Envoie un message au joueur indiquant qu'il est entré dans une maison.
            e.getPlayer().sendMessage("Vous entrez dans une maison");
        }
    }

    // Cette méthode est un gestionnaire d'événements pour RegionBlockBreakEvent, qui est déclenché lorsqu'un joueur casse un bloc dans une région.
    @EventHandler
    public void onBlockBreak(RegionBlockBreakEvent e) {
        // Vérifie si la région dans laquelle le bloc est cassé a un propriétaire.
        if (HouseRegion.getHouse(e.getRegion()).getOwner() == null) {
            // Si la région n'a pas de propriétaire, annule l'événement pour empêcher la casse du bloc.
            e.setCancelled(true);
            return;
        }

        // Vérifie si le joueur qui casse le bloc est le propriétaire de la région.
        if (HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())) {
            // Si le joueur est le propriétaire, diffuse un message indiquant qu'il a cassé un bloc dans sa propre maison.
            Bukkit.broadcastMessage("AHAHHAHAHA");
        } else {
            // Si le joueur n'est pas le propriétaire, annule l'événement pour l'empêcher de casser le bloc.
            e.setCancelled(true);
        }
    }

    // Cette méthode est un gestionnaire d'événements pour RegionBlockPlaceEvent, qui est déclenché lorsqu'un joueur place un bloc dans une région.
    @EventHandler
    public void onBlockPlace(RegionBlockPlaceEvent e) {
        // Vérifie si la région dans laquelle le bloc est placé a un propriétaire.
        if (HouseRegion.getHouse(e.getRegion()).getOwner() == null) {
            // Si la région n'a pas de propriétaire, annule l'événement pour empêcher le placement du bloc.
            e.setCancelled(true);
            return;
        }

        // Vérifie si le joueur qui place le bloc est le propriétaire de la région.
        if (HouseRegion.getHouse(e.getRegion()).getOwner().equals(e.getPlayer().getName())) {
            // Si le joueur est le propriétaire, diffuse un message indiquant qu'il a placé un bloc dans sa propre maison.
            Bukkit.broadcastMessage("AHAHHAHAHA");
        } else {
            // Si le joueur n'est pas le propriétaire, annule l'événement pour l'empêcher de placer le bloc.
            e.setCancelled(true);
        }
    }
}
