package tech.mistermel.petsplus.pet;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R1.EntityInsentient;
import tech.mistermel.petsplus.PetsPlus;

public class Pet {
	
	private Player owner;
	private PetType type;
	private Creature entity;
	
	protected Pet(Player owner, PetType type) {
		this.owner = owner;
		this.type = type;
		
		this.entity = (Creature) owner.getWorld().spawnEntity(owner.getLocation(), type.getEntityType());
		entity.setSilent(PetsPlus.getInstance().getConfigManager().getSetting("silent"));
		
		if(PetsPlus.getInstance().getConfigManager().getSetting("nametag")) {
			entity.setCustomName(ChatColor.GOLD + owner.getName() + "'s " + type.getName());
			entity.setCustomNameVisible(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void tick() {
		if(entity == null || owner == null || entity.isDead()) {
			return;
		}
		
		double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		if(entity.getHealth() < maxHealth) {
			entity.setHealth(maxHealth);
		}
		
		if(entity.getTarget() != null) {
			entity.setTarget(null);
		}
		
		double distance = entity.getLocation().distanceSquared(this.owner.getLocation());
		if(distance > 510.0 && owner.isOnGround()) {
			entity.teleport(owner);
		} else if(distance > 10.0) {
			walkTo(owner.getLocation().add(1, 0, 0), 1.3);
		}
	}
	
	private void walkTo(Location targetLocation, double speed) {
		EntityInsentient c = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
		c.getNavigation().a(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ(), speed);
	}
	
	public void despawn() {
		entity.remove();
		this.owner = null;
		this.entity = null;
	}
	
	protected Creature getEntity() {
		return entity;
	}
	
	public void addPassenger() {
		entity.addPassenger(owner);
	}
	
	public Location getLocation() {
		return entity.getLocation();
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public PetType getType() {
		return type;
	}
	
}
