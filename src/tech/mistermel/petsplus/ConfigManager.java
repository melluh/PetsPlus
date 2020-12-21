package tech.mistermel.petsplus;

import org.bukkit.ChatColor;

public class ConfigManager {
	
	public String getPrefix() {
		return this.getTranslation("prefix");
	}
	
	public String getMessage(String key) {
		return this.getTranslation("messages." + key);
	}

	public String getTranslation(String key) {
		String msg = PetsPlus.getInstance().getConfig().getString(key);
		if(msg == null) {
			return ChatColor.RED + "[" + key + "]";
		}
		
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public boolean getSetting(String key) {
		return PetsPlus.getInstance().getConfig().getBoolean("options." + key);
	}
	
	public String getGuiSetting(String key) {
		return ChatColor.translateAlternateColorCodes('&', PetsPlus.getInstance().getConfig().getString("gui." + key));
	}
}
