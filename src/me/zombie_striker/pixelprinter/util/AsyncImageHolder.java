/*
 *  Copyright (C) 2017 Zombie_Striker
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 */

package me.zombie_striker.pixelprinter.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.zombie_striker.pixelprinter.PixelPrinter;
import me.zombie_striker.pixelprinter.data.*;
import me.zombie_striker.pixelprinter.util.RGBBlockColor.Pixel;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncImageHolder extends Image {

	private final Pixel[][] result;
	private final BufferedImage bi;
	private String name = null;

	public AsyncImageHolder(Pixel[][] result1, Player p1, Location loc1, Direction dir1, BufferedImage bi1,
			boolean enableTrans) {
		this(null, result1, p1, loc1, dir1, bi1, enableTrans);
	}

	public AsyncImageHolder(String name, Pixel[][] result1, Player p1, Location loc1, Direction dir1, BufferedImage bi1,
			boolean enableTrans) {
		p = (p1 == null ? "Plugin" : p1.getName());
		result = result1;
		dir = dir1;
		bi = bi1;
		minCorner = loc1;
		neg = isMinUpNeg(dir);
		moving = isMovingX(dir);
		this.enableTransparent = enableTrans;

	}

	@SuppressWarnings("deprecation")
	public void loadImage(boolean allowUndo) {

		final IntHolder isDone = new IntHolder();
		isDone.setI(0);
		for (Player p2 : minCorner.getWorld().getPlayers())
			p2.sendMessage(PixelPrinter.getInstance().getPrefix() + " Loading image requested by " + p);
		Location start = getBlockAt(0, bi.getHeight(), bi.getHeight());
		Location end = getBlockAt(0, 0, bi.getHeight());
		UndoUtil.addNewSnapshot(name, start, end);
		new BukkitRunnable() {
			public void run() {
				final HashMap<String, List<DataHolder>> chunksorter = new HashMap<String, List<DataHolder>>();

				for (int width = 0; width < (bi.getWidth()); width += 2) {
					for (int height = (bi.getHeight() - 1); height >= 0; height -= 2) {
						Location b = getBlockAt(height, width, bi.getHeight());
						if (b == null || b.getY() > 256) {
							continue;
						}
						Color[] color = new Color[4];
						for (int i = 0; i < 4; i++) {
							int y = (height + 1 < result.length) ? height + (i % 2) : height;
							int x = (width + 1 < result[y].length) ? width + (i % 2) : width;
							color[i] = new Color(result[y][x].r, result[y][x].g, result[y][x].b, result[y][x].a);
						}
						MaterialData m = RGBBlockColor.getClosestBlockValue(color,
								(dir == Direction.FLAT_NORTHEAST || dir == Direction.FLAT_NORTHWEST
										|| dir == Direction.FLAT_SOUTHEAST || dir == Direction.FLAT_SOUTHWEST),
								enableTransparent, PixelPrinter.getInstance().supportedMaterials);
						String tempkey = (b.getBlockX() / 16) + "," + (b.getBlockZ() / 16);
						if (chunksorter.containsKey(tempkey)) {
							List<DataHolder> temp = chunksorter.get(tempkey);
							if (temp == null)
								temp = new ArrayList<DataHolder>();
							temp.add(new DataHolder(b, m, m.hasDirection()));
							chunksorter.put(tempkey, temp);
						} else {
							List<DataHolder> temp = new ArrayList<DataHolder>();
							temp.add(new DataHolder(b, m));
							chunksorter.put(tempkey, temp);
						}
					}
				}
				int delayLoadingMessage = 0;
				final int maxDelay = 7;
				int timesTicked = 0;

				final IntHolder blocksUpdated = new IntHolder();
				final IntHolder didNotHaveToReplace = new IntHolder();

				for (Entry<String, List<DataHolder>> ent : chunksorter.entrySet()) {
					final List<DataHolder> gg = ent.getValue();
					timesTicked++;
					final int tempDel = delayLoadingMessage++;
					delayLoadingMessage %= maxDelay;
					final int currTick = timesTicked;

					new BukkitRunnable() {
						@Override
						public void run() {
							for (final DataHolder dh : gg) {
								final BlockState bs = dh.b.getBlock().getState();

								if (dh.md.getMaterial() != Material.AIR) {

									byte rd = dh.md.getData();
									BlockFace bf = null;

									if (dh.hasFaces()) {
										bf = getBlockFace(dh, dir);
										rd = getBlockData(dh, dir);
									}
									if (dh.md.getMaterial() != bs.getType()
											|| (((int) bs.getRawData()) != ((int) rd))) {
										if (PixelPrinter.isAbove113) {
											bs.getBlock().setType(dh.md.getMaterial());
											if (bf != null) {
												Update13Handler.setFacing(bs, bf);
											}
										} else {
											bs.setType(dh.md.getMaterial());
											if (bs.getRawData() != rd)
												bs.setRawData(rd);
											bs.update(true, false);
										}
										if (dh.md.getMaterial().hasGravity()) {
											Block below = bs.getBlock().getLocation().subtract(0, 1, 0).getBlock();
											if (below.getType() == Material.AIR)
												below.setType(Material.STONE);
										}
										blocksUpdated.setI(blocksUpdated.getI() + 1);
										final BlockFace bf2 = bf;
										new BukkitRunnable() {

											@Override
											public void run() {
												boolean tryBoolean = Update13Handler.isFacing(bs, bf2);

												if (bs.getBlock().getType() != dh.md.getMaterial()
														|| ((bf2 != null) ? (!tryBoolean)
																: (PixelPrinter.isAbove113 ? false
																		: bs.getBlock().getData() != dh.md.getData()))
														|| bs.getBlock().getType() == Material.AIR) {
													if (bs.getBlock().getType().name().equals("VOID_AIR"))
														return;
													BlockFace test = null;
													if (Update13Handler.isDirectional(bs.getBlock().getState())) {
														test = Update13Handler.getFacing(bs.getBlock().getState());
													}
													for (Player p2 : minCorner.getWorld().getPlayers()) {
														p2.sendMessage(PixelPrinter.getInstance().getPrefix()
																+ "Incorrect value: " + dh.md.getMaterial().name() + ":"
																+ ((bf2 != null) ? bf2.name() : dh.md.getData())
																+ " is " + bs.getBlock().getType() + ":"
																+ (test != null ? test.name() : bs.getBlock().getData())
																+ " at " + bs.getBlock().getLocation().getBlockX() + ","
																+ bs.getBlock().getLocation().getBlockY() + ","
																+ bs.getBlock().getLocation().getBlockZ());
													}
												}
											}
										}.runTaskLater(PixelPrinter.getInstance(), 20 * 3);
									} else {
										didNotHaveToReplace.setI(2);
									}
								}
							}
							if (tempDel == 0)
								for (Player p2 : minCorner.getWorld().getPlayers())
									p2.sendMessage(PixelPrinter.getInstance().getPrefix() + " Loading: "
											+ ((int) (((double) currTick) / chunksorter.size() * 100)) + "%");

						}
					}.runTaskLater(PixelPrinter.getInstance(), 3 * timesTicked);

				}

				new BukkitRunnable() {
					@Override
					public void run() {
						for (Player p2 : minCorner.getWorld().getPlayers()) {
							p2.sendMessage(PixelPrinter.getInstance().getPrefix() + " Done!"
									+ (didNotHaveToReplace.getI() == 2 ? " Updated " + blocksUpdated.getI() + " blocks."
											: ""));
						}
					}
				}.runTaskLater(PixelPrinter.getInstance(), 3 * timesTicked);
			}
		}.runTaskAsynchronously(PixelPrinter.getInstance());
	}
	
	public static BlockFace getBlockFace(DataHolder dh, Direction dir) {

		try {
			if (dh.md.getMaterial() == Material.FURNACE || dh.md.getMaterial().name().equals("BURNING_FURNACE")) {
				// Go eat, then we need south.
				// Go south, then face west, ect.
				if (dir == Direction.UP_EAST)
					return BlockFace.SOUTH;
				if (dir == Direction.UP_SOUTH)
					return BlockFace.WEST;
				if (dir == Direction.UP_WEST)
					return BlockFace.NORTH;
				if (dir == Direction.UP_NORTH)
					return BlockFace.EAST;
			}
			if (dh.md.getMaterial() == Material.DISPENSER) {
				// Go eat, then we need south.
				// Go south, then face west, ect.
				if (dir == Direction.UP_EAST)
					return BlockFace.SOUTH;
				if (dir == Direction.UP_SOUTH)
					return BlockFace.WEST;
				if (dir == Direction.UP_WEST)
					return BlockFace.NORTH;
				if (dir == Direction.UP_NORTH)
					return BlockFace.EAST;

			}

			if (dh.md.getMaterial() == Material.OBSERVER) {
				if (dh.md.getDirection() == BlockFace.EAST) {
					if (PixelPrinter.isAbove113) {
						if (dir == Direction.UP_EAST)
							return BlockFace.SOUTH;
						if (dir == Direction.UP_SOUTH)
							return BlockFace.WEST;
						if (dir == Direction.UP_WEST)
							return BlockFace.NORTH;
						if (dir == Direction.UP_NORTH)
							return BlockFace.EAST;
					}

					if (dh.md.getDirection() == BlockFace.WEST) {
						// Go eat, then we need south.
						// Go south, then face west, ect.
						if (dir == Direction.UP_EAST)
							return BlockFace.NORTH;
						if (dir == Direction.UP_SOUTH)
							return BlockFace.EAST;
						if (dir == Direction.UP_WEST)
							return BlockFace.SOUTH;
						if (dir == Direction.UP_NORTH)
							return BlockFace.WEST;
					}

					if (dh.md.getDirection() == BlockFace.NORTH) {
						// Go eat, then we need south.
						// Go south, then face west, ect.
						if (dir == Direction.UP_EAST)
							return BlockFace.EAST;
						if (dir == Direction.UP_SOUTH)
							return BlockFace.SOUTH;
						if (dir == Direction.UP_WEST)
							return BlockFace.WEST;
						if (dir == Direction.UP_NORTH)
							return BlockFace.NORTH;
					}
				}
			}
		} catch (Error | Exception e54) {
		}
		return null;
	}

	public static byte getBlockData(DataHolder dh, Direction dir) {

		try {
			if (dh.md.getMaterial().name().endsWith("_DOOR") && !dh.md.getMaterial().name().contains("TRAP")) {
				// org.bukkit.material.Door door = (org.bukkit.material.Door)
				// bs.getData();
				if (dir == Direction.UP_NORTH)
					return ((byte) 2);
				if (dir == Direction.UP_EAST)
					return ((byte) 3);
				if (dir == Direction.UP_SOUTH)
					return ((byte) 0);
				if (dir == Direction.UP_WEST)
					return ((byte) 1);
			}
			if (dh.md.getMaterial() == Material.FURNACE || dh.md.getMaterial().name().equals("BURNING_FURNACE")) {
				// Go eat, then we need south.
				// Go south, then face west, ect.
				if (dir == Direction.UP_NORTH)
					return ((byte) 5);
				if (dir == Direction.UP_EAST)
					return ((byte) 3);
				if (dir == Direction.UP_SOUTH)
					return ((byte) 4);
				if (dir == Direction.UP_WEST)
					return ((byte) 2);
			}

			if (dh.md.getMaterial() == Material.DISPENSER) {
				// Go eat, then we need south.
				// Go south, then face west, ect.
				if (dir == Direction.UP_NORTH)
					return ((byte) 5);
				if (dir == Direction.UP_EAST)
					return ((byte) 3);
				if (dir == Direction.UP_SOUTH)
					return ((byte) 4);
				if (dir == Direction.UP_WEST)
					return ((byte) 2);

			}
			if (dh.md.getMaterial().name().equals("PISTON_BASE")
					|| dh.md.getMaterial().name().equals("PISTON_STICKY_BASE")) {
				if (dh.md.getDirection() == BlockFace.UP) {
					if (dir == Direction.UP_NORTH)
						return ((byte) 5);
					if (dir == Direction.UP_EAST)
						return ((byte) 3);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 4);
					if (dir == Direction.UP_WEST)
						return ((byte) 2);
				}
				if (dh.md.getDirection() == BlockFace.WEST) {
					if (dir == Direction.UP_NORTH)
						return ((byte) 4);
					if (dir == Direction.UP_EAST)
						return ((byte) 2);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 5);
					if (dir == Direction.UP_WEST)
						return ((byte) 3);
				}
			}
			if (dh.md.getMaterial() == Material.PUMPKIN || dh.md.getMaterial() == Material.JACK_O_LANTERN) {
				if (dh.md.getDirection() == BlockFace.EAST) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return ((byte) 3);
					if (dir == Direction.UP_EAST)
						return ((byte) 0);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 1);
					if (dir == Direction.UP_WEST)
						return ((byte) 2);
				}

				if (dh.md.getDirection() == BlockFace.WEST) {
					if (dir == Direction.UP_NORTH)
						return ((byte) 0);
					if (dir == Direction.UP_EAST)
						return ((byte) 3);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 2);
					if (dir == Direction.UP_WEST)
						return ((byte) 1);
				}

			}

			if (dh.md.getMaterial() == Material.OBSERVER) {
				if (dh.md.getDirection() == BlockFace.EAST) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return ((byte) 4);
					if (dir == Direction.UP_EAST)
						return ((byte) 2);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 5);
					if (dir == Direction.UP_WEST)
						return ((byte) 3);

				}

				if (dh.md.getDirection() == BlockFace.WEST) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return ((byte) 5);
					if (dir == Direction.UP_EAST)
						return ((byte) 3);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 4);
					if (dir == Direction.UP_WEST)
						return ((byte) 2);
				}

				if (dh.md.getDirection() == BlockFace.NORTH) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return ((byte) 3);
					if (dir == Direction.UP_EAST)
						return ((byte) 5);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 2);
					if (dir == Direction.UP_WEST)
						return ((byte) 4);
				}

			}
		} catch (Error | Exception e54) {
		}
		return dh.md.getData();
	}
}
