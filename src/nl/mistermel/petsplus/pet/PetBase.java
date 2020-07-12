package nl.mistermel.petsplus.pet;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class PetBase {
	
	private EntityType type;
	private Sound sound;
	private String skullOwner;
	private String name;
	private String permission;
	
	public PetBase(EntityType type,  Sound sound, String name, String skullOwner, String permission) {
		this.type = type;
		this.sound = sound;
		this.skullOwner = skullOwner;
		this.name = name;
		this.permission = permission;
	}
	
	public EntityType getType() {
		return type;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSkullOwner() {
		return skullOwner;
	}
	
	public String getPermission() {
		return permission;
	}
	
}
