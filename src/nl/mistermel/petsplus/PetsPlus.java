package nl.mistermel.petsplus;

import java.io.File;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.mistermel.petsplus.gui.GuiManager;
import nl.mistermel.petsplus.gui.PetOptions;
import nl.mistermel.petsplus.gui.PetSelection;
import nl.mistermel.petsplus.listener.EntityListener;
import nl.mistermel.petsplus.listener.InventoryListener;
import nl.mistermel.petsplus.pet.PetBase;
import nl.mistermel.petsplus.pet.PetManager;

public class PetsPlus extends JavaPlugin {
	
	private static PetsPlus instance;
	
	private ConfigManager configManager;
	private PetManager petManager;
	private GuiManager guiManager;
	
	public void onEnable() {
		instance = this;
		
		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		this.configManager = new ConfigManager();
		this.petManager = new PetManager();
		this.guiManager = new GuiManager();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new EntityListener(), this);
		
		this.registerPetBases();
		
		guiManager.registerGui(PetSelection.class, new PetSelection());
		guiManager.registerGui(PetOptions.class, new PetOptions());
		
		new BukkitRunnable() {
			public void run() {
				petManager.tick();
			}
		}.runTaskTimer(this, 0, 5);
	}
	
	private void registerPetBases() {
		petManager.registerPetBase(new PetBase(EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "Chicken", "MHF_Chicken", "petsplus.pet.chicken"));
		petManager.registerPetBase(new PetBase(EntityType.COW, Sound.ENTITY_COW_AMBIENT, "Cow", "MHF_Cow", "petsplus.pet.cow"));
		petManager.registerPetBase(new PetBase(EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "Sheep", "MHF_Sheep", "petsplus.pet.sheep"));
		petManager.registerPetBase(new PetBase(EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "Rabbit", "MHF_Rabbit", "petsplus.pet.rabbit"));
		petManager.registerPetBase(new PetBase(EntityType.OCELOT, Sound.ENTITY_CAT_PURREOW, "Ocelot", "MHF_Ocelot", "petsplus.pet.ocelot"));
		petManager.registerPetBase(new PetBase(EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "Pig", "MHF_Pig", "petsplus.pet.pig"));
		petManager.registerPetBase(new PetBase(EntityType.MUSHROOM_COW, Sound.ENTITY_COW_AMBIENT, "Mushroom", "MHF_MushroomCow", "petsplus.pet.mushroom"));
	}

	public void onDisable() {
		petManager.removeAll();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(configManager.getPrefix() + configManager.getMessage("player-only"));
			return true;
		}
		Player p = (Player) sender;
		
		guiManager.getGui(petManager.getPet(p.getUniqueId()) != null ? PetOptions.class : PetSelection.class).open(p);
		p.sendMessage(message("gui-opened"));
		return true;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public PetManager getPetManager() { 
		return petManager;
	}
	
	public GuiManager getGuiManager() {
		return guiManager;
	}

	public static PetsPlus getInstance() {
		return instance;
	}
	
	public static String guiSetting(String key) {
		return instance.getConfigManager().getGuiSetting(key);
	}
	
	public static String message(String key) {
		return instance.getConfigManager().getPrefix() + " " + instance.getConfigManager().getMessage(key);
	}
}
