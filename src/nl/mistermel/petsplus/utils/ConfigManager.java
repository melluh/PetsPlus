package nl.mistermel.petsplus.utils;

import org.bukkit.ChatColor;

import nl.mistermel.petsplus.Main;

public class ConfigManager {
	
	static Main plugin;
	
	public ConfigManager(Main instance) {
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
