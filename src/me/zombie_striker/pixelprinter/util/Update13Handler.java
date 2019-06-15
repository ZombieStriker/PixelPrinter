package me.zombie_striker.pixelprinter.util;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

public class Update13Handler {

	public static boolean isFacing(BlockState bs, BlockFace bf2) {
		try {
			return (((org.bukkit.block.data.Directional) bs.getBlock().getBlockData()).getFacing() == bf2);
		} catch (Error | Exception e45) {
		}
		return false;
	}

	public static BlockFace getFacing(BlockState bs) {
		try {
			return (((org.bukkit.block.data.Directional) bs.getBlock().getBlockData()).getFacing());
		} catch (Error | Exception e45) {
		}
		return null;
	}

	public static boolean isDirectional(BlockState bs) {
		try {
			return (bs.getBlock().getBlockData() instanceof org.bukkit.block.data.Directional);
		} catch (Error | Exception e45) {
		}
		return false;
	}

	public static void setFacing(BlockState bs, BlockFace bf) {
		try {
			org.bukkit.block.data.Directional d = ((org.bukkit.block.data.Directional) bs.getBlockData());
			d.setFacing(bf);
			bs.setBlockData(d);
			bs.update(true, false);
		} catch (Error | Exception e45) {

		}
	}

}
