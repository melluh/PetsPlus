package tech.mistermel.petsplus.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Gui {
	
	private String title;
	
	public Gui(String title) {
		this.title = title;
	}
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, this.getTitle());
		populateInventory(player, inv);
		player.openInventory(inv);
	}
	
	public abstract void populateInventory(Player player, Inventory inv);
	public abstract void onClick(Player player, ItemStack item);
	
	public String getTitle() {
		return title;
	}
	
}
