package me.zombie_striker.pixelprinter.util;

import org.bukkit.ChatColor;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RGBChatColor {

	public static Map<String, RGBValue> chatValue = new HashMap<>();

	public static String BLACK_BAR = Character.toString((char) 0x2588);
	private static String bol = (ChatColor.BOLD + "");

	static {

		chatValue.put(ChatColor.BLACK + bol + "#", new RGBValue(new Color(0, 0,
				0)));
		addColor("C", new double[]{155, 53, 128, 56}, new double[]{155,
				53, 128, 56}, new double[]{155, 53, 128, 56});
		addColor("$", new double[]{108, 94, 121, 94}, new double[]{108,
				94, 121, 94}, new double[]{108, 94, 121, 94});
		addColor("X", new double[]{105, 86, 93, 76}, new double[]{105, 86,
				93, 76}, new double[]{105, 86, 93, 76});
		addColor("M", new double[]{151, 125, 93, 80}, new double[]{151,
				125, 93, 80}, new double[]{151, 125, 93, 80});
		addColor("O", new double[]{138, 107, 104, 93}, new double[]{138,
				107, 104, 93}, new double[]{138, 107, 104, 93});
		addColor("0", new double[]{159, 131, 127, 102}, new double[]{159,
				131, 127, 102}, new double[]{159, 131, 127, 102});
		addColor("/", new double[]{36, 84, 82, 10}, new double[]{36, 84,
				82, 10}, new double[]{36, 84, 82, 10});
		addColor("\\", new double[]{84, 36, 10, 82}, new double[]{84, 36,
				10, 82}, new double[]{84, 36, 10, 82});
		addColor("_", new double[]{0, 0, 63, 51}, new double[]{0, 0, 63,
				51}, new double[]{0, 0, 63, 51});
		addColor("P", new double[]{201, 84, 94, 0}, new double[]{201, 84,
				94, 0}, new double[]{201, 84, 94, 0});
		addColor("a", new double[]{63, 63, 110, 150}, new double[]{63, 63,
				110, 150}, new double[]{63, 63, 110, 150});
		addColor("=", new double[]{80, 65, 80, 75}, new double[]{80, 65,
				80, 75}, new double[]{80, 65, 80, 75});
		addColor("-", new double[]{44, 33, 48, 43}, new double[]{44, 33,
				48, 43}, new double[]{44, 33, 48, 43});
		addColor("J", new double[]{0, 90, 63, 90}, new double[]{0, 90, 63,
				90}, new double[]{0, 90, 63, 90});
		addColor("U", new double[]{110, 90, 100, 90}, new double[]{110,
				90, 100, 90}, new double[]{110, 90, 100, 90});
		addColor("T", new double[]{115, 95, 40, 30}, new double[]{115, 95,
				40, 30}, new double[]{115, 95, 40, 30});
		addColor("E", new double[]{191, 60, 130, 35}, new double[]{191,
				60, 130, 35}, new double[]{191, 60, 130, 35});
		addColor("8", new double[]{144, 111, 105, 90}, new double[]{144,
				111, 105, 90}, new double[]{144, 111, 105, 90});
		addColor("4", new double[]{90, 147, 70, 105}, new double[]{90,
				147, 70, 105}, new double[]{90, 147, 70, 105});
		addColor("2", new double[]{98, 104, 118, 105}, new double[]{98,
				104, 118, 105}, new double[]{98, 104, 118, 105});
		addColor("6", new double[]{135, 65, 126, 91}, new double[]{135,
				65, 126, 91}, new double[]{135, 65, 126, 91});
		addColor("u", new double[]{66, 48, 111, 124}, new double[]{66, 48,
				111, 124}, new double[]{66, 48, 111, 124});
		addColor("Y", new double[]{80, 70, 44, 35}, new double[]{80, 70,
				44, 35}, new double[]{80, 70, 44, 35});
		addColor("I!", new double[]{136, 123, 113, 78}, new double[]{136,
				123, 113, 78}, new double[]{136, 123, 113, 78});
		addColor("!I", new double[]{156, 116, 102, 104}, new double[]{156,
				116, 102, 104}, new double[]{156, 116, 102, 104});
		addColor("h", new double[]{150, 50, 108, 88}, new double[]{150,
				50, 108, 88}, new double[]{150, 50, 108, 88});
		addColor("H", new double[]{155, 125, 100, 85}, new double[]{155,
				125, 100, 85}, new double[]{155, 125, 100, 85});
		addColor("x", new double[]{50, 50, 90, 71}, new double[]{50, 50,
				90, 71}, new double[]{50, 50, 90, 71});
		addColor("q", new double[]{80, 80, 70, 135}, new double[]{80, 80,
				70, 135}, new double[]{80, 80, 70, 135});
		addColor("![", new double[]{175, 81, 117, 77}, new double[]{175,
				81, 117, 77}, new double[]{175, 81, 117, 77});
		addColor("]!", new double[]{105, 146, 90, 191}, new double[]{105,
				146, 90, 191}, new double[]{105, 146, 90, 191});
		addColor("S", new double[]{117, 125, 68, 94}, new double[]{117,
				125, 68, 94}, new double[]{117, 125, 68, 94});
		addColor("N", new double[]{146, 116, 100, 90}, new double[]{146,
				116, 100, 90}, new double[]{146, 116, 100, 90});
		addColor("n", new double[]{106, 58, 107, 86}, new double[]{106,
				58, 107, 86}, new double[]{106, 58, 107, 86});
		addColor("b", new double[]{146, 50, 148, 99}, new double[]{146,
				50, 148, 99}, new double[]{146, 50, 148, 99});
		addColor("B", new double[]{197, 109, 133, 91}, new double[]{197,
				109, 133, 91}, new double[]{197, 109, 133, 91});
		addColor("w", new double[]{67, 52, 133, 141}, new double[]{67, 52,
				133, 141}, new double[]{67, 52, 133, 141});
		addColor("W", new double[]{121, 100, 133, 105}, new double[]{121,
				100, 133, 105}, new double[]{121, 100, 133, 105});
		addColor("#", new double[]{178, 136, 132, 116}, new double[]{178,
				136, 132, 116}, new double[]{178, 136, 132, 116});
		addColor("f", new double[]{129, 114, 82, 33}, new double[]{129,
				114, 82, 33}, new double[]{129, 114, 82, 33});
		addColor("F", new double[]{194, 68, 92, 0}, new double[]{194, 68,
				92, 0}, new double[]{194, 68, 92, 0});
		addColor("d", new double[]{80, 122, 112, 124}, new double[]{80,
				122, 112, 124}, new double[]{80, 122, 112, 124});
		addColor("D", new double[]{153, 101, 120, 84}, new double[]{153,
				101, 120, 84}, new double[]{153, 101, 120, 84});
		addColor("A", new double[]{174, 137, 94, 80}, new double[]{174,
				137, 94, 80}, new double[]{174, 137, 94, 80});
		addColor("k", new double[]{162, 32, 146, 55}, new double[]{162,
				32, 146, 55}, new double[]{162, 32, 146, 55});
		addColor("K", new double[]{157, 75, 100, 70}, new double[]{157,
				75, 100, 70}, new double[]{157, 75, 100, 70});
		addColor("v", new double[]{62, 50, 80, 71}, new double[]{62, 50,
				80, 71}, new double[]{62, 50, 80, 71});
		addColor("V", new double[]{119, 98, 79, 70}, new double[]{119, 98,
				79, 70}, new double[]{119, 98, 79, 70});
		addColor("L", new double[]{113, 0, 133, 54}, new double[]{113, 0,
				133, 54}, new double[]{113, 0, 133, 54});
		addColor("ll", new double[]{144, 82, 101, 75}, new double[]{144,
				82, 101, 75}, new double[]{144, 82, 101, 75});
		addColor("1", new double[]{73, 42, 99, 83}, new double[]{73, 42,
				99, 83}, new double[]{73, 42, 99, 83});
	}

	private static void addColor(String ch, double[] r, double[] g, double[] b) {
		double[] c = new double[4 * 3];
		for (int k = 0; k < 4; k++) {
			c[(k * 3) + 0] = r[k] / 255;
			c[(k * 3) + 1] = g[k] / 255;
			c[(k * 3) + 2] = b[k] / 255;
		}

		int[][] mods = {{0, 0, 0}, // black 0
				{0, 0, 170},// darkblue 1
				{0, 170, 0}, // dark green 2
				{0, 170, 170}, // cyan 3
				{170, 0, 0}, // dark red 4
				{170, 0, 170}, // purple 5
				{255, 170, 0}, // gold 6
				{170, 170, 170}, // light gray 7
				{85, 85, 85}, // dark gray 8
				{85, 85, 255}, // blue 9
				{85, 255, 85},// green a
				{85, 255, 255}, // light blue b
				{255, 85, 85}, // red c
				{255, 85, 255}, // light pink d
				{255, 255, 85}, // yellow e
				{300, 300, 300} // White +45 F //{ 255, 255, 255 } // white f
		};

		int cm = 0;
		for (ChatColor cc : ChatColor.values()) {
			if (cc == ChatColor.BOLD || cc == ChatColor.STRIKETHROUGH
					|| cc == ChatColor.RESET || cc == ChatColor.ITALIC
					|| cc == ChatColor.MAGIC || cc == ChatColor.UNDERLINE)
				continue;
			int x = 0;
			chatValue.put(cc + bol + ch, new RGBValue(new Color((c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3]),
					(c[x] * mods[cm][x % 3] > 255) ? 255
							: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3])), new Color(
					(c[x] * mods[cm][x % 3] > 255) ? 255
							: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3])), new Color(
					(c[x] * mods[cm][x % 3] > 255) ? 255
							: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3])), new Color(
					(c[x] * mods[cm][x % 3] > 255) ? 255
							: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3]), (c[x]
					* mods[cm][x % 3] > 255) ? 255
					: (int) (c[x] * mods[cm][x++ % 3]))));
			cm++;
		}
	}

	/**
	 * The color value of the four closest colors. Use this if you want to
	 * preserve hard edges in images. For the array, you need four color values.
	 * Use the following chart to understand which pixel should be at which
	 * index:
	 * <p>
	 * | 0 | 1 |
	 * <p>
	 * |---|---|
	 * <p>
	 * | 2 | 3 |
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static String getClosestBlockValue(Color[] c) {

		int[] r = new int[4];
		int[] b = new int[4];
		int[] g = new int[4];
		for (int i = 0; i < c.length; i++) {
			r[i] = c[i].getRed();
			b[i] = c[i].getBlue();
			g[i] = c[i].getGreen();
		}

		double cR = Integer.MAX_VALUE;
		double cG = Integer.MAX_VALUE;
		double cB = Integer.MAX_VALUE;

		String closest = null;

		double[] tR = new double[4];
		double[] tG = new double[4];
		double[] tB = new double[4];
		for (Entry<String, RGBValue> entry : chatValue.entrySet()) {
			for (int i = 0; i < 4; i++) {
				tR[i] = entry.getValue().r[i] - r[i];
				tG[i] = entry.getValue().g[i] - g[i];
				tB[i] = entry.getValue().b[i] - b[i];
				if (tR[i] < 0)
					tR[i] = -tR[i];
				if (tG[i] < 0)
					tG[i] = -tG[i];
				if (tB[i] < 0)
					tB[i] = -tB[i];
			}
			if ((tR[0] * tR[0]) + (tG[0] * tG[0]) + (tB[0] * tB[0])
					+ (tR[1] * tR[1]) + (tG[1] * tG[1]) + (tB[1] * tB[1])
					+ (tR[2] * tR[2]) + (tG[2] * tG[2]) + (tB[2] * tB[2])
					+ (tR[3] * tR[3]) + (tG[3] * tG[3]) + (tB[3] * tB[3]) < (cR
					+ cG + cB)) {
				cR = 0;
				cB = 0;
				cG = 0;
				for (int i = 0; i < 4; i++) {
					cR += (tR[i] * tR[i]);
					cG += (tG[i] * tG[i]);
					cB += (tB[i] * tB[i]);
				}
				closest = entry.getKey();
			}
		}
		return closest;
	}
}
