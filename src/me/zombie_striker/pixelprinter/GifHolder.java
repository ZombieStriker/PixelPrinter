package me.zombie_striker.pixelprinter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.zombie_striker.pixelprinter.RGBBlockColor.MaterialData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class GifHolder {

	BufferedImage[] frames;
	PixelPrinter pp1;
	int currentFrame = 0;

	Location minCorner;
	boolean neg;
	boolean moving;

	public GifHolder(BufferedImage[] bi, PixelPrinter pp2) {
		frames = bi;
		pp1 = pp2;
	}


	public int getCurrentFrame() {
		return currentFrame;
	}

	public BufferedImage getFrame(int i) {
		return frames[i];
	}

	public void setNegDir(boolean b) {
		neg = b;
	}

	public void setEastOrWest(boolean b) {
		moving = b;
	}

	public void setMinLocation(Location l) {
		minCorner = l;
	}

	public void loadFrame(int frame) {
		final PixelPrinter pp = pp1;
		final Location loc = minCorner;
		final RGBBlockColor.Pixel[][] result = RGBBlockColor
				.convertTo2DWithoutUsingGetRGB(frames[frame]);
		final BufferedImage bi = frames[frame];

		final List<DataHolder> holders = new ArrayList<>();

		for (int y = bi.getHeight() - 1; y >= 0; y--) {
			if (neg) {
				for (int x = 0; x > -bi.getWidth(); x--) {
					if(frame != 0 && (result[y][x+ bi.getWidth() - 1].r==0&&result[y][x+ bi.getWidth() - 1].g==0&&result[y][x+ bi.getWidth() - 1].b==0))continue;
					Block b;
					if (moving)
						b = loc.clone().add(x, bi.getHeight() - y - 1, 0)
								.getBlock();
					else
						b = loc.clone().add(0, bi.getHeight() - y - 1, x)
								.getBlock();
					MaterialData m = RGBBlockColor
							.getClosestBlockValue(new Color(result[y][x
									+ bi.getWidth() - 1].r, result[y][x
									+ bi.getWidth() - 1].g, result[y][x
									+ bi.getWidth() - 1].b));
					// pp.setBlock(b, m.m, m.data);
					holders.add(new DataHolder(b, m));
				}
			} else {
				for (int x = 0; x < bi.getWidth(); x++) {
					if(frame != 0 && (result[y][x].r==0&&result[y][x].g==0&&result[y][x].b==0))continue;
					Block b;
					if (moving)
						b = loc.clone().add(x, bi.getHeight() - y - 1, 0)
								.getBlock();
					else
						b = loc.clone().add(0, bi.getHeight() - y - 1, x)
								.getBlock();
					MaterialData m = RGBBlockColor
							.getClosestBlockValue(new Color(result[y][x].r,
									result[y][x].g, result[y][x].b));
					// pp.setBlock(b, m.m, m.data);
					holders.add(new DataHolder(b, m));
				}
			}
		}

		final int maxSize = 100000;
		// for (int i = 0; i < (holders.size() /maxSize); i++) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(pp, new Runnable() {

			@SuppressWarnings("deprecation")
			public void run() {
				for (int j = 0; j < maxSize; j++) {
					if(holders.size()<=j)break;
					DataHolder dh = holders.get(j);
					dh.b.setType(dh.md.m);
					dh.b.setData((byte) dh.md.data);
				}
			}
		},  10);
		this.currentFrame++;
		if(this.currentFrame==frames.length)currentFrame=0;
	}

	class DataHolder {
		MaterialData md;
		Block b;

		public DataHolder(Block b, MaterialData md) {
			this.b = b;
			this.md = md;
		}
	}

}
