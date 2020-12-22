package tech.mistermel.petsplus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import tech.mistermel.petsplus.PetsPlus;
import tech.mistermel.petsplus.pet.PetType;

public class PetSelection extends Gui {
	
	public PetSelection() {
		super(PetsPlus.getInstance().getConfigManager().getGuiSetting("titles.main"), 36);
	}

	@Override
	public void populateInventory(Player p, Inventory inv) {
		int index = 0;
		for(PetType type : PetType.values()) {
			inv.setItem(index, type.createSkull(p.hasPermission(type.getPermission())));
			index++;
		}
	}

	@Override
	public void onClick(Player player, ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.PLAYER_HEAD) {
			String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			
			for(PetType type : PetType.values()) {
				if(type.getName().equals(name)) {
					if(!player.hasPermission(type.getPermission())) {
						player.sendMessage(PetsPlus.getInstance().getConfigManager().getMessage("no-permission"));
						player.closeInventory();
						return;
					}
					
					if(PetsPlus.getInstance().getPetManager().getPet(player) != null) {
						return;
					}
					
					PetsPlus.getInstance().getPetManager().spawnPet(player, type);
					player.sendMessage(PetsPlus.message("spawned-pet").replace("%pet-name%", type.getName()));
					player.closeInventory();
				}
			}
		}
	}
	
}