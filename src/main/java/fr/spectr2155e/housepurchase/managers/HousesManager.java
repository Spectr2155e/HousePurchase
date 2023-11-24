package fr.spectr2155e.housepurchase.managers;

import fr.spectr2155e.housepurchase.classes.BuyHouse;
import fr.spectr2155e.housepurchase.classes.Houses;
import fr.spectr2155e.housepurchase.classes.LeaseHouse;
import fr.spectr2155e.housepurchase.objects.managers.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.Map;

public class HousesManager {

    public static boolean isHouseExist(Location location){
        boolean bool = false;
        for (Map.Entry<Integer, Houses> entry : Houses.houses.entrySet()) {
            if (entry.getValue().getLocation().equals(Utils.getLocationToJSON(location))) {bool = true;}
        }
        return bool;
    }

    public static boolean isHouseExist(Integer id){
        boolean bool = false;
        for (Map.Entry<Integer, Houses> entry : Houses.houses.entrySet()) {
            if (entry.getValue().getId() == id) {bool = true;}
        }
        return bool;
    }

    public static void buyHouse(Player player, int id){
        Houses.houses.get(id).setOwner(player.getName());
        Houses.houses.get(id).setOwned(true);
        Houses.houses.get(id).setBuy(true);
        Houses.houses.get(id).setDateOfBuy(new Timestamp(System.currentTimeMillis()));
        BuyHouse.buyHouse.remove(player);
    }

    public static void leaseHouse(Player player, int id){
        Houses.houses.get(id).setOwner(player.getName());
        Houses.houses.get(id).setOwned(true);
        Houses.houses.get(id).setLease(true);
        Houses.houses.get(id).setLeaseDate(Utils.addDays(new Timestamp(System.currentTimeMillis()), LeaseHouse.leaseHouse.get(player).getDayToPay()));
    }

    public static boolean checkHouse(Location location){
        boolean bool = false;
        location.setY(location.getY()+1);
        for (Map.Entry<Integer, Houses> entry : Houses.houses.entrySet()) {
            if (entry.getValue().getLocation().equals(Utils.getLocationToJSON(location))) {bool = true;}
        }
        return bool;
    }

    public static void unLeaseHouse(int id){
        Houses.houses.get(id).setOwner(null);
        Houses.houses.get(id).setOwned(false);
        Houses.houses.get(id).setLease(false);
        Houses.houses.get(id).setLeaseDate(null);
    }
}
