package nl.mistermel.petsplus;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import nl.mistermel.petsplus.gui.PetSelection;
import nl.mistermel.petsplus.utils.ConfigManager;

public class Main extends JavaPlugin {

	private static ConfigManager configManager;

	private static PetManager petManager;

	private static HashMap<UUID, Pet> pets = new HashMap<UUID, Pet>();
	
	public static boolean rightVersion = true;
	
	public void onEnable() {
		String ver = Bukkit.getVersion();
		if(!ver.contains("1.11")) {
			getServer().getLogger().log(Level.SEVERE, "!!!!!!!!!!!!!!!");
			getServer().getLogger().log(Level.SEVERE, "-=-IMPORTANT-=-");
			getServer().getLogger().log(Level.SEVERE, "This PetsPlus version isnt made for this Minecraft version, please download the right version for your Minecraft version from the plugin page.");
			getServer().getLogger().log(Level.SEVERE, "-=-IMPORTANT-=-");
			getServer().getLogger().log(Level.SEVERE, "!!!!!!!!!!!!!!!");
			rightVersion = false;
		}
		configManager = new ConfigManager(this);
		petManager = new PetManager();
		getServer().getPluginManager().registerEvents(new Events(this), this);
		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		registerPets();
		new BukkitRunnable() {
			public void run() {
				for(Pet p: getPets()) {
					p.tick();
				}
			}
		}.runTaskTimer(this, 0L, 1L);
	}

	public void onDisable() {
		for (Pet p : getPets()) {
			p.remove();
		}
		pets.clear();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!rightVersion) {
			sender.sendMessage(Main.getConfigManager().getPrefix() + ChatColor.RED + "ERROR: This PetsPlus version isnt made for this Minecraft version, please download the right version for your Minecraft version from the plugin page.");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(configManager.getPrefix() + configManager.getMessage("player-only"));
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(configManager.getPrefix() + configManager.getMessage("gui-opened"));
		PetSelection.open(p);
		return true;
	}

	private void registerPets() {
		if(!rightVersion) {
			return;
		}
		petManager.registerPet(new PetBase(EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "Chicken", "MHF_Chicken",
				"petsplus.pet.chicken"));
		petManager.registerPet(
				new PetBase(EntityType.COW, Sound.ENTITY_COW_AMBIENT, "Cow", "MHF_Cow", "petsplus.pet.cow"));
		petManager.registerPet(
				new PetBase(EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "Sheep", "MHF_Sheep", "petsplus.pet.sheep"));
		petManager.registerPet(new PetBase(EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "Rabbit", "MHF_Rabbit",
				"petsplus.pet.rabbit"));
		petManager.registerPet(new PetBase(EntityType.OCELOT, Sound.ENTITY_CAT_PURREOW, "Ocelot", "MHF_Ocelot",
				"petsplus.pet.ocelot"));
		petManager.registerPet(
				new PetBase(EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "Pig", "MHF_Pig", "petsplus.pet.pig"));
	}

	public Collection<Pet> getPets() {
		return pets.values();
	}

	public static void registerPet(Player p, Pet pet) {
		pets.put(p.getUniqueId(), pet);
	}

	public static boolean hasPet(Player p) {
		return pets.containsKey(p.getUniqueId());
	}

	public static boolean hasPetActive(Player p, EntityType type) {
		if (!hasPet(p))
			return false;
		return pets.get(p.getUniqueId()).getEntity().getType() == type;
	}

	public static void removePet(Player p) {
		if(!rightVersion) {
			return;
		}
		if (pets.containsKey(p.getUniqueId())) {
			((Pet) pets.get(p.getUniqueId())).remove();
			pets.remove(p.getUniqueId());
		}
	}

	public static Pet getPet(Player p) {
		return pets.get(p.getUniqueId());
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static PetManager getPetManager() {
		return petManager;
	}
}
