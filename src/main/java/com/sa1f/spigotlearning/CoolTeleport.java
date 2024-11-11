package com.sa1f.spigotlearning;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.UUID;

public class CoolTeleport implements CommandExecutor {

	private Spigotlearning main;

	public CoolTeleport(Spigotlearning main){
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		Player player = (Player) sender;
		UUID playerUUID = player.getUniqueId();
		if (args.length < 1) {
			player.sendMessage("You need to provide either your username or a set of coordinates!");
			return false;
		}

		int delay;
		if (player.hasPermission("coolteleport.instant")) {
			delay = 0;
		} else if (player.hasPermission("coolteleport.short")) {
			delay = main.getConfig().getInt("delay.short", 2);
		} else {
			delay = main.getConfig().getInt("delay.long", 5);
		}


		if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				player.sendMessage("That player could not be found!");
				return false;
			}
			UUID targetUUID = target.getUniqueId();

			if (targetUUID == playerUUID) {
				Teleport(player, player.getLocation(), delay);
				return true;
			} else{
				player.sendMessage("You can only teleport to yourself!");
				return false;
			}

		} else if (args.length == 3) {
			try {
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				Teleport(player, new Location(player.getWorld(), x, y, z), delay);
				player.sendMessage("You teleported to " + x + ", " + y + ", " + z);
				return true;
			} catch (NumberFormatException e) {
				player.sendMessage("The Coordinates you gave are not numbers!");
				return false;
			}
		} else {
			player.sendMessage("You need to provide either your username or a set of coordinates!");
			return false;
		}
	}

		private void Teleport(Player player, Location location, int delay) {
			if (delay == 0) {
				player.teleport(location);
				player.sendMessage("You teleported instantly");
			} else {
				player.sendMessage("Teleporting in " + delay + " seconds");

				new BukkitRunnable() {
					@Override
					public void run() {
						player.teleport(location);
						player.sendMessage("Teleported to your destination!");
					}
				}.runTaskLater(main, delay * 20L); // Convert seconds to ticks
			}
		}
	}
