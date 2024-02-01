package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.BuyHouse;
import fr.spectr2155e.housepurchase.classes.CreationRegion;
import fr.spectr2155e.housepurchase.classes.HouseRegion;
import fr.spectr2155e.housepurchase.classes.Houses;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class HouseGUICreationRegion implements CommonInventory, Listener {

    Inventory inventory;
    public static List<Player> loc1 = new ArrayList<>();
    public static List<Player> loc2 = new ArrayList<>();
    public static List<Player> name = new ArrayList<>();

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Creation de Region");
        for(int i = 0; i < 45; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));}
            if(i == 1 || i == 7 || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43) inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));

        }
        inventory.setItem(12, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°1", 0, "§7Cliquez afin de définir", "§7la location n°1."));
        inventory.setItem(13, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°2", 0, "§7Cliquez afin de définir", "§7la location n°2."));
        inventory.setItem(14, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§a§lNom de la region", 0, "§7Cliquez afin de définir", "§7le nom de la region."));
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUICreationRegion")) {
            createGui();
            HousePurchase.inventories.put("HouseGUICreationRegion", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45, "Creation de Region");
        inventory.setContents(HousePurchase.inventories.get("HouseGUICreationRegion"));
        if(CreationRegion.creationRegionHashMap.containsKey(player)){
            if(CreationRegion.creationRegionHashMap.get(player).getLoc1() != null){inventory.setItem(12, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°1", 5, "§7Location: x: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc1().getX()+"§7, y: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc1().getY()+"§7, z: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc1().getZ()));}
            if(CreationRegion.creationRegionHashMap.get(player).getLoc2() != null){inventory.setItem(13, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°2", 5, "§7Location: x: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc2().getX()+"§7, y: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc2().getY()+"§7, z: §e"+CreationRegion.creationRegionHashMap.get(player).getLoc2().getZ()));}
            if(CreationRegion.creationRegionHashMap.get(player).getName() != null){inventory.setItem(14, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§a§lNom de la region", 5, "§7Nom de la region: §e"+CreationRegion.creationRegionHashMap.get(player).getName()));}
            if(CreationRegion.creationRegionHashMap.get(player).getName() != null && CreationRegion.creationRegionHashMap.get(player).getLoc2() != null && CreationRegion.creationRegionHashMap.get(player).getLoc1() != null){inventory.setItem(31, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lConfirmer la region", 0, "§7Cliquez afin de définir", "§7le nom de la region."));}
        } else {CreationRegion.creationRegionHashMap.put(player, new CreationRegion(location, null, null, null));}
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Creation de Region")) return;
        if(!e.getCurrentItem().getType().equals(Material.STAINED_GLASS) && !e.getCurrentItem().getType().equals(Material.SLIME_BALL)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lLocation n°1")){
            if(e.getClick().isRightClick()){
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez supprimé la location n°1.");
                e.setCancelled(true);
                e.getClickedInventory().setItem(12, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°1", 0, "§7Cliquez afin de définir", "§7la location n°1."));
                return;
            }
            loc1.add((Player) e.getWhoClicked());
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVeuillez casser un bloc afin de définir la location n°1.");
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lLocation n°2")){
            if(e.getClick().isRightClick()){
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez supprimé la location n°2.");
                e.setCancelled(true);
                e.getClickedInventory().setItem(13, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§6§lLocation n°2", 0, "§7Cliquez afin de définir", "§7la location n°1."));
                return;
            }
            loc2.add((Player) e.getWhoClicked());
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVeuillez casser un bloc afin de définir la location n°2.");
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lNom de la region")){
            if(e.getClick().isRightClick()){
                e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous avez supprimé le nom de la region.");
                e.setCancelled(true);
                e.getClickedInventory().setItem(12, HousePurchase.utils.getItem(Material.STAINED_GLASS, "§a§lNom de la region", 0, "§7Cliquez afin de définir", "§7le nom de la region."));
                return;
            }
            name.add((Player) e.getWhoClicked());
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §fVeuillez écrire le nom de la region dans le chat. (cancel pour annuler)");
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lConfirmer la region")){
            HouseRegion.createRegion(
                    Houses.getId(CreationRegion.creationRegionHashMap.get((Player) e.getWhoClicked()).getLoc()),
                    CreationRegion.creationRegionHashMap.get((Player) e.getWhoClicked()).getLoc1(),
                    CreationRegion.creationRegionHashMap.get((Player) e.getWhoClicked()).getLoc2(),
                    CreationRegion.creationRegionHashMap.get((Player) e.getWhoClicked()).getName()
            );
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("§8§l[§6§lHousePurchase§8§l] §aRegion créé avec succès !");
            CreationRegion.creationRegionHashMap.remove((Player) e.getWhoClicked());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(loc1.contains(e.getPlayer())){
            HouseRegion.registerRegion(e.getPlayer(), "loc1", e.getBlock().getLocation(), null);
            loc1.remove(e.getPlayer());
            openInventoryWithLocation(e.getPlayer(), CreationRegion.creationRegionHashMap.get(e.getPlayer()).getLoc());
            e.setCancelled(true);
        }
        if(loc2.contains(e.getPlayer())){
            HouseRegion.registerRegion(e.getPlayer(), "loc2", e.getBlock().getLocation(), null);
            loc2.remove(e.getPlayer());
            openInventoryWithLocation(e.getPlayer(), CreationRegion.creationRegionHashMap.get(e.getPlayer()).getLoc());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(!name.contains(e.getPlayer())) return;
        if(e.getMessage().equals("cancel")){
            e.getPlayer().sendMessage("§8§l[§6§lHousePurchase§8§l] §cOpération annulé");
            CreationRegion.creationRegionHashMap.remove(e.getPlayer());
            name.remove(e.getPlayer());
            e.setCancelled(true);
            return;
        }
        HouseRegion.registerRegion(e.getPlayer(), "name", null, e.getMessage());
        openInventoryWithLocation(e.getPlayer(), CreationRegion.creationRegionHashMap.get(e.getPlayer()).getLoc());
        name.remove(e.getPlayer());
        e.setCancelled(true);
    }
}
