package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.economy.classes.EconomyClass;
import fr.spectr2155e.economy.managers.EconomyManager;
import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.BuyHouse;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import fr.spectr2155e.housepurchase.managers.EconomyHouseManager;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class HouseGUILeaseSelector implements CommonInventory, Listener {

    Inventory inventory;
    private static HashMap<Player, Location> locationOfDoor = new HashMap<>();

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Maison - Location");
        for(int i = 0; i < 45; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));}
            if(i == 1 || i == 7 || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43){inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));}
        }
        inventory.setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer", 0));
        inventory.setItem(21, HousePurchase.utils.getItem(Material.WOOD_BUTTON, "§c§lRetirer", 0, "§7▶ Cliquez pour enlever un jour", "§7▶Shift-Click pour enlever 7 jours"));
        inventory.setItem(23, HousePurchase.utils.getItem(Material.WOOD_BUTTON, "§a§lAjouter", 0, "§7▶ Cliquez pour ajouter un jour", "§7▶Shift-Click pour ajouter 7 jours"));
        inventory.setItem(31, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lConfirmer l'achat", 0, "§7Cliquez afin de louer la maison"));
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUILeaseSelector")) {
            createGui();
            HousePurchase.inventories.put("HouseGUILeaseSelector", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45, "Maison - Location");
        inventory.setContents(HousePurchase.inventories.get("HouseGUILeaseSelector"));
        if(player.hasPermission("houses.remove")) inventory.setItem(44, HousePurchase.utils.getItem(Material.BARRIER, "§c§lSupprimer la maison", 0, "§7Cliquez afin de supprimer cette maison."));
        LeaseHouse.leaseHouse.put(player, new LeaseHouse(player, Houses.getId(location),
                Houses.houses.get(Houses.getId(location)).getPriceOfLease(),
                Houses.houses.get(Houses.getId(location)).getPriceOfLease(),
                1));
        inventory.setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer: §a"+LeaseHouse.leaseHouse.get(player).getPriceToPay()+"€", 0, "§7Cela représente §a"+LeaseHouse.leaseHouse.get((Player) player).getDayToPay()+" §7jour(s) à payer."));
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Maison - Location")) return;
        if(!e.getCurrentItem().getType().equals(Material.WOOD_BUTTON) && !e.getCurrentItem().getType().equals(Material.BARRIER)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lRetirer")){
            if(e.getClick().isShiftClick()) {
                LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).removeDayToPay(7);
                e.setCancelled(true);
                e.getClickedInventory().setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer: §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getPriceToPay()+"€", 0, "§7Cela représente §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getDayToPay()+" §7jour(s) à payer."));
                return;
            }
            LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).removeDayToPay(1);
            e.getClickedInventory().setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer: §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getPriceToPay()+"€", 0, "§7Cela représente §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getDayToPay()+" §7jour(s) à payer."));
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lAjouter")){
            if(e.getClick().isShiftClick()) {
                e.setCancelled(true);
                LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).addDayToPay(7);
                e.getClickedInventory().setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer: §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getPriceToPay()+"€", 0, "§7Cela représente §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getDayToPay()+" §7jour(s) à payer."));
                return;
            }
            e.setCancelled(true);
            LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).addDayToPay(1);
            e.getClickedInventory().setItem(13, HousePurchase.utils.getItem(Material.PAPER, "§7§lPrix à payer: §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getPriceToPay()+"€", 0, "§7Cela représente §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getDayToPay()+" §7jour(s) à payer."));
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lSupprimer la maison")){
            Houses.removeHouse(LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getId());
            HouseRegion.removeRegion(LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getId());
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez supprimé la maison.");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lConfirmer l'achat")) {
            if(!EconomyHouseManager.hasEnoughMoney((Player) e.getWhoClicked(), LeaseHouse.leaseHouse.get(e.getWhoClicked()).getPriceToPay())){
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous n'avez pas l'argent nécessaire afin de louer cette maison.");
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                return;
            }
            HousePurchase.econ.withdrawPlayer((OfflinePlayer) e.getWhoClicked(), (double) LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getPriceToPay());
            HousesManager.leaseHouse((Player) e.getWhoClicked(), LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getId());
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous venez de louer cette maison pour §a"+LeaseHouse.leaseHouse.get((Player) e.getWhoClicked()).getDayToPay()+"§fjour(s)");
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }
}
