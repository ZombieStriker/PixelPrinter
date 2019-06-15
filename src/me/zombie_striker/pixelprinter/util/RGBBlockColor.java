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

import me.zombie_striker.pixelprinter.data.MaterialData;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Zombie_Striker on 3/30/2016
 **/
public class RGBBlockColor {

	private static final String SERVER_VERSION;
	public static Map<MaterialData, RGBBlockValue> materialValue = new HashMap<>();

	static {
		String name = Bukkit.getServer().getClass().getName();
		name = name.substring(name.indexOf("craftbukkit.") + "craftbukkit.".length());
		name = name.substring(0, name.indexOf("."));
		SERVER_VERSION = name;
	}

	public static RGBValue getRGBFromMatData(MaterialData m) {
		return getRGBFromMatData(m, false);
	}

	public static RGBValue getRGBFromMatData(MaterialData m, boolean topView) {
		for (MaterialData h : materialValue.keySet()) {
			if (h.getMaterial() == m.getMaterial() && h.getData() == m.getData())
				return materialValue.get(h);
		}
		return null;
	}

	private static void add(int r1, int g1, int b1, String mat) {
		new RGBBlockValue(new Color(r1, g1, b1), mat);
	}

	private static void add(int r1, int g1, int b1, String mat, int data) {
		new RGBBlockValue(new Color(r1, g1, b1), mat, data);
	}
	private static void add(int r, int g, int b, String mat, int data, BlockFace face) {
		add(r,g,b,r,g,b,r,g,b,r,g,b,mat,data,face);
	}

	private static void add(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String mat) {
		new RGBBlockValue(new Color(r1, g1, b1), new Color(r2, g2, b2), new Color(r3, g3, b3),
				new Color(r4, g4, b4), mat);
	}

	private static void add(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String mat, int data) {
		new RGBBlockValue(new Color(r1, g1, b1), new Color(r2, g2, b2), new Color(r3, g3, b3),
				new Color(r4, g4, b4), mat, data);
	}

	private static void add(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String mat, int data, BlockFace face) {
		new RGBBlockValue(new Color(r1, g1, b1), new Color(r2, g2, b2), new Color(r3, g3, b3),
				new Color(r4, g4, b4), mat, data, face);
	}




	private static void add(int r1, int g1, int b1, String mat, int data, boolean top) {
		new RGBBlockValue(new Color(r1, g1, b1), mat, data, top);
	}

	private static void add(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String mat, int data, boolean top) {
		new RGBBlockValue(new Color(r1, g1, b1), new Color(r2, g2, b2), new Color(r3, g3, b3),
				new Color(r4, g4, b4), mat, data, top);
	}

	private static void add(int r1, int g1, int b1, int r2, int g2, int b2, int r3, int g3, int b3, int r4, int g4, int b4, String mat, boolean top) {
		new RGBBlockValue(new Color(r1, g1, b1), new Color(r2, g2, b2), new Color(r3, g3, b3),
				new Color(r4, g4, b4), mat, 0, top);
	}

	@SuppressWarnings("deprecation")
	public static void activateBlocks() {

		if (ReflectionUtil.isVersionHigherThan(1, 14)) {


			add(102,95,86,105,98,89,104,97,88,104,97,88,"ACACIA_LOG");
			add(169,91,51,169,91,51,169,91,51,167,90,51,"ACACIA_PLANKS");
			add(137,137,137,135,136,137,136,136,137,138,138,138,"ANDESITE");
			add(112,85,53,106,80,50,111,84,51,104,78,48,"BARREL");
			add(85,85,85,81,81,81,81,81,81,94,94,94,"BEDROCK");
			add(209,207,202,225,224,220,221,220,215,212,210,205,"BIRCH_LOG");
			add(194,177,122,193,176,121,194,176,122,191,175,121,"BIRCH_PLANKS");
			add(8,10,15,9,11,16,9,11,16,9,11,16,"BLACK_CONCRETE");
			add(26,28,33,26,27,32,26,27,33,25,27,32,"BLACK_CONCRETE_POWDER");
			add(88,37,40,65,22,23,64,22,23,55,41,44,"BLACK_GLAZED_TERRACOTTA");
			add(37,23,17,37,23,17,37,23,17,38,24,17,"BLACK_TERRACOTTA");
			add(21,22,26,21,22,26,20,21,26,21,22,26,"BLACK_WOOL");
			add(106,105,105,115,116,115,101,101,101,110,110,109,"BLAST_FURNACE",0,BlockFace.EAST);
			add(94,93,93,125,124,125,93,93,93,122,122,122,"BLAST_FURNACE",0,BlockFace.NORTH);
			add(45,47,144,"BLUE_CONCRETE");
			add(70,73,167,71,74,167,72,74,168,70,73,167,"BLUE_CONCRETE_POWDER");
			add(49,69,143,47,61,134,46,61,135,50,70,147,"BLUE_GLAZED_TERRACOTTA");
			add(118,169,253,114,167,253,118,169,253,115,168,253,"BLUE_ICE");
			add(75,60,92,74,60,91,75,60,92,75,61,92,"BLUE_TERRACOTTA");
			add(54,58,158,53,58,158,53,57,157,53,58,158,"BLUE_WOOL");
			add(230,227,209,230,227,209,229,226,207,229,226,208,"BONE_BLOCK");
			add(126,77,46,115,100,71,112,109,72,118,95,52,"BOOKSHELF");
			add(152,98,83,151,98,83,150,97,83,152,99,84,"BRICKS");
			add(97,60,32,"BROWN_CONCRETE");
			add(125,85,54,125,84,54,127,87,55,126,85,54,"BROWN_CONCRETE_POWDER");
			add(149,103,65,114,111,97,114,111,97,104,102,86,"BROWN_GLAZED_TERRACOTTA");
			add(150,112,82,149,112,82,150,112,82,150,112,82,"BROWN_MUSHROOM_BLOCK");
			add(77,51,36,77,51,36,78,52,36,78,52,36,"BROWN_TERRACOTTA");
			add(115,72,41,115,73,41,114,72,40,115,72,41,"BROWN_WOOL");
			add(162,94,21,148,82,16,149,83,17,144,79,16,"CARVED_PUMPKIN");
			add(232,227,218,233,228,219,232,226,217,233,228,219,"CHISELED_QUARTZ_BLOCK");
			add(189,102,31,182,96,27,183,97,27,181,95,27,"CHISELED_RED_SANDSTONE");
			add(222,212,167,217,203,157,216,203,154,212,195,144,"CHISELED_SANDSTONE");
			add(125,124,125,122,121,122,121,120,121,113,112,113,"CHISELED_STONE_BRICKS");
			add(161,167,180,161,167,181,160,166,178,162,168,181,"CLAY");
			add(17,17,17,17,17,17,15,15,15,16,16,16,"COAL_BLOCK");
			add(116,116,116,120,120,120,118,118,118,112,112,112,"COAL_ORE");
			add(122,88,62,120,86,59,120,86,59,117,84,59,"COARSE_DIRT");
			add(130,129,130,127,127,127,131,130,131,125,125,125,"COBBLESTONE");
			add(117,73,34,108,68,30,119,75,35,106,67,29,"COMPOSTER");
			add(121,120,121,117,117,117,116,115,116,121,121,121,"CRACKED_STONE_BRICKS");
			add(196,107,36,190,103,32,189,102,32,183,98,29,"CUT_RED_SANDSTONE");
			add(223,213,171,219,208,161,218,206,158,214,200,151,"CUT_SANDSTONE");
			add(22,119,136,22,119,136,22,120,137,22,119,136,"CYAN_CONCRETE");
			add(37,146,156,37,148,157,37,149,158,37,150,159,"CYAN_CONCRETE_POWDER");
			add(61,130,133,56,101,111,34,112,122,59,133,137,"CYAN_GLAZED_TERRACOTTA");
			add(87,91,91,86,91,91,87,92,92,88,92,92,"CYAN_TERRACOTTA");
			add(22,139,146,22,139,146,22,137,145,22,138,146,"CYAN_WOOL");
			add(59,46,26,62,48,27,61,47,27,61,47,27,"DARK_OAK_LOG");
			add(68,44,21,67,44,21,67,44,21,66,43,20,"DARK_OAK_PLANKS");
			add(52,91,76,52,92,76,52,92,76,53,93,77,"DARK_PRISMARINE");
			add(122,116,112,128,121,118,121,115,113,128,119,115,"DEAD_BRAIN_CORAL_BLOCK");
			add(133,125,121,132,124,120,131,123,118,132,124,120,"DEAD_BUBBLE_CORAL_BLOCK");
			add(130,123,119,133,125,121,133,125,121,132,124,120,"DEAD_FIRE_CORAL_BLOCK");
			add(137,130,125,134,126,122,138,131,127,128,120,117,"DEAD_HORN_CORAL_BLOCK");
			add(135,128,124,130,122,119,134,127,124,124,117,114,"DEAD_TUBE_CORAL_BLOCK");
			add(133,248,236,88,235,227,94,236,228,80,232,223,"DIAMOND_BLOCK");
			add(127,142,141,126,138,136,124,138,136,125,154,153,"DIAMOND_ORE");
			add(186,186,186,185,185,185,195,195,195,191,191,191,"DIORITE");
			add(136,98,69,133,96,66,134,96,66,135,98,68,"DIRT");
			add(65,221,113,36,202,84,38,204,87,31,189,70,"EMERALD_BLOCK");
			add(114,140,123,120,141,127,122,131,125,115,138,123,"EMERALD_ORE");
			add(220,224,159,220,223,159,221,224,160,218,222,157,"END_STONE");
			add(221,227,165,221,228,165,217,223,161,216,220,159,"END_STONE_BRICKS");
			add(138,180,252,140,181,252,141,181,252,145,184,254,"PACKED_ICE");
			add(87,86,86,98,97,97,86,86,86,97,97,97,"FURNACE",0,BlockFace.EAST);
			add(108,107,107,133,133,133,107,107,107,135,135,135,"FURNACE");
			add(171,127,78,180,141,93,175,136,89,162,123,79,"GLOWSTONE");
			add(253,226,80,247,204,55,246,206,59,242,199,55,"GOLD_BLOCK");
			add(143,141,128,138,137,126,138,136,124,157,149,124,"GOLD_ORE");
			add(148,102,84,152,105,88,150,104,87,150,104,86,"GRANITE");
			add(132,127,125,134,129,128,132,128,127,133,128,127,"GRAVEL");
			add(55,58,62,"GRAY_CONCRETE");
			add(78,82,86,77,81,85,77,82,85,77,81,85,"GRAY_CONCRETE_POWDER");
			add(89,96,99,79,88,92,72,81,85,94,98,101,"GRAY_GLAZED_TERRACOTTA");
			add(58,43,36,58,42,36,58,43,36,59,43,36,"GRAY_TERRACOTTA");
			add(64,69,72,63,69,72,63,68,71,63,69,72,"GRAY_WOOL");
			add(74,92,37,"GREEN_CONCRETE");
			add(97,118,46,97,118,45,98,120,45,99,122,44,"GREEN_CONCRETE_POWDER");
			add(146,162,120,108,138,49,108,138,48,107,133,54,"GREEN_GLAZED_TERRACOTTA");
			add(76,83,43,76,83,43,77,84,43,77,84,43,"GREEN_TERRACOTTA");
			add(85,110,28,85,110,27,85,109,28,86,110,28,"GREEN_WOOL");
			add(169,140,40,165,135,38,165,135,38,167,137,39,"HAY_BLOCK");
			add(137,131,128,131,129,127,131,127,125,142,135,130,"IRON_ORE");
			add(221,158,55,210,146,50,219,157,55,210,150,53,"JACK_O_LANTERN");
			add(87,69,26,86,69,26,84,67,25,86,68,25,"JUNGLE_LOG");
			add(162,117,82,161,116,81,160,116,82,159,114,80,"JUNGLE_PLANKS");
			add(34,77,146,31,65,141,31,68,139,29,61,135,"LAPIS_BLOCK");
			add(99,112,134,101,111,133,114,119,129,86,104,137,"LAPIS_ORE");
			add(36,137,199,36,138,199,36,137,199,36,137,199,"LIGHT_BLUE_CONCRETE");
			add(75,182,214,74,180,213,75,182,215,74,181,214,"LIGHT_BLUE_CONCRETE_POWDER");
			add(69,140,196,96,152,202,98,169,211,117,200,228,"LIGHT_BLUE_GLAZED_TERRACOTTA");
			add(114,109,138,113,109,138,114,109,138,115,109,139,"LIGHT_BLUE_TERRACOTTA");
			add(59,176,218,59,177,219,57,174,217,58,175,218,"LIGHT_BLUE_WOOL");
			add(125,125,115,"LIGHT_GRAY_CONCRETE");
			add(155,155,148,155,155,148,155,155,148,157,157,150,"LIGHT_GRAY_CONCRETE_POWDER");
			add(163,172,175,108,157,159,142,162,164,166,175,177,"LIGHT_GRAY_GLAZED_TERRACOTTA");
			add(135,107,98,135,107,98,135,107,98,136,108,98,"LIGHT_GRAY_TERRACOTTA");
			add(143,143,136,143,143,136,142,142,134,142,142,135,"LIGHT_GRAY_WOOL");
			add(95,169,25,95,169,25,94,169,25,95,169,25,"LIME_CONCRETE");
			add(125,189,42,126,189,43,127,191,43,125,189,42,"LIME_CONCRETE_POWDER");
			add(138,196,48,180,199,62,179,198,60,156,199,52,"LIME_GLAZED_TERRACOTTA");
			add(104,118,53,103,118,53,104,118,53,105,119,53,"LIME_TERRACOTTA");
			add(113,186,26,113,186,26,112,185,26,113,186,26,"LIME_WOOL");
			add(170,49,160,170,49,160,169,49,159,170,49,160,"MAGENTA_CONCRETE");
			add(193,84,184,193,85,185,193,84,185,194,84,186,"MAGENTA_CONCRETE_POWDER");
			add(205,97,187,212,103,196,205,98,188,213,105,198,"MAGENTA_GLAZED_TERRACOTTA");
			add(150,88,109,149,88,108,150,89,109,150,89,109,"MAGENTA_TERRACOTTA");
			add(190,70,181,191,70,181,189,68,179,190,69,180,"MAGENTA_WOOL");
			add(115,121,103,103,115,82,112,121,96,113,120,100,"MOSSY_COBBLESTONE");
			add(115,123,103,115,119,108,110,118,96,123,126,115,"MOSSY_STONE_BRICKS");
			add(203,197,186,203,196,185,205,199,188,203,196,184,"MUSHROOM_STEM");
			add(98,39,39,98,39,39,99,39,39,96,38,38,"NETHERRACK");
			add(45,23,27,45,22,26,45,22,27,44,22,26,"NETHER_BRICKS");
			add(92,15,194,94,15,196,86,9,190,95,16,196,"NETHER_PORTAL");
			add(121,69,66,115,62,59,116,63,59,122,71,67,"NETHER_QUARTZ_ORE");
			add(115,4,3,113,2,2,118,5,4,116,3,3,"NETHER_WART_BLOCK");
			add(95,63,44,83,56,39,93,61,43,85,57,40,"NOTE_BLOCK");
			add(106,83,49,111,87,52,111,87,51,110,85,51,"OAK_LOG");
			add(164,132,80,163,131,79,163,131,79,161,130,78,"OAK_PLANKS");
			add(69,67,67,77,74,74,71,69,69,73,71,71,"OBSERVER",0,BlockFace.WEST);
			add(93,93,93,117,116,116,92,92,92,115,115,115,"OBSERVER",0,BlockFace.EAST);
			add(67,66,66,75,73,73,69,68,68,72,70,70,"OBSERVER",0,BlockFace.NORTH);
			add(16,12,26,16,12,26,15,11,24,15,10,24,"OBSIDIAN");
			add(225,98,1,225,97,1,225,98,1,225,97,1,"ORANGE_CONCRETE");
			add(228,133,33,227,131,32,227,131,31,228,134,33,"ORANGE_CONCRETE_POWDER");
			add(190,152,78,164,134,75,164,138,77,102,168,140,"ORANGE_GLAZED_TERRACOTTA");
			add(162,84,38,161,84,38,162,84,38,163,85,38,"ORANGE_TERRACOTTA");
			add(242,119,21,242,119,21,240,117,19,241,118,20,"ORANGE_WOOL");
			add(142,181,251,141,179,251,141,179,251,145,182,251,"PACKED_ICE");
			add(214,101,143,214,101,143,214,101,143,214,102,143,"PINK_CONCRETE");
			add(229,153,181,229,154,181,229,154,182,230,154,182,"PINK_CONCRETE_POWDER");
			add(232,155,180,239,153,182,239,157,185,232,155,181,"PINK_GLAZED_TERRACOTTA");
			add(162,79,79,162,78,79,162,79,79,163,79,79,"PINK_TERRACOTTA");
			add(239,143,174,238,143,174,237,140,171,238,141,173,"PINK_WOOL");
			add(142,144,142,133,136,135,131,134,133,124,128,127,"POLISHED_ANDESITE");
			add(210,209,211,192,192,193,191,191,193,181,182,184,"POLISHED_DIORITE");
			add(166,117,99,151,105,88,156,108,90,144,99,82,"POLISHED_GRANITE");
			add(94,158,142,98,162,146,102,164,148,102,164,149,"PRISMARINE");
			add(107,180,169,99,171,157,102,175,163,91,162,147,"PRISMARINE_BRICKS");
			add(206,125,30,189,108,20,202,121,28,187,106,21,"PUMPKIN",0,BlockFace.WEST);
			add(101,32,157,"PURPLE_CONCRETE");
			add(132,56,178,133,57,178,131,55,177,133,56,178,"PURPLE_CONCRETE_POWDER");
			add(124,63,167,102,44,141,102,44,142,113,43,161,"PURPLE_GLAZED_TERRACOTTA");
			add(119,71,86,118,70,86,119,71,87,120,71,87,"PURPLE_TERRACOTTA");
			add(123,43,174,122,42,172,121,42,172,122,43,173,"PURPLE_WOOL");
			add(171,127,171,170,125,169,171,127,170,169,126,169,"PURPUR_BLOCK");
			add(172,130,172,"PURPUR_PILLAR");
			add(238,232,226,236,231,224,236,230,223,234,229,221,"QUARTZ_BLOCK");
			add(236,231,224,236,231,225,236,231,225,235,231,224,"QUARTZ_PILLAR");
			add(174,25,5,180,26,6,175,25,6,175,25,5,"REDSTONE_BLOCK");
			add(97,56,30,94,54,30,97,55,30,95,54,30,"REDSTONE_LAMP");
			add(133,108,108,132,115,115,131,111,111,138,99,99,"REDSTONE_ORE");
			add(143,33,33,143,34,34,143,33,33,143,33,33,"RED_CONCRETE");
			add(168,54,51,167,54,50,169,55,51,169,55,51,"RED_CONCRETE_POWDER");
			add(169,47,43,192,70,61,177,54,49,192,70,61,"RED_GLAZED_TERRACOTTA");
			add(202,49,47,199,45,43,199,45,43,203,50,48,"RED_MUSHROOM_BLOCK");
			add(71,8,10,69,7,9,71,8,10,68,7,9,"RED_NETHER_BRICKS");
			add(191,104,34,192,104,33,190,103,33,191,103,34,"RED_SAND");
			add(188,101,30,187,99,29,187,100,30,186,99,29,"RED_SANDSTONE");
			add(144,61,47,143,61,47,144,61,47,144,62,48,"RED_TERRACOTTA");
			add(162,40,35,162,40,35,160,39,35,161,40,35,"RED_WOOL");
			add(220,208,164,220,209,164,219,206,162,220,208,163,"SAND");
			add(218,205,158,217,204,156,217,204,156,216,203,155,"SANDSTONE");
			add(173,201,191,171,199,190,174,201,192,173,201,192,"SEA_LANTERN");
			add(112,193,92,112,193,91,112,193,92,112,193,92,"SLIME_BLOCK");
			add(50,42,47,62,35,33,49,34,36,62,32,30,"SMITHING_TABLE");
			add(97,86,70,88,73,53,83,73,58,86,72,53,"SMOKER",0,BlockFace.EAST);
			add(107,96,80,101,90,73,104,94,78,100,89,74,"SMOKER",0,BlockFace.NORTH);
			add(158,158,158,159,159,159,158,158,158,160,160,160,"SMOOTH_STONE");
			add(172,172,172,172,172,172,164,164,164,164,164,164,"STONE",1);
			add(84,64,53,84,65,53,79,61,49,79,61,49,"SOUL_SAND");
			add(197,194,75,196,192,76,197,193,75,195,191,74,"SPONGE");
			add(57,37,16,61,39,18,59,38,17,59,38,17,"SPRUCE_LOG");
			add(116,86,50,116,86,49,115,86,49,115,84,48,"SPRUCE_PLANKS");
			add(125,125,125,127,127,127,124,124,124,128,128,128,"STONE");
			add(127,126,127,119,119,119,119,119,119,127,126,127,"STONE_BRICKS");
			add(175,93,60,175,94,60,174,93,60,176,93,60,"STRIPPED_ACACIA_LOG");
			add(197,176,118,198,177,120,199,177,119,196,175,117,"STRIPPED_BIRCH_LOG");
			add(97,77,50,97,76,50,98,77,50,97,76,50,"STRIPPED_DARK_OAK_LOG");
			add(172,133,85,177,135,89,169,133,83,169,133,83,"STRIPPED_JUNGLE_LOG");
			add(176,143,85,177,144,86,181,148,90,177,143,86,"STRIPPED_OAK_LOG");
			add(118,91,53,117,91,53,115,90,53,114,90,52,"STRIPPED_SPRUCE_LOG");
			add(89,74,89,89,74,89,90,76,91,90,75,91,"STRUCTURE_BLOCK");
			add(152,94,68,153,94,68,152,94,68,153,95,68,"TERRACOTTA");
			add(173,183,72,171,181,70,171,181,71,171,181,70,"WET_SPONGE");
			add(208,214,215,208,214,215,207,213,214,208,213,214,"WHITE_CONCRETE");
			add(227,229,229,224,226,227,227,229,229,226,228,228,"WHITE_CONCRETE_POWDER");
			add(237,232,178,194,216,207,194,216,208,131,187,220,"WHITE_GLAZED_TERRACOTTA");
			add(210,178,162,209,178,162,210,178,162,211,179,162,"WHITE_TERRACOTTA");
			add(234,237,238,235,238,238,233,236,236,234,237,237,"WHITE_WOOL");
			add(242,176,22,241,176,22,241,176,22,241,176,22,"YELLOW_CONCRETE");
			add(233,199,53,233,199,56,234,200,58,233,200,55,"YELLOW_CONCRETE_POWDER");
			add(251,217,114,241,197,81,240,197,82,206,159,79,"YELLOW_GLAZED_TERRACOTTA");
			add(187,133,36,186,133,35,187,134,36,187,134,36,"YELLOW_TERRACOTTA");
			add(249,199,40,249,199,40,249,197,39,249,198,40,"YELLOW_WOOL");





		} else if (ReflectionUtil.isVersionHigherThan(1, 13)) {
			new RGBBlockValue(new Color(105, 99, 89), new Color(104, 98, 88), new Color(108, 102, 91),
					new Color(106, 100, 90), "ACACIA_LOG", 0);
			new RGBBlockValue(new Color(170, 92, 51), new Color(171, 93, 52), new Color(170, 92, 51),
					new Color(170, 92, 51), "ACACIA_PLANKS", 0);
			new RGBBlockValue(new Color(129, 130, 130), new Color(134, 134, 134), new Color(131, 131, 131),
					new Color(131, 131, 132), "ANDESITE", 0);
			new RGBBlockValue(new Color(86, 86, 86), new Color(76, 76, 76), new Color(81, 81, 81),
					new Color(94, 94, 94), "BEDROCK", 0);
			new RGBBlockValue(new Color(199, 198, 193), new Color(213, 213, 208), new Color(215, 215, 210),
					new Color(201, 200, 195), "BIRCH_LOG", 0);
			new RGBBlockValue(new Color(196, 179, 123), new Color(197, 181, 124), new Color(196, 180, 124),
					new Color(196, 179, 123), "BIRCH_PLANKS", 0);
			new RGBBlockValue(new Color(8, 10, 15), new Color(9, 11, 16), new Color(9, 11, 16), new Color(9, 11, 16),
					"BLACK_CONCRETE", 0);
			new RGBBlockValue(new Color(26, 28, 33), new Color(26, 27, 32), new Color(26, 27, 33),
					new Color(25, 27, 32), "BLACK_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(88, 37, 40), new Color(65, 22, 23), new Color(64, 22, 23),
					new Color(55, 41, 44), "BLACK_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(37, 23, 17), new Color(37, 23, 17), new Color(37, 23, 17),
					new Color(38, 24, 17), "BLACK_TERRACOTTA", 0);
			new RGBBlockValue(new Color(21, 22, 26), new Color(21, 22, 26), new Color(20, 21, 26),
					new Color(21, 22, 26), "BLACK_WOOL", 0);
			new RGBBlockValue(new Color(45, 47, 144), new Color(45, 47, 144), new Color(45, 47, 144),
					new Color(45, 47, 144), "BLUE_CONCRETE", 0);
			new RGBBlockValue(new Color(70, 73, 167), new Color(71, 74, 167), new Color(72, 74, 168),
					new Color(70, 73, 167), "BLUE_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(49, 69, 143), new Color(47, 61, 134), new Color(46, 61, 135),
					new Color(50, 70, 147), "BLUE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(118, 169, 253), new Color(114, 167, 253), new Color(118, 169, 253),
					new Color(115, 168, 253), "BLUE_ICE", 0);
			new RGBBlockValue(new Color(75, 60, 92), new Color(74, 60, 91), new Color(75, 60, 92),
					new Color(75, 61, 92), "BLUE_TERRACOTTA", 0);
			new RGBBlockValue(new Color(54, 58, 158), new Color(53, 58, 158), new Color(53, 57, 157),
					new Color(53, 58, 158), "BLUE_WOOL", 0);
			new RGBBlockValue(new Color(225, 221, 201), new Color(225, 221, 200), new Color(225, 221, 201),
					new Color(225, 221, 201), "BONE_BLOCK", 0);
			new RGBBlockValue(new Color(108, 96, 69), new Color(116, 71, 42), new Color(107, 90, 49),
					new Color(102, 98, 71), "BOOKSHELF", 0);
			new RGBBlockValue(new Color(146, 100, 87), new Color(147, 100, 87), new Color(147, 101, 88),
					new Color(148, 101, 88), "BRICKS", 0);
			new RGBBlockValue(new Color(97, 60, 32), new Color(97, 60, 32), new Color(97, 60, 32),
					new Color(97, 60, 32), "BROWN_CONCRETE", 0);
			new RGBBlockValue(new Color(125, 85, 54), new Color(125, 84, 54), new Color(127, 87, 55),
					new Color(126, 85, 54), "BROWN_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(149, 103, 65), new Color(114, 111, 97), new Color(114, 111, 97),
					new Color(104, 102, 86), "BROWN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(142, 107, 84), new Color(141, 106, 83), new Color(141, 106, 83),
					new Color(145, 109, 85), "BROWN_MUSHROOM_BLOCK", 0);
			new RGBBlockValue(new Color(77, 51, 36), new Color(77, 51, 36), new Color(78, 52, 36),
					new Color(78, 52, 36), "BROWN_TERRACOTTA", 0);
			new RGBBlockValue(new Color(115, 72, 41), new Color(115, 73, 41), new Color(114, 72, 40),
					new Color(115, 72, 41), "BROWN_WOOL", 0);
			new RGBBlockValue(new Color(150, 84, 16), new Color(141, 76, 11), new Color(142, 77, 12),
					new Color(137, 73, 12), "CARVED_PUMPKIN", 0);
			new RGBBlockValue(new Color(233, 230, 222), new Color(233, 229, 221), new Color(232, 228, 220),
					new Color(232, 228, 219), "CHISELED_QUARTZ_BLOCK", 0);
			new RGBBlockValue(new Color(166, 84, 29), new Color(161, 82, 27), new Color(163, 83, 29),
					new Color(161, 82, 28), "CHISELED_RED_SANDSTONE", 0);
			new RGBBlockValue(new Color(221, 213, 163), new Color(214, 207, 152), new Color(218, 210, 158),
					new Color(211, 203, 147), "CHISELED_SANDSTONE", 0);
			new RGBBlockValue(new Color(124, 124, 124), new Color(119, 119, 119), new Color(119, 119, 119),
					new Color(114, 114, 114), "CHISELED_STONE_BRICKS", 0);
			new RGBBlockValue(new Color(159, 165, 177), new Color(159, 165, 177), new Color(159, 165, 177),
					new Color(159, 165, 177), "CLAY", 0);
			new RGBBlockValue(new Color(20, 20, 20), new Color(18, 18, 18), new Color(22, 22, 22),
					new Color(17, 17, 17), "COAL_BLOCK", 0);
			new RGBBlockValue(new Color(117, 117, 117), new Color(117, 117, 117), new Color(117, 117, 117),
					new Color(113, 113, 113), "COAL_ORE", 0);
			new RGBBlockValue(new Color(122, 88, 62), new Color(120, 86, 59), new Color(120, 86, 59),
					new Color(117, 84, 59), "COARSE_DIRT", 0);
			new RGBBlockValue(new Color(125, 125, 125), new Color(123, 123, 123), new Color(122, 122, 122),
					new Color(124, 124, 124), "COBBLESTONE", 0);
			new RGBBlockValue(new Color(121, 121, 121), new Color(116, 116, 116), new Color(117, 117, 117),
					new Color(122, 122, 122), "CRACKED_STONE_BRICKS", 0);
			new RGBBlockValue(new Color(110, 89, 55), new Color(123, 101, 68), new Color(120, 96, 59),
					new Color(123, 99, 61), "CRAFTING_TABLE", 0);
			new RGBBlockValue(new Color(172, 88, 32), new Color(169, 86, 31), new Color(168, 86, 31),
					new Color(165, 84, 30), "CUT_RED_SANDSTONE", 0);
			new RGBBlockValue(new Color(225, 217, 169), new Color(220, 212, 162), new Color(220, 212, 162),
					new Color(216, 208, 156), "CUT_SANDSTONE", 0);
			new RGBBlockValue(new Color(22, 119, 136), new Color(22, 119, 136), new Color(22, 120, 137),
					new Color(22, 119, 136), "CYAN_CONCRETE", 0);
			new RGBBlockValue(new Color(37, 146, 156), new Color(37, 148, 157), new Color(37, 149, 158),
					new Color(37, 150, 159), "CYAN_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(61, 130, 133), new Color(56, 101, 111), new Color(34, 112, 122),
					new Color(59, 133, 137), "CYAN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(87, 91, 91), new Color(86, 91, 91), new Color(87, 92, 92),
					new Color(88, 92, 92), "CYAN_TERRACOTTA", 0);
			new RGBBlockValue(new Color(22, 139, 146), new Color(22, 139, 146), new Color(22, 137, 145),
					new Color(22, 138, 146), "CYAN_WOOL", 0);
			new RGBBlockValue(new Color(52, 40, 24), new Color(51, 40, 23), new Color(54, 42, 25),
					new Color(53, 41, 24), "DARK_OAK_LOG", 0);
			new RGBBlockValue(new Color(61, 40, 19), new Color(62, 40, 19), new Color(61, 40, 19),
					new Color(61, 40, 19), "DARK_OAK_PLANKS", 0);
			new RGBBlockValue(new Color(60, 87, 75), new Color(60, 87, 74), new Color(61, 88, 76),
					new Color(60, 89, 77), "DARK_PRISMARINE", 0);
			new RGBBlockValue(new Color(122, 116, 112), new Color(128, 121, 118), new Color(121, 115, 113),
					new Color(128, 119, 115), "DEAD_BRAIN_CORAL_BLOCK", 0);
			new RGBBlockValue(new Color(133, 125, 121), new Color(132, 124, 120), new Color(131, 123, 118),
					new Color(132, 124, 120), "DEAD_BUBBLE_CORAL_BLOCK", 0);
			new RGBBlockValue(new Color(130, 123, 119), new Color(133, 125, 121), new Color(133, 125, 121),
					new Color(132, 124, 120), "DEAD_FIRE_CORAL_BLOCK", 0);
			new RGBBlockValue(new Color(137, 130, 125), new Color(134, 126, 122), new Color(138, 131, 127),
					new Color(128, 120, 117), "DEAD_HORN_CORAL_BLOCK", 0);
			new RGBBlockValue(new Color(135, 128, 124), new Color(130, 122, 119), new Color(134, 127, 124),
					new Color(124, 117, 114), "DEAD_TUBE_CORAL_BLOCK", 0);
			new RGBBlockValue(new Color(102, 219, 214), new Color(117, 225, 220), new Color(98, 220, 215),
					new Color(75, 214, 207), "DIAMOND_BLOCK", 0);
			new RGBBlockValue(new Color(132, 140, 144), new Color(126, 132, 135), new Color(127, 135, 138),
					new Color(133, 154, 158), "DIAMOND_ORE", 0);
			new RGBBlockValue(new Color(179, 179, 181), new Color(180, 180, 183), new Color(179, 179, 181),
					new Color(183, 183, 186), "DIORITE", 0);
			new RGBBlockValue(new Color(136, 98, 69), new Color(133, 96, 66), new Color(134, 96, 66),
					new Color(135, 98, 68), "DIRT", 0);
			new RGBBlockValue(new Color(47, 58, 37), new Color(46, 56, 37), new Color(30, 41, 22),
					new Color(32, 43, 23), "DRIED_KELP_BLOCK", 0);
			new RGBBlockValue(new Color(104, 231, 139), new Color(82, 220, 119), new Color(75, 216, 112),
					new Color(65, 203, 100), "EMERALD_BLOCK", 0);
			new RGBBlockValue(new Color(107, 133, 116), new Color(111, 131, 118), new Color(116, 124, 119),
					new Color(106, 128, 114), "EMERALD_ORE", 0);
			new RGBBlockValue(new Color(220, 223, 164), new Color(222, 225, 166), new Color(223, 225, 167),
					new Color(221, 224, 165), "END_STONE", 0);
			new RGBBlockValue(new Color(227, 233, 173), new Color(227, 233, 172), new Color(224, 230, 168),
					new Color(226, 230, 171), "END_STONE_BRICKS", 0);
			new RGBBlockValue(new Color(127, 175, 255), new Color(122, 172, 255), new Color(125, 173, 255),
					new Color(128, 175, 255), "PACKED_ICE", 0);
			new RGBBlockValue(new Color(71, 71, 71), new Color(83, 83, 83), new Color(73, 73, 73),
					new Color(86, 86, 86), "FURNACE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(96, 96, 96), new Color(133, 133, 133), new Color(96, 96, 96),
					new Color(130, 130, 130), "FURNACE", 0);
			new RGBBlockValue(new Color(150, 122, 74), new Color(144, 120, 72), new Color(149, 123, 72),
					new Color(133, 109, 62), "GLOWSTONE", 0);
			new RGBBlockValue(new Color(248, 238, 80), new Color(251, 242, 92), new Color(249, 239, 73),
					new Color(250, 228, 71), "GOLD_BLOCK", 0);
			new RGBBlockValue(new Color(144, 142, 129), new Color(135, 133, 123), new Color(138, 135, 124),
					new Color(158, 150, 126), "GOLD_ORE", 0);
			new RGBBlockValue(new Color(155, 116, 101), new Color(151, 112, 97), new Color(153, 113, 97),
					new Color(156, 117, 101), "GRANITE", 0);
			new RGBBlockValue(new Color(128, 125, 123), new Color(130, 127, 126), new Color(127, 124, 123),
					new Color(125, 122, 120), "GRAVEL", 0);
			new RGBBlockValue(new Color(55, 58, 62), new Color(55, 58, 62), new Color(55, 58, 62),
					new Color(55, 58, 62), "GRAY_CONCRETE", 0);
			new RGBBlockValue(new Color(78, 82, 86), new Color(77, 81, 85), new Color(77, 82, 85),
					new Color(77, 81, 85), "GRAY_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(89, 96, 99), new Color(79, 88, 92), new Color(72, 81, 85),
					new Color(94, 98, 101), "GRAY_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(58, 43, 36), new Color(58, 42, 36), new Color(58, 43, 36),
					new Color(59, 43, 36), "GRAY_TERRACOTTA", 0);
			new RGBBlockValue(new Color(64, 69, 72), new Color(63, 69, 72), new Color(63, 68, 71),
					new Color(63, 69, 72), "GRAY_WOOL", 0);
			new RGBBlockValue(new Color(74, 92, 37), new Color(74, 92, 37), new Color(74, 92, 37),
					new Color(74, 92, 37), "GREEN_CONCRETE", 0);
			new RGBBlockValue(new Color(97, 118, 46), new Color(97, 118, 45), new Color(98, 120, 45),
					new Color(99, 122, 44), "GREEN_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(146, 162, 120), new Color(108, 138, 49), new Color(108, 138, 48),
					new Color(107, 133, 54), "GREEN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(76, 83, 43), new Color(76, 83, 43), new Color(77, 84, 43),
					new Color(77, 84, 43), "GREEN_TERRACOTTA", 0);
			new RGBBlockValue(new Color(85, 110, 28), new Color(85, 110, 27), new Color(85, 109, 28),
					new Color(86, 110, 28), "GREEN_WOOL", 0);
			new RGBBlockValue(new Color(158, 116, 18), new Color(157, 118, 19), new Color(160, 117, 18),
					new Color(158, 117, 19), "HAY_BLOCK", 0);
			new RGBBlockValue(new Color(137, 132, 128), new Color(130, 127, 124), new Color(132, 128, 126),
					new Color(145, 136, 131), "IRON_ORE", 0);
			new RGBBlockValue(new Color(187, 135, 31), new Color(181, 125, 25), new Color(187, 135, 29),
					new Color(188, 140, 30), "JACK_O_LANTERN", 0);
			new RGBBlockValue(new Color(88, 69, 27), new Color(86, 67, 27), new Color(88, 68, 27),
					new Color(87, 69, 27), "JUNGLE_LOG", 0);
			new RGBBlockValue(new Color(154, 110, 77), new Color(156, 112, 79), new Color(154, 111, 78),
					new Color(154, 110, 77), "JUNGLE_PLANKS", 0);
			new RGBBlockValue(new Color(40, 76, 151), new Color(40, 65, 137), new Color(38, 67, 138),
					new Color(39, 62, 126), "LAPIS_BLOCK", 0);
			new RGBBlockValue(new Color(104, 115, 139), new Color(100, 109, 132), new Color(114, 118, 129),
					new Color(93, 107, 139), "LAPIS_ORE", 0);
			new RGBBlockValue(new Color(36, 137, 199), new Color(36, 138, 199), new Color(36, 137, 199),
					new Color(36, 137, 199), "LIGHT_BLUE_CONCRETE", 0);
			new RGBBlockValue(new Color(75, 182, 214), new Color(74, 180, 213), new Color(75, 182, 215),
					new Color(74, 181, 214), "LIGHT_BLUE_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(69, 140, 196), new Color(96, 152, 202), new Color(98, 169, 211),
					new Color(117, 200, 228), "LIGHT_BLUE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(114, 109, 138), new Color(113, 109, 138), new Color(114, 109, 138),
					new Color(115, 109, 139), "LIGHT_BLUE_TERRACOTTA", 0);
			new RGBBlockValue(new Color(59, 176, 218), new Color(59, 177, 219), new Color(57, 174, 217),
					new Color(58, 175, 218), "LIGHT_BLUE_WOOL", 0);
			new RGBBlockValue(new Color(125, 125, 115), new Color(125, 125, 115), new Color(125, 125, 115),
					new Color(125, 125, 115), "LIGHT_GRAY_CONCRETE", 0);
			new RGBBlockValue(new Color(155, 155, 148), new Color(155, 155, 148), new Color(155, 155, 148),
					new Color(157, 157, 150), "LIGHT_GRAY_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(163, 172, 175), new Color(108, 157, 159), new Color(142, 162, 164),
					new Color(166, 175, 177), "LIGHT_GRAY_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(135, 107, 98), new Color(135, 107, 98), new Color(135, 107, 98),
					new Color(136, 108, 98), "LIGHT_GRAY_TERRACOTTA", 0);
			new RGBBlockValue(new Color(143, 143, 136), new Color(143, 143, 136), new Color(142, 142, 134),
					new Color(142, 142, 135), "LIGHT_GRAY_WOOL", 0);
			new RGBBlockValue(new Color(95, 169, 25), new Color(95, 169, 25), new Color(94, 169, 25),
					new Color(95, 169, 25), "LIME_CONCRETE", 0);
			new RGBBlockValue(new Color(125, 189, 42), new Color(126, 189, 43), new Color(127, 191, 43),
					new Color(125, 189, 42), "LIME_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(138, 196, 48), new Color(180, 199, 62), new Color(179, 198, 60),
					new Color(156, 199, 52), "LIME_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(104, 118, 53), new Color(103, 118, 53), new Color(104, 118, 53),
					new Color(105, 119, 53), "LIME_TERRACOTTA", 0);
			new RGBBlockValue(new Color(113, 186, 26), new Color(113, 186, 26), new Color(112, 185, 26),
					new Color(113, 186, 26), "LIME_WOOL", 0);
			new RGBBlockValue(new Color(170, 49, 160), new Color(170, 49, 160), new Color(169, 49, 159),
					new Color(170, 49, 160), "MAGENTA_CONCRETE", 0);
			new RGBBlockValue(new Color(193, 84, 184), new Color(193, 85, 185), new Color(193, 84, 185),
					new Color(194, 84, 186), "MAGENTA_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(205, 97, 187), new Color(212, 103, 196), new Color(205, 98, 188),
					new Color(213, 105, 198), "MAGENTA_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(150, 88, 109), new Color(149, 88, 108), new Color(150, 89, 109),
					new Color(150, 89, 109), "MAGENTA_TERRACOTTA", 0);
			new RGBBlockValue(new Color(190, 70, 181), new Color(191, 70, 181), new Color(189, 68, 179),
					new Color(190, 69, 180), "MAGENTA_WOOL", 0);
			new RGBBlockValue(new Color(137, 67, 25), new Color(140, 68, 25), new Color(128, 62, 26),
					new Color(136, 66, 26), "MAGMA_BLOCK", 0);
			new RGBBlockValue(new Color(104, 124, 104), new Color(106, 122, 106), new Color(104, 121, 104),
					new Color(103, 121, 103), "MOSSY_COBBLESTONE", 0);
			new RGBBlockValue(new Color(119, 124, 109), new Color(109, 115, 99), new Color(112, 117, 103),
					new Color(120, 122, 114), "MOSSY_STONE_BRICKS", 0);
			new RGBBlockValue(new Color(209, 205, 195), new Color(207, 204, 194), new Color(209, 205, 195),
					new Color(208, 204, 194), "MUSHROOM_STEM", 0);
			new RGBBlockValue(new Color(118, 61, 59), new Color(108, 50, 49), new Color(108, 50, 49),
					new Color(112, 57, 54), "NETHERRACK", 0);
			new RGBBlockValue(new Color(46, 23, 27), new Color(45, 23, 27), new Color(45, 23, 27),
					new Color(44, 23, 27), "NETHER_BRICKS", 0);
			new RGBBlockValue(new Color(92, 15, 194), new Color(94, 15, 196), new Color(86, 9, 190),
					new Color(95, 16, 196), "NETHER_PORTAL", 0);
			new RGBBlockValue(new Color(135, 97, 91), new Color(125, 82, 78), new Color(123, 84, 79),
					new Color(119, 77, 73), "NETHER_QUARTZ_ORE", 0);
			new RGBBlockValue(new Color(117, 7, 8), new Color(115, 6, 7), new Color(120, 8, 8), new Color(119, 7, 7),
					"NETHER_WART_BLOCK", 0);
			new RGBBlockValue(new Color(101, 67, 50), new Color(102, 69, 52), new Color(101, 68, 51),
					new Color(101, 68, 51), "NOTE_BLOCK", 0);
			new RGBBlockValue(new Color(101, 80, 50), new Color(100, 80, 49), new Color(106, 84, 52),
					new Color(103, 82, 50), "OAK_LOG", 0);
			new RGBBlockValue(new Color(157, 128, 79), new Color(159, 129, 80), new Color(157, 128, 79),
					new Color(157, 127, 79), "OAK_PLANKS", 0);
			new RGBBlockValue(new Color(68, 66, 66), new Color(69, 67, 67), new Color(69, 67, 67),
					new Color(70, 68, 68), "OBSERVER", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(91, 90, 90), new Color(117, 117, 117), new Color(88, 87, 87),
					new Color(115, 115, 115), "OBSERVER", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(62, 60, 60), new Color(63, 61, 61), new Color(64, 62, 62),
					new Color(63, 62, 62), "OBSERVER", 0, BlockFace.NORTH);
			new RGBBlockValue(new Color(21, 19, 31), new Color(20, 18, 29), new Color(21, 19, 31),
					new Color(20, 18, 30), "OBSIDIAN", 0);
			new RGBBlockValue(new Color(225, 98, 1), new Color(225, 97, 1), new Color(225, 98, 1),
					new Color(225, 97, 1), "ORANGE_CONCRETE", 0);
			new RGBBlockValue(new Color(228, 133, 33), new Color(227, 131, 32), new Color(227, 131, 31),
					new Color(228, 134, 33), "ORANGE_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(190, 152, 78), new Color(164, 134, 75), new Color(164, 138, 77),
					new Color(102, 168, 140), "ORANGE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(162, 84, 38), new Color(161, 84, 38), new Color(162, 84, 38),
					new Color(163, 85, 38), "ORANGE_TERRACOTTA", 0);
			new RGBBlockValue(new Color(242, 119, 21), new Color(242, 119, 21), new Color(240, 117, 19),
					new Color(241, 118, 20), "ORANGE_WOOL", 0);
			new RGBBlockValue(new Color(167, 196, 246), new Color(164, 193, 245), new Color(164, 193, 244),
					new Color(168, 197, 249), "PACKED_ICE", 0);
			new RGBBlockValue(new Color(214, 101, 143), new Color(214, 101, 143), new Color(214, 101, 143),
					new Color(214, 102, 143), "PINK_CONCRETE", 0);
			new RGBBlockValue(new Color(229, 153, 181), new Color(229, 154, 181), new Color(229, 154, 182),
					new Color(230, 154, 182), "PINK_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(232, 155, 180), new Color(239, 153, 182), new Color(239, 157, 185),
					new Color(232, 155, 181), "PINK_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(162, 79, 79), new Color(162, 78, 79), new Color(162, 79, 79),
					new Color(163, 79, 79), "PINK_TERRACOTTA", 0);
			new RGBBlockValue(new Color(239, 143, 174), new Color(238, 143, 174), new Color(237, 140, 171),
					new Color(238, 141, 173), "PINK_WOOL", 0);
			new RGBBlockValue(new Color(142, 143, 143), new Color(134, 134, 136), new Color(133, 134, 135),
					new Color(124, 124, 126), "POLISHED_ANDESITE", 0);
			new RGBBlockValue(new Color(193, 193, 195), new Color(184, 184, 186), new Color(185, 185, 188),
					new Color(173, 173, 175), "POLISHED_DIORITE", 0);
			new RGBBlockValue(new Color(171, 126, 109), new Color(159, 114, 97), new Color(160, 115, 98),
					new Color(150, 106, 91), "POLISHED_GRANITE", 0);
			new RGBBlockValue(new Color(105, 169, 148), new Color(105, 168, 149), new Color(110, 172, 151),
					new Color(109, 173, 157), "PRISMARINE", 0);
			new RGBBlockValue(new Color(103, 163, 146), new Color(101, 161, 143), new Color(103, 163, 145),
					new Color(95, 155, 140), "PRISMARINE_BRICKS", 0);
			new RGBBlockValue(new Color(203, 126, 27), new Color(195, 118, 21), new Color(195, 119, 23),
					new Color(199, 122, 24), "PUMPKIN", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(101, 32, 157), new Color(101, 32, 157), new Color(101, 32, 157),
					new Color(101, 32, 157), "PURPLE_CONCRETE", 0);
			new RGBBlockValue(new Color(132, 56, 178), new Color(133, 57, 178), new Color(131, 55, 177),
					new Color(133, 56, 178), "PURPLE_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(124, 63, 167), new Color(102, 44, 141), new Color(102, 44, 142),
					new Color(113, 43, 161), "PURPLE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(119, 71, 86), new Color(118, 70, 86), new Color(119, 71, 87),
					new Color(120, 71, 87), "PURPLE_TERRACOTTA", 0);
			new RGBBlockValue(new Color(123, 43, 174), new Color(122, 42, 172), new Color(121, 42, 172),
					new Color(122, 43, 173), "PURPLE_WOOL", 0);
			new RGBBlockValue(new Color(167, 122, 167), new Color(168, 124, 168), new Color(165, 121, 165),
					new Color(168, 123, 168), "PURPUR_BLOCK", 0);
			new RGBBlockValue(new Color(170, 127, 170), new Color(171, 127, 171), new Color(170, 127, 170),
					new Color(171, 127, 171), "PURPUR_PILLAR", 0);
			new RGBBlockValue(new Color(238, 236, 229), new Color(236, 233, 226), new Color(237, 234, 227),
					new Color(234, 231, 224), "QUARTZ_BLOCK", 0);
			new RGBBlockValue(new Color(232, 228, 220), new Color(232, 228, 220), new Color(232, 228, 220),
					new Color(232, 228, 220), "QUARTZ_PILLAR", 0);
			new RGBBlockValue(new Color(171, 28, 10), new Color(174, 28, 10), new Color(171, 28, 10),
					new Color(171, 28, 10), "REDSTONE_BLOCK", 0);
			new RGBBlockValue(new Color(71, 44, 27), new Color(67, 42, 26), new Color(70, 43, 27),
					new Color(75, 47, 29), "REDSTONE_LAMP", 0);
			new RGBBlockValue(new Color(134, 109, 109), new Color(129, 112, 112), new Color(130, 111, 111),
					new Color(139, 100, 100), "REDSTONE_ORE", 0);
			new RGBBlockValue(new Color(143, 33, 33), new Color(143, 34, 34), new Color(143, 33, 33),
					new Color(143, 33, 33), "RED_CONCRETE", 0);
			new RGBBlockValue(new Color(168, 54, 51), new Color(167, 54, 50), new Color(169, 55, 51),
					new Color(169, 55, 51), "RED_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(169, 47, 43), new Color(192, 70, 61), new Color(177, 54, 49),
					new Color(192, 70, 61), "RED_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(184, 41, 40), new Color(181, 34, 32), new Color(184, 37, 35),
					new Color(184, 41, 39), "RED_MUSHROOM_BLOCK", 0);
			new RGBBlockValue(new Color(70, 5, 7), new Color(71, 4, 6), new Color(67, 6, 9), new Color(67, 4, 7),
					"RED_NETHER_BRICKS", 0);
			new RGBBlockValue(new Color(169, 88, 33), new Color(169, 87, 33), new Color(171, 89, 34),
					new Color(171, 89, 34), "RED_SAND", 0);
			new RGBBlockValue(new Color(167, 85, 30), new Color(166, 85, 30), new Color(167, 85, 30),
					new Color(165, 84, 30), "RED_SANDSTONE", 0);
			new RGBBlockValue(new Color(166, 85, 30), new Color(167, 85, 30), new Color(169, 86, 30),
					new Color(167, 86, 30), "SMOOTH_RED_SANDSTONE", 0);
			new RGBBlockValue(new Color(144, 61, 47), new Color(143, 61, 47), new Color(144, 61, 47),
					new Color(144, 62, 48), "RED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(162, 40, 35), new Color(162, 40, 35), new Color(160, 39, 35),
					new Color(161, 40, 35), "RED_WOOL", 0);
			new RGBBlockValue(new Color(219, 212, 160), new Color(218, 210, 159), new Color(220, 213, 162),
					new Color(220, 212, 161), "SAND", 0);
			new RGBBlockValue(new Color(219, 211, 160), new Color(217, 210, 157), new Color(218, 210, 158),
					new Color(216, 209, 155), "SANDSTONE", 0);
			new RGBBlockValue(new Color(216, 208, 157), new Color(219, 211, 160), new Color(221, 213, 160),
					new Color(219, 211, 159), "SMOOTH_SANDSTONE", 0);
			new RGBBlockValue(new Color(173, 201, 191), new Color(171, 199, 190), new Color(174, 201, 192),
					new Color(173, 201, 192), "SEA_LANTERN", 0);
			new RGBBlockValue(new Color(121, 201, 102), new Color(123, 202, 104), new Color(121, 201, 100),
					new Color(119, 198, 100), "SLIME_BLOCK", 0);
			new RGBBlockValue(new Color(82, 62, 49), new Color(88, 68, 55), new Color(84, 64, 51),
					new Color(86, 65, 53), "SOUL_SAND", 0);
			new RGBBlockValue(new Color(194, 195, 84), new Color(195, 196, 85), new Color(197, 197, 86),
					new Color(195, 196, 86), "SPONGE", 0);
			new RGBBlockValue(new Color(45, 28, 12), new Color(45, 28, 12), new Color(48, 31, 14),
					new Color(47, 30, 13), "SPRUCE_LOG", 0);
			new RGBBlockValue(new Color(104, 78, 47), new Color(106, 79, 48), new Color(104, 78, 47),
					new Color(103, 78, 47), "SPRUCE_PLANKS", 0);
			new RGBBlockValue(new Color(125, 125, 125), new Color(125, 125, 125), new Color(125, 125, 125),
					new Color(128, 128, 128), "STONE", 0);
			new RGBBlockValue(new Color(126, 126, 126), new Color(119, 119, 119), new Color(118, 118, 118),
					new Color(126, 126, 126), "STONE_BRICKS", 0);
			new RGBBlockValue(new Color(171, 171, 171), new Color(170, 170, 170), new Color(163, 163, 163),
					new Color(165, 165, 165), "STONE", 0);
			new RGBBlockValue(new Color(175, 93, 60), new Color(175, 94, 60), new Color(174, 93, 60),
					new Color(176, 93, 60), "STRIPPED_ACACIA_LOG", 0);
			new RGBBlockValue(new Color(197, 176, 118), new Color(198, 177, 120), new Color(199, 177, 119),
					new Color(196, 175, 117), "STRIPPED_BIRCH_LOG", 0);
			new RGBBlockValue(new Color(97, 77, 50), new Color(97, 76, 50), new Color(98, 77, 50),
					new Color(97, 76, 50), "STRIPPED_DARK_OAK_LOG", 0);
			new RGBBlockValue(new Color(172, 133, 85), new Color(177, 135, 89), new Color(169, 133, 83),
					new Color(169, 133, 83), "STRIPPED_JUNGLE_LOG", 0);
			new RGBBlockValue(new Color(176, 143, 85), new Color(177, 144, 86), new Color(181, 148, 90),
					new Color(177, 143, 86), "STRIPPED_OAK_LOG", 0);
			new RGBBlockValue(new Color(118, 91, 53), new Color(117, 91, 53), new Color(115, 90, 53),
					new Color(114, 90, 52), "STRIPPED_SPRUCE_LOG", 0);
			new RGBBlockValue(new Color(89, 74, 89), new Color(89, 74, 89), new Color(90, 76, 91),
					new Color(90, 75, 91), "STRUCTURE_BLOCK", 0);
			new RGBBlockValue(new Color(151, 93, 67), new Color(150, 92, 66), new Color(151, 93, 67),
					new Color(152, 94, 68), "TERRACOTTA", 0);
			new RGBBlockValue(new Color(159, 158, 63), new Color(162, 161, 64), new Color(160, 159, 62),
					new Color(161, 160, 66), "WET_SPONGE", 0);
			new RGBBlockValue(new Color(208, 214, 215), new Color(208, 214, 215), new Color(207, 213, 214),
					new Color(208, 213, 214), "WHITE_CONCRETE", 0);
			new RGBBlockValue(new Color(227, 229, 229), new Color(224, 226, 227), new Color(227, 229, 229),
					new Color(226, 228, 228), "WHITE_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(237, 232, 178), new Color(194, 216, 207), new Color(194, 216, 208),
					new Color(131, 187, 220), "WHITE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(210, 178, 162), new Color(209, 178, 162), new Color(210, 178, 162),
					new Color(211, 179, 162), "WHITE_TERRACOTTA", 0);
			new RGBBlockValue(new Color(234, 237, 238), new Color(235, 238, 238), new Color(233, 236, 236),
					new Color(234, 237, 237), "WHITE_WOOL", 0);
			new RGBBlockValue(new Color(242, 176, 22), new Color(241, 176, 22), new Color(241, 176, 22),
					new Color(241, 176, 22), "YELLOW_CONCRETE", 0);
			new RGBBlockValue(new Color(233, 199, 53), new Color(233, 199, 56), new Color(234, 200, 58),
					new Color(233, 200, 55), "YELLOW_CONCRETE_POWDER", 0);
			new RGBBlockValue(new Color(251, 217, 114), new Color(241, 197, 81), new Color(240, 197, 82),
					new Color(206, 159, 79), "YELLOW_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(187, 133, 36), new Color(186, 133, 35), new Color(187, 134, 36),
					new Color(187, 134, 36), "YELLOW_TERRACOTTA", 0);
			new RGBBlockValue(new Color(249, 199, 40), new Color(249, 199, 40), new Color(249, 197, 39),
					new Color(249, 198, 40), "YELLOW_WOOL", 0);
		} else {
			new RGBBlockValue(new Color(109, 73, 44), "WORKBENCH", true);

			new RGBBlockValue(new Color(228, 206, 207), "CAKE_BLOCK", true);
			new RGBBlockValue(new Color(133, 118, 96), "DAYLIGHT_DETECTOR", true);
			new RGBBlockValue(new Color(105, 109, 113), "DAYLIGHT_DETECTOR_INVERTED", true);
			new RGBBlockValue(new Color(84, 111, 91), "ENDER_PORTAL_FRAME", true);
			new RGBBlockValue(new Color(100, 100, 100), "FURNACE", true);
			new RGBBlockValue(new Color(107, 73, 55), "JUKEBOX", true);
			new RGBBlockValue(new Color(150, 152, 37), "MELON_BLOCK", true);
			new RGBBlockValue(new Color(111, 100, 106), "MYCEL", true);
			new RGBBlockValue(new Color(70, 44, 27), "REDSTONE_LAMP_OFF");
			new RGBBlockValue(new Color(216, 208, 157), "SANDSTONE", true);

			new RGBBlockValue(new Color(154, 91, 64), new Color(154, 92, 65), new Color(155, 93, 65),
					new Color(155, 92, 65), "LOG_2", 12);
			new RGBBlockValue(new Color(129, 130, 130), new Color(134, 134, 134), new Color(131, 131, 131),
					new Color(131, 131, 132), "STONE", 5);
			new RGBBlockValue(new Color(123, 224, 218), new Color(117, 222, 216), new Color(117, 223, 217),
					new Color(111, 219, 214), "BEACON", 0);
			new RGBBlockValue(new Color(86, 86, 86), new Color(76, 76, 76), new Color(81, 81, 81),
					new Color(94, 94, 94), "BEDROCK", 0);
			new RGBBlockValue(new Color(188, 170, 125), new Color(178, 160, 115), new Color(181, 163, 119),
					new Color(193, 174, 130), "LOG", 14);
			new RGBBlockValue(new Color(88, 37, 40), new Color(65, 22, 23), new Color(64, 22, 23),
					new Color(55, 41, 44), "BLACK_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(49, 69, 143), new Color(47, 61, 134), new Color(46, 61, 135),
					new Color(50, 70, 147), "BLUE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(225, 221, 201), new Color(225, 221, 200), new Color(225, 221, 201),
					new Color(225, 221, 201), "BONE_BLOCK", 0);
			new RGBBlockValue(new Color(108, 96, 69), new Color(116, 71, 42), new Color(107, 90, 49),
					new Color(102, 98, 71), "BOOKSHELF", 0);
			new RGBBlockValue(new Color(146, 100, 87), new Color(147, 100, 87), new Color(147, 101, 88),
					new Color(148, 101, 88), "BRICK", 0);
			new RGBBlockValue(new Color(149, 103, 65), new Color(114, 111, 97), new Color(114, 111, 97),
					new Color(104, 102, 86), "BROWN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(130, 165, 151), new Color(130, 161, 148), new Color(130, 160, 147),
					new Color(130, 154, 142), "COMMAND_CHAIN", 0);
			new RGBBlockValue(new Color(159, 165, 177), new Color(159, 165, 177), new Color(159, 165, 177),
					new Color(159, 165, 177), "CLAY", 0);
			new RGBBlockValue(new Color(20, 20, 20), new Color(18, 18, 18), new Color(22, 22, 22),
					new Color(17, 17, 17), "COAL_BLOCK", 0);
			new RGBBlockValue(new Color(117, 117, 117), new Color(117, 117, 117), new Color(117, 117, 117),
					new Color(113, 113, 113), "COAL_ORE", 0);
			new RGBBlockValue(new Color(125, 125, 125), new Color(123, 123, 123), new Color(122, 122, 122),
					new Color(124, 124, 124), "COBBLESTONE", 0);
			new RGBBlockValue(new Color(178, 135, 108), new Color(174, 134, 109), new Color(173, 133, 109),
					new Color(166, 129, 109), "COMMAND", 0);
			new RGBBlockValue(new Color(114, 91, 56), new Color(118, 98, 66), new Color(110, 91, 63),
					new Color(121, 103, 73), "WORKBENCH", 0);
			new RGBBlockValue(new Color(61, 130, 133), new Color(56, 101, 111), new Color(34, 112, 122),
					new Color(59, 133, 137), "CYAN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(78, 62, 41), new Color(79, 63, 41), new Color(79, 63, 42),
					new Color(79, 63, 42), "LOG", 12);
			new RGBBlockValue(new Color(102, 219, 214), new Color(117, 225, 220), new Color(98, 220, 215),
					new Color(75, 214, 207), "DIAMOND_BLOCK", 0);
			new RGBBlockValue(new Color(132, 140, 144), new Color(126, 132, 135), new Color(127, 135, 138),
					new Color(133, 154, 158), "DIAMOND_ORE", 0);
			new RGBBlockValue(new Color(179, 179, 181), new Color(180, 180, 183), new Color(179, 179, 181),
					new Color(183, 183, 186), "STONE", 3);
			new RGBBlockValue(new Color(136, 98, 69), new Color(133, 96, 66), new Color(134, 96, 66),
					new Color(135, 98, 68), "DIRT", 0);
			new RGBBlockValue(new Color(104, 231, 139), new Color(82, 220, 119), new Color(75, 216, 112),
					new Color(65, 203, 100), "EMERALD_BLOCK", 0);
			new RGBBlockValue(new Color(107, 133, 116), new Color(111, 131, 118), new Color(116, 124, 119),
					new Color(106, 128, 114), "EMERALD_ORE", 0);
			new RGBBlockValue(new Color(220, 223, 164), new Color(222, 225, 166), new Color(223, 225, 167),
					new Color(221, 224, 165), "ENDER_STONE", 0);
			new RGBBlockValue(new Color(127, 175, 255), new Color(122, 172, 255), new Color(125, 173, 255),
					new Color(128, 175, 255), "PACKED_ICE", 0);
			new RGBBlockValue(new Color(71, 71, 71), new Color(83, 83, 83), new Color(73, 73, 73),
					new Color(86, 86, 86), "FURNACE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(71, 71, 71), new Color(179, 135, 99), new Color(73, 73, 73),
					new Color(178, 131, 98), "BURNING_FURNACE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(96, 96, 96), new Color(133, 133, 133), new Color(96, 96, 96),
					new Color(130, 130, 130), "FURNACE", 0);
			new RGBBlockValue(new Color(150, 122, 74), new Color(144, 120, 72), new Color(149, 123, 72),
					new Color(133, 109, 62), "GLOWSTONE", 0);
			new RGBBlockValue(new Color(248, 238, 80), new Color(251, 242, 92), new Color(249, 239, 73),
					new Color(250, 228, 71), "GOLD_BLOCK", 0);
			new RGBBlockValue(new Color(144, 142, 129), new Color(135, 133, 123), new Color(138, 135, 124),
					new Color(158, 150, 126), "GOLD_ORE", 0);
			new RGBBlockValue(new Color(144, 144, 144), new Color(132, 132, 132), new Color(142, 142, 142),
					new Color(131, 131, 131), "GRASS", 0);
			new RGBBlockValue(new Color(128, 125, 123), new Color(130, 127, 126), new Color(127, 124, 123),
					new Color(125, 122, 120), "GRAVEL", 0);
			new RGBBlockValue(new Color(89, 96, 99), new Color(79, 88, 92), new Color(72, 81, 85),
					new Color(94, 98, 101), "GRAY_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(146, 162, 120), new Color(108, 138, 49), new Color(108, 138, 48),
					new Color(107, 133, 54), "GREEN_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(158, 116, 18), new Color(157, 118, 19), new Color(160, 117, 18),
					new Color(158, 117, 19), "HAY_BLOCK", 0);
			new RGBBlockValue(new Color(222, 222, 222), new Color(222, 222, 222), new Color(218, 218, 218),
					new Color(217, 217, 217), "IRON_BLOCK", 0);
			new RGBBlockValue(new Color(137, 132, 128), new Color(130, 127, 124), new Color(132, 128, 126),
					new Color(145, 136, 131), "IRON_ORE", 0);
			new RGBBlockValue(new Color(156, 120, 74), new Color(153, 119, 73), new Color(154, 119, 74),
					new Color(154, 119, 74), "LOG", 15);
			new RGBBlockValue(new Color(40, 76, 151), new Color(40, 65, 137), new Color(38, 67, 138),
					new Color(39, 62, 126), "LAPIS_BLOCK", 0);
			new RGBBlockValue(new Color(104, 115, 139), new Color(100, 109, 132), new Color(114, 118, 129),
					new Color(93, 107, 139), "LAPIS_ORE", 0);
			new RGBBlockValue(new Color(69, 140, 196), new Color(96, 152, 202), new Color(98, 169, 211),
					new Color(117, 200, 228), "LIGHT_BLUE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(163, 172, 175), new Color(108, 157, 159), new Color(142, 162, 164),
					new Color(166, 175, 177), "WHITE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(138, 196, 48), new Color(180, 199, 62), new Color(179, 198, 60),
					new Color(156, 199, 52), "LIME_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(205, 97, 187), new Color(212, 103, 196), new Color(205, 98, 188),
					new Color(213, 105, 198), "MAGENTA_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(137, 67, 25), new Color(140, 68, 25), new Color(128, 62, 26),
					new Color(136, 66, 26), "MAGMA", 0);
			new RGBBlockValue(new Color(138, 144, 35), new Color(143, 148, 37), new Color(143, 147, 37),
					new Color(144, 147, 37), "MELON_BLOCK", 0);
			new RGBBlockValue(new Color(104, 124, 104), new Color(106, 122, 106), new Color(104, 121, 104),
					new Color(103, 121, 103), "MOSSY_COBBLESTONE", 0);
			new RGBBlockValue(new Color(101, 88, 89), new Color(131, 95, 67), new Color(97, 80, 76),
					new Color(127, 92, 65), "MYCEL", 0);
			new RGBBlockValue(new Color(118, 61, 59), new Color(108, 50, 49), new Color(108, 50, 49),
					new Color(112, 57, 54), "NETHERRACK", 0);
			new RGBBlockValue(new Color(46, 23, 27), new Color(45, 23, 27), new Color(45, 23, 27),
					new Color(44, 23, 27), "NETHER_BRICK", 0);
			new RGBBlockValue(new Color(117, 7, 8), new Color(115, 6, 7), new Color(120, 8, 8), new Color(119, 7, 7),
					"NETHER_WART_BLOCK", 0);
			new RGBBlockValue(new Color(101, 67, 50), new Color(102, 69, 52), new Color(101, 68, 51),
					new Color(101, 68, 51), "NOTE_BLOCK", 0);
			new RGBBlockValue(new Color(154, 124, 77), new Color(155, 125, 77), new Color(157, 127, 78),
					new Color(156, 126, 78), "LOG", 12);
			new RGBBlockValue(new Color(68, 66, 66), new Color(69, 67, 67), new Color(69, 67, 67),
					new Color(70, 68, 68), "OBSERVER", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(91, 90, 90), new Color(117, 117, 117), new Color(88, 87, 87),
					new Color(115, 115, 115), "OBSERVER", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(62, 60, 60), new Color(63, 61, 61), new Color(64, 62, 62),
					new Color(63, 62, 62), "OBSERVER", 0, BlockFace.NORTH);
			new RGBBlockValue(new Color(21, 19, 31), new Color(20, 18, 29), new Color(21, 19, 31),
					new Color(20, 18, 30), "OBSIDIAN", 0);
			new RGBBlockValue(new Color(190, 152, 78), new Color(164, 134, 75), new Color(164, 138, 77),
					new Color(102, 168, 140), "ORANGE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(167, 196, 246), new Color(164, 193, 245), new Color(164, 193, 244),
					new Color(168, 197, 249), "PACKED_ICE", 0);
			new RGBBlockValue(new Color(232, 155, 180), new Color(239, 153, 182), new Color(239, 157, 185),
					new Color(232, 155, 181), "PINK_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(96, 96, 96), new Color(98, 98, 98), new Color(96, 96, 96),
					new Color(96, 96, 96), "PISTON_BASE", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(120, 112, 97), new Color(99, 99, 99), new Color(113, 105, 91),
					new Color(94, 94, 94), "PISTON_BASE", 0);
			new RGBBlockValue(new Color(152, 129, 89), new Color(154, 130, 89), new Color(155, 131, 92),
					new Color(156, 131, 91), "PISTON_BASE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(139, 144, 99), new Color(143, 150, 102), new Color(143, 146, 101),
					new Color(145, 146, 100), "PISTON_STICKY_BASE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(105, 169, 148), new Color(105, 168, 149), new Color(110, 172, 151),
					new Color(109, 173, 157), "PRISMARINE", 0);
			new RGBBlockValue(new Color(103, 163, 146), new Color(101, 161, 143), new Color(103, 163, 145),
					new Color(95, 155, 140), "PRISMARINE", 1);
			new RGBBlockValue(new Color(150, 84, 16), new Color(141, 76, 11), new Color(142, 77, 12),
					new Color(137, 73, 12), "PUMPKIN", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(187, 135, 31), new Color(181, 125, 25), new Color(187, 135, 29),
					new Color(188, 140, 30), "JACK_O_LANTERN", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(203, 126, 27), new Color(195, 118, 21), new Color(195, 119, 23),
					new Color(199, 122, 24), "PUMPKIN", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(124, 63, 167), new Color(102, 44, 141), new Color(102, 44, 142),
					new Color(113, 43, 161), "PURPLE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(167, 122, 167), new Color(168, 124, 168), new Color(165, 121, 165),
					new Color(168, 123, 168), "PURPUR_BLOCK", 0);
			new RGBBlockValue(new Color(170, 127, 170), new Color(171, 127, 171), new Color(170, 127, 170),
					new Color(171, 127, 171), "PURPUR_PILLAR", 0);
			new RGBBlockValue(new Color(238, 236, 229), new Color(236, 233, 226), new Color(237, 234, 227),
					new Color(234, 231, 224), "QUARTZ_BLOCK", 0);
			new RGBBlockValue(new Color(135, 97, 91), new Color(125, 82, 78), new Color(123, 84, 79),
					new Color(119, 77, 73), "QUARTZ_ORE", 0);
			new RGBBlockValue(new Color(171, 28, 10), new Color(174, 28, 10), new Color(171, 28, 10),
					new Color(171, 28, 10), "REDSTONE_BLOCK", 0);
			new RGBBlockValue(new Color(71, 44, 27), new Color(67, 42, 26), new Color(70, 43, 27),
					new Color(75, 47, 29), "REDSTONE_LAMP_OFF", 0);
			new RGBBlockValue(new Color(121, 91, 56), new Color(114, 84, 50), new Color(118, 89, 56),
					new Color(126, 94, 59), "REDSTONE_LAMP_ON", 0);
			new RGBBlockValue(new Color(134, 109, 109), new Color(129, 112, 112), new Color(130, 111, 111),
					new Color(139, 100, 100), "REDSTONE_ORE", 0);
			new RGBBlockValue(new Color(96, 0, 0), new Color(135, 112, 64), new Color(64, 0, 0), new Color(64, 48, 32),
					"REDSTONE_TORCH_OFF", 0);
			new RGBBlockValue(new Color(169, 47, 43), new Color(192, 70, 61), new Color(177, 54, 49),
					new Color(192, 70, 61), "RED_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(70, 5, 7), new Color(71, 4, 6), new Color(67, 6, 9), new Color(67, 4, 7),
					"RED_NETHER_BRICK", 0);
			new RGBBlockValue(new Color(167, 85, 30), new Color(166, 85, 30), new Color(167, 85, 30),
					new Color(165, 84, 30), "RED_SANDSTONE", 0);
			new RGBBlockValue(new Color(124, 107, 179), new Color(124, 108, 173), new Color(124, 107, 172),
					new Color(125, 109, 165), "COMMAND_REPEATING", 0);
			new RGBBlockValue(new Color(219, 212, 160), new Color(218, 210, 159), new Color(220, 213, 162),
					new Color(220, 212, 161), "SAND", 0);
			new RGBBlockValue(new Color(219, 211, 160), new Color(217, 210, 157), new Color(218, 210, 158),
					new Color(216, 209, 155), "SANDSTONE", 0);
			new RGBBlockValue(new Color(173, 201, 191), new Color(171, 199, 190), new Color(174, 201, 192),
					new Color(173, 201, 192), "SEA_LANTERN", 0);
			new RGBBlockValue(new Color(121, 201, 102), new Color(123, 202, 104), new Color(121, 201, 100),
					new Color(119, 198, 100), "SLIME_BLOCK", 0);
			new RGBBlockValue(new Color(243, 252, 252), new Color(239, 251, 251), new Color(240, 251, 251),
					new Color(240, 251, 251), "SNOW_BLOCK", 0);
			new RGBBlockValue(new Color(82, 62, 49), new Color(88, 68, 55), new Color(84, 64, 51),
					new Color(86, 65, 53), "SOUL_SAND", 0);
			new RGBBlockValue(new Color(194, 195, 84), new Color(195, 196, 85), new Color(197, 197, 86),
					new Color(195, 196, 86), "SPONGE", 0);
			new RGBBlockValue(new Color(104, 81, 48), new Color(105, 82, 48), new Color(106, 82, 49),
					new Color(106, 82, 49), "LOG", 13);
			new RGBBlockValue(new Color(125, 125, 125), new Color(125, 125, 125), new Color(125, 125, 125),
					new Color(128, 128, 128), "STONE", 0);
			new RGBBlockValue(new Color(155, 116, 101), new Color(151, 112, 97), new Color(153, 113, 97),
					new Color(156, 117, 101), "STONE", 1);
			new RGBBlockValue(new Color(171, 171, 171), new Color(170, 170, 170), new Color(163, 163, 163),
					new Color(165, 165, 165), "STONE", 0);
			new RGBBlockValue(new Color(160, 160, 160), new Color(159, 159, 159), new Color(159, 159, 159),
					new Color(162, 162, 162), "DOUBLE_STEP", 8);
			new RGBBlockValue(new Color(89, 74, 89), new Color(89, 74, 89), new Color(90, 76, 91),
					new Color(90, 75, 91), "STRUCTURE_BLOCK", 0);
			new RGBBlockValue(new Color(237, 232, 178), new Color(194, 216, 207), new Color(194, 216, 208),
					new Color(131, 187, 220), "WHITE_GLAZED_TERRACOTTA", 0);
			new RGBBlockValue(new Color(251, 217, 114), new Color(241, 197, 81), new Color(240, 197, 82),
					new Color(206, 159, 79), "YELLOW_GLAZED_TERRACOTTA", 0);

			new RGBBlockValue(new Color(159, 158, 63), new Color(160, 159, 62), new Color(162, 161, 64),
					new Color(161, 160, 66), "SPONGE", 1);

			new RGBBlockValue(new Color(210, 177, 125), new Color(210, 144, 102), new Color(210, 177, 125),
					new Color(205, 173, 122), "HUGE_MUSHROOM_2");
			if (!isVersionHigherThan(1, 12)) {
				new RGBBlockValue(new Color(137, 101, 77), new Color(137, 108, 84), new Color(139, 103, 79),
						new Color(153, 117, 93), "HUGE_MUSHROOM_2", 14);
				new RGBBlockValue(new Color(183, 31, 29), new Color(183, 28, 26), new Color(181, 29, 27),
						new Color(189, 37, 35), "HUGE_MUSHROOM_1", 14);
				new RGBBlockValue(new Color(190, 187, 178), new Color(190, 209, 198), new Color(196, 193, 183),
						new Color(214, 211, 200), "HUGE_MUSHROOM_1", 10);
			}

			if (!isVersionHigherThan(1, 12)) {
				new RGBBlockValue(new Color(23, 19, 19), "WOOL", DyeColor.BLACK);
				new RGBBlockValue(new Color(43, 53, 133), "WOOL", DyeColor.BLUE);
				new RGBBlockValue(new Color(76, 48, 30), "WOOL", DyeColor.BROWN);
				new RGBBlockValue(new Color(49, 116, 143), "WOOL", DyeColor.CYAN);
				new RGBBlockValue(new Color(61, 61, 61), "WOOL", DyeColor.GRAY);
				new RGBBlockValue(new Color(55, 72, 28), "WOOL", DyeColor.GREEN);
				new RGBBlockValue(new Color(113, 143, 203), "WOOL", DyeColor.LIGHT_BLUE);
				new RGBBlockValue(new Color(66, 180, 58), "WOOL", DyeColor.LIME);
				new RGBBlockValue(new Color(184, 83, 193), "WOOL", DyeColor.MAGENTA);
				new RGBBlockValue(new Color(221, 129, 67), "WOOL", DyeColor.ORANGE);
				new RGBBlockValue(new Color(210, 128, 158), "WOOL", DyeColor.PINK);
				new RGBBlockValue(new Color(130, 62, 188), "WOOL", DyeColor.PURPLE);
				new RGBBlockValue(new Color(157, 56, 51), "WOOL", DyeColor.RED);
				try {
					new RGBBlockValue(new Color(162, 168, 168), "WOOL", DyeColor.valueOf("SILVER"));
				} catch (Error | Exception e45) {
					new RGBBlockValue(new Color(162, 168, 168), "WOOL", DyeColor.valueOf("LIGHT_GRAY"));

				}
				new RGBBlockValue(new Color(232, 231, 231), "WOOL", DyeColor.WHITE);
				new RGBBlockValue(new Color(188, 176, 42), "WOOL", DyeColor.YELLOW);
			} else {

				new RGBBlockValue(new Color(21, 22, 26), new Color(21, 22, 26), new Color(20, 21, 26),
						new Color(21, 22, 26), "WOOL", 15);
				new RGBBlockValue(new Color(54, 58, 158), new Color(53, 58, 158), new Color(53, 57, 157),
						new Color(53, 58, 158), "WOOL", 11);
				new RGBBlockValue(new Color(115, 72, 41), new Color(115, 73, 41), new Color(114, 72, 40),
						new Color(115, 72, 41), "WOOL", 12);
				new RGBBlockValue(new Color(22, 139, 146), new Color(22, 139, 146), new Color(22, 137, 145),
						new Color(22, 138, 146), "WOOL", 9);
				new RGBBlockValue(new Color(64, 69, 72), new Color(63, 69, 72), new Color(63, 68, 71),
						new Color(63, 69, 72), "WOOL", 7);
				new RGBBlockValue(new Color(85, 110, 28), new Color(85, 110, 27), new Color(85, 109, 28),
						new Color(86, 110, 28), "WOOL", 13);
				new RGBBlockValue(new Color(59, 176, 218), new Color(59, 177, 219), new Color(57, 174, 217),
						new Color(58, 175, 218), "WOOL", 3);
				new RGBBlockValue(new Color(143, 143, 136), new Color(143, 143, 136), new Color(142, 142, 134),
						new Color(142, 142, 135), "WOOL", DyeColor.GRAY.getWoolData());
				new RGBBlockValue(new Color(190, 70, 181), new Color(191, 70, 181), new Color(189, 68, 179),
						new Color(190, 69, 180), "WOOL", 2);
				new RGBBlockValue(new Color(242, 119, 21), new Color(242, 119, 21), new Color(240, 117, 19),
						new Color(241, 118, 20), "WOOL", 1);
				new RGBBlockValue(new Color(239, 143, 174), new Color(238, 143, 174), new Color(237, 140, 171),
						new Color(238, 141, 173), "WOOL", 6);
				new RGBBlockValue(new Color(123, 43, 174), new Color(122, 42, 172), new Color(121, 42, 172),
						new Color(122, 43, 173), "WOOL", 10);
				new RGBBlockValue(new Color(162, 40, 35), new Color(162, 40, 35), new Color(160, 39, 35),
						new Color(161, 40, 35), "WOOL", 14);
				new RGBBlockValue(new Color(234, 237, 238), new Color(235, 238, 238), new Color(233, 236, 236),
						new Color(234, 237, 237), "WOOL", 0);
				new RGBBlockValue(new Color(249, 199, 40), new Color(249, 199, 40), new Color(249, 197, 39),
						new Color(249, 198, 40), "WOOL", 4);
			}

			new RGBBlockValue(new Color(96, 96, 96), new Color(133, 133, 133), new Color(96, 96, 96),
					new Color(130, 130, 130), "FURNACE", 0, false);
			new RGBBlockValue(new Color(71, 71, 71), new Color(83, 83, 83), new Color(73, 73, 73),
					new Color(86, 86, 86), "FURNACE", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(71, 71, 71), new Color(179, 135, 99), new Color(73, 73, 73),
					new Color(178, 131, 98), "BURNING_FURNACE", 0, BlockFace.EAST);

			new RGBBlockValue(new Color(68, 66, 66), new Color(69, 67, 67), new Color(69, 67, 67),
					new Color(70, 68, 68), "OBSERVER", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(91, 90, 90), new Color(117, 117, 117), new Color(88, 87, 87),
					new Color(115, 115, 115), "OBSERVER", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(62, 60, 60), new Color(63, 61, 61), new Color(64, 62, 62),
					new Color(63, 62, 62), "OBSERVER", 0, BlockFace.NORTH);

			new RGBBlockValue(new Color(150, 84, 16), new Color(142, 77, 12), new Color(141, 76, 11),
					new Color(137, 73, 12), "PUMPKIN", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(187, 135, 31), new Color(187, 135, 29), new Color(181, 125, 25),
					new Color(188, 140, 30), "JACK_O_LANTERN", 0, BlockFace.EAST);
			new RGBBlockValue(new Color(203, 126, 27), new Color(195, 119, 23), new Color(195, 118, 21),
					new Color(199, 122, 24), "PUMPKIN", 0, BlockFace.WEST);
			new RGBBlockValue(new Color(152, 129, 89), new Color(155, 131, 92), new Color(154, 130, 89),
					new Color(156, 131, 91), "PISTON_BASE", 0, BlockFace.UP);
			new RGBBlockValue(new Color(139, 144, 99), new Color(143, 146, 101), new Color(143, 150, 102),
					new Color(145, 146, 100), "PISTON_STICKY_BASE", 0, BlockFace.UP);
			new RGBBlockValue(new Color(96, 96, 96), new Color(96, 96, 96), new Color(98, 98, 98),
					new Color(96, 96, 96), "PISTON_BASE", 0, BlockFace.WEST);

		}
		/**
		 * Depending on which way you choose (north-south or east-west), the sides and
		 * colors may be drastically different (one side is all dark gray the other has
		 * a light gray stip in the middle). Removing for now since I cannot determine
		 * which side a user chooses.
		 */

	}

	public static void printOutLineFor(File texture, Material material, int data) {
		if (texture.getName().endsWith("png") || texture.getName().endsWith("jpg")) {
			try {
				BufferedImage bi = ImageIO.read(texture);
				bi = bi.getSubimage(0, 0, bi.getWidth(), bi.getWidth());
				bi = RGBBlockColor.resize(bi, 2, 2);

				Pixel[][] pixels = convertTo2DWithoutUsingGetRGB(bi);
				MaterialData md = new MaterialData(material, (byte) data);

				Bukkit.getLogger().info("new RGBBlockValue(new Color(" + pixels[0][0].r + "," + pixels[0][0].g + ","
						+ pixels[0][0].b + "),new Color(" + pixels[0][1].r + "," + pixels[0][1].g + "," + pixels[0][1].b
						+ "),new Color(" + pixels[1][0].r + "," + pixels[1][0].g + "," + pixels[1][0].b + "),new Color("
						+ pixels[1][1].r + "," + pixels[1][1].g + "," + pixels[1][1].b + "),\""
						+ md.getMaterial().name() + "\"," + md.getData() + "," + false + ");");
			} catch (IOException e) {
				System.out
						.println("File " + texture.getName() + " was not an image. Remove this file from the folder.");
			}
		}
	}

	public static void loadResourcepackTextures(File file) {
		boolean logStuff = false;
		FileWriter fw = null;
		if (logStuff)
			try {
				fw = new FileWriter(new File(file.getParentFile(), "codeoutput.txt"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		for (File f : file.listFiles()) {
			if (f.getName().endsWith("png") || f.getName().endsWith("jpg")) {
				try {
					BufferedImage bi = ImageIO.read(f);
					// bi = bi.getSubimage(0, 0, bi.getWidth(), bi.getWidth());
					// bi = RGBBlockColor.resize(bi, 2, 2);
					// Pixel[][] pixels = convertTo2DWithoutUsingGetRGB(bi);
					Pixel[][] pixels = loadBlockTextures(bi);
					// TODO: Don't resize then average, get a corner and get the average pixel for
					// that corner.

					MaterialData md = FileNameToMaterialUtil
							.getMaterialData(f.getName().substring(0, f.getName().length() - 4));

					if (md == null) {
						continue;
					}
					if (fw != null) {
						boolean same = ((pixels[0][0].r == pixels[0][1].r) && (pixels[1][0].r == pixels[1][1].r) && (pixels[0][0].r == pixels[1][1].r)) && ((pixels[0][0].g == pixels[0][1].g) && (pixels[1][0].g == pixels[1][1].g) && (pixels[0][0].g == pixels[1][1].g)) && ((pixels[0][0].b == pixels[0][1].b) && (pixels[1][0].b == pixels[1][1].b) && (pixels[0][0].b == pixels[1][1].b));
						if (same) {
							fw.append("add(" + pixels[0][0].r + "," + pixels[0][0].g + "," + pixels[0][0].b + ",\"" + md.getMaterial().name()+"\"" + (md.getData() == 0 ? "" : "," + md.getData())
									+ (md.hasDirection() ? (",BlockFace." + md.getDirection().name()) : "")+");");
						} else {
							fw.append("add(" + pixels[0][0].r + "," + pixels[0][0].g + "," + pixels[0][0].b + ","
									+ pixels[0][1].r + "," + pixels[0][1].g + "," + pixels[0][1].b + ","
									+ pixels[1][0].r + "," + pixels[1][0].g + "," + pixels[1][0].b + ","
									+ pixels[1][1].r + "," + pixels[1][1].g + "," + pixels[1][1].b + ","

									+ "\""+md.getMaterial().name() +"\""+ (md.getData() == 0 ? "" : "," + md.getData()) + (md.hasDirection() ? (",BlockFace." + md.getDirection().name()) : "")+");");
						}
						/*fw.append(("new RGBBlockValue(new Color(" + pixels[0][0].r + ", " + pixels[0][0].g + ", "
								+ pixels[0][0].b + ")," + "new Color(" + pixels[0][1].r + ", " + pixels[0][1].g + ", "
								+ pixels[0][1].b + ")," + "new Color(" + pixels[1][0].r + ", " + pixels[1][0].g + ", "
								+ pixels[1][0].b + ")," + "new Color(" + pixels[1][1].r + ", " + pixels[1][1].g + ", "
								+ pixels[1][1].b + "), \"" + md.getMaterial().name() + "\"," + md.getData()
								+ (md.hasDirection() ? (",BlockFace." + md.getDirection().name()) : "") + ");"));*/
						fw.append("\n");
					}

					boolean needsFalse = false;
					MaterialData existing = null;
					if ((existing = MaterialData.getMatDataByTypes(md.getMaterial(), md.getData(),
							md.getDirection())) != null) {
						if ((materialValue.get(existing).hasFaces()))
							needsFalse = true;
						if ((!materialValue.get(existing).hasFaces()) || (!materialValue.get(existing).isTop())) {
							System.out.println("Overriding Text" + existing.getMaterial().name() + ":"
									+ existing.getData() + " for " + f.getName());
							materialValue.remove(existing);
						}
					}

					if (needsFalse) {
						new RGBBlockValue(new Color(pixels[0][0].r, pixels[0][0].g, pixels[0][0].b),
								new Color(pixels[0][1].r, pixels[0][1].g, pixels[0][1].b),
								new Color(pixels[1][0].r, pixels[1][0].g, pixels[1][0].b),
								new Color(pixels[1][1].r, pixels[1][1].g, pixels[1][1].b), md.getMaterial().name(),
								md.getData(), false, md.getDirection());

					} else {
						new RGBBlockValue(new Color(pixels[0][0].r, pixels[0][0].g, pixels[0][0].b),
								new Color(pixels[0][1].r, pixels[0][1].g, pixels[0][1].b),
								new Color(pixels[1][0].r, pixels[1][0].g, pixels[1][0].b),
								new Color(pixels[1][1].r, pixels[1][1].g, pixels[1][1].b), md.getMaterial().name(),
								md.getData(), md.getDirection());
					}
				} catch (IOException e) {
					System.out.println("File " + f.getName() + " was not an image. Remove this file from the folder.");
				}
			}
		}

		if (fw != null) {
			try {
				fw.write("anything");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This checks if the the server is running on a version higher or equal to the
	 * one specified. Useage: upToDate(1,8) will check if the version is greater
	 * than or equal to 1.8
	 *
	 * @return if the server version is greater than or equal to specified version.
	 */
	public static boolean isVersionHigherThan(int mainVersion, int secondVersion) {
		String firstChar = SERVER_VERSION.substring(1, 2);
		int fInt = Integer.parseInt(firstChar);
		if (fInt < mainVersion)
			return false;
		StringBuilder secondChar = new StringBuilder();
		for (int i = 3; i < 10; i++) {
			if (SERVER_VERSION.charAt(i) == '_' || SERVER_VERSION.charAt(i) == '.')
				break;
			secondChar.append(SERVER_VERSION.charAt(i));
		}
		int sInt = Integer.parseInt(secondChar.toString());
		return sInt >= secondVersion;
	}

	/**
	 * Used for debugging: Finds the value with the highest addition of all rgb
	 * blocks colors.
	 *
	 * @return
	 */
	public static int getFurthestColor() {

		int furthestPoint = 0;
		for (int r = 0; r < 256; r += 2) {
			for (int g = 0; g < 256; g += 2) {
				for (int b = 0; b < 256; b += 2) {
					MaterialData md = getClosestBlockValue(new Color(r, g, b), false);
					RGBBlockValue rgb = materialValue.get(md);
					int tr = r - rgb.r[0];
					int tg = g - rgb.g[0];
					int tb = b - rgb.b[0];
					int tr2 = r - rgb.r[2];
					int tg2 = g - rgb.g[2];
					int tb2 = b - rgb.b[2];
					if (tr < 0)
						tr = -tr;
					if (tg < 0)
						tg = -tg;
					if (tb < 0)
						tb = -tb;
					if (tr2 < 0)
						tr2 = -tr2;
					if (tg2 < 0)
						tg2 = -tg2;
					if (tb2 < 0)
						tb2 = -tb2;

					if (tr + tg + tb > furthestPoint)
						if (tr2 + tg2 + tb2 > furthestPoint)
							furthestPoint = Math.max(tr + tb + tg, tr2 + tg2 + tb2);
				}
			}
		}
		return furthestPoint;
	}

	/**
	 * This will return the Material and durability that has the closest color to
	 * Color "c".
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static MaterialData getClosestBlockValue(Color c, boolean topView) {
		Color[] color = {c, c, c, c};
		return getClosestBlockValue(color, topView, false);
	}

	/**
	 * This will return the Material and durability that has the closest color to
	 * Color "c".
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static MaterialData getClosestBlockValue(Color[] c, boolean topView) {
		return getClosestBlockValue(c, topView, false);
	}

	/**
	 * This will return the Material and durability that has the closest color to
	 * Color "c".
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static MaterialData getClosestBlockValue(Color c, boolean topView, boolean supportTransparent) {
		Color[] color = {c, c, c, c};
		return getClosestBlockValue(color, topView, supportTransparent);
	}

	/**
	 * Returns if the color is transparent (if the image supports it) or black (for
	 * non-transparent images)
	 *
	 * @param c
	 * @return
	 */
	public static boolean isBlackTransparent(Color c) {
		return c.getBlue() == 0 && c.getRed() == 0 && c.getGreen() == 0;
	}

	/**
	 * The color value of the four closest colors. Use this if you want to preserve
	 * hard edges in images. For the array, you need four color values. Use the
	 * following chart to understand which pixel should be at which index:
	 * <p>
	 * | 0 | 1 |
	 * <p>
	 * | 2 | 3 |
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static MaterialData getClosestBlockValue(Color[] c, boolean topView, boolean supportTransparent) {
		return getClosestBlockValue(c, topView, supportTransparent, null);
	}

	/**
	 * The color value of the four closest colors. Use this if you want to preserve
	 * hard edges in images. For the array, you need four color values. Use the
	 * following chart to understand which pixel should be at which index:
	 * <p>
	 * | 0 | 1 |
	 * <p>
	 * | 2 | 3 |
	 *
	 * @param c - The color value
	 * @return The closest material and durability.
	 */
	public static MaterialData getClosestBlockValue(Color[] c, boolean topView, boolean supportTransparent,
													Material[] supportedMaterials) {

		int cutoff = 20;

		int[] r = new int[4];
		int[] b = new int[4];
		int[] g = new int[4];
		int[] a = new int[4];
		for (int i = 0; i < c.length; i++) {
			r[i] = c[i].getRed();
			b[i] = c[i].getBlue();
			g[i] = c[i].getGreen();
			a[i] = c[i].getAlpha();
		}
		if (supportTransparent) {
			if (isBlackTransparent(c[0]) && isBlackTransparent(c[1]) && isBlackTransparent(c[2])
					&& isBlackTransparent(c[3]))
				return new MaterialData(Material.AIR);
		}
		if (a[0] + a[1] + a[2] + a[3] < (4 * cutoff)) {
			// TODO: If correct, this should help average the alpha, and make sure that if
			// any of the A's are greater than 80, then continue
			return new MaterialData(Material.AIR);
		}

		// double cR = 1000000;
		// double cB = 1000000;
		// double cG = 1000000;
		// TODO: It does not matter the difference for specific colors. Look for
		// difference in ALL colours.
		double difference = Double.MAX_VALUE;

		MaterialData closest = null;

		double[] tR = new double[4];
		double[] tG = new double[4];
		double[] tB = new double[4];
		for (Entry<MaterialData, RGBBlockValue> entry : materialValue.entrySet()) {
			if (supportedMaterials != null) {
				boolean supportedMaterial = false;
				for (Material k : supportedMaterials)
					if (k == entry.getKey().getMaterial()) {
						supportedMaterial = true;
						break;
					}
				if (!supportedMaterial)
					continue;
			}

			if (entry.getValue().hasFaces()) {
				if (!entry.getValue().hasTextureOnAllSides() && entry.getValue().isTop() != topView)
					continue;
			}

			for (int i = 0; i < 4; i++) {
				if (a[i] < cutoff) {
					tR[i] = 0;
					tG[i] = 0;
					tB[i] = 0;
					continue;
				}
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
			// Distance (terrible)
			/*
			 * if ((tR[0] * tR[0]) + (tG[0] * tG[0]) + (tB[0] * tB[0]) + (tR[1] * tR[1]) +
			 * (tG[1] * tG[1]) + (tB[1] * tB[1]) + (tR[2] * tR[2]) + (tG[2] * tG[2]) +
			 * (tB[2] * tB[2]) + (tR[3] * tR[3]) + (tG[3] * tG[3]) + (tB[3] * tB[3]) <
			 * cB+cR+cG) {
			 */
			// Distance (total closest favored)

			// Changing += to *=. Big differences will now stack.

			// double diffs = 1;
			// for (int i = 0; i < 4; i++) {
			// diffs *= 1 + Math.sqrt((tR[i] * tR[i]) + (tG[i] * tG[i]) + (tB[i] * tB[i]));
			// }

			double diffs = 1;
			for (int i = 0; i < 4; i++) {
				diffs *= ((tR[i] * tR[i]) + (tG[i] * tG[i]) + (tB[i] * tB[i]));
			}

			/*
			 * double diffs = 0; for (int i = 0; i < 4; i++) { diffs += Math.sqrt((tR[i] *
			 * tR[i]) + (tG[i] * tG[i]) + (tB[i] * tB[i])); }
			 */

			if (diffs < difference) {
				difference = diffs;
				closest = entry.getKey();
			}
		}
		return closest;
	}

	/**
	 * Creates a resized version of an image. Since this takes 2x2 pixels to turn
	 * into 1 block, you can resize this by 2 if you want a 1-1 scale image.
	 *
	 * @param originalImage
	 * @param scaledHeight
	 * @param preserveAlpha
	 * @return
	 */
	@Deprecated
	public static BufferedImage createResizedCopy(BufferedImage originalImage, int scaledHeight,
												  boolean preserveAlpha) {
		int imageType = BufferedImage.TYPE_INT_ARGB;
		int WIDTH = (int) (((double) originalImage.getWidth()) * (((double) scaledHeight) / originalImage.getHeight()));
		int HEIGHT = scaledHeight;
		BufferedImage scaledBI = new BufferedImage(WIDTH, HEIGHT, imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledBI.getWidth(), scaledBI.getHeight(), null);
		g.dispose();
		return scaledBI;
	}

	/**
	 * From:
	 */

	public static BufferedImage resize(BufferedImage image, int areaWidth, int areaHeight) {
		float scaleX = (float) areaWidth / image.getWidth();
		float scaleY = (float) areaHeight / image.getHeight();
		float scale = Math.min(scaleX, scaleY);
		int w = Math.round(image.getWidth() * scale);
		int h = Math.round(image.getHeight() * scale);

		int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_ARGB
				: BufferedImage.TYPE_INT_ARGB;

		boolean scaleDown = scale < 1;

		if (scaleDown) {
			// multi-pass bilinear div 2
			int currentW = image.getWidth();
			int currentH = image.getHeight();
			BufferedImage resized = image;
			while (currentW > w || currentH > h) {
				currentW = Math.max(w, currentW / 2);
				currentH = Math.max(h, currentH / 2);

				BufferedImage temp = new BufferedImage(currentW, currentH, type);
				Graphics2D g2 = temp.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(resized, 0, 0, currentW, currentH, null);
				g2.dispose();
				resized = temp;
			}
			return resized;
		} else {
			Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC
					: RenderingHints.VALUE_INTERPOLATION_BILINEAR;

			BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resized.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(image, 0, 0, w, h, null);
			g2.dispose();
			return resized;
		}
	}

	/**
	 * This returns the pixels for block textures. Instead of shriking the image,
	 * wich causes some blends, this uses sub-images being reduced to single pixels,
	 * which prevents cross-over for certain pixels
	 *
	 * @param image
	 * @return
	 */
	public static Pixel[][] loadBlockTextures(BufferedImage image) {
		Pixel[][] result = new Pixel[2][2];
		for (int x = 0; x < 4; x++) {
			BufferedImage temp = image.getSubimage(0 + ((x % 2) * (image.getWidth() / 2)),
					0 + ((x / 2) * (image.getWidth() / 2)), image.getWidth() / 2, (image.getWidth() / 2));
			temp = resize(temp, 1, 1);
			result[x % 2][x / 2] = convertTo2DWithoutUsingGetRGB(temp)[0][0];
		}
		return result;
	}

	/**
	 * This gets all the pixel values for an image. Use this to get all the pixels
	 * for an image.
	 * <p>
	 * The first array stores the Row value (e.g. MC's "Y" value), and the second
	 * array stores the Columb value (MC's X or Z)
	 * <p>
	 * For example: If you want to get the pixel at the top left of an image, use
	 * convertTo2DWithoutUsingGetRGB(Image)[HEIGHT][0]
	 * <p>
	 * For example: If you want to get the pixel at the bottom right of an image,
	 * use convertTo2DWithoutUsingGetRGB(Image)[0][WIDTH]
	 *
	 * @param image
	 * @return
	 */
	public static Pixel[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
		if (image.getRaster().getDataBuffer() instanceof DataBufferByte) {
			final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			final int width = image.getWidth();
			final int height = image.getHeight();
			final boolean hasAlphaChannel = image.getAlphaRaster() != null;

			Pixel[][] result = new Pixel[height][width];
			int pixelLength = 0;
			int offset = 0;
			if (hasAlphaChannel) {
				pixelLength = 4;
				offset = 1;
			} else {
				pixelLength = 3;
			}

			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int r = 0;
				int b = 0;
				int g = 0;
				int a = 255;
				// argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				b += ((int) pixels[pixel + offset] & 0xff); // blue
				g += (((int) pixels[pixel + offset + 1] & 0xff) /* << 8 */); // green
				r += (((int) pixels[pixel + offset + 2] & 0xff) /* << 16 */); // red
				if (hasAlphaChannel)
					a = (((int) pixels[pixel] & 0xff));
				// System.out.println(row+" c"+col);
				result[row][col] = new Pixel(r, g, b, a);
				col++;
				if (col == width) {
					col = 0;
					row++;
					if (row == height)
						break;
				}
			}

			return result;
		} else if (image.getRaster().getDataBuffer() instanceof DataBufferInt) {
			final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
			final int width = image.getWidth();
			final int height = image.getHeight();
			final boolean hasAlphaChannel = image.getAlphaRaster() != null;

			Pixel[][] result = new Pixel[height][width];
			if (hasAlphaChannel) {
				final int pixelLength = 1;// 4;
				for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
					int r = 0;
					int b = 0;
					int g = 0;
					int a = 0;
					/*
					 * // argb += (((int) pixels[pixel] & 0xff) << 24); // alpha b += ((int)
					 * pixels[pixel + 1] & 0xff); // blue g += (((int) pixels[pixel + 2] & 0xff) /*
					 * << 8 *); // green r += (((int) pixels[pixel + 3] & 0xff) /* << 16 *); // red
					 */

					a = (pixels[pixel] >> 24) & 0xFF;
					r = (pixels[pixel] >> 16) & 0xFF;
					g = (pixels[pixel] >> 8) & 0xFF;
					b = pixels[pixel] & 0xFF;

					result[row][col] = new Pixel(r, g, b, a);
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
					result[row][col] = new Pixel(r, g, b);
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

	/**
	 * Creates a file with an image where each pixel represense the colorcode for a
	 * block
	 *
	 * @param output     file
	 * @param bottomLeft corner of the image
	 * @param topRight   corner of the image
	 */
	@SuppressWarnings("deprecation")
	public void createImageFromBlocks(File output, Location bottomLeft, Location topRight) {
		boolean isX = bottomLeft.getBlockZ() == topRight.getBlockZ();
		MaterialData[][] blocks = new MaterialData[topRight.getBlockY() - bottomLeft.getBlockY()][isX
				? Math.max(topRight.getBlockX(), bottomLeft.getBlockX())
				- Math.min(topRight.getBlockX(), bottomLeft.getBlockX())
				: Math.max(topRight.getBlockZ(), bottomLeft.getBlockZ())
				- Math.min(topRight.getBlockZ(), bottomLeft.getBlockZ())];
		for (int y = bottomLeft.getBlockY(); y < topRight.getBlockY(); y++) {
			if (isX) {
				for (int x = Math.min(topRight.getBlockX(), bottomLeft.getBlockX()); x < Math.max(topRight.getBlockX(),
						bottomLeft.getBlockX()); x++) {
					Block t = new Location(topRight.getWorld(), x, y, topRight.getBlockZ()).getBlock();
					blocks[y - bottomLeft.getBlockY()][x
							- Math.min(topRight.getBlockX(), bottomLeft.getBlockX())] = MaterialData
							.getMatDataByTypes(t.getType(), t.getData());
				}
			} else {
				for (int z = Math.min(topRight.getBlockZ(), bottomLeft.getBlockZ()); z < Math.max(topRight.getBlockZ(),
						bottomLeft.getBlockZ()); z++) {
					Block t = new Location(topRight.getWorld(), topRight.getBlockX(), y, z).getBlock();
					blocks[y - bottomLeft.getBlockY()][z
							- Math.min(topRight.getBlockZ(), bottomLeft.getBlockZ())] = MaterialData
							.getMatDataByTypes(t.getType(), t.getData());
				}
			}
		}
		createImageFromBlocks(output, blocks);
	}

	/**
	 * Creates a file with an image where each pixel represense the colorcode for a
	 * block
	 */
	public void createImageFromBlocks(File output, MaterialData[][] blocks) {
		BufferedImage canvas = new BufferedImage(blocks[0].length, blocks.length, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < blocks.length; y++) {
			for (int x = 0; x < blocks[0].length; x++) {
				for (MaterialData rgb : materialValue.keySet()) {
					if (rgb.getMaterial() == blocks[y][x].getMaterial() && rgb.getData() == blocks[y][x].getData()) {
						int col = (materialValue.get(rgb).r[0] << 16) | (materialValue.get(rgb).g[0] << 8)
								| materialValue.get(rgb).b[0];
						canvas.setRGB(x, y, col);
					}
				}
			}
		}
		try {
			ImageIO.write(canvas, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Pixel {
		public int r;
		public int b;
		public int g;
		public int a;

		public Pixel(int r, int g, int b) {
			this(r, g, b, 255);
		}

		public Pixel(int r, int g, int b, int a) {
			this.r = r;
			this.b = b;
			this.g = g;
			this.a = a;
		}
	}

}

class RGBValue {
	protected int[] r = new int[4];
	protected int[] b = new int[4];
	protected int[] g = new int[4];
	protected int[] a = new int[4];

	public RGBValue(Color c) {
		init(c, c, c, c);
	}

	public RGBValue(Color c, Color c2, Color c3, Color c4) {
		init(c, c2, c3, c4);
	}

	private void init(Color c, Color c2, Color c3, Color c4) {
		this.r[0] = c.getRed();
		this.g[0] = c.getGreen();
		this.b[0] = c.getBlue();
		// /
		this.r[1] = c2.getRed();
		this.g[1] = c2.getGreen();
		this.b[1] = c2.getBlue();
		// /
		this.r[2] = c3.getRed();
		this.g[2] = c3.getGreen();
		this.b[2] = c3.getBlue();
		// /
		this.r[3] = c4.getRed();
		this.g[3] = c4.getGreen();
		this.b[3] = c4.getBlue();

		this.a[0] = c4.getAlpha();
		this.a[1] = c4.getAlpha();
		this.a[2] = c4.getAlpha();
		this.a[3] = c4.getAlpha();
	}
}

class RGBBlockValue extends RGBValue {

	private boolean hasFaces = false;
	private boolean isTop = false;
	private boolean hasTextureOnAllSides = true;

	public RGBBlockValue(Color c, String mat) {
		super(c);
		if (Material.matchMaterial(mat) == null)
			return;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) 0), this);

	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String m) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(m) == null)
			return;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(m), (byte) 0), this);
	}

	public RGBBlockValue(Color c, String m, byte d) {
		super(c);
		if (Material.matchMaterial(m) == null)
			return;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(m), d), this);
	}

	public RGBBlockValue(Color c, String mat, int d) {
		super(c);
		if (Material.matchMaterial(mat) == null)
			return;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) d), this);
	}

	public RGBBlockValue(Color c, String mat, int d, boolean isTop) {
		super(c);
		if (Material.matchMaterial(mat) == null)
			return;
		this.isTop = isTop;
		this.hasFaces = true;
		hasTextureOnAllSides = false;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) d), this);
	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, int d) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(mat) == null)
			return;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) d), this);
	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, boolean isTop) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(mat) == null)
			return;
		this.hasFaces = true;
		this.isTop = isTop;
		hasTextureOnAllSides = false;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) 0), this);
	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, int d, boolean isTop) {
		this(c, c2, c3, c4, mat, d, isTop, null);
	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, int d, boolean isTop, BlockFace face) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(mat) == null)
			return;
		this.hasFaces = true;
		hasTextureOnAllSides = false;
		this.isTop = isTop;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) d, face), this);
	}

	/**
	 * Door variables
	 */

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, int d, BlockFace direction) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(mat) == null)
			return;
		this.hasFaces = true;
		hasTextureOnAllSides = false;
		this.isTop = false;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) d, direction), this);
	}

	public RGBBlockValue(Color c, Color c2, Color c3, Color c4, String mat, BlockFace direction) {
		super(c, c2, c3, c4);
		if (Material.matchMaterial(mat) == null)
			return;
		this.hasFaces = true;
		hasTextureOnAllSides = false;
		this.isTop = false;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) 0, direction), this);
	}

	public RGBBlockValue(Color c, String mat, boolean isTop) {
		super(c);
		if (Material.matchMaterial(mat) == null)
			return;
		this.hasFaces = true;
		hasTextureOnAllSides = false;
		this.isTop = isTop;
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), (byte) 0), this);
	}

	public RGBBlockValue(Color c, String mat, DyeColor d) {
		super(c);
		if (Material.matchMaterial(mat) == null)
			return;
		byte dyecolor = -1;
		if ((ReflectionUtil.isVersionHigherThan(1, 10)))
			dyecolor = (byte) ReflectionUtil.invokeMethod(d, "getWoolData", null);
		else
			dyecolor = (byte) ReflectionUtil.invokeMethod(d, "getData", null);
		RGBBlockColor.materialValue.put(new MaterialData(Material.matchMaterial(mat), dyecolor), this);
	}

	public boolean hasTextureOnAllSides() {
		return hasTextureOnAllSides;
	}

	public boolean isTop() {
		return isTop;
	}

	public boolean hasFaces() {
		return hasFaces;
	}
}
