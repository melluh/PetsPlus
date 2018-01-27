package nl.mistermel.petsplus.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiManager implements Listener {
	
	private Map<Class<? extends Gui>, Gui> guis = new HashMap<Class<? extends Gui>, Gui>();
	
	public void registerGui(Class<? extends Gui> clazz, Gui gui) {
		guis.put(clazz, gui);
	}
	
	public Gui getGui(Class<? extends Gui> clazz) {
		return guis.get(clazz);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) return;
		for(Gui gui : guis.values()) {
			if(gui.getTitle().equals(e.getInventory().getTitle())) {
				gui.onClick((Player) e.getWhoClicked(), e.getCurrentItem());
				return;
			}
		}
	}
	
}
