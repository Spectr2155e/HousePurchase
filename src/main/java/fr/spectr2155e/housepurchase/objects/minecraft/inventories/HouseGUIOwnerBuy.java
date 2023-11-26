package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.economy.managers.EconomyManager;
import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class HouseGUIOwnerBuy implements CommonInventory, Listener {

    Inventory inventory;
    private static HashMap<Player, Location> locationOfDoor = new HashMap<>();

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Maison - Achat");
        for(int i = 0; i < 45; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));
            }
            if((i > 0 && i < 8) || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));
            }
            inventory.setItem(20, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lPrix de la maison", 0));
            inventory.setItem(22, HousePurchase.utils.getItem(Material.WOOD_DOOR, "§6§lMaison", 0));
            inventory.setItem(24, HousePurchase.utils.getItem(Material.EMPTY_MAP, "§7§lQuartier", 0));
            inventory.setItem(38, HousePurchase.utils.getItem(Material.SKULL_ITEM, "§7§lMembres", 0, "§7Cliquez afin d'ajouter une personne de confiance."));
            inventory.setItem(40, HousePurchase.utils.getItem(Material.NAME_TAG, "§fID", 0));
            inventory.setItem(42, HousePurchase.utils.getItem(Material.TRIPWIRE_HOOK, "§eVérouiller/Déverouiller la porte", 0, "§7Cliquez afin de verrouiller", "§7ou déverouiller la porte."));
        }
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUIOwnerBuy")) {
            createGui();
            HousePurchase.inventories.put("HouseGUIOwnerBuy", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45, "Maison - Achat");
        inventory.setContents(HousePurchase.inventories.get("HouseGUIOwnerBuy"));
        inventory.setItem(40, HousePurchase.utils.getItem(Material.NAME_TAG, "§fID: §e"+Houses.getId(location), 0));
        if(player.hasPermission("houses.remove")) inventory.setItem(44, HousePurchase.utils.getItem(Material.BARRIER, "§c§lSupprimer la maison", 0, "§7Cliquez afin de supprimer cette maison."));
        inventory.setItem(20, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lPrix de la maison", 0, "§7La maison coûte §a"+ HousePurchase.numberFormat.format(Houses.houses.get(Houses.getId(location)).getPriceOfBuy())+"€"));
        inventory.setItem(24, HousePurchase.utils.getItem(Material.EMPTY_MAP, "§7§lQuartier", 0)); // Quartier
        inventory.setItem(38, HousePurchase.utils.getItem(Material.SKULL_ITEM, "§7§lMembres", 0, "§7Cliquez afin d'ajouter une personne de confiance."));
        if(Houses.houses.get(Houses.getId(location)).getOwner().equals(player.getName())){
            if(Houses.houses.get(Houses.getId(location)).isLease()){
                inventory.setItem(36, HousePurchase.utils.getItem(Material.INK_SACK, "§c§lArrêter la location", 1, "§7Cette action entrainera un arrêt de la location sans remboursement."));
            }
            if(Houses.houses.get(Houses.getId(location)).isBuy()){
                inventory.setItem(36, HousePurchase.utils.getItem(Material.INK_SACK, "§c§lVendre la maison", 1, "§7Cette action entrainera un remboursement de 70% de l'achat de la maison."));
            }
        }
        if(Houses.houses.get(Houses.getId(location)).getTrustedPlayers() == null && !Houses.houses.get(Houses.getId(location)).getOwner().equals(player.getName())){
            inventory.setItem(38, HousePurchase.utils.getItem(Material.REDSTONE, "§c§lRetour", 0, "§7Cliquez afin de quitter ce menu."));
            player.openInventory(inventory);
            locationOfDoor.put(player, location);
            return;
        }
        if(Houses.houses.get(Houses.getId(location)).getOwner().equals(player.getName())){
            player.openInventory(inventory);
            locationOfDoor.put(player, location);
            return;
        }
        if(!DatabaseHouseManager.getArrayFromJson(Houses.houses.get(Houses.getId(location)).getTrustedPlayers()).contains(player.getName()) && !Houses.houses.get(Houses.getId(location)).getOwner().equals(player.getName())) {
            inventory.setItem(38, HousePurchase.utils.getItem(Material.REDSTONE, "§c§lRetour", 0, "§7Cliquez afin de quitter ce menu."));
            player.openInventory(inventory);
            locationOfDoor.put(player, location);
            return;
        }
        if(Houses.houses.get(Houses.getId(location)).getTrustedPlayers() != null && !Houses.houses.get(Houses.getId(location)).getOwner().equals(player.getName())){
            if(!DatabaseHouseManager.getArrayFromJson(Houses.houses.get(Houses.getId(location)).getTrustedPlayers()).contains(player.getName())) {
                inventory.setItem(38, HousePurchase.utils.getItem(Material.REDSTONE, "§c§lRetour", 0, "§7Cliquez afin de quitter ce menu."));
                player.openInventory(inventory);
                locationOfDoor.put(player, location);
                return;
            }
        }

        locationOfDoor.put(player, location);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Maison - Achat")) return;
        if(!e.getCurrentItem().getType().equals(Material.REDSTONE) && !e.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK) && !e.getCurrentItem().getType().equals(Material.BARRIER)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§7§lMembres")){
            e.setCancelled(true);
            new HouseGUITrustedUsers().openInventoryWithLocation((Player) e.getWhoClicked(), locationOfDoor.get((Player) e.getWhoClicked()));
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRetour")){
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVérouiller/Déverouiller la porte")){
            if(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getTrustedPlayers() == null && !Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getOwner().equals(e.getWhoClicked().getName())){
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous n'avez pas la permission d'ouvrir ou de fermer cette maison.");
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getOwner().equals(e.getWhoClicked().getName())){
                if(!Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).isLocked()) {
                    e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez §cverouillé §fvotre porte.");
                    Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).setLocked(true);
                    e.getWhoClicked().closeInventory();
                    e.setCancelled(true);
                    return;
                }
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez §adéverrouillé §fvotre porte.");
                Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).setLocked(false);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }
            if(!DatabaseHouseManager.getArrayFromJson(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getTrustedPlayers()).contains(e.getWhoClicked().getName()) && !Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getOwner().equals(e.getWhoClicked().getName())) {
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous n'avez pas la permission d'ouvrir ou de fermer cette maison.");
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            if(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getTrustedPlayers() != null){
                if(!DatabaseHouseManager.getArrayFromJson(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getTrustedPlayers()).contains(e.getWhoClicked().getName()) && !Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getOwner().equals(e.getWhoClicked().getName())) {
                    e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous n'avez pas la permission d'ouvrir ou de fermer cette maison.");
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                    return;
                }
            }
            if(!Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).isLocked()) {
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez §cverouillé §fvotre porte.");
                Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).setLocked(true);
                e.getWhoClicked().closeInventory();
                e.setCancelled(true);
                return;
            }
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez §adéverrouillé §fvotre porte.");
            Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).setLocked(false);
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lSupprimer la maison")){
            HouseRegion.removeRegion(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            Houses.removeHouse(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez supprimé la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lVendre la maison")){
            EconomyManager.addBankMoney(e.getWhoClicked().getName(), String.valueOf(Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getPriceOfBuy()*70/100), null);
            HousesManager.unLeaseHouse(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez vendu votre maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lArrêter la location")){
            HousesManager.unLeaseHouse(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez arrêter la location de la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }
}
