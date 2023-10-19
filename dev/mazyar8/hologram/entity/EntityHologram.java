package dev.mazyar8.hologram.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class EntityHologram {

	private String name;
	private ArmorStand[] armorStands;

	public EntityHologram(String name, Location location, String... lines) {
		this.name = name;
		this.armorStands = new ArmorStand[lines.length];
		int i = 0;
		for (String l : lines) {
			ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0, (lines.length * 0.25) - i * 0.25 - 1, 0), EntityType.ARMOR_STAND);
			as.setVisible(false);
			as.setGravity(false);
			as.setInvulnerable(true);
			as.setCustomName(l);
			as.setCustomNameVisible(true);
			armorStands[i] = as;
			i++;
		}
	}
	
	public void remove() {
		for (ArmorStand as : armorStands)
			as.remove();
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return armorStands[0].getLocation();
	}
	
	public void setLocation(Location location) {
		int i = 0;
		for (ArmorStand as : armorStands) {
			as.teleport(location.clone().add(0, (armorStands.length * 0.25) - i * 0.25 - 1, 0));
			i++;
		}
	}
	
	public List<String> getLines() {
		List<String> lines = new ArrayList<>();
		for (ArmorStand as : armorStands)
			lines.add(as.getName());
		return lines;
	}
	
}
