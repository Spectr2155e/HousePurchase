package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.BuyHouse;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.managers.EconomyHouseManager;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

public class HouseGUINotOwner implements CommonInventory, Listener {

    Inventory inventory;

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Maison - Achat/Location");
        for(int i = 0; i < 45; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));
            }
            if(i == 1 || i == 7 || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));
            }
        }
        inventory.setItem(21, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lAcheter la maison", 0, "§7En cliquant ici, vous deviendrez", "§7propriétaire de cette maison à vie"));
        inventory.setItem(23, HousePurchase.utils.getItem(Material.FEATHER, "§6§lLouer la maison", 0, "§7En cliquant ici, vous deviendrez", "§7locataire de cette maison à une durée limitée"));
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUINotOwner")) {
            createGui();
            HousePurchase.inventories.put("HouseGUINotOwner", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45,"Maison - Achat/Location");
        inventory.setContents(HousePurchase.inventories.get("HouseGUINotOwner"));
        if(player.hasPermission("housepurchase.remove")) {
            inventory.setItem(44, HousePurchase.utils.getItem(Material.BARRIER, "§c§lSupprimer la maison", 0, "§7Cliquez afin de supprimer cette maison."));
            if(!HouseRegion.regions.containsKey(Houses.getId(location))) {inventory.setItem(35, HousePurchase.utils.getItem(Material.FEATHER, "§6§lCréer la region", 0, "§7Cliquez afin de créer la region", "§7de cette maison."));}
            else {
                inventory.setItem(35, HousePurchase.utils.getItem(Material.BARRIER, "§c§lSupprimer la region", 0, "§7Cliquez afin de supprimer la region", "§7de cette maison."));
                inventory.setItem(26, HousePurchase.utils.getItem(Material.FEATHER, "§6§lAjouter une region", 0, "§7Cliquez afin d'ajouter une region", "§7à cette maison."));
            }
        }
        inventory.setItem(21, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lAcheter la maison", 0, "§7En cliquant ici, vous deviendrez", "§7propriétaire de cette maison à vie", "",
                "§7Prix: §a"+ HousePurchase.numberFormat.format(Houses.houses.get(Houses.getId(location)).getPriceOfBuy())+"€"));
        inventory.setItem(23, HousePurchase.utils.getItem(Material.FEATHER, "§6§lLouer la maison", 0, "§7En cliquant ici, vous deviendrez", "§7locataire de cette maison à une durée limitée", "",
                "§7Prix: §a"+ HousePurchase.numberFormat.format(Houses.houses.get(Houses.getId(location)).getPriceOfLease())+"€§7/jour"));
        inventory.setItem(40, HousePurchase.utils.getItem(Material.NAME_TAG, "§7Id: §e"+Houses.getId(location), 0));
        BuyHouse.buyHouse.put(player, new BuyHouse(player, Houses.getId(location), Houses.houses.get(Houses.getId(location)).getPriceOfBuy(), Houses.houses.get(Houses.getId(location)).getPriceOfLease(), location));
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) throws IOException {
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Maison - Achat/Location")) return;
        if(!e.getCurrentItem().getType().equals(Material.FEATHER) && !e.getCurrentItem().getType().equals(Material.SLIME_BALL) && !e.getCurrentItem().getType().equals(Material.BARRIER)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lAcheter la maison")){
            if(!EconomyHouseManager.hasEnoughMoney((Player) e.getWhoClicked(), BuyHouse.buyHouse.get(e.getWhoClicked()).getPriceOfBuy())){
                e.getWhoClicked().sendMessage("§8§l[§4§lErreur§8§l] §cVous n'avez pas l'argent nécessaire afin d'acheter cette maison.");
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez acheté la maison §e"+BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId()+"§f, §aFélicitations §f!");
            EconomyHouseManager.withdrawMoney((Player) e.getWhoClicked(), BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getPriceOfBuy());
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            HousesManager.buyHouse((Player) e.getWhoClicked(), BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId());
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lLouer la maison")){
            e.setCancelled(true);
            new HouseGUILeaseSelector().openInventoryWithLocation((Player) e.getWhoClicked(), BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getLocation());
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lSupprimer la maison")){
            Houses.removeHouse(BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId(), (Player) e.getWhoClicked());
            HouseRegion.removeRegion(BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId());
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez supprimé la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lCréer la region")){
            new HouseGUICreationRegion().openInventoryWithLocation((Player) e.getWhoClicked(), BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getLocation());
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lSupprimer la region")){
            HouseRegion.removeRegion(BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId());
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez supprimé la region de la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lAjouter une region")){
            new HouseGUICreationRegion().openInventoryWithLocation((Player) e.getWhoClicked(), BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getLocation());
            HouseRegion.registerRegion((Player) e.getWhoClicked(), "name", null, HouseRegion.regions.get(BuyHouse.buyHouse.get((Player) e.getWhoClicked()).getId()).getName());
            e.setCancelled(true);
        }
    }
}
