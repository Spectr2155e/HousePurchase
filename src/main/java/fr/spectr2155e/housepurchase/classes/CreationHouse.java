package fr.spectr2155e.housepurchase.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreationHouse {

    // HashMap pour stocker la correspondance entre les joueurs et leurs maisons de création respectives
    public static HashMap<Player, CreationHouse> playerCreationHouseHashMap = new HashMap<>();

    // Variables d'instance
    private Location location;
    private Player player;
    private int priceOfBuy;
    private int priceOfLease;

    // Constructeur
    public CreationHouse(Location location, Player player, int priceOfBuy, int priceOfLease) {
        this.location = location;
        this.player = player;
        this.priceOfBuy = priceOfBuy;
        this.priceOfLease = priceOfLease;

        // Ajouter le joueur et sa maison de création à la HashMap
        playerCreationHouseHashMap.put(player, this);
    }

    // Méthodes getter et setter pour l'emplacement
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Méthodes getter et setter pour le joueur
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Méthodes getter et setter pour le prix d'achat
    public int getPriceOfBuy() {
        return priceOfBuy;
    }

    public void setPriceOfBuy(int priceOfBuy) {
        this.priceOfBuy = priceOfBuy;
    }

    // Méthodes getter et setter pour le prix de location
    public int getPriceOfLease() {
        return priceOfLease;
    }

    public void setPriceOfLease(int priceOfLease) {
        this.priceOfLease = priceOfLease;
    }
}
