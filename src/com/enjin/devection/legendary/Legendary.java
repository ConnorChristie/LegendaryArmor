package com.enjin.devection.legendary;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Legendary
{
	public static enum Type
	{
		ARMOR(),
		ITEM();
	}
	
	private Type type;
	private ArmorType armorType;
	
	public Legendary(Type type)
	{
		this.type = type;
	}
	
	public Legendary(Type type, ArmorType armorType)
	{
		this.type = type;
		this.armorType = armorType;
	}
	
	private Legendary setArmorType(ArmorType armorType)
	{
		this.armorType = armorType;
		
		return this;
	}
	
	public ArmorType getArmorType()
	{
		return armorType;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public ItemStack getInventoryItem(Player player)
	{
		if (type == Type.ARMOR && armorType != null)
		{
			return player.getInventory().getArmorContents()[armorType.ordinal()];
		}

		return null;
	}
	
	public void setInventoryItem(Player player, ItemStack item)
	{
		if (type == Type.ARMOR && armorType != null)
		{
			ItemStack[] armorContents = player.getInventory().getArmorContents();
			
			armorContents[armorType.ordinal()] = item;
			
			player.getInventory().setArmorContents(armorContents);
		}
	}
	
	public static Legendary getType(String name)
	{
		if (name.contains("_"))
		{
			String[] spl = name.split("_");
			
			return Legendary.valueOf(spl[0]).setArmorType(ArmorType.valueOf(spl[1]));
		}
		
		return Legendary.valueOf(name);
	}
	
	public String getName()
	{
		String name = type.name();
		
		if (armorType != null)
		{
			name += "_" + armorType.name();
		}
		
		return name;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Legendary)
		{
			Legendary legendary = (Legendary) other;
			
			return type == legendary.type && armorType == legendary.armorType;
		}
		
		return false;
	}
	
	public static Legendary valueOf(String name)
	{
		return new Legendary(Type.valueOf(name));
	}
	
	public enum ArmorType
	{
		HELMET(),
		CHESTPLATE(),
		LEGGINGS(),
		BOOTS();
	}
}