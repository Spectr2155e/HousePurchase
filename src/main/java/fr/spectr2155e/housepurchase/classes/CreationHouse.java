package fr.spectr2155e.housepurchase.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreationHouse {

    public static HashMap<Player, CreationHouse> playerCreationHouseHashMap = new HashMap<>();

    private Location location;
    private Player player;
    private int priceOfBuy;
    private int priceOfLease;

    public CreationHouse(Location location, Player player, int priceOfBuy, int priceOfLease) {
        this.location = location;
        this.player = player;
        this.priceOfBuy = priceOfBuy;
        this.priceOfLease = priceOfLease;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public int getPriceOfBuy() {return priceOfBuy;}
    public void setPriceOfBuy(int priceOfBuy) {
        this.priceOfBuy = priceOfBuy;
    }
    public int getPriceOfLease() {
        return priceOfLease;
    }
    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
    }

}
