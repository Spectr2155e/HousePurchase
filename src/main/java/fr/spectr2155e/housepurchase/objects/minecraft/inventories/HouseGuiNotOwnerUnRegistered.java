package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.CreationHouse;
import fr.spectr2155e.housepurchase.classes.Houses;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.HashMap;

public class HouseGuiNotOwnerUnRegistered implements CommonInventory, Listener {

    private static HashMap<Player, Location> locationOfDoor = new HashMap<>();
    Inventory inventory;

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Maison - Création");
        for(int i = 0; i < 45; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));
            }
            if(i == 1 || i == 7 || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));
            }
        }
        inventory.setItem(22, HousePurchase.utils.getItem(Material.SLIME_BALL, "§a§lCréer la maison", 0, "§7En cliquant ici, vous allez", "§7créer une maison"));
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUINotOwnerUnRegistered")) {
            createGui();
            HousePurchase.inventories.put("HouseGUINotOwnerUnRegistered", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45, "Maison - Création");
        inventory.setContents(HousePurchase.inventories.get("HouseGUINotOwnerUnRegistered"));
        locationOfDoor.put(player, location);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Maison - Création")) return;
        if(!e.getCurrentItem().getType().equals(Material.SLIME_BALL) && !e.getCurrentItem().getType().equals(Material.BARRIER)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lCréer la maison")){
            createHouse((Player) e.getWhoClicked(), locationOfDoor.get((Player) e.getWhoClicked()));
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
        }
    }

    private void createHouse(Player player, Location location){
        if(CreationHouse.playerCreationHouseHashMap.containsKey(player)) {player.sendMessage("§8§l[§6§lHousePurchase§8§l] §fVous êtes déjà en création d'une maison tapez §ccancel§f, afin d'annuler la création."); return;}
        player.sendMessage("§8§l[§6§lHousePurchase§8§l] §fVeuillez insérer dans le chat le prix de l'achat de cette maison...");
        CreationHouse.playerCreationHouseHashMap.put(player, new CreationHouse(location, player, -1, -1));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) throws IOException {
        if(!CreationHouse.playerCreationHouseHashMap.containsKey(e.getPlayer())) return;
        if(e.getMessage().equalsIgnoreCase("cancel")) {
            e.getPlayer().sendMessage("§8§l[§6§lHousePurchase§8§l] §cVous avez arrêté la création de la maison.");
            CreationHouse.playerCreationHouseHashMap.remove(e.getPlayer());
            e.setCancelled(true);
            return;
        }
        if(!HousePurchase.utils.isInt(e.getMessage())){
            e.getPlayer().sendMessage("§8§l[§4§lErreur§8§l] §cVeuillez insérer un nombre correct. (si vous voulez arrêtez veuillez écrire \"cancel\")");
            e.setCancelled(true);
            return;
        }
        if(CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getPriceOfBuy() == -1){
            CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).setPriceOfBuy(Integer.parseInt(e.getMessage()));
            e.getPlayer().sendMessage("§8§l[§6§lHousePurchase§8§l] §fLe prix à l'achat de cette maison est maintenant de §a"+HousePurchase.numberFormat.format(Integer.parseInt(e.getMessage()))+"€§f, désormais veuillez indiquez le prix à la location de cette maison par jour.");
            e.setCancelled(true);
            return;
        }
        if(CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getPriceOfLease() == -1){
            CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).setPriceOfLease(Integer.parseInt(e.getMessage()));
            e.getPlayer().sendMessage("§8§l[§6§lHousePurchase§8§l] §fLa maison est désormais créé, le prix de location est désormais défini à §a"+CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getPriceOfLease()+"€");
            Houses.createHouse(CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getLocation(),
                    CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getPriceOfBuy(),
                    CreationHouse.playerCreationHouseHashMap.get(e.getPlayer()).getPriceOfLease(),
                    e.getPlayer());
            e.setCancelled(true);
            CreationHouse.playerCreationHouseHashMap.remove(e.getPlayer());
        }
    }
}
