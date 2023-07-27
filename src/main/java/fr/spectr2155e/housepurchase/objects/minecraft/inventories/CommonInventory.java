package fr.spectr2155e.housepurchase.objects.minecraft.inventories;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface CommonInventory {
    void createGui();
    void openInventoryWithLocation(Player player, Location location);
}
