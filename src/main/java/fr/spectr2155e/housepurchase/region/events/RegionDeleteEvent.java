package fr.spectr2155e.housepurchase.region.events;

import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 * Event when a region is delete
 * 
 * @author CanardConfit
 * @version 1.0.0
 */
public class RegionDeleteEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	/**
	 * plugin that removes the region
	 */
	private Plugin destructor;
	
	/**
	 * Deleted region
	 */
	private Region region;

	/**
	 * Constructor of event
	 * 
	 * @param destructor Plugin of event
	 * @param region Region of event
	 */
	public RegionDeleteEvent(Plugin destructor, Region region) {
		this.destructor = destructor;
		this.region = region;
	}

	/**
	 * Get plugin
	 * 
	 * @return Plugin
	 */
	public Plugin getDestructor() {
		return destructor;
	}

	/**
	 * Get Region deleted
	 * 
	 * @return Region
	 */
	public Region getDeletedRegion() {
		return region;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
