package nl.mistermel.petsplus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
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
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(!Main.rightVersion) {
			e.getPlayer().sendMessage(Main.getConfigManager().getPrefix() + ChatColor.RED + "ERROR: This PetsPlus version isnt made for this Minecraft version, please download the right version for your Minecraft version from the plugin page.");
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(!Main.rightVersion) {
			e.getWhoClicked().sendMessage(Main.getConfigManager().getPrefix() + ChatColor.RED + "ERROR: This PetsPlus version isnt made for this Minecraft version, please download the right version for your Minecraft version from the plugin page.");
			e.getWhoClicked().closeInventory();
			return;
		}
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
				if(!Main.rightVersion) {
					e.getPlayer().sendMessage(Main.getConfigManager().getPrefix() + ChatColor.RED + "ERROR: This PetsPlus version isnt made for this Minecraft version, please download the right version for your Minecraft version from the plugin page.");
					return;
				}
				e.setCancelled(true);
				if(p.getOwner() == e.getPlayer()) {
					PetOptions.open(e.getPlayer());
				}
			}
		}
	}
}
