package nl.mistermel.petsplus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.mistermel.petsplus.gui.PetOptions;
import nl.mistermel.petsplus.gui.PetSelection;

public class Events implements Listener {
	
	private Main main;
	
	public Events(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Main.removePet(e.getPlayer());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getName().equals(Main.getConfigManager().getGuiSetting("title-main"))) {
			PetSelection.click((Player) e.getWhoClicked(), e.getCurrentItem());
			e.setCancelled(true);
		}
		if(e.getInventory().getName().equals(Main.getConfigManager().getGuiSetting("title-options"))) {
			PetOptions.click((Player) e.getWhoClicked(), e.getCurrentItem());
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		for(Pet p : main.getPets()) {
			if(p.getEntity() == e.getEntity()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent e) {
		for(Pet p : main.getPets()) {
			if(p.getEntity() == e.getRightClicked()) {
				e.setCancelled(true);
				if(p.getOwner() == e.getPlayer()) {
					PetOptions.open(e.getPlayer());
				}
			}
		}
	}
}
