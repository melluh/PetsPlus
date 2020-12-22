package tech.mistermel.petsplus.pet;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import tech.mistermel.petsplus.PetsPlus;

public enum PetType {

	BEE(EntityType.BEE, Sound.ENTITY_BEE_LOOP),
	IRON_GOLEM(EntityType.IRON_GOLEM, Sound.ENTITY_IRON_GOLEM_STEP),
	CHICKEN(EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT),
	COW(EntityType.COW, Sound.ENTITY_COW_AMBIENT),
	MUSHROOM(EntityType.MUSHROOM_COW, Sound.ENTITY_COW_AMBIENT),
	SHEEP(EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT),
	RABBIT(EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT),
	OCELOT(EntityType.OCELOT, Sound.ENTITY_OCELOT_AMBIENT),
	PIG(EntityType.PIG, Sound.ENTITY_PIG_AMBIENT),
	FOX(EntityType.FOX, Sound.ENTITY_FOX_AMBIENT),
	CAT(EntityType.CAT, Sound.ENTITY_CAT_AMBIENT),
	WOLF(EntityType.WOLF, Sound.ENTITY_WOLF_AMBIENT),
	POLAR_BEAR(EntityType.POLAR_BEAR, Sound.ENTITY_POLAR_BEAR_WARNING);
	
	private EntityType type;
	private Sound sound;
	
	private PetType(EntityType type, Sound sound) {
		this.type = type;
		this.sound = sound;
	}
	
	public boolean isEnabled() {
		return this.getConfigSection().getBoolean("enabled");
	}
	
	public String getName() {
		return this.getConfigSection().getString("name");
	}
	
	public ItemStack createSkull(boolean hasPermission) {
		String skullTexture = this.getConfigSection().getString("skullTexture");
		if(skullTexture == null)
			return new ItemStack(Material.BARRIER);
		
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new Property("textures", skullTexture, null));
		
		try {
			Field profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, profile);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
		meta.setDisplayName((hasPermission ? ChatColor.GREEN : ChatColor.RED) + this.getName());
		
		item.setItemMeta(meta);
		return item;
	}
	
	private ConfigurationSection getConfigSection() {
		return PetsPlus.getInstance().getConfig().getConfigurationSection("petTypes." + this.name().toLowerCase());
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
	
}
