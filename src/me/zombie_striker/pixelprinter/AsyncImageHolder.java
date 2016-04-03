package me.zombie_striker.pixelprinter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.zombie_striker.pixelprinter.RGBBlockColor.MaterialData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AsyncImageHolder {

	final PixelPrinter pp;
	final Player p;
	final RGBBlockColor.Pixel[][] result;
	final String dir;
	final BufferedImage bi;
	final Location loc;

	public AsyncImageHolder(PixelPrinter pp1, RGBBlockColor.Pixel[][] result1,
			Player p1, String dir1, BufferedImage bi1) {
		pp = pp1;
		p = p1;
		result = result1;
		dir = dir1;
		bi = bi1;
		loc = p.getLocation().clone();
	}

	public int loadImage() {

		final List<DataHolder> holders = new ArrayList<>();

		boolean neg = isMinNeg(dir);
		boolean moving = isMovingX(dir);
		for (int y = bi.getHeight() - 1; y >= 0; y--) {
			if (neg) {
				for (int x = 0; x > -bi.getWidth(); x--) {
					Block b;
					if (moving)
						b =  loc.clone()
								.add(x, bi.getHeight() - y - 1, 0).getBlock();
					else
						b = loc.clone()
								.add(0, bi.getHeight() - y - 1, x).getBlock();
					MaterialData m = RGBBlockColor
							.getClosestBlockValue(new Color(result[y][x
									+ bi.getWidth() - 1].r, result[y][x
									+ bi.getWidth() - 1].g, result[y][x
									+ bi.getWidth() - 1].b));
					// pp.setBlock(b, m.m, m.data);
					holders.add(new DataHolder(b, m));

					;
				}
			} else {
				for (int x = 0; x < bi.getWidth(); x++) {
					Block b;
					if (moving)
						b =  loc.clone()
								.add(x, bi.getHeight() - y - 1, 0).getBlock();
					else
						b =  loc.clone()
								.add(0, bi.getHeight() - y - 1, x).getBlock();
					MaterialData m = RGBBlockColor
							.getClosestBlockValue(new Color(result[y][x].r,
									result[y][x].g, result[y][x].b));
					// pp.setBlock(b, m.m, m.data);
					holders.add(new DataHolder(b, m));
					;
				}
			}
		}
		for(Player p2 : p.getWorld().getPlayers())
		p2.sendMessage(pp.prefix + " Loading image requested by "+p.getName());
		int delay = 0;
		final int maxSize = 500;
		for (int i = 0; i < (holders.size() / maxSize)+1; i++) {
			final int ii = i;
			Bukkit.getScheduler().scheduleSyncDelayedTask(pp, new Runnable() {
				int k = ii;

				public void run() {
					for (int j = 0; j < maxSize; j++) {
						if(holders.size()<=j)break;
						DataHolder dh = holders.get((k * maxSize) + j);
						
						dh.b.setType(dh.md.m);
						dh.b.setData((byte) dh.md.data);
					}
					for(Player p2 : p.getWorld().getPlayers())
					p2.sendMessage(pp.prefix
							+ " Loading: "
							+ (int)(( ((double)k) /(holders.size() / maxSize))*100)
							+ "%");
				}
			}, i);
			delay = i;
		}
		return delay;
	}

	public boolean isMovingX(String s) {
		if (s.equalsIgnoreCase("east") || s.equalsIgnoreCase("west"))
			return true;
		return false;
	}

	public boolean isMinNeg(String s) {
		if (s.equalsIgnoreCase("west") || s.equalsIgnoreCase("north"))
			return true;
		return false;
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
