package tech.mistermel.petsplus;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tech.mistermel.petsplus.gui.GuiManager;
import tech.mistermel.petsplus.gui.PetOptions;
import tech.mistermel.petsplus.gui.PetSelection;
import tech.mistermel.petsplus.listener.EntityListener;
import tech.mistermel.petsplus.listener.InventoryListener;
import tech.mistermel.petsplus.pet.PetManager;

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
		
		guiManager.registerGui(PetSelection.class, new PetSelection());
		guiManager.registerGui(PetOptions.class, new PetOptions());
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> petManager.tick(), 5, 5);
	}

	public void onDisable() {
		petManager.despawnAll();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(configManager.getMessage("player-only"));
			return true;
		}
		Player player = (Player) sender;
		
		guiManager.getGui(petManager.getPet(player) != null ? PetOptions.class : PetSelection.class).open(player);
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 5, 10);
		
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
		return instance.getConfigManager().getMessage(key);
	}
	
	public static String messageArgs(String key, Object... args) {
		String msg = instance.getConfigManager().getMessage(key);
		for(Object arg : args)
			msg = msg.replaceFirst("\\{\\}", arg.toString());
		return msg;
	}
}
