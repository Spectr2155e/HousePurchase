package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.economy.classes.EconomyClass;
import fr.spectr2155e.economy.managers.EconomyManager;
import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyHouseManager {

    public static String economyMode;
    public static net.milkbowl.vault.economy.Economy econ;

    public static boolean hasEnoughMoney(Player player, int money){
        switch (economyMode){
            case "vault":
                return HousePurchase.econ.getBalance(player) >= money;
            case "custom":
                return EconomyClass.economyUsers.get(player.getName()).getBankMoney() >= money;
        }
        return false;
    }

    public static void withdrawMoney(Player player, int money){
        switch (economyMode){
            case "vault":
                HousePurchase.econ.withdrawPlayer(player, (double) money);
                break;
            case "custom":
                EconomyManager.removeBankMoney(player.getName(), String.valueOf(money), null);
                break;
        }
    }

    public static void giveMoney(Player player, int money){
        switch (economyMode){
            case "vault":
                HousePurchase.econ.depositPlayer(player, (double) money);
                break;
            case "custom":
                EconomyManager.addBankMoney(player.getName(), String.valueOf(money), null);
                break;
        }
    }

    public static RegisteredServiceProvider<Economy> initEconomyMode(){
        String configEconomyMode = HousePurchase.instance.getConfig().getString("config.economy");
        economyMode = configEconomyMode;
        if(!configEconomyMode.equals("vault") && !configEconomyMode.equals("custom")){
            System.out.println("§4Erreur de l'initialisation de la méthode d'economy, veillez à bien avoir préciser custom ou vault dans la config du plugin.");
            HousePurchase.instance.getServer().shutdown();
            return null;
        }
        if(configEconomyMode.equals("vault") && !setupEconomy()){
            HousePurchase.instance.getLogger().severe("Le plugin a cessé de fonctionner suite à un dysfonctionnement de Vault ou de son inexistance");
            Bukkit.getPluginManager().disablePlugin(HousePurchase.instance);
            HousePurchase.instance.getServer().shutdown();
            return null;
        } else {
            RegisteredServiceProvider<Economy> economyProvider = HousePurchase.instance.getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            return economyProvider;
        }
    }

    public static Boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = HousePurchase.instance.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
            return true;
        }
        return false;
    }

}
