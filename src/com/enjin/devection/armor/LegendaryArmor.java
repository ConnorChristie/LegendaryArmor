package com.enjin.devection.armor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public enum LegendaryArmor
{
	//TODO: Config for setting name and material
	
	//Type  //Name      //Material
	HELMET("Apollos",   Material.DIAMOND_HELMET, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.WATER_WORKER,
			Enchantment.OXYGEN
		},
			new PotionLevel(PotionEffectType.NIGHT_VISION, 1)),
	
	CHEST( "Aegis",     Material.DIAMOND_CHESTPLATE, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.LOOT_BONUS_MOBS
		},
			new PotionLevel(PotionEffectType.INCREASE_DAMAGE, 2)),
	
	LEGS(  "Ethereals", Material.DIAMOND_LEGGINGS, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY
		},
			new PotionLevel(PotionEffectType.INVISIBILITY, 1)),
	
	BOOTS( "Hermes",    Material.DIAMOND_BOOTS, new Enchantment[] { 
			Enchantment.PROTECTION_ENVIRONMENTAL,
			Enchantment.PROTECTION_FIRE,
			Enchantment.DURABILITY,
			Enchantment.PROTECTION_FALL
		},
			new PotionLevel(PotionEffectType.SPEED, 2));
	
	public static String PREFIX = ChatColor.DARK_RED + "" + ChatColor.BOLD;
	
	private String name;
	private Material material;
	
	private Enchantment[] enchantments;
	private PotionLevel potion;
	
	private LegendaryArmor(String name, Material material, Enchantment[] enchantments, PotionLevel potion)
	{
		this.name = name;
		this.material = material;
		
		this.enchantments = enchantments;
		this.potion = potion;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public ItemStack getInventoryItem(Player player)
	{
		switch (this)
		{
			case HELMET: return player.getInventory().getHelmet();
			case CHEST:  return player.getInventory().getChestplate();
			case LEGS:   return player.getInventory().getLeggings();
			case BOOTS:  return player.getInventory().getBoots();
		}
		
		return null;
	}
	
	public ItemStack getItem()
	{
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(PREFIX + name);
		
		for (Enchantment ench : enchantments)
		{
			if (ench.canEnchantItem(item))
			{
				meta.addEnchant(ench, ench.getMaxLevel(), true);
			}
		}
		
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.DARK_AQUA + "Legendary Helmet");
		lore.add(ChatColor.GREEN + "Infused with: " + WordUtils.capitalize(potion.getPotion().getName().replace("_", " ").toLowerCase()));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
}