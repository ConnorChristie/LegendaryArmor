package com.enjin.devection.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.enjin.devection.armor.LegendaryArmor;

public class LegendaryListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.getPlayer().getInventory().addItem(LegendaryArmor.HELMET.getItem());
	}
}