package fr.spectr2155e.housepurchase.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BuyHouse {

    // HashMap BuyHouse - Utiliser dans le gui d'achat de maison, location.
    public static HashMap<Player, BuyHouse> buyHouse = new HashMap<>();

    private Player player;
    private int id;
    private int priceOfBuy;
    private int priceOfLease;
    private Location location;

    public BuyHouse(Player player, int id, int priceOfBuy, int priceOfLease, Location location) {
        this.player = player;
        this.id = id;
        this.priceOfBuy = priceOfBuy;
        this.priceOfLease = priceOfLease;
        this.location = location;
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
    public int getPriceOfBuy() {
        return priceOfBuy;
    }
    public void setPriceOfBuy(int priceOfBuy) {
        this.priceOfBuy = priceOfBuy;
    }
    public int getPriceOfLease() {
        return priceOfLease;
    }
    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

}
