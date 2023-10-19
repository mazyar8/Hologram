package dev.mazyar8.hologram;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mazyar8.hologram.command.HologramCommand;
import dev.mazyar8.hologram.entity.EntityHologramManager;
import dev.mazyar8.hologram.permissions.Permissions;

public class Hologram extends JavaPlugin {
	
	private static Hologram hologram;
	private EntityHologramManager hologramManager;

	@Override
	public void onEnable() {
		hologram = this;
		hologramManager = new EntityHologramManager();
		addCommand("hologram", new HologramCommand());
		addPermission(Permissions.COMMAND_HOLOGRAM);
		addPermission(Permissions.SUBCOMMAND_ADD);
		addPermission(Permissions.SUBCOMMAND_MOVEHERE);
		addPermission(Permissions.SUBCOMMAND_TELEPORT);
		addPermission(Permissions.SUBCOMMAND_REMOVE);
		load();
	}
	
	@Override
	public void onDisable() {
		save();
	}
	
	public void load() {
		hologramManager.load(getConfig());
	}
	
	public void save() {
		hologramManager.save(getConfig());
		saveConfig();
	}
	
	/** add new command executor to server */
	public void addCommand(String cmd, CommandExecutor executor) {
		Bukkit.getPluginCommand(cmd).setExecutor(executor);
	}
	
	/** add new permission to server */
	public void addPermission(Permission permission) {
		Bukkit.getPluginManager().addPermission(permission);
	}

	public static Hologram getHologram() {
		return hologram;
	}

	public EntityHologramManager getHologramManager() {
		return hologramManager;
	}
	
}
