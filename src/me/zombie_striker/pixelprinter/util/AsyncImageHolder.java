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
import me.zombie_striker.pluginconstructor.*;
import me.zombie_striker.pluginconstructor.RGBBlockColor.Pixel;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncImageHolder extends Image {

	private final Pixel[][] result;
	private final BufferedImage bi;

	public AsyncImageHolder(Pixel[][] result1, Player p1, Location loc1, Direction dir1, BufferedImage bi1,
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
	public void loadImage() {

		// final List<DataHolder> holders = new ArrayList<>();
		final IntHolder isDone = new IntHolder();
		// final int fHeight = bi.getHeight() / 2;
		isDone.setI(0);
		for (Player p2 : minCorner.getWorld().getPlayers())
			p2.sendMessage(PixelPrinter.getInstance().getPrefix() + " Loading image requested by " + p);
		new BukkitRunnable() {
			public void run() {
				final HashMap<String, List<DataHolder>> chunksorter = new HashMap<String, List<DataHolder>>();

				for (int width = 0; width < (bi.getWidth()); width += 2) {
					for (int height = (bi.getHeight() - 1); height >= 0; height -= 2) {
						Location b = getBlockAt(height, width, bi.getHeight());
						if (b == null || b.getY() > 260) {
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
							if(temp==null)
								temp = new ArrayList<DataHolder>();
							temp.add(new DataHolder(b, m, m.hasDirection()));
							chunksorter.put(tempkey, temp);
						} else {
							List<DataHolder> temp = new ArrayList<DataHolder>();
							temp.add(new DataHolder(b, m));
							chunksorter.put(tempkey, temp);
						}
						// holders.add(new DataHolder(b, m));
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
										rd=getBlockData(dh, dir);
									}
									if (dh.md.getMaterial() != bs.getType()
											|| (((int) bs.getRawData()) != ((int) rd))) {
										if (PixelPrinter.isAbove113) {
											bs.getBlock().setType(dh.md.getMaterial());
											try {
												if (bf != null) {
													org.bukkit.block.data.Directional d = ((org.bukkit.block.data.Directional) bs
															.getBlock().getBlockData());
													d.setFacing(bf);
													bs.getBlock().setBlockData(d);
												}
											} catch (Error | Exception e45) {
												e45.printStackTrace();
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
												if (bs.getBlock().getType() != dh.md.getMaterial()
														|| (PixelPrinter.isAbove113
																? (bf2 != null
																		? (((org.bukkit.block.data.Directional) bs
																				.getBlock().getBlockData())
																						.getFacing() != bf2)
																		: false)
																: bs.getBlock().getData() != dh.md.getData())
														|| bs.getBlock().getType() == Material.AIR)
													Bukkit.broadcastMessage(PixelPrinter.getInstance().getPrefix()
															+ "Incorrect value: " + dh.md.getMaterial().name() + ":"
															+ dh.md.getData() + " is " + bs.getBlock().getType() + ":"
															+ bs.getBlock().getData() + " at "
															+ bs.getBlock().getLocation().getBlockX() + ","
															+ bs.getBlock().getLocation().getBlockY() + ","
															+ bs.getBlock().getLocation().getBlockZ());
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

				/*
				 * new BukkitRunnable() {
				 * 
				 * @Override public void run() { Chunk previusChunk =
				 * holders.get(0).b.getChunk(); int lastUsedInt = 0; int timesTicked = 0; for
				 * (int i = 0; i < holders.size(); i++) { if ((previusChunk.getX() !=
				 * holders.get(i).b .getChunk().getX() || previusChunk.getZ() != holders
				 * .get(i).b.getChunk().getZ()) || (i == holders.size() - 1)) { previusChunk =
				 * holders.get(i).b.getChunk(); timesTicked++; final int starting = lastUsedInt;
				 * final int end = i; new BukkitRunnable() {
				 * 
				 * @Override public void run() { for (int k = starting; k < end; k++) {
				 * DataHolder dh = holders.get(k);
				 * 
				 * BlockState bs = dh.b.getBlock() .getState(); if (dh.md.getMaterial() !=
				 * Material.AIR) { bs.setType(dh.md.getMaterial());
				 * bs.setRawData(dh.md.getData()); bs.update(true, false); } }
				 * 
				 * if (end == holders.size() - 1) {
				 * 
				 * for (Player p2 : minCorner .getWorld().getPlayers())
				 * p2.sendMessage(PixelPrinter .getInstance() .getPrefix() + " Done!"); } else {
				 * 
				 * for (Player p2 : minCorner .getWorld().getPlayers())
				 * p2.sendMessage(PixelPrinter .getInstance() .getPrefix() + " Loading: " +
				 * (int) ((((double) end) / holders .size()) * 100) + "%"); } }
				 * }.runTaskLater(PixelPrinter.getInstance(), 3 * timesTicked);
				 * 
				 * lastUsedInt = i; if (i >= holders.size() - 1) break; } } }
				 * }.runTask(PixelPrinter.getInstance());
				 */
			}
		}.runTaskAsynchronously(PixelPrinter.getInstance());

		/*
		 * new BukkitRunnable() { public void run() { if (isDone.getI() == 0 ||
		 * activated.getI() == 1) return; activated.setI(1); // final IntHolder maxSize
		 * = new IntHolder(); //
		 * maxSize.setI(fHeight*16/*PixelPrinter.getInstance().loadCount* /);
		 * total.setI((holders.size() / (fHeight * 8)) /* holders.size() /
		 * maxSize.getI() /);
		 * 
		 * for (int i = 0; i < (holders.size() / (fHeight * 8)) + 1 /* ((fWidth / 16))+
		 * 1
		 *//*
			 * ( holders . size ( ) / maxSize . getI ( ) ) + 1 /; i++) { final int ii = i;
			 * Bukkit.getScheduler().scheduleSyncDelayedTask( PixelPrinter.getInstance(),
			 * new Runnable() { int k = ii; long tick = System.currentTimeMillis();
			 * 
			 * public void run() { for (int j = 0; j < (((fHeight * 8)))/* maxSize. getI ()
			 * /; j++) { if (holders.size() <= ((k * (((fHeight * 8))))/* maxSize . getI ( )
			 * ) /+ j)) { break; } DataHolder dh = holders .get((k * (((fHeight * 8)))/*
			 * maxSize . getI ( ) /) + j);
			 * 
			 * BlockState bs = dh.b.getBlock() .getState(); bs.setType(dh.md.getMaterial());
			 * bs.setRawData(dh.md.getData()); bs.update(true, false); //
			 * NMSHandler.setBlockFast(dh.b.getWorld(), // dh.b.getBlockX(), // dh
			 * .b.getBlockY(),dh.b.getBlockZ(),dh.md.getMaterial().getId(),dh.md
			 * .getData()); } done.setI(k);
			 * 
			 * if (System.currentTimeMillis() - tick > 1000) { tick =
			 * System.currentTimeMillis(); for (Player p2 : minCorner.getWorld()
			 * .getPlayers()) p2.sendMessage(PixelPrinter .getInstance().getPrefix() +
			 * " Loading: " + (int) ((((double) done .getI()) / total .getI()) * 100) +
			 * "%"); } if (total.getI() <= done.getI()) { for (Player p2 :
			 * minCorner.getWorld() .getPlayers()) p2.sendMessage(PixelPrinter
			 * .getInstance().getPrefix() + " Done!"); System.out.println(PixelPrinter
			 * .getInstance().getPrefix() + " Done creating image.");
			 * Bukkit.getScheduler().cancelTask( id.getI()); }
			 * 
			 * } }, 20/* i * 20 / 5 + (25 * (/*i / 5)) * /); } }
			 * }.runTaskTimer(PixelPrinter.getInstance(), 4, 5);
			 */
	}

	/*class DataHolder {
		MaterialData md;
		Location b;
		boolean hasFaces = false;

		public DataHolder(Location b, MaterialData md) {
			this(b, md, false);
		}

		public DataHolder(Location b, MaterialData md, boolean hasFaces) {
			this.b = b;
			this.md = md;
			this.hasFaces = hasFaces;
		}
		public boolean hasFaces() {
			return hasFaces;
		}
	}*/

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
					return  ((byte) 2);
				if (dir == Direction.UP_EAST)
					return  ((byte) 3);
				if (dir == Direction.UP_SOUTH)
					return  ((byte) 0);
				if (dir == Direction.UP_WEST)
					return  ((byte) 1);
			}
			if (dh.md.getMaterial() == Material.FURNACE || dh.md.getMaterial().name().equals("BURNING_FURNACE")) {
				// Go eat, then we need south.
				// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return  ((byte) 5);
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
						return  ((byte) 5);
					if (dir == Direction.UP_EAST)
						return ((byte) 3);
					if (dir == Direction.UP_SOUTH)
						return  ((byte) 4);
					if (dir == Direction.UP_WEST)
						return  ((byte) 2);
				

			}
			if (dh.md.getMaterial().name().equals("PISTON_BASE")
					|| dh.md.getMaterial().name().equals("PISTON_STICKY_BASE")) {
				if (dh.md.getDirection() == BlockFace.UP) {
					if (dir == Direction.UP_NORTH)
						return  ((byte) 5);
					if (dir == Direction.UP_EAST)
						return  ((byte) 3);
					if (dir == Direction.UP_SOUTH)
						return  ((byte) 4);
					if (dir == Direction.UP_WEST)
						return  ((byte) 2);
				}
				if (dh.md.getDirection() == BlockFace.WEST) {
					if (dir == Direction.UP_NORTH)
						return  ((byte) 4);
					if (dir == Direction.UP_EAST)
						return  ((byte) 2);
					if (dir == Direction.UP_SOUTH)
						return ((byte) 5);
					if (dir == Direction.UP_WEST)
						return  ((byte) 3);
				}
			}
			if (dh.md.getMaterial() == Material.PUMPKIN || dh.md.getMaterial() == Material.JACK_O_LANTERN) {
				if (dh.md.getDirection() == BlockFace.EAST) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
					if (dir == Direction.UP_NORTH)
						return  ((byte) 3);
					if (dir == Direction.UP_EAST)
						return  ((byte) 0);
					if (dir == Direction.UP_SOUTH)
						return  ((byte) 1);
					if (dir == Direction.UP_WEST)
						return ((byte) 2);
				}

				if (dh.md.getDirection() == BlockFace.WEST) {
					if (dir == Direction.UP_NORTH)
						return  ((byte) 0);
					if (dir == Direction.UP_EAST)
						return  ((byte) 3);
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
							return  ((byte) 4);
						if (dir == Direction.UP_EAST)
							return ((byte) 2);
						if (dir == Direction.UP_SOUTH)
							return  ((byte) 5);
						if (dir == Direction.UP_WEST)
							return ((byte) 3);
					
				}

				if (dh.md.getDirection() == BlockFace.WEST) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
						if (dir == Direction.UP_NORTH)
							return  ((byte) 5);
						if (dir == Direction.UP_EAST)
							return ((byte) 3);
						if (dir == Direction.UP_SOUTH)
							return  ((byte) 4);
						if (dir == Direction.UP_WEST)
							return ((byte) 2);
					}
				

				if (dh.md.getDirection() == BlockFace.NORTH) {
					// Go eat, then we need south.
					// Go south, then face west, ect.
						if (dir == Direction.UP_NORTH)
							return  ((byte) 3);
						if (dir == Direction.UP_EAST)
							return  ((byte) 5);
						if (dir == Direction.UP_SOUTH)
							return  ((byte) 2);
						if (dir == Direction.UP_WEST)
							return ((byte) 4);
					}
				
			}
		} catch (Error | Exception e54) {
		}
		return dh.md.getData();
	}
}
