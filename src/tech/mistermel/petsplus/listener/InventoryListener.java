package tech.mistermel.petsplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import tech.mistermel.petsplus.PetsPlus;
import tech.mistermel.petsplus.gui.Gui;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) return;
		for(Gui gui : PetsPlus.getInstance().getGuiManager().getGuis()) {
			if(gui.getTitle().equals(e.getView().getTitle())) {
				e.setCancelled(true);
				gui.onClick((Player) e.getWhoClicked(), e.getCurrentItem());
				return;
			}
		}
	}
	
}
