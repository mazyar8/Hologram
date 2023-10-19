package dev.mazyar8.hologram.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mazyar8.hologram.Hologram;
import dev.mazyar8.hologram.entity.EntityHologram;
import dev.mazyar8.hologram.permissions.Permissions;
import dev.mazyar8.hologram.util.ColorUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class HologramCommand implements CommandExecutor {
	
	private String COMMAND_USAGE_FORMAT = ChatColor.RED + "Usage: /{command} <add/list/movehere/teleport/remove>";
	private String SUBCOMMAND_USAGE_FORMAT_ADD = ChatColor.RED + "Usage: /{command} add <name> <lines>";
	private String SUBCOMMAND_USAGE_FORMAT_MOVEHERE = ChatColor.RED + "Usage: /{command} movehere <name>";
	private String SUBCOMMAND_USAGE_FORMAT_TELEPORT = ChatColor.RED + "Usage: /{command} teleport <name>";
	private String SUBCOMMAND_USAGE_FORMAT_REMOVE = ChatColor.RED + "Usage: /{command} remove <name>";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String message, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "just players allow to use this command.");
			return false;
		}
		
		Player player = (Player) sender;
		if (!player.hasPermission(Permissions.COMMAND_HOLOGRAM)) {
			sendNoPermissionMessage(player);
			return false;
		}
		
		if (args.length > 0) {
			switch (args[0].toLowerCase()) {
			case "add":
				if (player.hasPermission(Permissions.COMMAND_HOLOGRAM)) {
					if (args.length > 2) {
						if (Hologram.getHologram().getHologramManager().getHologram(args[1]) == null) {
							String[] lines = new String[args.length - 2];
							for (int i = 0; i < lines.length; i++)
								lines[i] = format(args[i + 2]);
							EntityHologram hologram = new EntityHologram(args[1], player.getLocation(), lines);
							Hologram.getHologram().getHologramManager().addHologram(hologram);
						} else {
							player.sendMessage(ChatColor.RED + "there is a hologram with this name.");
						}
					} else {
						sendCommandUsageToPlayer(player, SUBCOMMAND_USAGE_FORMAT_ADD, message);
					}
				} else {
					sendNoPermissionMessage(player);
				}
				break;
			case "list":
				for (EntityHologram eh : Hologram.getHologram().getHologramManager().getHolograms()) {
					BaseComponent[] components = new BaseComponent[] {getTextComponent("teleport", "/hologram teleport " + eh.getName()), getTextComponent("movehere", "/hologram movehere " + eh.getName()), getTextComponent("remove", "/hologram remove " + eh.getName())};
					ComponentBuilder componentBuilder = new ComponentBuilder(eh.getName());
					for (BaseComponent c : components) {
						componentBuilder.append(" ");
						componentBuilder.append(c);
					}
					player.spigot().sendMessage(componentBuilder.create());
				}
				break;
			case "movehere":
				if (player.hasPermission(Permissions.COMMAND_HOLOGRAM)) {
					if (args.length > 1) {
						EntityHologram eh = Hologram.getHologram().getHologramManager().getHologram(args[1]);
						if (eh != null)
							eh.setLocation(player.getLocation());
						else
							player.sendMessage(ChatColor.RED + "hologram with that name does not exist.");
					} else {
						sendCommandUsageToPlayer(player, SUBCOMMAND_USAGE_FORMAT_MOVEHERE, message);
					}
				} else {
					sendNoPermissionMessage(player);
				}
				break;
			case "teleport":
				if (player.hasPermission(Permissions.COMMAND_HOLOGRAM)) {
					if (args.length > 1) {
						EntityHologram eh = Hologram.getHologram().getHologramManager().getHologram(args[1]);
						if (eh != null)
							player.teleport(eh.getLocation().clone().add(0, 1, 0));
						else
							player.sendMessage(ChatColor.RED + "hologram with that name does not exist.");
					} else {
						sendCommandUsageToPlayer(player, SUBCOMMAND_USAGE_FORMAT_TELEPORT, message);
					}
				} else {
					sendNoPermissionMessage(player);
				}
				break;
			case "remove":
				if (player.hasPermission(Permissions.COMMAND_HOLOGRAM)) {
					if (args.length > 1) {
						EntityHologram eh = Hologram.getHologram().getHologramManager().getHologram(args[1]);
						if (eh != null)
							Hologram.getHologram().getHologramManager().removeHologram(eh);
						else
							player.sendMessage(ChatColor.RED + "hologram with that name does not exist.");
					} else {
						sendCommandUsageToPlayer(player, SUBCOMMAND_USAGE_FORMAT_REMOVE, message);
					}
				} else {
					sendNoPermissionMessage(player);
				}
				break;
			}
		} else {
			sendCommandUsageToPlayer(player, COMMAND_USAGE_FORMAT, message);
		}
		return true;
	}
	
	private void sendCommandUsageToPlayer(Player player, String usage, String cmd) {
		player.sendMessage(usage.replace("{command}", cmd));
	}
	
	private String format(String l) {
		return ColorUtil.applyColor(l.replace("_", " "));
	}
	
	private TextComponent getTextComponent(String text, String command) {
		TextComponent textComponent = new TextComponent(ChatColor.UNDERLINE + text);
		textComponent.setClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
		return textComponent;
	}
	
	private void sendNoPermissionMessage(Player player) {
		player.sendMessage(ChatColor.RED + "you don't have access to that command.");
	}

}
