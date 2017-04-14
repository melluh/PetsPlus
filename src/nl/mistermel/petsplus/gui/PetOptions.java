package nl.mistermel.petsplus.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.mistermel.petsplus.Main;

public class PetOptions {
	
	private static Inventory gui;
	
	public static void open(Player p) {
		if(gui == null) {
			gui = Bukkit.createInventory(null, InventoryType.HOPPER, Main.getConfigManager().getGuiSetting("title-options"));
			ItemStack sound = new ItemStack(Material.JUKEBOX);
			ItemMeta soundMeta = sound.getItemMeta();
			soundMeta.setDisplayName(Main.getConfigManager().getGuiSetting("make-sound-item"));
			sound.setItemMeta(soundMeta);
			gui.setItem(2, sound);
		}
		p.openInventory(gui);
	}
	
	public static void click(Player p, ItemStack item) {
		if(item == null) return;
		if(item.getType() == Material.JUKEBOX) {
			p.closeInventory();
			for(Player p2 : Bukkit.getOnlinePlayers()) {
				p2.playSound(Main.pets.get(p.getUniqueId()).getEntity().getLocation(), Main.pets.get(p.getUniqueId()).getSound(), SoundCategory.AMBIENT, 1f, 1f);
			}
		}	
	}
	
}
