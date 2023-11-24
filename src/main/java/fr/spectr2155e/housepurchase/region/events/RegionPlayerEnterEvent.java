package fr.spectr2155e.housepurchase.region.events;

import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;





/**
 * Event when a player enter in a region
 * 
 * @author CanardConfit
 * @version 1.0.0
 */
public class RegionPlayerEnterEvent extends Event  {
	
	private static final HandlerList handlers = new HandlerList();
	
	/**
	 * player enter in a region
	 */
	private Player player;
	
	/**
	 * region that the player entered
	 */
	private Region region;
	
	/**
	 * If the event is cancelled
	 */
	private boolean isCancelled;

	/**
	 * Constructor of event
	 * 
	 * @param region Region of event
	 * @param p Player of event
	 */
	public RegionPlayerEnterEvent(Region region, Player p) {
		this.isCancelled = false;
		this.player = p;
		this.region = region;
	}

	/**
	 * Get player
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get region
	 * 
	 * @return Region
	 */
	public Region getRegion() {
		return region;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * if event is cancelled
	 * 
	 * @return isCancelled
	 */
	public boolean isCancelled() {
        return isCancelled;
    }

	/**
	 * Set event cancelation
	 * 
	 * @param cancelled boolean, true to cancel
	 */
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

	
}
