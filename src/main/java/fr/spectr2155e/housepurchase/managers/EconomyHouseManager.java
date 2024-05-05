package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.slegacy.EconomyAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
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
                return EconomyAPI.getPlayerEconomy(player).hasEnoughMoneyInBank(money);
        }
        return false;
    }

    public static void withdrawMoney(Player player, int money){
        switch (economyMode){
            case "vault":
                HousePurchase.econ.withdrawPlayer(player, money);
                break;
            case "custom":
                EconomyAPI.getPlayerEconomy(player).removeBankMoney(money);
                break;
        }
    }

    public static void giveMoney(Player player, int money){
        switch (economyMode){
            case "vault":
                HousePurchase.econ.depositPlayer(player, money);
                break;
            case "custom":
                EconomyAPI.getPlayerEconomy(player).addBankMoney(money);
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
        } else if(configEconomyMode.equals("vault")){
            RegisteredServiceProvider<Economy> economyProvider = HousePurchase.instance.getServer().getServicesManager()
                    .getRegistration(net.milkbowl.vault.economy.Economy.class);
            return economyProvider;
        }
        return null;
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
