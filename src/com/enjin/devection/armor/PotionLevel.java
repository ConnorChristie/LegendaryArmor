package com.enjin.devection.armor;

import org.bukkit.potion.PotionEffectType;

public class PotionLevel
{
	private PotionEffectType potion;
	private int level;
	
	public PotionLevel(PotionEffectType potion, int level)
	{
		this.potion = potion;
		this.level = level;
	}
	
	public PotionEffectType getPotion()
	{
		return potion;
	}
	
	public int getLevel()
	{
		return level;
	}
}