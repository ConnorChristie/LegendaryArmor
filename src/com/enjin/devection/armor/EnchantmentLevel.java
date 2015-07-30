package com.enjin.devection.armor;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentLevel
{
	private Enchantment ench;
	private int level;
	
	public EnchantmentLevel(Enchantment ench, int level)
	{
		this.ench = ench;
		this.level = level;
	}
	
	public Enchantment getEnchantment()
	{
		return ench;
	}
	
	public int getLevel()
	{
		return level;
	}
}