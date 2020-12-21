package tech.mistermel.petsplus.pet;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public enum PetType {

	CHICKEN("Chicken", EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "MHF_Chicken", false),
	COW("Cow", EntityType.COW, Sound.ENTITY_COW_AMBIENT, "MHF_Cow", false),
	MUSHROOM("Mushroom", EntityType.MUSHROOM_COW, Sound.ENTITY_COW_AMBIENT, "MHF_MushroomCow", false),
	SHEEP("Sheep", EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "MHF_Sheep", false),
	RABBIT("Rabbit", EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "MHF_Rabbit", false),
	OCELOT("Ocelot", EntityType.OCELOT, Sound.ENTITY_OCELOT_AMBIENT, "MHF_Ocelot", false),
	PIG("Pig", EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "MHF_Pig", false),
	
	BABY_CHICKEN("Baby Chicken", EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "MHF_Chicken", true),
	BABY_COW("Baby Cow", EntityType.COW, Sound.ENTITY_COW_AMBIENT, "MHF_Cow", true),
	BABY_MUSHROOM("Baby Mushroom", EntityType.MUSHROOM_COW, Sound.ENTITY_COW_AMBIENT, "MHF_MushroomCow", true),
	BABY_SHEEP("Baby Sheep", EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "MHF_Sheep", true),
	BABY_RABBIT("Baby Rabbit", EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "MHF_Rabbit", true),
	BABY_OCELOT("Baby Ocelot", EntityType.OCELOT, Sound.ENTITY_OCELOT_AMBIENT, "MHF_Ocelot", true),
	BABY_PIG("Baby Pig", EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "MHF_Pig", true);
	
	private String name;
	private EntityType type;
	private Sound sound;
	private String skullOwner;
	private boolean isBaby;
	
	private PetType(String name, EntityType type, Sound sound, String skullOwner, boolean isBaby) {
		this.name = name;
		this.type = type;
		this.sound = sound;
		this.skullOwner = skullOwner;
		this.isBaby = isBaby;
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
	
	public boolean isBaby() {
		return isBaby;
	}
	
}
