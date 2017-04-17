package nl.mistermel.petsplus.gui;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import nl.mistermel.petsplus.Main;
import nl.mistermel.petsplus.Pet;
import nl.mistermel.petsplus.PetBase;

public class PetSelection {
	
	private static HashMap<UUID, Inventory> menus = new HashMap<UUID, Inventory>();
	
	public static void open(Player p) {
		if(menus.containsKey(p.getUniqueId())) {
			if(menus.get(p.getUniqueId()).getTitle() != Main.getConfigManager().getGuiSetting("title-main")) {
				Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, Main.getConfigManager().getGuiSetting("title-main"));
				menus.put(p.getUniqueId(), inv);
			}
			update(p);
			p.openInventory(menus.get(p.getUniqueId()));
		} else {
			Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, Main.getConfigManager().getGuiSetting("title-main"));
			menus.put(p.getUniqueId(), inv);
			update(p);
			p.openInventory(inv);
		}
	}
	
	public static void update(Player p) {
		Inventory inv = menus.get(p.getUniqueId());
		inv.clear();
		for(PetBase pet : Main.getPetManager().getPets()) {
			inv.addItem(createItem(pet.getName(), pet.getSkullOwner(), p.hasPermission(pet.getPermission())));
		}
		ItemStack remove = new ItemStack(Material.BARRIER);
		ItemMeta removeMeta = remove.getItemMeta();
		removeMeta.setDisplayName(Main.getConfigManager().getGuiSetting("remove-pet-item"));
		remove.setItemMeta(removeMeta);
		inv.setItem(21, remove);
		ItemStack options = new ItemStack(Material.REDSTONE_COMPARATOR);
		ItemMeta optionsMeta = options.getItemMeta();
		optionsMeta.setDisplayName(Main.getConfigManager().getGuiSetting("pet-options-item"));
		options.setItemMeta(optionsMeta);
		inv.setItem(23, options);
		menus.put(p.getUniqueId(), inv);
	}
	
	@SuppressWarnings("deprecation")
	public static void click(Player p, ItemStack item) {
		if(item == null) return;
		if(item.getType() == Material.BARRIER) {
			if(!Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("dont-have-pet"));
				p.closeInventory();
				return;
			}
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("removed-pet").replaceAll("%pet-name%", Main.getPet(p).getEntity().getType().getName().toLowerCase()));
			p.closeInventory();
			Main.removePet(p);
		}
		if(item.getType() == Material.REDSTONE_COMPARATOR) {
			if(!Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("dont-have-pet"));
				p.closeInventory();
				return;
			}
			p.closeInventory();
			PetOptions.open(p);
		}
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getType() == Material.SKULL_ITEM) {
			String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			for(PetBase pet : Main.getPetManager().getPets()) {
				if(pet.getName().equals(name)) {
					if(Main.hasPetActive(p, pet.getType())) {
						p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", pet.getType().getName().toLowerCase()));
						p.closeInventory();
						return;
					}
					if(Main.hasPet(p)) {
						p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
						p.closeInventory();
						return;
					}
					if(!p.hasPermission(pet.getPermission())) {
						p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
						p.closeInventory();
						return;
					}
					Main.registerPet(p, new Pet(p, pet.getType(), pet.getSound()));
					p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", pet.getType().getName().toLowerCase()));
					p.closeInventory();
				}
			}
		}
	}	
	private static ItemStack createItem(String displayName, String ownerName, boolean available) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
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