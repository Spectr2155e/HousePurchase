package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
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
            inventory.setItem(38, HousePurchase.utils.getItem(Material.REDSTONE, "§c§lRetour", 0, "§7Cliquez afin de quitter."));
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
        if(player.hasPermission("houses.remove")) inventory.setItem(44, HousePurchase.utils.getItem(Material.BARRIER, "§c§lSupprimer la maison", 0, "§7Cliquez afin de supprimer cette maison."));
        inventory.setItem(20, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lPrix de la maison", 0, "§7La maison coûte §a"+ HousePurchase.numberFormat.format(Houses.houses.get(Houses.getId(location)).getPriceOfBuy())+"€"));
        inventory.setItem(24, HousePurchase.utils.getItem(Material.EMPTY_MAP, "§7§lQuartier", 0)); // Quartier
        inventory.setItem(40, HousePurchase.utils.getItem(Material.NAME_TAG, "§fID: §e"+Houses.getId(location), 0));

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
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRetour")){
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVérouiller/Déverouiller la porte")){
            if(!Houses.houses.get(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked()))).getOwner().equals(e.getWhoClicked().getName())) {
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous n'êtes pas le prioritaire de cette maison.");
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
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
            Houses.removeHouse(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            HouseRegion.removeRegion(Houses.getId(locationOfDoor.get((Player) e.getWhoClicked())));
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez supprimé la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }
}
