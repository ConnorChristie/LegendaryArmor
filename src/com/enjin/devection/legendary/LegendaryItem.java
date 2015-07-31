package com.enjin.devection.legendary;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static org.bukkit.ChatColor.*;

public enum LegendaryItem
{
	//TODO: Config for setting name and material
	
	//Type  //Name      //Material
	HELMET(DARK_RED + "Apollos Crest", LegendaryType.ARMOR, Material.DIAMOND_HELMET, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.WATER_WORKER,
			Enchantment.OXYGEN
		},
			new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1)),
	
	CHESTPLATE(DARK_GREEN + "Aegis", LegendaryType.ARMOR, Material.DIAMOND_CHESTPLATE, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.LOOT_BONUS_MOBS
		},
			new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1)),
	
	LEGGINGS(DARK_GRAY + "Ethereals", LegendaryType.ARMOR, Material.DIAMOND_LEGGINGS, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.LOOT_BONUS_MOBS
		},
			new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1)),
	
	BOOTS(GOLD + "Hermes", LegendaryType.ARMOR, Material.DIAMOND_BOOTS, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FALL,
			Enchantment.DEPTH_STRIDER
		},
			new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
	
	private String name;
	private Material material;
	
	private LegendaryType type;
	
	private Enchantment[] enchantments;
	private PotionEffect potion;
	
	private LegendaryItem(String name, LegendaryType type, Material material, Enchantment[] enchantments, PotionEffect potion)
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
		switch (this)
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
		switch (this)
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
		
		lore.add(DARK_AQUA + "Legendary Helmet");
		lore.add(GREEN + "Infused with: " + DARK_PURPLE + WordUtils.capitalize(potion.getType().getName().replace("_", " ").toLowerCase()));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public String toString()
	{
		return name().toLowerCase();
	}
	
	public static LegendaryItem fromItemStack(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		
		return (meta != null && meta.getDisplayName() != null) ? fromName(meta.getDisplayName()) : null;
	}
	
	public static LegendaryItem fromName(String name)
	{
		for (LegendaryItem item : values())
		{
			if (item.getName().toLowerCase().contains(name.toLowerCase()))
			{
				return item;
			}
		}
		
		return null;
	}
}