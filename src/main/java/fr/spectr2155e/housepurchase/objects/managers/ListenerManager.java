package fr.spectr2155e.housepurchase.objects.managers;

import fr.spectr2155e.housepurchase.HousePurchase;
import fr.spectr2155e.housepurchase.listeners.ClickDoor;
import fr.spectr2155e.housepurchase.objects.minecraft.inventories.*;
import org.bukkit.event.Listener;

public class ListenerManager {
    public void createListener(Object listener){
        HousePurchase.instance.getServer().getPluginManager().registerEvents((Listener) listener, HousePurchase.instance);
    }

    public void initListeners(){
        createListener(new ClickDoor());
        createListener(new HouseGUINotOwner());
        createListener(new HouseGuiNotOwnerUnRegistered());
        createListener(new HouseGUIOwnerBuy());
        createListener(new HouseGUILeaseSelector());
        createListener(new HouseGUICreationRegion());
    }
}
