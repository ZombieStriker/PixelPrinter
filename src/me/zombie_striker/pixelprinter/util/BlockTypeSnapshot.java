package me.zombie_striker.pixelprinter.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class BlockTypeSnapshot {

	public HashMap<Location, BlockState> originalBlockState = new HashMap<>();

	public BlockTypeSnapshot(Location start, Location stop) {
		World world = start.getWorld();
		for (int x = Math.min(start.getBlockX(), stop.getBlockX()); x < Math.max(start.getBlockX(), stop.getBlockX())
				+ 1; x++) {
			for (int y = Math.min(start.getBlockY(), stop.getBlockY()); y < Math.min(256,
					Math.max(start.getBlockY(), stop.getBlockY())) + 1; y++) {
				for (int z = Math.min(start.getBlockZ(), stop.getBlockZ()); z < Math.max(start.getBlockZ(),
						stop.getBlockZ()) + 1; z++) {
					Block b = world.getBlockAt(x, y, z);
					originalBlockState.put(b.getLocation(), b.getState());
				}
			}
		}
	}

	public void undo() {
		HashMap<Location, BlockState> newOnes = new HashMap<>();
		for (Entry<Location, BlockState> e : originalBlockState.entrySet()) {
			if (!e.getKey().getBlock().getState().equals(e.getValue())) {
				newOnes.put(e.getKey(), e.getKey().getBlock().getState());
				e.getValue().update(true, false);
			}
		}
		this.originalBlockState = newOnes;
	}
}
