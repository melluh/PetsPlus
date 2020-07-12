package tech.mistermel.petsplus;

import org.bukkit.ChatColor;

public class ConfigManager {
	
	public String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', PetsPlus.getInstance().getConfig().getString("prefix"));
	}

	public String getMessage(String name) {
		return ChatColor.translateAlternateColorCodes('&', PetsPlus.getInstance().getConfig().getString("messages." + name));
	}
	
	public boolean getSetting(String name) {
		return PetsPlus.getInstance().getConfig().getBoolean("options." + name);
	}
	
	public String getGuiSetting(String name) {
		return ChatColor.translateAlternateColorCodes('&', PetsPlus.getInstance().getConfig().getString("gui." + name));
	}
}
