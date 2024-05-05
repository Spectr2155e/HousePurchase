package fr.spectr2155e.housepurchase.classes;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class HouseAddTrustedPlayers {
    public static HashMap<Player, HouseAddTrustedPlayers> houseAddTrustedPlayersHashMap = new HashMap<>();

    public HouseAddTrustedPlayers(Player player, int id) {
        this.player = player;
        this.id = id;
    }

    private Player player;
    private int id;

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

}
