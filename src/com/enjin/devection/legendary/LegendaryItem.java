package com.enjin.devection.legendary;

import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_PURPLE;
import static org.bukkit.ChatColor.GREEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import com.enjin.devection.main.Main;

public class LegendaryItem implements ConfigurationSerializable
{
	private String name;
	private Material material;
	
	private LegendaryType type;
	
	private Enchantment[] enchantments;
	private PotionEffect potion;
	
	public LegendaryItem(String name, LegendaryType type, Material material, Enchantment[] enchantments, PotionEffect potion)
	{
		this.name = name;
		this.material = material;
		
		this.type = type;
		
		this.enchantments = enchantments;
		this.potion = potion;
	}
	
	public String getName()
	{
		return name;
	}
	
	public LegendaryType getType()
	{
		return type;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public PotionEffect getPotion()
	{
		return potion;
	}
	
	public ItemStack getInventoryItem(Player player)
	{
		switch (type.getArmorType())
		{
			case HELMET:     return player.getInventory().getHelmet();
			case CHESTPLATE: return player.getInventory().getChestplate();
			case LEGGINGS:   return player.getInventory().getLeggings();
			case BOOTS:      return player.getInventory().getBoots();
		}
		
		return null;
	}
	
	public void setInventoryItem(Player player, ItemStack item)
	{
		switch (type.getArmorType())
		{
			case HELMET:     player.getInventory().setHelmet(item); break;
			case CHESTPLATE: player.getInventory().setChestplate(item); break;
			case LEGGINGS:   player.getInventory().setLeggings(item); break;
			case BOOTS:      player.getInventory().setBoots(item); break;
		}
	}
	
	public ItemStack getItem()
	{
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		
		for (Enchantment ench : enchantments)
		{
			if (ench.canEnchantItem(item))
			{
				meta.addEnchant(ench, ench.getMaxLevel(), true);
			}
		}
		
		List<String> lore = new ArrayList<String>();
		
		lore.add(DARK_AQUA + "Legendary");
		lore.add(GREEN + "Infused with: " + DARK_PURPLE + WordUtils.capitalize(potion.getType().getName().replace("_", " ").toLowerCase()));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public boolean hasLegendary(Player player)
	{
		if (type == LegendaryType.ITEM)
		{
			for (ItemStack item : player.getInventory().getContents())
			{
				if (item != null)
				{
					if (this == Main.getInstance().getLegendaryItems().getLegendary(item))
					{
						return true;
					}
				}
			}
		} else if (type == LegendaryType.ARMOR)
		{
			ItemStack item = getInventoryItem(player);
			
			if (item != null)
			{
				if (this == Main.getInstance().getLegendaryItems().getLegendary(item))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String toString()
	{
		return name;
	}
	
	public LegendaryItem(Map<String, Object> map)
	{
		name = (String) map.get("name");
		material = (Material) map.get("material");
		
		type = (LegendaryType) map.get("type");
		
		enchantments = (Enchantment[]) map.get("enchantments");
		potion = (PotionEffect) map.get("potion");
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("name", name);
		map.put("material", material);
		
		map.put("type", type);
		
		map.put("enchantments", enchantments);
		map.put("potion", potion);
		
		return map;
	}
}