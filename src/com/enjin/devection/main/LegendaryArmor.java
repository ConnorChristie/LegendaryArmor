package com.enjin.devection.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.enjin.devection.listener.LegendaryListener;

public class LegendaryArmor extends JavaPlugin
{
	/*
		Apollos (Helmet) - Night Vision
		Aegis (Chestplate) - Strength 2
		Ethereals (Leggings) - Invisibility
		Hermes (Boots) - Speed 2
	 */
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new LegendaryListener(), this);
	}
	
	public void onDisable()
	{
		
	}
}