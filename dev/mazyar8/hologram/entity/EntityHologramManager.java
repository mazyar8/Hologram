package dev.mazyar8.hologram.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import dev.mazyar8.hologram.config.Configurable;

public class EntityHologramManager implements Configurable {
	
	private List<EntityHologram> holograms = new ArrayList<>();

	@Override
	public void load(FileConfiguration fc) {
		for (String s : fc.getKeys(true)) {
			String[] args = s.replace(".", " ").split(" ");
			if (args.length == 2) {
				String linesPath = s.concat(".lines"), locationPath = s.concat(".location");
				if (fc.isSet(linesPath) && fc.isSet(locationPath)) {
					Location location = (Location) fc.get(locationPath);
					List<String> lines = fc.getStringList(linesPath);
					String[] linesArray = new String[lines.size()];
					int i = 0;
					for (String l : lines) {
						linesArray[i] = l;
						i++;
					}
					holograms.add(new EntityHologram(args[1], location, linesArray));
				}
			}
		}
	}

	@Override
	public void save(FileConfiguration fc) {
		for (EntityHologram eh : holograms) {
			fc.set("hologram.".concat(eh.getName()) + ".location", eh.getLocation());
			fc.set("hologram.".concat(eh.getName()) + ".lines", eh.getLines());
			eh.remove();
		}
	}
	
	/** add new hologram to list */
	public void addHologram(EntityHologram hologram) {
		holograms.add(hologram);
	}
	
	/** get hologram by name */
	public EntityHologram getHologram(String name) {
		for (EntityHologram eh : holograms)
			if (eh.getName().toLowerCase().equals(name.toLowerCase()))
				return eh;
		return null;
	}
	
	/** remove the hologram from list */
	public boolean removeHologram(EntityHologram hologram) {
		hologram.remove();
		return holograms.remove(hologram);
	}

	public List<EntityHologram> getHolograms() {
		return holograms;
	}

}
