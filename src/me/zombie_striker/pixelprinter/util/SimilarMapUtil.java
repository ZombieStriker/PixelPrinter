package me.zombie_striker.pixelprinter.util;

import me.zombie_striker.pixelprinter.data.CustomMapView;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimilarMapUtil {

	private static File mapFolder;

	public static void registerAllMaps(File folder) {
		mapFolder = folder;
		for (File f : mapFolder.listFiles()) {
			int id = Integer.parseInt(f.getName().split(".png")[0]);
			try {
				renderMap(id, ImageIO.read(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void renderMap(int mapIds, BufferedImage image) {
		try {
			Material map = Material.MAP;
			if (ReflectionUtil.isVersionHigherThan(1, 13))
				map = Material.FILLED_MAP;
			@SuppressWarnings("deprecation")
			ItemStack is = new ItemStack(map, 1, (short) mapIds);

			MapMeta meta = (MapMeta) is.getItemMeta();
			meta.setMapView(new CustomMapView(mapIds));
			if (!meta.hasMapView()) {
				Bukkit.getMap(mapIds);
			}
			MapView mv = meta.getMapView();
			for (MapRenderer mr : mv.getRenderers()) {
				mv.removeRenderer(mr);
			}
			mv.addRenderer(new CustomImageRenderer(image, CustomImageRenderer.TICK_FOR_STILLS));
		} catch (Error | Exception e4) {
			e4.printStackTrace();
		}
	}

	public static int findSimilarImage(BufferedImage image) {
		for (File f : mapFolder.listFiles()) {
			String filename = f.getName();
			if (filename.contains("."))
				filename = filename.split("\\.")[0];
			int id = Integer.parseInt(filename);
			try {
				if (compareImages(image, ImageIO.read(f))) {
					return id;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		MapView mv = Bukkit.createMap(Bukkit.getWorlds().get(0));
		for (MapRenderer mr : mv.getRenderers()) {
			mv.removeRenderer(mr);
		}
		mv.addRenderer(new CustomImageRenderer(image, CustomImageRenderer.TICK_FOR_STILLS));

		try {
			ImageIO.write(image, "png", new File(mapFolder, mv.getId() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image.flush();

		return mv.getId();
	}

	/**
	 * Compares two images pixel by pixel.
	 *
	 * @param imgA the first image.
	 * @param imgB the second image.
	 * @return whether the images are both the same or not.
	 */
	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
		// The images must be the same size.
		if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
			return false;
		}

		int width = imgA.getWidth();
		int height = imgA.getHeight();

		// Loop over every pixel.
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// Compare the pixels for equality.
				if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
					return false;
				}
			}
		}

		return true;
	}
}
