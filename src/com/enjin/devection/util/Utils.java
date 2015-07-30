package com.enjin.devection.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;

public class Utils
{
	public static void addToOfflineInventory(OfflinePlayer offlinePlayer, ItemStack item)
	{
		Player player = loadPlayer(offlinePlayer.getUniqueId());
		Inventory inv = player.getInventory();
		
		inv.addItem(item);
		
		player.saveData();
	}
	
	private static Player loadPlayer(UUID uuid)
	{
		try
		{
			OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
			
			if (player == null || !player.hasPlayedBefore())
			{
				return null;
			}
			
			GameProfile profile = new GameProfile(uuid, player.getName());
			MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
			EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), profile, new PlayerInteractManager(server.getWorldServer(0)));
			
			Player target = entity.getBukkitEntity();
			
			if (target != null)
			{
				target.loadData();
				
				return target;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}