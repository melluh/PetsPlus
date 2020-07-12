package tech.mistermel.petsplus.pet;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R1.EntityInsentient;
import tech.mistermel.petsplus.PetsPlus;


@SuppressWarnings("deprecation")
public class Pet {
	
	private Player owner;
	private LivingEntity entity;
	private Sound sound;
	
	public Pet(Player owner, EntityType type, Sound sound) {
		this.owner = owner;
		this.sound = sound;
		this.entity = (LivingEntity) owner.getWorld().spawnEntity(owner.getLocation(), type);
		entity.setSilent(PetsPlus.getInstance().getConfigManager().getSetting("silent"));
		if(PetsPlus.getInstance().getConfigManager().getSetting("nametag")) {
			entity.setCustomName(ChatColor.GOLD + owner.getName() + "'s " + type.getName().toLowerCase());
			entity.setCustomNameVisible(true);
		}
	}
	
	public void tick() {
		if(entity == null || owner == null || entity.isDead()) {
			return;
		}
		if(entity.getHealth() < entity.getMaxHealth()) {
			entity.setHealth(entity.getMaxHealth());
		}
		if(entity instanceof Creature && ((Creature) entity).getTarget() != null) {
			((Creature) entity).setTarget(null);
		}
		double dist = entity.getLocation().distanceSquared(this.owner.getLocation());
		if(dist > 510.0D && owner.isOnGround()) {
			entity.teleport(owner);
		} else if(dist > 10.0D) {
			walkTo(this.owner.getLocation().clone().add(1.0D, 0.0D, 0.0D), 1.3D);
		}
	}
	
	public void remove() {
		entity.remove();
		this.owner = null;
		this.entity = null;
	}
	
	public void walkTo(Location targetLocation, double speed) {
		EntityInsentient c = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
		c.getNavigation().a(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ(), speed);
	}
	
	public LivingEntity getEntity() {
		return entity;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public Sound getSound() {
		return sound;
	}
	
}
