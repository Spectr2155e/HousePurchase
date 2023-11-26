package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.listeners.ClickDoor;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.*;
import fr.spectr2155e.housepurchase.region.listener.RegionListener;
import fr.spectr2155e.housepurchase.region.manager.RegionManager;
import org.bukkit.event.Listener;

public class ListenerManager {

    private RegionManager rm;

    public void createListener(Object listener){
        HousePurchase.instance.getServer().getPluginManager().registerEvents((Listener) listener, HousePurchase.instance);
    }

    public void initListeners(){
        rm = new RegionManager();
        createListener(new ClickDoor());
        createListener(new HouseGUINotOwner());
        createListener(new HouseGuiNotOwnerUnRegistered());
        createListener(new HouseGUIOwnerBuy());
        createListener(new HouseGUILeaseSelector());
        createListener(new HouseGUICreationRegion());
        createListener(new HouseGUITrustedUsers());
        createListener(new RegionListener(rm));
        createListener(new fr.spectr2155e.housepurchase.listeners.RegionListener());
    }
}
