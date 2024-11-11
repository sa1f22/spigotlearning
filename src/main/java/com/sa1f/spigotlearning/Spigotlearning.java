package com.sa1f.spigotlearning;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.UUID;

public  class Spigotlearning extends JavaPlugin implements Listener {
	public HashMap<UUID,UUID> recentmessages = new HashMap<>();


	@Override
	public void onEnable() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new Togglelistener(), this);
		getCommand("spawn").setExecutor(new SpawnEntityCommand());
		getCommand("book").setExecutor(new BookCommand());
		getCommand("msg2").setExecutor(new MessageCommand(this));
		//getCommand("reply").setExecutor(new ReplyCommand(this));
		getCommand("coolteleport").setExecutor(new CoolTeleport(this));
		// Plugin startup logic

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (player.getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)) {
					player.launchProjectile(Egg.class, player.getLocation().getDirection());
				}
			}
		}
	}
}
