package me.zombie_striker.pixelprinter;
import java.util.ArrayList;
import java.util.List;

import me.zombie_striker.pixelprinter.V1_9.BlockColor_1_9;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

/**
* Created by Zombie_Striker on 3/30/2016
**/
public class BlockColor {

	// This stores all the Chatcolors and DyeColors for each block
	private static List<BlockColorData> colorList = new ArrayList<BlockColorData>();

	//This is the instance of this class.
	private static BlockColor bc = new BlockColor();

	private BlockColor(){}
	
	//Add all of the materials.
	static {
		// Base Materials
		// bc.new BlockColorData(Material,ChatColor,DyeColor);
		bc.new BlockColorData(Material.DIRT, ColorData.BROWN);
		bc.new BlockColorData(Material.GRASS, ColorData.GREEN);
		bc.new BlockColorData(Material.SAND, ColorData.YELLOW);
		bc.new BlockColorData(Material.PACKED_ICE, ColorData.AQUA);
		bc.new BlockColorData(Material.ICE, ColorData.AQUA);
		bc.new BlockColorData(Material.MYCEL, ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.GRAVEL, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.STONE, 0, ColorData.ORANGE);
		bc.new BlockColorData(Material.STONE, 1, ColorData.ORANGE);
		bc.new BlockColorData(Material.STONE, 2, ColorData.WHITE);
		bc.new BlockColorData(Material.STONE, 3, ColorData.WHITE);
		bc.new BlockColorData(Material.STONE, 4, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.STONE, 5, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.BEDROCK, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.FURNACE, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.CAULDRON, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.HOPPER, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.BREWING_STAND, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.ANVIL, ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.DIAMOND_BLOCK, ColorData.AQUA);
		bc.new BlockColorData(Material.GOLD_BLOCK, ColorData.YELLOW);
		bc.new BlockColorData(Material.IRON_BLOCK, ColorData.WHITE);
		bc.new BlockColorData(Material.EMERALD_BLOCK, ColorData.GREEN);
		bc.new BlockColorData(Material.REDSTONE_BLOCK, ColorData.RED);
		bc.new BlockColorData(Material.COAL_BLOCK, ColorData.BLACK);
		bc.new BlockColorData(Material.LAPIS_BLOCK, ColorData.BLUE);
		bc.new BlockColorData(Material.OBSIDIAN, ColorData.BLACK);
		bc.new BlockColorData(Material.WATER, ColorData.BLUE);
		bc.new BlockColorData(Material.LAVA, ColorData.ORANGE);
		bc.new BlockColorData(Material.WATER_LILY, ColorData.GREEN);
		bc.new BlockColorData(Material.NETHER_BRICK, ColorData.BROWN);
		bc.new BlockColorData(Material.NETHER_BRICK_STAIRS, ColorData.BROWN);
		bc.new BlockColorData(Material.NETHER_STALK, ColorData.RED);
		bc.new BlockColorData(Material.NETHERRACK, ColorData.RED);
		bc.new BlockColorData(Material.GLOWSTONE, ColorData.YELLOW);
		bc.new BlockColorData(Material.SOUL_SAND, ColorData.BROWN);
		bc.new BlockColorData(Material.SOIL, ColorData.BROWN);
		bc.new BlockColorData(Material.GLASS, ColorData.WHITE);
		bc.new BlockColorData(Material.TNT, ColorData.RED);
		bc.new BlockColorData(Material.BRICK, ColorData.RED);
		bc.new BlockColorData(Material.BRICK_STAIRS, ColorData.RED);
		bc.new BlockColorData(Material.CHEST, ColorData.BROWN);
		bc.new BlockColorData(Material.TRAPPED_CHEST, ColorData.BROWN);
		bc.new BlockColorData(Material.BEACON, ColorData.BLUE);
		bc.new BlockColorData(Material.NOTE_BLOCK, ColorData.BROWN);
		bc.new BlockColorData(Material.JUKEBOX,ColorData.BROWN);
		bc.new BlockColorData(Material.MOB_SPAWNER,ColorData.BLACK);
		bc.new BlockColorData(Material.FIRE,ColorData.ORANGE);
		bc.new BlockColorData(Material.CLAY,ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.CLAY_BRICK,ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.RED_SANDSTONE,ColorData.RED);
		bc.new BlockColorData(Material.RED_SANDSTONE_STAIRS,ColorData.RED);
		
		//Ender
		bc.new BlockColorData(Material.ENDER_STONE, ColorData.YELLOW);
		bc.new BlockColorData(Material.ENDER_PORTAL_FRAME, ColorData.WHITE);
		bc.new BlockColorData(Material.ENDER_PORTAL, ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.ENDER_CHEST, ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.DRAGON_EGG, ColorData.LIGHT_PURPLE);

		bc.new BlockColorData(Material.PUMPKIN, ColorData.ORANGE);
		bc.new BlockColorData(Material.MELON_BLOCK, ColorData.GREEN);
		bc.new BlockColorData(Material.CAKE_BLOCK, ColorData.WHITE);
		bc.new BlockColorData(Material.CACTUS, ColorData.GREEN);
		bc.new BlockColorData(Material.DEAD_BUSH, ColorData.BROWN);
		bc.new BlockColorData(Material.SUGAR_CANE_BLOCK, ColorData.GREEN);
		bc.new BlockColorData(Material.MELON_STEM, ColorData.BROWN);
		bc.new BlockColorData(Material.CROPS, ColorData.YELLOW);
		bc.new BlockColorData(Material.CARROT, ColorData.ORANGE);
		bc.new BlockColorData(Material.POTATO, ColorData.GREEN);

		//Redstone
		bc.new BlockColorData(Material.REDSTONE_LAMP_OFF, ColorData.BROWN);
		bc.new BlockColorData(Material.REDSTONE_LAMP_ON, ColorData.ORANGE);
		bc.new BlockColorData(Material.REDSTONE_TORCH_OFF, ColorData.BROWN);
		bc.new BlockColorData(Material.REDSTONE_TORCH_ON, ColorData.ORANGE);
		bc.new BlockColorData(Material.REDSTONE, ColorData.RED);
		bc.new BlockColorData(Material.REDSTONE_COMPARATOR, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.REDSTONE_COMPARATOR_OFF, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.REDSTONE_COMPARATOR_ON, ColorData.LIGHT_GRAY);
		
		// Wood Colors
		bc.new BlockColorData(Material.WOOD, 0, ColorData.BROWN);
		bc.new BlockColorData(Material.LOG, 0, ColorData.BROWN);
		bc.new BlockColorData(Material.WOOD, 1, ColorData.BROWN);
		bc.new BlockColorData(Material.LOG, 1, ColorData.BROWN);
		bc.new BlockColorData(Material.WOOD, 2, ColorData.YELLOW);
		bc.new BlockColorData(Material.LOG, 2, ColorData.WHITE);
		bc.new BlockColorData(Material.WOOD, 3, ColorData.RED);
		bc.new BlockColorData(Material.LOG, 3, ColorData.BROWN);
		bc.new BlockColorData(Material.WOOD, 4, ColorData.RED);
		bc.new BlockColorData(Material.LOG, 4, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.WOOD, 5, ColorData.BROWN);
		bc.new BlockColorData(Material.LOG, 6, ColorData.BROWN);

		bc.new BlockColorData(Material.WOOD_STAIRS, ColorData.BROWN);
		bc.new BlockColorData(Material.SPRUCE_WOOD_STAIRS, ColorData.BROWN);
		bc.new BlockColorData(Material.BIRCH_WOOD_STAIRS, ColorData.YELLOW);
		bc.new BlockColorData(Material.JUNGLE_WOOD_STAIRS, ColorData.RED);
		bc.new BlockColorData(Material.ACACIA_STAIRS, ColorData.RED);
		bc.new BlockColorData(Material.DARK_OAK_STAIRS, ColorData.BROWN);

		bc.new BlockColorData(Material.LEAVES, ColorData.GREEN);
		bc.new BlockColorData(Material.LEAVES_2, ColorData.GREEN);

		bc.new BlockColorData(Material.BOOKSHELF, ColorData.BROWN);
		bc.new BlockColorData(Material.ENCHANTMENT_TABLE, ColorData.BLACK);

		// TODO: Add all data for each type of step.
		bc.new BlockColorData(Material.STEP, ColorData.BROWN);

		// Bricks:
		bc.new BlockColorData(Material.MOSSY_COBBLESTONE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.COBBLESTONE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.SMOOTH_BRICK, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.SMOOTH_STAIRS, ColorData.LIGHT_GRAY);

		bc.new BlockColorData(Material.QUARTZ_BLOCK, ColorData.WHITE);
		bc.new BlockColorData(Material.QUARTZ_STAIRS, ColorData.WHITE);
		bc.new BlockColorData(Material.QUARTZ_ORE, ColorData.RED);

		// Glass

		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.BLACK.ordinal(),
				ColorData.BLACK);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.BLUE.ordinal(),
				ColorData.BLUE);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.BROWN.ordinal(),
				ColorData.BROWN);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.CYAN.ordinal(),
				ColorData.CYAN);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.GRAY.ordinal(),
				ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.GREEN.ordinal(),
				ColorData.DARK_GREEN);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.LIGHT_BLUE.ordinal(),
				ColorData.AQUA);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.LIME.ordinal(),
				ColorData.GREEN);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.MAGENTA.ordinal(),
				ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.ORANGE.ordinal(),
				ColorData.ORANGE);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.RED.ordinal(),
				ColorData.RED);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.PINK.ordinal(),
				ColorData.PINK);
		bc.new BlockColorData(Material.STAINED_GLASS, DyeColor.SILVER.ordinal(),
				ColorData.LIGHT_GRAY);


		
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.BLACK.ordinal(),
				ColorData.BLACK);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.BLUE.ordinal(),
				ColorData.BLUE);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.BROWN.ordinal(),
				ColorData.BROWN);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.CYAN.ordinal(),
				ColorData.CYAN);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.GRAY.ordinal(),
				ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.GREEN.ordinal(),
				ColorData.DARK_GREEN);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.LIGHT_BLUE.ordinal(),
				ColorData.AQUA);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.LIME.ordinal(),
				ColorData.GREEN);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.MAGENTA.ordinal(),
				ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.ORANGE.ordinal(),
				ColorData.ORANGE);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.RED.ordinal(),
				ColorData.RED);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.PINK.ordinal(),
				ColorData.PINK);
		bc.new BlockColorData(Material.STAINED_GLASS_PANE, DyeColor.SILVER.ordinal(),
				ColorData.LIGHT_GRAY);
		
		// Clay
		bc.new BlockColorData(Material.HARD_CLAY, ColorData.BROWN);
		
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.BLACK.ordinal(),
				ColorData.BLACK);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.BLUE.ordinal(),
				ColorData.BLUE);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.BROWN.ordinal(),
				ColorData.BROWN);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.CYAN.ordinal(),
				ColorData.CYAN);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.GRAY.ordinal(),
				ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.GREEN.ordinal(),
				ColorData.DARK_GREEN);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.LIGHT_BLUE.ordinal(),
				ColorData.AQUA);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.LIME.ordinal(),
				ColorData.GREEN);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.MAGENTA.ordinal(),
				ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.ORANGE.ordinal(),
				ColorData.ORANGE);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.RED.ordinal(),
				ColorData.RED);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.PINK.ordinal(),
				ColorData.PINK);
		bc.new BlockColorData(Material.STAINED_CLAY, DyeColor.SILVER.ordinal(),
				ColorData.LIGHT_GRAY);
		
		// WoolColors

		bc.new BlockColorData(Material.WOOL, DyeColor.BLACK.ordinal(),
				ColorData.BLACK);
		bc.new BlockColorData(Material.WOOL, DyeColor.BLUE.ordinal(),
				ColorData.BLUE);
		bc.new BlockColorData(Material.WOOL, DyeColor.BROWN.ordinal(),
				ColorData.BROWN);
		bc.new BlockColorData(Material.WOOL, DyeColor.CYAN.ordinal(),
				ColorData.CYAN);
		bc.new BlockColorData(Material.WOOL, DyeColor.GRAY.ordinal(),
				ColorData.DARK_GRAY);
		bc.new BlockColorData(Material.WOOL, DyeColor.GREEN.ordinal(),
				ColorData.DARK_GREEN);
		bc.new BlockColorData(Material.WOOL, DyeColor.LIGHT_BLUE.ordinal(),
				ColorData.AQUA);
		bc.new BlockColorData(Material.WOOL, DyeColor.LIME.ordinal(),
				ColorData.GREEN);
		bc.new BlockColorData(Material.WOOL, DyeColor.MAGENTA.ordinal(),
				ColorData.LIGHT_PURPLE);
		bc.new BlockColorData(Material.WOOL, DyeColor.ORANGE.ordinal(),
				ColorData.ORANGE);
		bc.new BlockColorData(Material.WOOL, DyeColor.RED.ordinal(),
				ColorData.RED);
		bc.new BlockColorData(Material.WOOL, DyeColor.PINK.ordinal(),
				ColorData.PINK);
		bc.new BlockColorData(Material.WOOL, DyeColor.SILVER.ordinal(),
				ColorData.LIGHT_GRAY);

		// Ores
		bc.new BlockColorData(Material.REDSTONE_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.DIAMOND_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.EMERALD_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.IRON_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.COAL_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.GOLD_ORE, ColorData.LIGHT_GRAY);
		bc.new BlockColorData(Material.LAPIS_ORE, ColorData.LIGHT_GRAY);

		// Doors
		bc.new BlockColorData(Material.ACACIA_DOOR, ColorData.BROWN);
		bc.new BlockColorData(Material.DARK_OAK_DOOR, ColorData.BROWN);
		bc.new BlockColorData(Material.BIRCH_DOOR, ColorData.BROWN);
		bc.new BlockColorData(Material.WOODEN_DOOR, ColorData.BROWN);
		bc.new BlockColorData(Material.SPRUCE_DOOR, ColorData.BROWN);
		bc.new BlockColorData(Material.IRON_DOOR_BLOCK, ColorData.WHITE);
		
		//Extras
		bc.new BlockColorData(Material.BARRIER, ColorData.WHITE);
		
		if(Bukkit.getVersion().contains("1.9-")){
			new BlockColor_1_9(bc);
		}
		
	}

	/**
	 * Returns the ChatColor associated with the Type ID.
	 * 
	 * @param id
	 * @return ChatColor
	 */
	public static ChatColor getChatColor(int id) {
		for (BlockColorData bcd : colorList) {
			if (bcd.typeID == id && !bcd.hasData) {
				return bcd.getChatColor();
			}
		}
		return ChatColor.BLACK;
	}

	/**
	 * Returns the DyeColor associated with the Type ID.
	 * 
	 * @param id
	 * @return DyeColor
	 */
	public static DyeColor getDyeColor(int id) {
		for (BlockColorData bcd : colorList) {
			if (bcd.typeID == id && !bcd.hasData) {
				return bcd.getDyeColor();
			}
		}
		return DyeColor.BLACK;
	}

	/**
	 * Returns the ChatColor associated with the Type.
	 * 
	 * @param mat
	 * @return ChatColor
	 */
	public static ChatColor getChatColor(Material mat) {
		for (BlockColorData bcd : colorList) {
			if (bcd.usesMaterials() && bcd.type == mat && !bcd.hasData) {
				return bcd.getChatColor();
			}
		}
		return ChatColor.BLACK;
	}

	/**
	 * Returns the DyeColorColor associated with the Type.
	 * 
	 * @param mat
	 * @return DyeColor
	 */
	public static DyeColor getDyeColor(Material mat) {
		for (BlockColorData bcd : colorList) {
			if (bcd.usesMaterials() && bcd.type == mat && !bcd.hasData) {
				return bcd.getDyeColor();
			}
		}
		return DyeColor.BLACK;
	}

	/**
	 * Returns the ChatColor associated with the Type ID and data.
	 * 
	 * @param id
	 * @param data
	 * @return ChatColor
	 */
	public static ChatColor getChatColor(int id, short data) {
		for (BlockColorData bcd : colorList) {
			if (bcd.typeID == id && bcd.getData() == data) {
				return bcd.getChatColor();
			}
		}
		return ChatColor.BLACK;
	}
	/**
	 * Returns the DyeColor associated with the Type ID and data.
	 * @param id
	 * @param data
	 * @return DyeColor
	 */
	public static DyeColor getDyeColor(int id, short data) {
		for (BlockColorData bcd : colorList) {
			if (bcd.typeID == id && bcd.getData() == data) {
				return bcd.getDyeColor();
			}
		}
		return DyeColor.BLACK;
	}
	/**
	 * Returns the ChatColor associated with the Type  and data.
	 * @param mat
	 * @param data
	 * @return ChatColor
	 */
	public static ChatColor getChatColor(Material mat, short data) {
		for (BlockColorData bcd : colorList) {
			if (bcd.usesMaterials() && bcd.type == mat && bcd.getData() == data) {
				return bcd.getChatColor();
			}
		}
		return ChatColor.BLACK;
	}
	/**
	 * Returns the DyeColorColor associated with the Type and data.
	 * @param mat
	 * @param data
	 * @return
	 */
	public static DyeColor getDyeColor(Material mat, short data) {
		for (BlockColorData bcd : colorList) {
			if (bcd.usesMaterials() && bcd.type == mat && bcd.getData() == data) {
				return bcd.getDyeColor();
			}
		}
		return DyeColor.BLACK;
	}
	/**
	 * Gets the Type associated with the DyeColor
	 * @param dc
	 * @return Material
	 */
	public static Material getMaterialWithColor(DyeColor dc) {
		for (BlockColorData bdc : colorList) {
			if (bdc.getDyeColor().equals(dc) && !bdc.hasData)
				return bdc.getType();
		}
		return Material.STONE;
	}
	/**
	 * Gets the Type associated with the ChatColorColor
	 * @param cc
	 * @return Material
	 */
	public static Material getMaterialWithColor(ChatColor cc) {
		for (BlockColorData bdc : colorList) {
			if (bdc.getChatColor().equals(cc) && !bdc.hasData)
				return bdc.getType();
		}
		return Material.STONE;
	}
	/**
	 * Gets the TypeID associated with the DyeColor
	 * @param dc
	 * @return int
	 */
	public static int getIDWithColor(DyeColor dc) {
		for (BlockColorData bdc : colorList) {
			if (bdc.getDyeColor().equals(dc) && !bdc.hasData)
				return bdc.getTypeID();
		}
		return 1;
	}
	/**
	 * Gets the TypeID associated with the ChatColor
	 * @param cc
	 * @return int
	 */
	public static int getIDWithColor(ChatColor cc) {
		for (BlockColorData bdc : colorList) {
			if (bdc.getChatColor().equals(cc) && !bdc.hasData)
				return bdc.getTypeID();
		}
		return 1;
	}

	public enum ColorData {
		BLACK(ChatColor.BLACK, DyeColor.BLACK), BLUE(ChatColor.BLUE,
				DyeColor.BLUE), BROWN(ChatColor.DARK_RED, DyeColor.BROWN), CYAN(
				ChatColor.AQUA, DyeColor.CYAN), DARK_GRAY(ChatColor.DARK_GRAY,
				DyeColor.GRAY), DARK_GREEN(ChatColor.DARK_GREEN, DyeColor.GREEN), AQUA(
				ChatColor.BLUE, DyeColor.LIGHT_BLUE), GREEN(ChatColor.GREEN,
				DyeColor.LIME), LIGHT_PURPLE(ChatColor.LIGHT_PURPLE,
				DyeColor.MAGENTA), ORANGE(ChatColor.GOLD, DyeColor.ORANGE), PINK(
				ChatColor.RED, DyeColor.PINK), LIGHT_GRAY(ChatColor.GRAY,
				DyeColor.SILVER), WHITE(ChatColor.WHITE, DyeColor.WHITE), YELLOW(
				ChatColor.YELLOW, DyeColor.YELLOW), RED(ChatColor.RED,
				DyeColor.RED);

		ChatColor cC;
		DyeColor dC;

		private ColorData(ChatColor cc, DyeColor dc) {
			this.cC = cc;
			this.dC = dc;
		}

		public ChatColor getChatColor() {
			return this.cC;
		}

		public DyeColor getDyeColor() {
			return this.dC;
		}
	}

	public class BlockColorData {

		boolean usesMaterial;
		boolean hasData = false;

		int typeID;
		Material type;

		ColorData c;

		short data = 0;

		public BlockColorData(int id, int data, ColorData cd) {
			this.typeID = id;
			this.usesMaterial = false;
			this.c = cd;
			this.data = (short) data;
			this.hasData = true;
			colorList.add(this);
		}

		@SuppressWarnings("deprecation")
		public BlockColorData(Material mat, int data, ColorData cd) {
			this.type = mat;
			this.typeID = mat.getId();
			this.usesMaterial = true;
			this.c = cd;
			this.data = (short) data;
			this.hasData = true;
			colorList.add(this);
		}

		public BlockColorData(int id, ColorData cd) {
			this.typeID = id;
			this.usesMaterial = false;
			this.c = cd;
			colorList.add(this);
		}

		@SuppressWarnings("deprecation")
		public BlockColorData(Material mat, ColorData cd) {
			this.type = mat;
			this.typeID = mat.getId();
			this.usesMaterial = true;
			this.c = cd;
			colorList.add(this);
		}

		public short getData() {
			return this.data;
		}

		public boolean usesMaterials() {
			return this.usesMaterial;
		}

		public int getTypeID() {
			return this.typeID;
		}

		public Material getType() {
			return this.type;
		}

		public ChatColor getChatColor() {
			return this.c.getChatColor();
		}

		public DyeColor getDyeColor() {
			return this.c.getDyeColor();
		}

		public boolean hasData() {
			return this.hasData;
		}
	}

}
