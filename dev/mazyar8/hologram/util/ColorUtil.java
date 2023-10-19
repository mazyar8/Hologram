package dev.mazyar8.hologram.util;

import org.bukkit.ChatColor;

public class ColorUtil {

	public static String applyColor(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
