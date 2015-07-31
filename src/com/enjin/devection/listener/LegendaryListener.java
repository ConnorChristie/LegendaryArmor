package com.enjin.devection.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.enjin.devection.events.ArmorEquipEvent;
import com.enjin.devection.legendary.LegendaryItem;
import com.enjin.devection.main.Main;

public class LegendaryListener implements Listener
{
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		if (event.getEntityType() == EntityType.PLAYER)
		{
			if (Main.getInstance().hasLegendary((Player) event.getEntity(), LegendaryItem.HELMET))
			{
				event.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event)
	{
		Player player = event.getPlayer();
		
		ItemStack newItem = event.getNewArmorPiece();
		ItemStack oldItem = event.getOldArmorPiece();
		
		if (newItem == null || (newItem != null && newItem.getType() == Material.AIR))
		{
			//De-Equipped legendary
			
			LegendaryItem legendary = LegendaryItem.fromItemStack(oldItem);
			
			if (legendary != null)
			{
				player.removePotionEffect(legendary.getPotion().getType());
			}
		} else
		{
			//Equipped legendary
			
			LegendaryItem legendary = LegendaryItem.fromItemStack(newItem);
			
			if (legendary != null)
			{
				player.addPotionEffect(legendary.getPotion());
			}
		}
	}
}