package nl.mistermel.petsplus.gui;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import nl.mistermel.petsplus.Main;
import nl.mistermel.petsplus.Pet;

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
		inv.addItem(createItem("Cow", "MHF_Cow", p.hasPermission("petsplus.pet.cow")));
		inv.addItem(createItem("Chicken", "MHF_Chicken", p.hasPermission("petsplus.pet.chicken")));
		inv.addItem(createItem("Sheep", "MHF_Sheep", p.hasPermission("petsplus.pet.sheep")));
		inv.addItem(createItem("Rabbit", "MHF_Rabbit", p.hasPermission("petsplus.pet.rabbit")));
		inv.addItem(createItem("Ocelot", "MHF_Ocelot", p.hasPermission("petsplus.pet.ocelot")));
		inv.addItem(createItem("Pig", "MHF_Pig", p.hasPermission("petsplus.pet.pig")));
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
		if(!item.hasItemMeta()) return;
		if(item.getType() != Material.SKULL_ITEM) return;
		if(!((SkullMeta)item.getItemMeta()).hasOwner()) return;
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		if(meta.getOwner().equals("MHF_Cow")) {
			if(Main.hasPetActive(p, EntityType.COW)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "cow"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.cow")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.COW, Sound.ENTITY_COW_AMBIENT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "cow"));
			p.closeInventory();
		}
		if(meta.getOwner().equals("MHF_Chicken")) {
			if(Main.hasPetActive(p, EntityType.CHICKEN)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "chicken"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.chicken")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.CHICKEN, Sound.ENTITY_CHICKEN_AMBIENT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "chicken"));
			p.closeInventory();
		}
		if(meta.getOwner().equals("MHF_Sheep")) {
			if(Main.hasPetActive(p, EntityType.SHEEP)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "sheep"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.sheep")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.SHEEP, Sound.ENTITY_SHEEP_AMBIENT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "sheep"));
			p.closeInventory();
		}
		if(meta.getOwner().equals("MHF_Rabbit")) {
			if(Main.hasPetActive(p, EntityType.RABBIT)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "rabbit"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.rabbit")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.RABBIT, Sound.ENTITY_RABBIT_HURT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "rabbit"));
			p.closeInventory();
		}
		if(meta.getOwner().equals("MHF_Ocelot")) {
			if(Main.hasPetActive(p, EntityType.OCELOT)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "ocelot"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.ocelot")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.OCELOT, Sound.ENTITY_CAT_AMBIENT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "ocelot"));
			p.closeInventory();
		}
		if(meta.getOwner().equals("MHF_Pig")) {
			if(Main.hasPetActive(p, EntityType.PIG)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-this-pet").replaceAll("%pet-name%", "pig"));
				p.closeInventory();
				return;
			}
			if(Main.hasPet(p)) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("already-have-pet"));
				p.closeInventory();
				return;
			}
			if(!p.hasPermission("petsplus.pet.pig")) {
				p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("no-permission"));
				p.closeInventory();
				return;
			}
			Main.registerPet(p, new Pet(p, EntityType.PIG, Sound.ENTITY_PIG_AMBIENT));
			p.sendMessage(Main.getConfigManager().getPrefix() + Main.getConfigManager().getMessage("spawned-pet").replaceAll("%pet-name%", "pig"));
			p.closeInventory();
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
