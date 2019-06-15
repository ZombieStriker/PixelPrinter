package me.zombie_striker.pixelprinter.data;

import me.zombie_striker.pixelprinter.PixelPrinter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class DataHolder implements ConfigurationSerializable {
	public MaterialData md;
	public Location b;
	boolean hasFaces = false;

	public DataHolder(Location b2, MaterialData md) {
		this.b = b2;
		this.md = md;
	}

	public DataHolder(Location b, MaterialData md, boolean hasFaces) {
		this.b = b;
		this.md = md;
		this.hasFaces = hasFaces;
	}

	public DataHolder(Map<String, Object> data) {
		final Map<String, Object> tempData = data;
		new BukkitRunnable() {

			@Override
			public void run() {
				if (Bukkit.getWorld((String) tempData.get("b.w")) != null) {
					b = new Location(Bukkit.getWorld((String) tempData.get("b.w")), (int) tempData.get("b.x"),
							(int) tempData.get("b.y"), (int) tempData.get("b.z"));
					cancel();
				}
			}
		}.runTaskTimer(PixelPrinter.getInstance(), 0, 20);
		this.md = (MaterialData) data.get("md");
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("b.x", this.b.getBlockX());
		data.put("b.y", this.b.getBlockY());
		data.put("b.z", this.b.getBlockZ());
		data.put("b.w", this.b.getWorld().getName());
		data.put("md", this.md);
		return data;
	}

	public boolean hasFaces() {
		return hasFaces;
	}
}
