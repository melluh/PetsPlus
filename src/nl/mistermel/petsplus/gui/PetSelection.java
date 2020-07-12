package nl.mistermel.petsplus.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
		for(PetBase pet : PetsPlus.getInstance().getPetManager().getPets()) {
			inv.addItem(createSkull(pet.getName(), pet.getSkullOwner(), p.hasPermission(pet.getPermission())));
		}
		
		ItemStack options = new ItemStack(Material.BEACON);
		ItemMeta optionsMeta = options.getItemMeta();
		optionsMeta.setDisplayName(PetsPlus.getInstance().getConfigManager().getGuiSetting("pet-options-item"));
		options.setItemMeta(optionsMeta);
		inv.setItem(21, options);
		ItemStack remove = new ItemStack(Material.BARRIER);
		ItemMeta removeMeta = remove.getItemMeta();
		removeMeta.setDisplayName(PetsPlus.getInstance().getConfigManager().getGuiSetting("remove-pet-item"));
		remove.setItemMeta(removeMeta);
		inv.setItem(23, remove);
	}

	@Override
	public void onClick(Player p, ItemStack item) {
		if(item.getType() == Material.BARRIER) {
			if(!PetsPlus.getInstance().hasPet(p)) {
				p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("dont-have-pet"));
				p.closeInventory();
				return;
			}
			p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("removed-pet").replaceAll("%pet-name%", PetsPlus.getInstance().getPet(p).getEntity().getType().getName()));
			p.closeInventory();
			PetsPlus.getInstance().removePet(p);
		}
		if(item.getType() == Material.BEACON) {
			if(!PetsPlus.getInstance().hasPet(p)) {
				p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("dont-have-pet"));
				p.closeInventory();
				return;
			}
			p.closeInventory();
			PetsPlus.getInstance().getGuiManager().getGui(PetOptions.class).open(p);
		}
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.PLAYER_HEAD) {
			String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			for(PetBase pet : PetsPlus.getInstance().getPetManager().getPets()) {
				if(pet.getName().equals(name)) {
					if(PetsPlus.getInstance().hasPetActive(p, pet.getType())) {
						p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", pet.getType().getName().toLowerCase()));
						p.closeInventory();
						return;
					}
					if(PetsPlus.getInstance().hasPet(p)) {
						p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("already-have-pet"));
						p.closeInventory();
						return;
					}
					if(!p.hasPermission(pet.getPermission())) {
						p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("no-permission"));
						p.closeInventory();
						return;
					}
					PetsPlus.getInstance().registerPet(p, new Pet(p, pet.getType(), pet.getSound()));
					p.sendMessage(PetsPlus.getInstance().getConfigManager().getPrefix() + PetsPlus.getInstance().getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", pet.getType().getName().toLowerCase()));
					p.closeInventory();
				}
			}
		}
	}
	
	private static ItemStack createSkull(String displayName, String ownerName, boolean available) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(ownerName);
		if(available)
			meta.setDisplayName(ChatColor.GREEN + displayName);
		else
			meta.setDisplayName(ChatColor.RED + displayName);
		item.setItemMeta(meta);
		return item;
	}
	
}