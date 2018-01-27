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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import nl.mistermel.petsplus.gui.GuiManager;
import nl.mistermel.petsplus.gui.PetOptions;
import nl.mistermel.petsplus.gui.PetSelection;

public class PetsPlus extends JavaPlugin {

	private static HashMap<UUID, Pet> pets = new HashMap<UUID, Pet>();
	
	private static PetsPlus instance;
	
	private ConfigManager configMan;
	private PetManager petMan;
	private GuiManager guiMan;
	
	public void onEnable() {
		instance = this;
		
		String ver = Bukkit.getVersion();
		if(!ver.contains("1.12")) {
			getServer().getLogger().log(Level.SEVERE, "!!!!!!!!!!!!!!!");
			getServer().getLogger().log(Level.SEVERE, "-=-IMPORTANT-=-");
			getServer().getLogger().log(Level.SEVERE, "Expected version: 1.12.x");
			getServer().getLogger().log(Level.SEVERE, "Actual version: " + ver);
			getServer().getLogger().log(Level.SEVERE, "-=-IMPORTANT-=-");
			getServer().getLogger().log(Level.SEVERE, "!!!!!!!!!!!!!!!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		this.configMan = new ConfigManager(this);
		this.petMan = new PetManager();
		this.guiMan = new GuiManager();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(guiMan, this);
		pm.registerEvents(new Events(this), this);
		

		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		petMan.registerPet(new PetBase(EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT, "Chicken", "MHF_Chicken", "petsplus.pet.chicken"));
		petMan.registerPet(new PetBase(EntityType.COW, Sound.ENTITY_COW_AMBIENT, "Cow", "MHF_Cow", "petsplus.pet.cow"));
		petMan.registerPet(new PetBase(EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT, "Sheep", "MHF_Sheep", "petsplus.pet.sheep"));
		petMan.registerPet(new PetBase(EntityType.RABBIT, Sound.ENTITY_RABBIT_AMBIENT, "Rabbit", "MHF_Rabbit", "petsplus.pet.rabbit"));
		petMan.registerPet(new PetBase(EntityType.OCELOT, Sound.ENTITY_CAT_PURREOW, "Ocelot", "MHF_Ocelot", "petsplus.pet.ocelot"));
		petMan.registerPet(new PetBase(EntityType.PIG, Sound.ENTITY_PIG_AMBIENT, "Pig", "MHF_Pig", "petsplus.pet.pig"));
		
		guiMan.registerGui(PetSelection.class, new PetSelection());
		guiMan.registerGui(PetOptions.class, new PetOptions());
		
		new BukkitRunnable() {
			public void run() {
				for(Pet p: getPets()) {
					p.tick();
				}
			}
		}.runTaskTimer(this, 0, 5);
	}

	public void onDisable() {
		for (Pet p : getPets()) {
			p.remove();
		}
		pets.clear();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(configMan.getPrefix() + configMan.getMessage("player-only"));
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(configMan.getPrefix() + configMan.getMessage("gui-opened"));
		guiMan.getGui(PetSelection.class).open(p);
		return true;
	}
	
	public ConfigManager getConfigManager() {
		return configMan;
	}
	
	public PetManager getPetManager() { 
		return petMan;
	}
	
	public GuiManager getGuiManager() {
		return guiMan;
	}

	public Collection<Pet> getPets() {
		return pets.values();
	}

	public void registerPet(Player p, Pet pet) {
		pets.put(p.getUniqueId(), pet);
	}

	public boolean hasPet(Player p) {
		return pets.containsKey(p.getUniqueId());
	}

	public boolean hasPetActive(Player p, EntityType type) {
		if (!hasPet(p))
			return false;
		return pets.get(p.getUniqueId()).getEntity().getType() == type;
	}

	public void removePet(Player p) {
		if (pets.containsKey(p.getUniqueId())) {
			((Pet) pets.get(p.getUniqueId())).remove();
			pets.remove(p.getUniqueId());
		}
	}

	public Pet getPet(Player p) {
		return pets.get(p.getUniqueId());
	}

	public static PetsPlus getInstance() {
		return instance;
	}
}
