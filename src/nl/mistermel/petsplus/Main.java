package nl.mistermel.petsplus;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mistermel.petsplus.gui.PetSelection;

public class Main extends JavaPlugin {
	
	private static ConfigManager configManager;
	
	public static HashMap<UUID, Pet> pets = new HashMap<UUID, Pet>();
	
	public void onEnable() {
		configManager = new ConfigManager(this);
		getServer().getPluginManager().registerEvents(new Events(this), this);
		if(!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		new PetTick(this).runTaskTimer(this, 0L, 1L);
	}
	
	public void onDisable() {
		for(Pet p : getPets()) {
			p.remove();
		}
		pets.clear();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(configManager.getPrefix() + configManager.getMessage("player-only"));
			return true;
		}
		Player p = (Player) sender;
		p.sendMessage(configManager.getPrefix() + configManager.getMessage("gui-opened"));
		PetSelection.open(p);
		return true;
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
		if(!hasPet(p)) return false;
		return pets.get(p.getUniqueId()).getEntity().getType() == type;
	}
	
	public static void removePet(Player p) {
		if(pets.containsKey(p.getUniqueId())) {
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
}
