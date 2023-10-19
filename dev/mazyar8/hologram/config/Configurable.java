package dev.mazyar8.hologram.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface Configurable {

	void load(FileConfiguration fc);
	
	void save(FileConfiguration fc);
	
}
