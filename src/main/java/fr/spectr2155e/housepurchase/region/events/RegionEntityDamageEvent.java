package fr.spectr2155e.housepurchase.region.events;

import fr.spectr2155e.housepurchase.region.tools.Region;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;





/**
 * Event when a entity take damage in a region
 * 
 * @author CanardConfit
 * @version 1.0.0
 */
public class RegionEntityDamageEvent extends Event  {

	private static final HandlerList handlers = new HandlerList();

	/**
	 * player damage in a region
	 */
	private Entity entity;
	
	/**
	 * region that the player damage
	 */
	private Region region;
	
	/**
	 * Damager of the player
	 */
	private Entity damager;
	
	/**
	 * Cause of Damage
	 */
	private DamageCause cause;
	
	/**
	 * Damage final
	 */
	private double finalDamage;
	
	/**
	 * If the event is cancelled
	 */
	private boolean isCancelled;
	
	/**
	 * Constructor of event
	 * 
	 * @param reg Region of event
	 * @param entity Entity of event
	 * @param damager Entity damaged entity
	 * @param cause Damage cause
	 * @param finalDamage Damage final
	 */
	public RegionEntityDamageEvent(Region reg, Entity entity, Entity damager, DamageCause cause, double finalDamage) {
		this.isCancelled = false;
		this.region = reg;
		this.entity = entity;
		this.damager = damager;
		this.cause = cause;
		this.finalDamage = finalDamage;
	}

	/**
	 * Get entity
	 * 
	 * @return Entity
	 */
	public Entity getEntity() {
		return entity;
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
	 * Get Damager of Player
	 * 
	 * @return Entity
	 */
	public Entity getDamager() {
		return damager;
	}

	/**
	 * Get Cause of Damage
	 * 
	 * @return DamageCause
	 */
	public DamageCause getCause() {
		return cause;
	}
	
	/**
	 * Get Final Damage
	 * 
	 * @return finalDamage
	 */
	public double getFinalDamage() {
		return finalDamage;
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
