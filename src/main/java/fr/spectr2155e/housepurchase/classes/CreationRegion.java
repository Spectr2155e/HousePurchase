package fr.spectr2155e.housepurchase.classes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CreationRegion {

    public static HashMap<Player, CreationRegion> creationRegionHashMap = new HashMap<>();
    private Location loc;
    private Location loc1;
    private Location loc2;
    private String name;

    public CreationRegion(Location loc, Location loc1, Location loc2, String name) {
        this.loc = loc;
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.name = name;
    }
    public Location getLoc() {return loc;}
    public void setLoc(Location loc) {this.loc = loc;}
    public Location getLoc1() {return loc1;}
    public void setLoc1(Location loc1) {this.loc1 = loc1;}
    public Location getLoc2() {return loc2;}
    public void setLoc2(Location loc2) {this.loc2 = loc2;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
