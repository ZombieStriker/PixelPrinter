package me.zombie_striker.pixelprinter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.zombie_striker.pixelprinter.V1_9.RGB_1_9;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;

/**
 * Created by Zombie_Striker on 3/30/2016
 **/
public class RGBBlockColor {

	private static Map<MaterialData, RGBBlockValue> materialValue = new HashMap<>();

	private static RGBBlockColor bc = new RGBBlockColor();

	static{
		bc.new RGBBlockValue(new Color(87, 87, 87), Material.BEDROCK);
		bc.new RGBBlockValue(new Color(180, 100, 80), Material.BRICK);
		bc.new RGBBlockValue(new Color(66, 66, 66), Material.CAULDRON);
		bc.new RGBBlockValue(new Color(157, 163, 174), Material.CLAY);
		bc.new RGBBlockValue(new Color(169, 169, 169), Material.COBBLESTONE);
		bc.new RGBBlockValue(new Color(109, 134, 109), Material.MOSSY_COBBLESTONE);
		bc.new RGBBlockValue(new Color(105, 223, 218), Material.DIAMOND_BLOCK);
		bc.new RGBBlockValue(new Color(121, 85, 58), Material.DIRT);
		bc.new RGBBlockValue(new Color(72, 224, 115), Material.EMERALD_BLOCK);
		bc.new RGBBlockValue(new Color(225, 232, 168), Material.ENDER_STONE);
		bc.new RGBBlockValue(new Color(255, 188, 94), Material.GLOWSTONE);
		bc.new RGBBlockValue(new Color(255, 241, 68), Material.GOLD_BLOCK);
		bc.new RGBBlockValue(new Color(129, 127, 127), Material.GRAVEL);
		bc.new RGBBlockValue(new Color(148, 90, 65), Material.HARD_CLAY);
		bc.new RGBBlockValue(new Color(37, 22, 16), Material.STAINED_CLAY,DyeColor.BLACK);
		bc.new RGBBlockValue(new Color(75, 61, 92), Material.STAINED_CLAY,DyeColor.BLUE);
		bc.new RGBBlockValue(new Color(78, 52, 36), Material.STAINED_CLAY,DyeColor.BROWN);
		bc.new RGBBlockValue(new Color(86, 90, 91), Material.STAINED_CLAY,DyeColor.CYAN);
		bc.new RGBBlockValue(new Color(58, 42, 36), Material.STAINED_CLAY,DyeColor.GRAY);
		bc.new RGBBlockValue(new Color(75, 82, 42), Material.STAINED_CLAY,DyeColor.GREEN);
		bc.new RGBBlockValue(new Color(114, 109, 138), Material.STAINED_CLAY,DyeColor.LIGHT_BLUE);
		bc.new RGBBlockValue(new Color(100, 116,51), Material.STAINED_CLAY,DyeColor.LIME);
		bc.new RGBBlockValue(new Color(148, 86,108), Material.STAINED_CLAY,DyeColor.MAGENTA);
		bc.new RGBBlockValue(new Color(163, 85,39), Material.STAINED_CLAY,DyeColor.ORANGE);
		bc.new RGBBlockValue(new Color(160, 77,78), Material.STAINED_CLAY,DyeColor.PINK);
		bc.new RGBBlockValue(new Color(115, 68,84), Material.STAINED_CLAY,DyeColor.PURPLE);
		bc.new RGBBlockValue(new Color(142, 59,45), Material.STAINED_CLAY,DyeColor.RED);
		bc.new RGBBlockValue(new Color(136, 107,98), Material.STAINED_CLAY,DyeColor.SILVER);
		bc.new RGBBlockValue(new Color(210, 177,161), Material.STAINED_CLAY,DyeColor.WHITE);
		bc.new RGBBlockValue(new Color(190, 136,36), Material.STAINED_CLAY,DyeColor.YELLOW);
		bc.new RGBBlockValue(new Color(160, 120, 17), Material.HAY_BLOCK);
		//bc.new RGBBlockValue(new Color(119, 169, 255), Material.ICE);
		bc.new RGBBlockValue(new Color(224, 224, 224), Material.IRON_BLOCK);
		bc.new RGBBlockValue(new Color(120, 87, 57), Material.JUKEBOX);
		bc.new RGBBlockValue(new Color(38, 66,144), Material.LAPIS_BLOCK);
		bc.new RGBBlockValue(new Color(106, 85,60), Material.LOG,0);//oak
		bc.new RGBBlockValue(new Color(47, 30,11), Material.LOG,1);//spruce
		bc.new RGBBlockValue(new Color(234, 236,227), Material.LOG,2);//birch
		bc.new RGBBlockValue(new Color(118, 102,38), Material.LOG,3);//jungle
		bc.new RGBBlockValue(new Color(105, 99,89), Material.LOG,4);//Accacia
		bc.new RGBBlockValue(new Color(48, 37,22), Material.LOG,5);//Dark oak
		bc.new RGBBlockValue(new Color(148, 156, 37), Material.MELON_BLOCK);
		bc.new RGBBlockValue(new Color(36, 20, 23), Material.NETHER_BRICK);
		bc.new RGBBlockValue(new Color(165, 88, 88), Material.NETHERRACK);
		bc.new RGBBlockValue(new Color(15, 15, 23), Material.OBSIDIAN);
		bc.new RGBBlockValue(new Color(159, 132,77), Material.WOOD,0);//oak
		bc.new RGBBlockValue(new Color(102, 79,47), Material.WOOD,1);//spruce
		bc.new RGBBlockValue(new Color(200, 183,122), Material.WOOD,2);//birch
		bc.new RGBBlockValue(new Color(159, 113,74), Material.WOOD,3);//jungle
		bc.new RGBBlockValue(new Color(186, 104,59), Material.WOOD,4);//Accacia
		bc.new RGBBlockValue(new Color(62, 41,18), Material.WOOD,5);//Dark oak
		bc.new RGBBlockValue(new Color(151, 180, 231), Material.PACKED_ICE);
		bc.new RGBBlockValue(new Color(125, 195, 167), Material.PRISMARINE,0);
		bc.new RGBBlockValue(new Color(151, 180, 231), Material.PRISMARINE,1);
		bc.new RGBBlockValue(new Color(53, 85, 75), Material.PRISMARINE,2);
		bc.new RGBBlockValue(new Color(208, 124, 20), Material.PUMPKIN);
		bc.new RGBBlockValue(new Color(230, 225, 217), Material.QUARTZ_BLOCK,0);
		bc.new RGBBlockValue(new Color(227, 223, 213), Material.QUARTZ_BLOCK,1);
		bc.new RGBBlockValue(new Color(156, 26, 8), Material.REDSTONE_BLOCK);
		bc.new RGBBlockValue(new Color(69, 34, 13), Material.REDSTONE_LAMP_OFF);
		bc.new RGBBlockValue(new Color(188, 129, 59), Material.REDSTONE_LAMP_ON);
		bc.new RGBBlockValue(new Color(200, 99, 140), Material.SAND);
		bc.new RGBBlockValue(new Color(216, 208, 157), Material.SANDSTONE);
		bc.new RGBBlockValue(new Color(196, 227, 214), Material.SEA_LANTERN);
		bc.new RGBBlockValue(new Color(127, 201, 108), Material.SLIME_BLOCK);
		bc.new RGBBlockValue(new Color(238, 255, 255), Material.SNOW_BLOCK);
		bc.new RGBBlockValue(new Color(85, 65, 52), Material.SOUL_SAND);
		bc.new RGBBlockValue(new Color(200, 201, 95), Material.SPONGE);
		bc.new RGBBlockValue(new Color(116, 116, 116), Material.STONE,0);//base
		bc.new RGBBlockValue(new Color(159, 107, 88), Material.STONE,1);//gran hard
		bc.new RGBBlockValue(new Color(150, 100, 83), Material.STONE,2);//gran smooth
		bc.new RGBBlockValue(new Color(206, 206, 210), Material.STONE,3);//dorite hard
		bc.new RGBBlockValue(new Color(203, 203, 207), Material.STONE,4);//dorite smooth
		bc.new RGBBlockValue(new Color(173, 174, 165), Material.STONE,5);//ander hard
		bc.new RGBBlockValue(new Color(132, 133, 132), Material.STONE,6);//ander smooth
		bc.new RGBBlockValue(new Color(136, 136, 136), Material.SMOOTH_BRICK);
		bc.new RGBBlockValue(new Color(23, 19, 19), Material.WOOL,DyeColor.BLACK);
		bc.new RGBBlockValue(new Color(43, 53, 133), Material.WOOL,DyeColor.BLUE);
		bc.new RGBBlockValue(new Color(76, 48, 30), Material.WOOL,DyeColor.BROWN);
		bc.new RGBBlockValue(new Color(49, 116, 143), Material.WOOL,DyeColor.CYAN);
		bc.new RGBBlockValue(new Color(61, 61, 61), Material.WOOL,DyeColor.GRAY);
		bc.new RGBBlockValue(new Color(55, 72, 28), Material.WOOL,DyeColor.GREEN);
		bc.new RGBBlockValue(new Color(113, 143, 203), Material.WOOL,DyeColor.LIGHT_BLUE);
		bc.new RGBBlockValue(new Color(66, 180,58), Material.WOOL,DyeColor.LIME);
		bc.new RGBBlockValue(new Color(184, 83,193), Material.WOOL,DyeColor.MAGENTA);
		bc.new RGBBlockValue(new Color(221, 129,67), Material.WOOL,DyeColor.ORANGE);
		bc.new RGBBlockValue(new Color(210, 128,158), Material.WOOL,DyeColor.PINK);
		bc.new RGBBlockValue(new Color(130, 62,188), Material.WOOL,DyeColor.PURPLE);
		bc.new RGBBlockValue(new Color(157, 56,51), Material.WOOL,DyeColor.RED);
		bc.new RGBBlockValue(new Color(162, 168,168), Material.WOOL,DyeColor.SILVER);
		bc.new RGBBlockValue(new Color(232, 231,231), Material.WOOL,DyeColor.WHITE);
		bc.new RGBBlockValue(new Color(188, 176,42), Material.WOOL,DyeColor.YELLOW);
		if (Bukkit.getVersion().contains("1.9-")) {
			new RGB_1_9(bc);
		}
	}

	public static MaterialData getClosestBlockValue(Color c) {

		int r = c.getRed();
		int b = c.getBlue();
		int g = c.getGreen();

		String mainColor = null;
		double rRat = 0;
		double gRat = 0;
		double bRat = 0;

		mainColor = "R";
		rRat = r;
		gRat = g;
		bRat = b;

		double cR = 1000000;
		double cB = 1000000;
		double cG = 1000000;
		Material cMat = Material.COAL_BLOCK;
		short data = 0;

		for (Entry<MaterialData, RGBBlockValue> entry : materialValue
				.entrySet()) {
			double tR = entry.getValue().rRat - rRat;
			double tG = entry.getValue().gRat - gRat;
			double tB = entry.getValue().bRat - bRat;
			if (tR < 0)
				tR = -tR;
			if (tG < 0)
				tG = -tG;
			if (tB < 0)
				tB = -tB;
			if (tR + tG + tB < cR + cG + cB) {
				cR = tR;
				cB = tB;
				cG = tG;
				cMat = entry.getKey().m;
				data = entry.getKey().data;
			}
		}

		return bc.new MaterialData(cMat, data);
	}

	public class RGBBlockValue {
		int r;
		int b;
		int g;
		Material mat;

		double rRat;
		double gRat;
		double bRat;

		String mainColor;
		short data;

		public RGBBlockValue(Color c, Material mat) {
			this.r = c.getRed();
			this.g = c.getGreen();
			this.b = c.getBlue();
			// if(r > 0){
			this.mainColor = "R";
			rRat = r;
			gRat = g;
			bRat = b;
			this.mat = mat;
			bc.materialValue.put(bc.new MaterialData(mat), this);
		}

		public RGBBlockValue(Color c, Material mat, DyeColor d) {
			this.r = c.getRed();
			this.g = c.getGreen();
			this.b = c.getBlue();
			// if(r > 0){
			this.mainColor = "R";
			rRat = r;
			gRat = g;
			bRat = b;
			this.mat = mat;
			this.data = (short) d.ordinal();
			bc.materialValue.put(bc.new MaterialData(mat, data), this);
		}

		public RGBBlockValue(Color c, Material mat, int d) {
			this.r = c.getRed();
			this.g = c.getGreen();
			this.b = c.getBlue();
			// if(r > 0){
			this.mainColor = "R";
			rRat = r;
			gRat = g;
			bRat = b;
			this.mat = mat;
			this.data = (short) d;
			bc.materialValue.put(bc.new MaterialData(mat, data), this);
		}
	}

	class MaterialData {
		Material m;
		short data;

		public MaterialData(Material m, short data2) {
			this.m = m;
			this.data = data2;
		}

		public MaterialData(Material m) {
			this.m = m;
			this.data = 0;
		}
	}

	public static Pixel[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
		if (image.getRaster().getDataBuffer() instanceof DataBufferByte) {
			final byte[] pixels = ((DataBufferByte) image.getRaster()
					.getDataBuffer()).getData();
			final int width = image.getWidth();
			final int height = image.getHeight();
			final boolean hasAlphaChannel = image.getAlphaRaster() != null;

			Pixel[][] result = new Pixel[height][width];
			if (hasAlphaChannel) {
				final int pixelLength = 4;
				for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
					int r = 0;
					int b = 0;
					int g = 0;
					// argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
					b += ((int) pixels[pixel + 1] & 0xff); // blue
					g += (((int) pixels[pixel + 2] & 0xff) /* << 8 */); // green
					r += (((int) pixels[pixel + 3] & 0xff) /* << 16 */); // red
					result[row][col] = bc.new Pixel(r, g, b);
					col++;
					if (col == width) {
						col = 0;
						row++;
					}
				}
			} else {
				final int pixelLength = 3;
				for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
					int r = 0;
					int b = 0;
					int g = 0;
					// argb += -16777216; // 255 alpha
					b += ((int) pixels[pixel] & 0xff); // blue
					g += (((int) pixels[pixel + 1] & 0xff) /* <<8 */); // green
					r += (((int) pixels[pixel + 2] & 0xff) /* <<16 */); // red
					result[row][col] = bc.new Pixel(r, g, b);
					col++;
					if (col == width) {
						col = 0;
						row++;
					}
				}
			}

			return result;
		} else if (image.getRaster().getDataBuffer() instanceof DataBufferInt) {
			final int[] pixels = ((DataBufferInt) image.getRaster()
					.getDataBuffer()).getData();
			final int width = image.getWidth();
			final int height = image.getHeight();
			final boolean hasAlphaChannel = image.getAlphaRaster() != null;

			Pixel[][] result = new Pixel[height][width];
			if (hasAlphaChannel) {
				final int pixelLength = 4;
				for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
					int r = 0;
					int b = 0;
					int g = 0;
					// argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
					b += ((int) pixels[pixel + 1] & 0xff); // blue
					g += (((int) pixels[pixel + 2] & 0xff) /* << 8 */); // green
					r += (((int) pixels[pixel + 3] & 0xff) /* << 16 */); // red
					result[row][col] = bc.new Pixel(r, g, b);
					col++;
					if (col == width) {
						col = 0;
						row++;
					}
				}
			} else {
				final int pixelLength = 1;
				for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
					int r = 0;
					int b = 0;
					int g = 0;
					int rgb = pixels[pixel];
					r = (rgb >> 16) & 0xFF;
					g = (rgb >> 8) & 0xFF;
					b = rgb & 0xFF;
					result[row][col] = bc.new Pixel(r, g, b);
					col++;
					if (col == width) {
						col = 0;
						row++;
					}
				}
			}

			return result;
		}
		return null;
	}

	class Pixel {
		int r;
		int b;
		int g;

		public Pixel(int r, int g, int b) {
			this.r = r;
			this.b = b;
			this.g = g;
		}
	}
}
