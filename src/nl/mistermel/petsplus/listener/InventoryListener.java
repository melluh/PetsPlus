package nl.mistermel.petsplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import nl.mistermel.petsplus.PetsPlus;
import nl.mistermel.petsplus.gui.Gui;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) return;
		for(Gui gui : PetsPlus.getInstance().getGuiManager().getGuis()) {
			if(gui.getTitle().equals(e.getView().getTitle())) {
				gui.onClick((Player) e.getWhoClicked(), e.getCurrentItem());
				return;
			}
		}
	}
	
}
