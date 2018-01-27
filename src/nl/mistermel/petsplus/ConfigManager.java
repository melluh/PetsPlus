package nl.mistermel.petsplus;

import org.bukkit.ChatColor;

public class ConfigManager {
	
	static PetsPlus plugin;
	
	public ConfigManager(PetsPlus instance) {
		plugin = instance;
	}
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + " ";
	}

	public String getMessage(String name) {
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages." + name));
	}
	
	public boolean getSetting(String name) {
		return plugin.getConfig().getBoolean("options." + name);
	}
	
	public String getGuiSetting(String name) {
		return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("gui." + name));
	}
}
