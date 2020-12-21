package tech.mistermel.petsplus.pet;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public enum PetType {

	CHICKEN("Chicken", EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "MHF_Chicken"),
	COW("Cow", EntityType.COW, Sound.ENTITY_COW_AMBIENT, "MHF_Cow"),
	SHEEP("Sheep", EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "MHF_Sheep"),
	RABBIT("Rabbit", EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "MHF_Rabbit"),
	OCELOT("Ocelot", EntityType.OCELOT, Sound.ENTITY_OCELOT_AMBIENT, "MHF_Ocelot"),
	PIG("Pig", EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "MHF_Pig"),
	MUSHROOM("Mushroom", EntityType.MUSHROOM_COW, Sound.ENTITY_COW_AMBIENT, "MHF_Mushroom");
	
	private String name;
	private EntityType type;
	private Sound sound;
	private String skullOwner;
	
	private PetType(String name, EntityType type, Sound sound, String skullOwner) {
		this.name = name;
		this.type = type;
		this.sound = sound;
		this.skullOwner = skullOwner;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return "petsplus.pet." + this.name().toLowerCase();
	}
	
	public EntityType getEntityType() {
		return type;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public String getSkullOwner() {
		return skullOwner;
	}
	
}
