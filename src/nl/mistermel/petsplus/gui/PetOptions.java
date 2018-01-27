package nl.mistermel.petsplus.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.mistermel.petsplus.PetsPlus;

public class PetOptions extends Gui {
	
	public PetOptions() {
		super(PetsPlus.getInstance().getConfigManager().getGuiSetting("title-options"));
	}

	@Override
	public void populateInventory(Player p, Inventory inv) {
		ItemStack sound = new ItemStack(Material.JUKEBOX);
		ItemMeta soundMeta = sound.getItemMeta();
		soundMeta.setDisplayName(PetsPlus.getInstance().getConfigManager().getGuiSetting("make-sound-item"));
		sound.setItemMeta(soundMeta);
		
		ItemStack ride = new ItemStack(Material.SADDLE);
		ItemMeta rideMeta = ride.getItemMeta();
		rideMeta.setDisplayName(PetsPlus.getInstance().getConfigManager().getGuiSetting("ride-item"));
		ride.setItemMeta(rideMeta);
		
		inv.setItem(11, sound);
		inv.setItem(13, ride);
	}

	@Override
	public void onClick(Player p, ItemStack item) {
		if(item.getType() == Material.JUKEBOX) {
			p.closeInventory();
			for(Player p2 : Bukkit.getOnlinePlayers()) {
				p2.playSound(PetsPlus.getInstance().getPet(p).getEntity().getLocation(), PetsPlus.getInstance().getPet(p).getSound(), SoundCategory.AMBIENT, 1f, 1f);
			}
		}
		if(item.getType() == Material.SADDLE) {
			p.closeInventory();
			PetsPlus.getInstance().getPet(p).getEntity().addPassenger(p);
		}
	}
	
}
