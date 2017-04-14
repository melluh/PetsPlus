package nl.mistermel.petsplus;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_11_R1.EntityInsentient;

public class Pet {
	
	private Player owner;
	private LivingEntity e;
	private Sound s;
	
	@SuppressWarnings("deprecation")
	public Pet(Player owner, EntityType type, Sound sound) {
		this.owner = owner;
		this.s = sound;
		this.e = (LivingEntity) owner.getWorld().spawnEntity(owner.getLocation(), type);
		e.setSilent(Main.getConfigManager().getSetting("silent"));
		if(Main.getConfigManager().getSetting("nametag")) {
			e.setCustomName(ChatColor.GOLD + owner.getName() + "'s " + type.getName().toLowerCase());
			e.setCustomNameVisible(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void tick() {
		if(this.e == null || this.owner == null || this.e.isDead()) {
			return;
		}
		if(this.e.getHealth() < e.getMaxHealth()) {
			this.e.setHealth(e.getMaxHealth());
		}
		if(this.e instanceof Creature && ((Creature)this.e).getTarget() != null) {
			((Creature)this.e).setTarget(null);
		}
		double dist = this.e.getLocation().distanceSquared(this.owner.getLocation());
		if(dist > 10.0D) {
			if(dist > 510.0D && this.owner.isOnGround()) {
				this.e.teleport(this.owner);
			}
			walkTo(this.owner.getLocation().clone().add(1.0D, 0.0D, 0.0D), 1.3D);
		}
	}
	
	public void remove() {
		this.e.remove();
		this.owner = null;
		this.e = null;
	}
	
	public void walkTo(Location targetLocation, double speed) {
		EntityInsentient c = (EntityInsentient) ((CraftLivingEntity)this.e).getHandle();
		c.getNavigation().a(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ(), speed);
	}
	
	public LivingEntity getEntity() {
		return e;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public Sound getSound() {
		return s;
	}
	
}
