package fr.spectr2155e.housepurchase.systems.crochetage;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.CommonInventory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CrochetageGUI implements Listener, CommonInventory {
    private static final HashMap<Player, Location> locationOfDoor = new HashMap<>();

    private static final HashMap<Player, Integer> timer = new HashMap<>();

    Inventory inventory;

    public void createGui() {
        this.inventory = Bukkit.createInventory(null, 54, "Crochetage");
        for (int i = 0; i <= 54; i++) {
            if (i == 1 || i == 5 || i == 12 || i == 16 || i == 19 || i == 23 || i == 25 || i == 30 || i == 39 || i == 41 || i == 48 || i == 50)
                this.inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 15));
            if (i == 3 || i == 7 || i == 10 || i == 14 || i == 21 || i == 28 || i == 32 || i == 34 || i == 46 || i == 52)
                this.inventory.setItem(i, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 7));
        }
    }

    public void openInventoryWithLocation(Player player, Location location) {
        if (!HousePurchase.inventories.containsKey("CrochetageGUI")) {
            createGui();
            HousePurchase.inventories.put("CrochetageGUI", this.inventory.getContents());
        }
        locationOfDoor.put(player, location);
        if (Crochetage.crochetageHashMap.get(player) == null) {
            Inventory inventory = Bukkit.createInventory(player, 54, "Crochetage");
            inventory.setContents(HousePurchase.inventories.get("CrochetageGUI"));
            Crochetage.crochetageHashMap.put(player, new Crochetage(Houses.getId(location),
                    Arrays.asList(Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100))), Arrays.asList(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false)), Arrays.asList(Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100))), 120, 15, 5));
            int random = Utils.getRandomNumberInRange(0, 100);
            if (random <= Crochetage.crochetageHashMap.get(player).getChancePourcentageCallPolice())
                alertPolice(location);
            inventory.setItem(37, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(0)));
                    inventory.setItem(39, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(1)));
                            inventory.setItem(41, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(2)));
                                    inventory.setItem(43, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(3)));
                                            player.openInventory(inventory);
            startTimer(player);
    } else {
                Crochetage.crochetageHashMap.get(player).setButtonsLevel(Arrays.asList(Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100))));
                Crochetage.crochetageHashMap.get(player).setButtonsState(Arrays.asList(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false)));
                Crochetage.crochetageHashMap.get(player).setButtonsToReach(Arrays.asList(Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100)), Integer.valueOf(Utils.getRandomNumberInRange(0, 100))));
                player.getOpenInventory().getTopInventory().setContents(HousePurchase.inventories.get("CrochetageGUI"));
                player.getOpenInventory().getTopInventory().setItem(37, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(0)));
                        player.getOpenInventory().getTopInventory().setItem(39, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(1)));
                                player.getOpenInventory().getTopInventory().setItem(41, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(2)));
                                        player.getOpenInventory().getTopInventory().setItem(43, HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(player).getButtonsLevel().get(3)));
                                                }
                                                System.out.println(Crochetage.crochetageHashMap.get(player).getButtonsLevel());
                System.out.println(Crochetage.crochetageHashMap.get(player).getButtonsToReach());
  }

  @EventHandler
                public void onClick(InventoryClickEvent e) {
                    if (e.getCurrentItem() == null)
                        return;
                    if (!e.getInventory().getName().equals("Crochetage"))
                        return;
                    if (e.getCurrentItem().getItemMeta() == null)
                        return;
                    switch (e.getSlot()) {
                        case 37:
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(0).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0))) {
                                confirmColumn((Player)e.getWhoClicked(), 0);
                                break;
                            }
                            switch (e.getClick()) {
                                case LEFT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() >= 0 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() < 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(0, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() + 1));
                                    break;
                                case RIGHT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() >= 1 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() <= 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(0, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0).intValue() - 1));
                                    break;
                            }
                            e.getWhoClicked().getOpenInventory().setItem(e.getSlot(), HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0)));
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(0).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(0)))
                                confirmColumn((Player)e.getWhoClicked(), 0);
                            break;
                        case 39:
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(1).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1))) {
                                confirmColumn((Player)e.getWhoClicked(), 1);
                                break;
                            }
                            switch (e.getClick()) {
                                case LEFT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() >= 0 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() < 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(1, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() + 1));
                                    break;
                                case RIGHT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() >= 1 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() <= 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(1, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1).intValue() - 1));
                                    break;
                            }
                            e.getWhoClicked().getOpenInventory().setItem(e.getSlot(), HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1)));
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(1).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(1)))
                                confirmColumn((Player)e.getWhoClicked(), 1);
                            break;
                        case 41:
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(2).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2))) {
                                confirmColumn((Player)e.getWhoClicked(), 2);
                                break;
                            }
                            switch (e.getClick()) {
                                case LEFT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() >= 0 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() < 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(2, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() + 1));
                                    break;
                                case RIGHT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() >= 1 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() <= 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(2, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2).intValue() - 1));
                                    break;
                            }
                            e.getWhoClicked().getOpenInventory().setItem(e.getSlot(), HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2)));
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(2).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(2)))
                                confirmColumn((Player)e.getWhoClicked(), 2);
                            break;
                        case 43:
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(3).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3))) {
                                confirmColumn((Player)e.getWhoClicked(), 3);
                                break;
                            }
                            switch (e.getClick()) {
                                case LEFT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() >= 0 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() < 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(3, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() + 1));
                                    break;
                                case RIGHT:
                                    if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() >= 1 && Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() <= 100)
                                        Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().set(3, Integer.valueOf(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3).intValue() - 1));
                                    break;
                            }
                            e.getWhoClicked().getOpenInventory().setItem(e.getSlot(), HousePurchase.utils.getItem(Material.STONE_BUTTON, null, 0, ""+ Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3)));
                            if (Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsToReach().get(3).equals(Crochetage.crochetageHashMap.get(e.getWhoClicked()).getButtonsLevel().get(3)))
                                confirmColumn((Player)e.getWhoClicked(), 3);
                            break;
                 }
                        e.setCancelled(true);
  }

                        private void confirmColumn(Player player, int index) {
                            Inventory inv = player.getOpenInventory().getTopInventory();
                            List<Boolean> arrayList = Crochetage.crochetageHashMap.get(player).getButtonsState();
                            int random = Utils.getRandomNumberInRange(0, 100);
                            if (random <= Crochetage.crochetageHashMap.get(player).getChancePourcentageOtherSwitch()) {
                                resetColumns(player);
                                return;
                            }
                            switch (index) {
                                case 0:
                                    inv.setItem(1, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(10, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(19, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(28, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    break;
                                case 1:
                                    inv.setItem(3, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(12, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(21, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(30, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    break;
                                case 2:
                                    inv.setItem(5, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(14, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(23, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(32, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    break;
                                case 3:
                                    inv.setItem(7, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(16, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    inv.setItem(25, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 13));
                                    inv.setItem(34, HousePurchase.utils.getItem(Material.STAINED_GLASS_PANE, null, 5));
                                    break;
                            }
                            arrayList.set(index, Boolean.valueOf(true));
                            Crochetage.crochetageHashMap.get(player).setButtonsState(arrayList);
                            if (Crochetage.crochetageHashMap.get(player).getButtonsState().get(0).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(1).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(2).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(3).booleanValue()) {
                                Houses.houses.get(Integer.valueOf(Houses.getId(locationOfDoor.get(player)))).setLocked(false);
                                player.sendMessage("maison a d");
                                        Crochetage.crochetageHashMap.remove(player);
                                player.closeInventory();
                            }
                        }

                        private void resetColumns(Player player) {
                            player.sendMessage("de chance la serrure a reun choc, il va donc falloir rechercher une nouvelle fois les combinaisons...");
                            openInventoryWithLocation(player, locationOfDoor.get(player));
                        }

                        @EventHandler
                        public void onCloseInventory(InventoryCloseEvent e) {
                            (new BukkitRunnable() {
                                public void run() {
                                    if (!e.getInventory().getTitle().equals("Crochetage"))
                                        return;
                                    if (Crochetage.crochetageHashMap.get(e.getPlayer()).getButtonsState().get(0).booleanValue() && Crochetage.crochetageHashMap.get(e.getPlayer()).getButtonsState().get(2).booleanValue() && Crochetage.crochetageHashMap.get(e.getPlayer()).getButtonsState().get(3).booleanValue() && Crochetage.crochetageHashMap.get(e.getPlayer()).getButtonsState().get(4).booleanValue())
                                        return;
                                    e.getPlayer().sendMessage("crochetage a )");
                                            CrochetageGUI.this.alertPolice(CrochetageGUI.locationOfDoor.get(e.getPlayer()));
                                    Crochetage.crochetageHashMap.remove(e.getPlayer());
                                }
                            }).runTaskLaterAsynchronously(HousePurchase.instance, 2L);
                        }

                        private void alertPolice(Location location) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (!players.hasPermission("police.konoha"))
                                    return;
                                players.sendMessage("maison la position: x: " + location.getX() + ", y: " + location.getY() + ", z: " + location.getZ() + " est en train de se faire crocheter.");
                            }
                        }

                        private void startTimer(Player player) {
                            timer.put(player, Integer.valueOf(Crochetage.crochetageHashMap.get(player).getSecondsTimer()));
                            (new BukkitRunnable() {
                                public void run() {
                                    if (CrochetageGUI.timer.get(player) == null) {
                                        cancel();
                                        return;
                                    }
                                    if (Crochetage.crochetageHashMap.get(player) == null) {
                                        CrochetageGUI.timer.remove(player);
                                        cancel();
                                        return;
                                    }
                                    if (Crochetage.crochetageHashMap.get(player).getButtonsState().get(0).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(2).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(3).booleanValue() && Crochetage.crochetageHashMap.get(player).getButtonsState().get(4).booleanValue())
                                        cancel();
                                    if (CrochetageGUI.timer.get(player).intValue() > 0) {
                                        int timerState = CrochetageGUI.timer.get(player).intValue();
                                        CrochetageGUI.timer.remove(player);
                                        CrochetageGUI.timer.put(player, Integer.valueOf(timerState - 1));
                                        if (player.getOpenInventory().getTopInventory().getTitle().equals("Crochetage"))
                                            player.getOpenInventory().setItem(49, HousePurchase.utils.getItem(Material.getMaterial(347), "Temps restant: " + CrochetageGUI.timer.get(player), 0));
                                        return;
                                    }
                                    player.closeInventory();
                                    cancel();
                                }
                            }).runTaskTimer(HousePurchase.instance, 0L, 20L);
                        }
                    }
