package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.HouseAddTrustedPlayers;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.managers.DatabaseHouseManager;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HouseGUITrustedUsers implements CommonInventory, Listener {

    Inventory inventory;
    ArrayList<String> addTrustedPlayers = new ArrayList<>();

    @Override
    public void createGui() {
        inventory = Bukkit.createInventory(null, 45, "Maison - Personnes");
        for(int i = 0; i < 54; i++){
            if(i == 0 || i == 8 || i == 9 || i == 17 || i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 0));
            }
            if((i > 0 && i < 8) || i == 10 || i == 16 || i == 19 || i == 25 || i == 28 || i == 34 || i == 37 || i == 43){
                inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));
            }
        }
    }

    @Override
    public void openInventoryWithLocation(Player player, Location location) {
        if(!HousePurchase.inventories.containsKey("HouseGUITrustedUsers")) {
            createGui();
            HousePurchase.inventories.put("HouseGUITrustedUsers", inventory.getContents());
        }
        Inventory inventory = Bukkit.createInventory(player, 45, "Maison - Personnes");
        inventory.setContents(HousePurchase.inventories.get("HouseGUITrustedUsers"));
        HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.put(player, new HouseAddTrustedPlayers(player, Houses.getId(location)));
        if(Houses.houses.get(Houses.getId(location)).getTrustedPlayers() == null){
            if(Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(player).getId()).getOwner().equals(player.getName())) {
                inventory.setItem(11, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, "§a§lAjouter une personne", 5, "§7Ajouter une personne à votre maison."));
            }
            player.openInventory(inventory);
            return;
        }
        List<String> list = DatabaseHouseManager.getArrayFromJson(Houses.houses.get(Houses.getId(location)).getTrustedPlayers());
        if(list.isEmpty()){
            if(Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(player).getId()).getOwner().equals(player.getName())){
                inventory.setItem(11, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, "§a§lAjouter une personne", 5, "§7Ajouter une personne à votre maison."));
            }
            player.openInventory(inventory);
            return;
        }
        new BukkitRunnable(){
            int i = 11;
            @Override
            public void run() {
                for(String players: list){
                    inventory.setItem(i, HousePurchase.utils.getPlayerHead(players, "§e§l"+players, "§7Clique-gauche: Accéder aux informations du joueur.", "§7Clique-droit: Supprimer le joueur de votre maison."));
                    if(i == 15 || i == 24 || i == 33 || i == 42) i+=5;
                    else i++;
                }
                if(Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(player).getId()).getOwner().equals(player.getName())){
                    inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, "§a§lAjouter une personne", 5, "§7Ajouter une personne à votre maison."));
                }
            }
        }.runTaskAsynchronously(HousePurchase.instance);
        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(!e.getInventory().getName().equals("Maison - Personnes")) return;
        if(!e.getCurrentItem().getType().equals(Material.REDSTONE) && !e.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK) && !e.getCurrentItem().getType().equals(Material.BARRIER)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) e.setCancelled(true);
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lAjouter une personne")){
            if(e.getClick().isShiftClick()){
                e.setCancelled(true);
            }
            if(!addTrustedPlayers.contains(e.getWhoClicked().getName())){
                addTrustedPlayers.add(e.getWhoClicked().getName());
            }
            e.getWhoClicked().sendMessage("§8§l(§6§lHousePurchase§8§l) §fVeuillez indiquer dans le chat une personne que vous souhaiterez ajouter à votre maison.");
            e.setCancelled(true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    e.getWhoClicked().closeInventory();
                }
            }.runTaskLater(HousePurchase.instance, 1L);
        }
        if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)){
            if(e.getClick().isRightClick()) {
                if(!Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getWhoClicked()).getId()).getOwner().equals(e.getWhoClicked().getName())){
                    e.setCancelled(true);
                    e.getWhoClicked().sendMessage("§8§l(§4§lErreur§8§l) §cVous n'avez pas la permission de supprimer un joueur de cette maison.");
                    e.getWhoClicked().closeInventory();
                    return;
                }
                e.setCancelled(true);
                e.getWhoClicked().sendMessage("§8§l(§6§lHousePurchase§8§l) §fVous avez supprimé le joueur §a"+e.getCurrentItem().getItemMeta().getDisplayName().replace("§e§l", "")+" §fde votre maison.");
                e.getWhoClicked().closeInventory();
                Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getWhoClicked()).getId()).removeTrustedPlayer(e.getCurrentItem().getItemMeta().getDisplayName().replace("§e§l", ""));
            }
            if(e.getClick().isLeftClick()) {
                e.setCancelled(true);
                e.getWhoClicked().sendMessage("§8§l(§6§lHousePurchase§8§l) §fLe joueur §a"+e.getCurrentItem().getItemMeta().getDisplayName()+" §ffait parti de votre maison.");
                e.getWhoClicked().closeInventory();
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(!addTrustedPlayers.contains(e.getPlayer().getName())){
            return;
        }
        if(e.getMessage().equals(e.getPlayer().getName())){
            e.getPlayer().sendMessage("§8§l(§4§lErreur§8§l) §cVous ne pouvez pas vous ajouter à votre propre maison.");
            e.setCancelled(true);
            addTrustedPlayers.remove(e.getPlayer().getName());
            return;
        }
        if(!Bukkit.getOfflinePlayer(e.getMessage()).hasPlayedBefore() && Bukkit.getPlayer(e.getMessage()) == null){
            e.getPlayer().sendMessage("§8§l(§4§lErreur§8§l) §cVeuillez insérer un joueur valide qui a déjà joué au serveur, veuillez réessayer en ouvrant de nouveau la porte.");
            e.setCancelled(true);
            addTrustedPlayers.remove(e.getPlayer().getName());
            return;
        }
        List<String> list;
        if(Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getPlayer()).getId()).getTrustedPlayers() == null){
            list = new ArrayList<>();
            list.add(e.getMessage());
            Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getPlayer()).getId()).setTrustedPlayers(DatabaseHouseManager.getJsonFromArray(list));
            e.setCancelled(true);
            e.getPlayer().sendMessage("§8§l(§6§lHousePurchase§8§l) §fLe joueur §a"+e.getMessage()+"§f a maintenant les clés de votre maison.");
            addTrustedPlayers.remove(e.getPlayer().getName());
            return;
        }
        list = DatabaseHouseManager.getArrayFromJson(Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getPlayer()).getId()).getTrustedPlayers());
        if(list.contains(e.getMessage())){
            e.setCancelled(true);
            e.getPlayer().sendMessage("§8§l(§4§lErreur§8§l) §cVous ne pouvez pas ajouter une personne qui a déjà les clés de votre maison.");
            addTrustedPlayers.remove(e.getPlayer().getName());
            return;
        }
        if(list.isEmpty()){
            list = new ArrayList<>();
            list.add(e.getMessage());
            Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getPlayer()).getId()).setTrustedPlayers(DatabaseHouseManager.getJsonFromArray(list));
            e.setCancelled(true);
            e.getPlayer().sendMessage("§8§l(§6§lHousePurchase§8§l) §fLe joueur §a"+e.getMessage()+"§f a maintenant les clés de votre maison.");
            addTrustedPlayers.remove(e.getPlayer().getName());
            return;
        }
        list.add(e.getMessage());
        Houses.houses.get(HouseAddTrustedPlayers.houseAddTrustedPlayersHashMap.get(e.getPlayer()).getId()).setTrustedPlayers(DatabaseHouseManager.getJsonFromArray(list));
        e.setCancelled(true);
        e.getPlayer().sendMessage("§8§l(§6§lHousePurchase§8§l) §fLe joueur §a"+e.getMessage()+"§f a maintenant les clés de votre maison.");
        addTrustedPlayers.remove(e.getPlayer().getName());
    }
}
