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
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;

import me.zombie_striker.pixelprinter.PixelPrinter;
import me.zombie_striker.pixelprinter.data.*;
import me.zombie_striker.pluginconstructor.*;
import me.zombie_striker.pluginconstructor.RGBBlockColor.Pixel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.scheduler.BukkitRunnable;

public class GifHolder extends Image implements ConfigurationSerializable {

	private BufferedImage[] frames;
	private int currentFrame = 0;
	private boolean isFulluLoaded = false;

	private DataHolder[][][] materials;
	private boolean[][][] isTrans;

	private int gifID;
	private UUID owner;

	private String fileName;
	private int height;

	public boolean isLoaded() {
		return isFulluLoaded;
	}

	public static void registerClass() {
		ConfigurationSerialization.registerClass(GifHolder.class);
		ConfigurationSerialization.registerClass(DataHolder.class);
	}

	public static List<Integer> freeID = new ArrayList<Integer>();;

	public GifHolder(String filename, Location minLocation, int height, String dir, UUID owner) {
		this.dir = Direction.getDir(dir);
		this.p = Bukkit.getPlayer(owner) == null ? "null" : Bukkit.getPlayer(owner).getName();
		this.minCorner = minLocation;
		this.height = height * 2;
		this.fileName = filename;
		this.owner = owner;
		while (true) {
			gifID++;
			if (!freeID.contains(gifID)) {
				freeID.add(gifID);
				break;
			}
		}
		createFrames(new File(PixelPrinter.getInstance().getImageFile() + File.separator + this.fileName), this.height,
				owner);
	}

	public void init() {
		new BukkitRunnable() {

			@Override
			public void run() {
				final List<DataHolder> holders = new ArrayList<>();
				int fWidth = 0;
				int fHeight = 0;
				for (int frames = 0; frames < getFrames().length; frames++) {
					if (fWidth < getFrame(frames).getWidth() / 2)
						fWidth = getFrame(frames).getWidth() / 2;
					if (fHeight < getFrame(frames).getHeight() / 2)
						fHeight = getFrame(frames).getHeight() / 2;
				}
				materials = new DataHolder[getFrames().length][fWidth][fHeight];
				isTrans = new boolean[getFrames().length][fWidth][fHeight];

				for (int frame = 0; frame < getFrames().length; frame++) {
					holders.clear();

					final BufferedImage bi = getFrames()[frame];
					final Pixel[][] result = RGBBlockColor.convertTo2DWithoutUsingGetRGB(bi);

					for (int width = 0; width < bi.getWidth(); width += 2) {
						for (int height = (bi.getHeight() - 1); height >= 0; height -= 2) {
							if (width / 2 >= fWidth || height / 2 >= fHeight)
								continue;
								
							Location b = getBlockAt(height, width, bi.getHeight());
							Color[] color = new Color[4];
							boolean allTrans = true;

							int defaultR = 0;
							int defaultG = 0;
							int defaultB = 0;

							for (int iT = 0; iT < 4; iT++) {
								int yT = (1 + height < result.length) ? height + (iT % 2) : height;
								int xT = (width + 1 < result[height].length) ? width + (iT / 2) : width;
								Pixel rgb = result[yT][xT];
								if (rgb.r != 0 || rgb.g != 0 || rgb.b != 0) {
									allTrans = false;
									defaultR = rgb.r;
									defaultG = rgb.g;
									defaultB = rgb.b;
									break;
								}
							}
							if (allTrans) {
								isTrans[frame][width / 2][height / 2] = true;
							} else {
								for (int i = 0; i < 4; i++) {
									int y = (1 + height < result.length) ? height + (i % 2) : height;
									int x = (width + 1 < result[height].length) ? width + (i / 2) : width;
									Pixel rgb = result[y][x];
									if (rgb.r != 0 || rgb.g != 0 || rgb.b != 0) {
										color[i] = new Color(rgb.r, rgb.g, rgb.b);
									} else {
										color[i] = new Color(defaultR, defaultG, defaultB);
									}
								}
								MaterialData m = RGBBlockColor.getClosestBlockValue(color,
										(dir == Direction.FLAT_NORTHEAST || dir == Direction.FLAT_NORTHWEST
												|| dir == Direction.FLAT_SOUTHEAST || dir == Direction.FLAT_SOUTHWEST));
								DataHolder dh = new DataHolder(b, m);
								holders.add(dh);
									materials[frame][width / 2][height / 2] = dh;
							}
						}
					}
				}
				isFulluLoaded = true;
			}
		}.runTaskLaterAsynchronously(PixelPrinter.getInstance(), 0);
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public BufferedImage getFrame(int i) {
		return getFrames()[i];
	}

	public void setNegDir(boolean b) {
		neg = b;
	}

	public void setEastOrWest(boolean b) {
		moving = b;
	}

	@SuppressWarnings("deprecation")
	public void loadFrame() {
		if (getFrames() == null || getFrames().length < 1)
			return;

		for (int x = 0; x < materials[currentFrame].length; x++) {
			for (int y = materials[currentFrame][x].length - 1; y >= 0; y--) {
				if (!isTrans[currentFrame][x][y]) {
					DataHolder dh = materials[currentFrame][x][y];
					if (dh == null)
						continue;
					Block b = dh.b.getBlock();
					if (b.getType() != dh.md.getMaterial()) {
						BlockState state = b.getState();
						BlockFace bf=null;
						byte rd = dh.md.getData();
						if (dh.hasFaces()) {
							bf = AsyncImageHolder.getBlockFace(dh, dir);
							rd=AsyncImageHolder.getBlockData(dh, dir);
						}
						if (PixelPrinter.isAbove113) {
							b.setType(dh.md.getMaterial());
							try {
								if (bf != null) {
									Update13Handler.setFacing(b.getState(), bf);
								}
							} catch (Error | Exception e45) {
							}
						} else {
							state.setType(dh.md.getMaterial());
							//b.setType(dh.md.getMaterial());
						try {
							if (dh.md.getData() != 0&&b.getData() != rd)
								state.setRawData(rd);
								//b.setData(dh.md.getData());
						} catch (Exception e) {
							e.printStackTrace();
						}
						state.update(true, false);
						}
					}
				}
			}
		}
		this.currentFrame++;
		if (this.currentFrame >= getFrames().length)
			currentFrame = 0;
	}

	public int getSize() {
		return getFrames().length;
	}

	public UUID getOwner() {
		return owner;
	}

	public int getID() {
		return gifID;
	}

	public Location getMinCorner() {
		return minCorner;
	}

	public BufferedImage[] getFrames() {
		return frames;
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}

	public String getFileName() {
		return this.fileName;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("currentframe", this.currentFrame);
		data.put("fileName", this.fileName);
		data.put("gifID", this.gifID);
		data.put("height", this.height);
		data.put("minCorner.x", this.minCorner.getBlockX());
		data.put("minCorner.y", this.minCorner.getBlockY());
		data.put("minCorner.z", this.minCorner.getBlockZ());
		data.put("minCorner.w", this.minCorner.getWorld().getName());
		data.put("moving", this.moving);
		data.put("neg", this.neg);
		data.put("owner", this.owner.toString());

		data.put("dir", this.dir.getName());
		return data;
	}

	public GifHolder(Map<String, Object> data) {
		final Map<String, Object> tempData = data;
		this.currentFrame = (int) data.get("currentframe");
		this.fileName = (String) data.get("fileName");
		this.gifID = (int) data.get("gifID");
		this.dir = Direction.getDir((String) data.get("dir"));
		freeID.add(gifID);
		this.height = (int) data.get("height");
		this.moving = (boolean) data.get("moving");
		this.neg = (boolean) data.get("neg");
		this.owner = UUID.fromString((String) data.get("owner"));
		createFrames(new File(PixelPrinter.getInstance().getImageFile() + File.separator + this.fileName), this.height,
				owner);
		final IntHolder temp = new IntHolder();
		temp.setI(Bukkit.getScheduler().scheduleSyncRepeatingTask(PixelPrinter.getInstance(), new Runnable() {
			public void run() {
				if (Bukkit.getWorld((String) tempData.get("minCorner.w")) != null) {
					minCorner = Bukkit.getWorld((String) tempData.get("minCorner.w"))
							.getBlockAt((int) tempData.get("minCorner.x"), (int) tempData.get("minCorner.y"),
									(int) tempData.get("minCorner.z"))
							.getLocation();
					Bukkit.getScheduler().cancelTask(temp.getI());
				}
			}
		}, 0, 20));
		final IntHolder temp2 = new IntHolder();
		temp2.setI(Bukkit.getScheduler().scheduleSyncRepeatingTask(PixelPrinter.getInstance(), new Runnable() {
			public void run() {
				if (minCorner != null && getFrames() != null && getFrames().length > 1) {
					init();
					Bukkit.getScheduler().cancelTask(temp2.getI());
				}
			}
		}, 0, 20));
	}

	@SuppressWarnings("deprecation")
	public void createFrames(final File gif, final int height, UUID owner) {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(PixelPrinter.getInstance(), new Runnable() {
			public void run() {
				setFrames(getFrames(gif, height));
			}
		}, 0);

	}

	public static BufferedImage[] getFrames(File gif, int height) {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(gif);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getFrames(iis, height);
	}

	public static BufferedImage[] getFrames(URL gif, int height) {
		try {
			return getFrames((ImageInputStream) gif.openStream(), height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static BufferedImage[] getFrames(ImageInputStream gif, int height) {
		try {
			ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput((ImageInputStream) gif, true);
			Iterator<IIOImage> iter = reader.readAll(null);
			List<BufferedImage> bii = new ArrayList<>();
			while (iter.hasNext()) {
				IIOImage img = iter.next();
				BufferedImage frame = (BufferedImage) img.getRenderedImage();
				frame = RGBBlockColor.resize(frame, (int) (frame.getWidth() * ((double) height) / frame.getHeight()),
						height);// .createResizedCopy(frame,
				// height, false);
				bii.add(frame);
			}
			gif.flush();
			gif.close();
			Object[] array1 = bii.toArray();
			BufferedImage[] array = new BufferedImage[array1.length];
			for (int i = 0; i < array1.length; i++) {
				if (array1[i] instanceof BufferedImage) {
					array[i] = (BufferedImage) array1[i];
				} else {
					System.out.println("ERROR: Image is not an image.");
				}
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

