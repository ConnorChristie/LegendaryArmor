package com.enjin.devection.legendary;

import static org.bukkit.ChatColor.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.enjin.devection.legendary.Legendary.ArmorType;
import com.enjin.devection.legendary.Legendary.Type;
import com.enjin.devection.main.Main;

public class LegendaryItems
{
	private List<String> loreLines = new ArrayList<String>();
	private List<LegendaryItem> legendaryItems = new ArrayList<LegendaryItem>();
	
	@SuppressWarnings("unchecked")
	public LegendaryItems()
	{
		setDefaults();
		
		loreLines = (List<String>) Main.getInstance().getConfig().getList("legendary_hover", loreLines);
		
		if (Main.getInstance().getConfig().contains("legendary_items"))
		{
			legendaryItems = (List<LegendaryItem>) Main.getInstance().getConfig().getList("legendary_items", legendaryItems);
		} else
		{
			Main.getInstance().getConfig().set("legendary_items", legendaryItems);
			Main.getInstance().saveConfig();
		}
	}
	
	public List<String> getLoreLines(String type, String code, String potion)
	{
		List<String> lore = new ArrayList<String>();
		
		for (String line : loreLines)
		{
			lore.add(ChatColor.translateAlternateColorCodes('&', line).replace("{type}", type).replace("{code}", code).replace("{potion}", potion));
		}
		
		return lore;
	}
	
	public LegendaryItem getLegendary(String name)
	{
		for (LegendaryItem legendary : legendaryItems)
		{
			if (legendary.getName().toLowerCase().contains(name.toLowerCase()))
			{
				return legendary;
			}
		}
		
		return null;
	}
	
	public LegendaryItem getLegendary(ItemStack item)
	{
		ItemMeta meta = item.getItemMeta();
		
		return (meta != null && meta.getDisplayName() != null) ? getLegendary(meta.getDisplayName()) : null;
	}
	
	public LegendaryItem getLegendary(Legendary type)
	{
		for (LegendaryItem legendary : legendaryItems)
		{
			if (legendary.getType().equals(type))
			{
				return legendary;
			}
		}
		
		return null;
	}
	
	private void setDefaults()
	{
		loreLines.add("&3Legend{type}: &b{code}");
		loreLines.add("&2Infused with: &a{potion}");
		
		legendaryItems.add(new LegendaryItem(
				DARK_RED + "Apollos Crest",
				new Legendary(Type.ARMOR, ArmorType.HELMET),
				Material.DIAMOND_HELMET,
				new Enchantment[] { 
					Enchantment.PROTECTION_ENVIRONMENTAL,
					Enchantment.PROTECTION_FIRE,
					Enchantment.DURABILITY,
					Enchantment.WATER_WORKER,
					Enchantment.OXYGEN
				},
				new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1)
			));
		
		legendaryItems.add(new LegendaryItem(
				DARK_GREEN + "Aegis",
				new Legendary(Type.ARMOR, ArmorType.CHESTPLATE),
				Material.DIAMOND_CHESTPLATE,
				new Enchantment[] { 
						Enchantment.PROTECTION_ENVIRONMENTAL,
						Enchantment.PROTECTION_FIRE,
						Enchantment.DURABILITY,
						Enchantment.LOOT_BONUS_MOBS
				},
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2)
			));
		
		legendaryItems.add(new LegendaryItem(
				DARK_GRAY + "Ethereals",
				new Legendary(Type.ARMOR, ArmorType.LEGGINGS),
				Material.DIAMOND_LEGGINGS,
				new Enchantment[] { 
						Enchantment.PROTECTION_ENVIRONMENTAL,
						Enchantment.PROTECTION_FIRE,
						Enchantment.DURABILITY,
						Enchantment.LOOT_BONUS_MOBS
				},
				new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1)
			));
		
		legendaryItems.add(new LegendaryItem(
				GOLD + "Hermes",
				new Legendary(Type.ARMOR, ArmorType.BOOTS),
				Material.DIAMOND_BOOTS,
				new Enchantment[] { 
						Enchantment.PROTECTION_ENVIRONMENTAL,
						Enchantment.PROTECTION_FIRE,
						Enchantment.DURABILITY,
						Enchantment.PROTECTION_FALL,
						Enchantment.DEPTH_STRIDER
				},
				new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2)
			));
	}
}