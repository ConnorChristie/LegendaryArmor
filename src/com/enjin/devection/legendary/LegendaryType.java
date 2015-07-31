package com.enjin.devection.legendary;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum LegendaryType
{
	ARMOR(),
	ITEM();
	
	private ArmorType armorType;
	
	public LegendaryType setArmorType(ArmorType armorType)
	{
		this.armorType = armorType;
		
		return this;
	}
	
	public ArmorType getArmorType()
	{
		return armorType;
	}
	
	public ItemStack getInventoryItem(Player player)
	{
		if (this == ARMOR && armorType != null)
		{
			return player.getInventory().getArmorContents()[armorType.ordinal()];
		}

		return null;
	}
	
	public void setInventoryItem(Player player, ItemStack item)
	{
		if (this == ARMOR && armorType != null)
		{
			ItemStack[] armorContents = player.getInventory().getArmorContents();
			
			armorContents[armorType.ordinal()] = item;
			
			player.getInventory().setArmorContents(armorContents);
		}
	}
	
	public enum ArmorType
	{
		HELMET(),
		CHESTPLATE(),
		LEGGINGS(),
		BOOTS();
	}
}