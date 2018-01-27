package nl.mistermel.petsplus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.mistermel.petsplus.gui.PetOptions;

public class Events implements Listener {
	
	private PetsPlus main;
	
	public Events(PetsPlus main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PetsPlus.getInstance().removePet(e.getPlayer());
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
					PetsPlus.getInstance().getGuiManager().getGui(PetOptions.class).open(e.getPlayer());
				}
			}
		}
	}
}
