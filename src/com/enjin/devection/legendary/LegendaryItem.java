package com.enjin.devection.legendary;

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

import com.enjin.devection.legendary.Legendary.Type;
import com.enjin.devection.main.Main;

public class LegendaryItem implements ConfigurationSerializable
{
	private String name;
	private Material material;
	
	private Legendary type;
	private String code;
	
	private Enchantment[] enchantments;
	private PotionEffect potion;
	
	public LegendaryItem(String name, Legendary type, Material material, Enchantment[] enchantments, PotionEffect potion)
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
	
	public Legendary getType()
	{
		return type;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getCode()
	{
		return code;
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
			case HELMET:
				return player.getInventory().getHelmet();
			case CHESTPLATE:
				return player.getInventory().getChestplate();
			case LEGGINGS:
				return player.getInventory().getLeggings();
			case BOOTS:
				return player.getInventory().getBoots();
		}
		
		return null;
	}
	
	public void setInventoryItem(Player player, ItemStack item)
	{
		switch (type.getArmorType())
		{
			case HELMET:
				player.getInventory().setHelmet(item);
				break;
			case CHESTPLATE:
				player.getInventory().setChestplate(item);
				break;
			case LEGGINGS:
				player.getInventory().setLeggings(item);
				break;
			case BOOTS:
				player.getInventory().setBoots(item);
				break;
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
				if (ench.equals(Enchantment.PROTECTION_ENVIRONMENTAL))
				{
					meta.addEnchant(ench, 5, true);
				} else
				{
					meta.addEnchant(ench, ench.getMaxLevel(), true);
				}
			}
		}
		
		List<String> lore = Main.getInstance().getLegendaryItems().getLoreLines(
				type.getArmorType() != null ? (" " + WordUtils.capitalize(type.getArmorType().name().toLowerCase())) : "",
				getCode(),
				WordUtils.capitalize(potion.getType().getName().replace("_", " ").toLowerCase()));
				
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public boolean hasLegendary(Player player)
	{
		if (type.getType() == Type.ITEM)
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
		} else if (type.getType() == Type.ARMOR)
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
	
	@SuppressWarnings("unchecked")
	public LegendaryItem(Map<String, Object> map)
	{
		name = (String) map.get("name");
		material = Material.getMaterial((String) map.get("material"));
		
		type = Legendary.getType((String) map.get("type"));
		potion = (PotionEffect) map.get("potion");
		
		code = (String) map.get("code");
		
		List<String> enchantNames = (List<String>) map.get("enchantments");
		enchantments = new Enchantment[enchantNames.size()];
		
		for (int i = 0; i < enchantNames.size(); i++)
		{
			enchantments[i] = Enchantment.getByName(enchantNames.get(i));
		}
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("name", name);
		map.put("material", material.name());
		
		map.put("code", code);
		
		map.put("type", type.getName());
		map.put("potion", potion);
		
		String[] enchantNames = new String[enchantments.length];
		
		for (int i = 0; i < enchantments.length; i++)
		{
			enchantNames[i] = enchantments[i].getName();
		}
		
		map.put("enchantments", enchantNames);
		
		return map;
	}
}