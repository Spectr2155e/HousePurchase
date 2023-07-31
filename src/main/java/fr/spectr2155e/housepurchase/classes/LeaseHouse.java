package fr.spectr2155e.housepurchase.classes;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.managers.HousesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LeaseHouse {

    public static HashMap<Player, LeaseHouse> leaseHouse = new HashMap<>();

    private Player player;
    private int id;
    private int priceToPay;
    private int priceOfLease;
    private int dayToPay;

    public LeaseHouse(Player player, int id, int priceToPay, int priceOfLease, int dayToPay) {
        this.player = player;
        this.id = id;
        this.priceToPay = priceToPay;
        this.priceOfLease = priceOfLease;
        this.dayToPay = dayToPay;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPriceToPay() {
        return priceToPay;
    }
    public void setPriceToPay(int priceToPay) {this.priceToPay = priceToPay;}
    public int getPriceOfLease() {
        return priceOfLease;
    }
    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
    }
    public int getDayToPay() {
        return dayToPay;
    }
    public void setDayToPay(int dayToPay) {
        this.dayToPay = dayToPay;
    }
    public void addDayToPay(int dayToPay) {
        this.dayToPay += dayToPay;
        setPriceToPay(this.dayToPay*getPriceOfLease());
    }
    public void removeDayToPay(int dayToPay) {
        if(dayToPay >= this.dayToPay){
            setDayToPay(1);
            setPriceToPay(this.dayToPay*getPriceOfLease());
        } else {
            setDayToPay(this.dayToPay-dayToPay);
            setPriceToPay(this.dayToPay*getPriceOfLease());
        }
    }
    public static void initTimer(){
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(HousePurchase.instance, () -> {
            for (Map.Entry<Integer, Houses> entry : Houses.houses.entrySet()) {
                if(entry.getValue().getLeaseDate() == null) return;
                if (entry.getValue().getLeaseDate().getTime() <= System.currentTimeMillis()) {HousesManager.unLeaseHouse(entry.getKey());}
            }
        }, 0L, 20L);
    }
}
