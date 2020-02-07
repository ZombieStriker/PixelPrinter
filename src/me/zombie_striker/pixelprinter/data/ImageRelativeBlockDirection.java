package me.zombie_striker.pixelprinter.data;

import org.bukkit.block.BlockFace;

public enum ImageRelativeBlockDirection {
	FRONT,SIDE,BACK,TOP,BOTTOM;

	public BlockFace convertToBlockFace(){
		if(this == FRONT)
			return BlockFace.EAST;
		if(this == BACK)
			return BlockFace.WEST;
		if(this == SIDE)
			return BlockFace.NORTH;
		if(this == TOP)
			return BlockFace.UP;
		if(this == BOTTOM)
			return BlockFace.DOWN;
		return null;
	}
}
