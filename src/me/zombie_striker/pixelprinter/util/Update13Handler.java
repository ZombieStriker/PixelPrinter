package me.zombie_striker.pixelprinter.util;

import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

public class Update13Handler {
	
	public static boolean isFacing(BlockState bs, BlockFace bf2) {
		return (((org.bukkit.block.data.Directional) bs.getBlock()
				.getBlockData()).getFacing() != bf2);
	}
	
	public static void setFacing(BlockState bs, BlockFace bf) {
		org.bukkit.block.data.Directional d = ((org.bukkit.block.data.Directional) bs.getBlockData());
		d.setFacing(bf);
		bs.setBlockData(d);
		bs.update(true, false);
	}

}
