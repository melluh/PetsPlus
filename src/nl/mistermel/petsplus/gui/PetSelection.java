package nl.mistermel.petsplus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import nl.mistermel.petsplus.Pet;
import nl.mistermel.petsplus.PetBase;
import nl.mistermel.petsplus.PetsPlus;

public class PetSelection extends Gui {
	
	public PetSelection() {
		super(PetsPlus.getInstance().getConfigManager().getGuiSetting("title-main"));
	}

	@Override
	public void populateInventory(Player p, Inventory inv) {
		int index = 10;
		for(PetBase pet : PetsPlus.getInstance().getPetManager().getPets()) {
			inv.setItem(index, createSkull(pet.getName(), pet.getSkullOwner(), p.hasPermission(pet.getPermission())));
			index++;
		}
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createSkull(String displayName, String ownerName, boolean available) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(ownerName);
		meta.setDisplayName((available ? ChatColor.GREEN : ChatColor.RED) + displayName);
		
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public void onClick(Player p, ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.PLAYER_HEAD) {
			String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			for(PetBase petBase : PetsPlus.getInstance().getPetManager().getPets()) {
				if(petBase.getName().equals(name)) {
					if(!p.hasPermission(petBase.getPermission())) {
						p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("no-permission"));
						p.closeInventory();
						return;
					}
					
					if(PetsPlus.getInstance().getPetManager().getPet(p.getUniqueId()) != null) {
						return;
					}
					
					PetsPlus.getInstance().getPetManager().registerPet(new Pet(p, petBase.getType(), petBase.getSound()));
					
					@SuppressWarnings("deprecation")
					String entityName = petBase.getType().getName().toLowerCase();
					p.sendMessage(PetsPlus.message("spawned-pet").replaceAll("%pet-name%", entityName));
					p.closeInventory();
				}
			}
		}
	}
	
}