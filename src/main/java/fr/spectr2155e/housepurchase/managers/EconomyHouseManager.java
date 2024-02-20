package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.economy.classes.EconomyClass;
import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import org.bukkit.entity.Player;

public class EconomyHouseManager {
    public static boolean hasEnoughMoney(Player player, int money){
        switch (HousePurchase.economyMode){
            case "vault":
                return HousePurchase.econ.getBalance(player) >= money;
            case "custom":
                return EconomyClass.economyUsers.get(player.getName()).getBankMoney() >= money;
        }
        return false;
    }
}
