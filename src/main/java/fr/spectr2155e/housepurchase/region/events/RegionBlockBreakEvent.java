package fr.spectr2155e.housepurchase.region.events;

import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;



/**
 * Event when a player break a block in a region
 * 
 * @author CanardConfit
 * @version 1.0.0
 */
public class RegionBlockBreakEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();

	/**
	 * player break block in a region
	 */
	private Player player;
	
	/**
	 * region that the player break block
	 */
	private Region region;
	
	/**
	 * Block broken
	 */
	private Block block;
	
	/**
	 * If the event is cancelled
	 */
	private boolean isCancelled;
	
	/**
	 * Constructor of event
	 * 
	 * @param region Region of event
	 * @param block Block of event
	 * @param p Player of event
	 */
	public RegionBlockBreakEvent(Region region, Block block, Player p) {
		this.isCancelled = false;
		this.player = p;
		this.region = region;
		this.block = block;
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

	/**
	 * Get Block broken
	 * 
	 * @return Block
	 */
	public Block getBlock() {
		return block;
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
