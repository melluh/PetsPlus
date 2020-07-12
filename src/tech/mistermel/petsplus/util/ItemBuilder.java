package tech.mistermel.petsplus.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack item;
	
	private String name;
	private boolean hideAttributes;
	private List<String> lore = new ArrayList<String>();
	
	public ItemBuilder(Material material) {
		this.item = new ItemStack(material);
	}
	
	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ItemBuilder addLoreSpacer() {
		this.lore.add("");
		return this;
	}
	
	public ItemBuilder addLoreSpacer(boolean requirement) {
		if(!requirement)
			return this;
		
		return this.addLoreSpacer();
	}
	
	public ItemBuilder addLore(String lore) {
		String[] lores = lore.split("\\\\n");
		for(String splitLore : lores) {
			this.lore.add(splitLore);
		}

		return this;
	}
	
	public ItemBuilder addLore(boolean requirement, String lore) {
		if(!requirement)
			return this;
		
		return this.addLore(lore);
	}
	
	public ItemBuilder hideAttributes() {
		this.hideAttributes = true;
		return this;
	}
	
	public ItemStack get() {
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		meta.setLore(lore);
		if(hideAttributes)
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		return item;
	}
	
}
