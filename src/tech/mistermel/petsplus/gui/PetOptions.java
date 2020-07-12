package tech.mistermel.petsplus.gui;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tech.mistermel.petsplus.PetsPlus;
import tech.mistermel.petsplus.pet.Pet;
import tech.mistermel.petsplus.util.ItemBuilder;

public class PetOptions extends Gui {
	
	public PetOptions() {
		super(PetsPlus.getInstance().getConfigManager().getGuiSetting("title-options"));
	}

	@Override
	public void populateInventory(Player p, Inventory inv) {
		inv.setItem(11, new ItemBuilder(Material.JUKEBOX).setName(PetsPlus.guiSetting("make-sound-item")).get());
		inv.setItem(13, new ItemBuilder(Material.SADDLE).setName(PetsPlus.guiSetting("ride-item")).get());
		inv.setItem(15, new ItemBuilder(Material.BARRIER).setName(PetsPlus.guiSetting("remove-pet-item")).get());
	}

	@Override
	public void onClick(Player p, ItemStack item) {
		if(item.getType() == Material.JUKEBOX) {
			p.closeInventory();
			
			Pet pet = PetsPlus.getInstance().getPetManager().getPet(p.getUniqueId());
			
			Location loc = pet.getEntity().getLocation();
			Sound sound = pet.getSound();
			for(Player p2 : Bukkit.getOnlinePlayers()) {
				p2.playSound(loc, sound, SoundCategory.AMBIENT, 1f, 1f);
			}
			return;
		}
		
		if(item.getType() == Material.SADDLE) {
			p.closeInventory();
			PetsPlus.getInstance().getPetManager().getPet(p.getUniqueId()).getEntity().addPassenger(p);
			return;
		}
		
		if(item.getType() == Material.BARRIER) {
			Pet pet = PetsPlus.getInstance().getPetManager().getPet(p.getUniqueId());
			@SuppressWarnings("deprecation")
			String entityName = pet.getEntity().getType().getName();
			
			p.sendMessage(PetsPlus.message("removed-pet").replaceAll("%pet-name%", entityName));
			p.closeInventory();
			PetsPlus.getInstance().getPetManager().removePet(pet);
			return;
		}
	}
	
}
