package com.enjin.devection.awaiting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.enjin.devection.legendary.LegendaryItem;
import com.enjin.devection.main.Main;

public class AwaitingLegendaries
{
	private File awaitingFile;
	private YamlConfiguration awaitingConfig;
	
	private Map<UUID, List<LegendaryItem>> awaitingLegendaries = new HashMap<UUID, List<LegendaryItem>>();
	
	public AwaitingLegendaries()
	{
		awaitingFile = new File(Main.getInstance().getDataFolder(), "awaiting.yml");
		awaitingFile.getParentFile().mkdirs();
		
		awaitingConfig = YamlConfiguration.loadConfiguration(awaitingFile);
		
		Object awaitingMap = awaitingConfig.get("awaiting_legendaries");
		
		if (awaitingMap instanceof Map)
		{
			deserialize(awaitingMap);
		}
	}
	
	public Map<UUID, List<LegendaryItem>> getAwaiting()
	{
		return awaitingLegendaries;
	}
	
	public void addAwaiting(UUID uuid, LegendaryItem item)
	{
		List<LegendaryItem> items = new ArrayList<LegendaryItem>();
		
		if (awaitingLegendaries.containsKey(uuid)) items = awaitingLegendaries.get(uuid);
		
		items.add(item);
		awaitingLegendaries.put(uuid, items);
		
		save();
	}
	
	@SuppressWarnings("deprecation")
	public void redeemLegendaries(Player player)
	{
		UUID uuid = player.getUniqueId();
		
		if (awaitingLegendaries.containsKey(uuid))
		{
			List<LegendaryItem> items = awaitingLegendaries.get(uuid);
			
			if (emptySlots(player.getInventory()) >= items.size())
			{
				for (LegendaryItem item : items)
				{
					player.getInventory().addItem(item.getItem());
				}
				
				player.updateInventory();
				player.sendMessage(Main.PRETTY_PREFIX + ChatColor.GREEN + "Added the legendary items to your inventory!");
				
				awaitingLegendaries.remove(uuid);
			} else
			{
				int errorCount = 0;
				
				List<LegendaryItem> added = new ArrayList<LegendaryItem>();
				List<LegendaryItem> errors = new ArrayList<LegendaryItem>();
				
				for (LegendaryItem item : items)
				{
					if (item.getInventoryItem(player) == null)
					{
						added.add(item);
						item.setInventoryItem(player, item.getItem());
					} else
					{
						errorCount++;
						
						errors.add(item);
					}
				}
				
				if (errorCount == 0)
				{
					player.sendMessage(Main.PRETTY_PREFIX + ChatColor.GREEN + "Placed the legendary items in your armor slots!");
				} else if (errorCount != items.size())
				{
					player.sendMessage(Main.PRETTY_PREFIX + ChatColor.GREEN + "Added " + StringUtils.join(added, ", ") + " to your armor slots!");
					player.sendMessage(Main.PRETTY_PREFIX + ChatColor.RED + "Please clear out slots for " + StringUtils.join(errors, ", "));
				} else
				{
					player.sendMessage(Main.PRETTY_PREFIX + ChatColor.RED + "You do not have enough space in your inventory...");
					player.sendMessage(Main.PRETTY_PREFIX + ChatColor.RED + "Please clear out " + items.size() + " slot" + (items.size() == 1 ? "" : "s") + "!");
				}
				
				awaitingLegendaries.put(uuid, errors);
			}
			
			save();
		}
	}
	
	private int emptySlots(PlayerInventory inv)
	{
		int empty = 0;
		
		for (ItemStack item : inv.getContents()) if (item == null) empty++;
		
		return empty;
	}
	
	public void save()
	{
		awaitingConfig.set("awaiting_legendaries", serialize());
		
		try
		{
			awaitingConfig.save(awaitingFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		awaitingLegendaries.clear();
	}
	
	private Map<String, List<LegendaryItem>> serialize()
	{
		Map<String, List<LegendaryItem>> awaitingLegendariesConfig = new HashMap<String, List<LegendaryItem>>();
		
		for (Map.Entry<UUID, List<LegendaryItem>> awaiting : awaitingLegendaries.entrySet())
		{
			awaitingLegendariesConfig.put(awaiting.getKey().toString(), awaiting.getValue());
		}
		
		return awaitingLegendariesConfig;
	}
	
	@SuppressWarnings("unchecked")
	private void deserialize(Object awaitingMap)
	{
		Map<String, List<LegendaryItem>> awaitingLegendariesConfig = (Map<String, List<LegendaryItem>>) awaitingMap;
		
		for (Map.Entry<String, List<LegendaryItem>> awaiting : awaitingLegendariesConfig.entrySet())
		{
			awaitingLegendaries.put(UUID.fromString(awaiting.getKey()), awaiting.getValue());
		}
	}
}