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
package me.zombie_striker.pixelprinter;

import me.zombie_striker.easygui.EasyGUI;
import me.zombie_striker.pixelprinter.data.Direction;
import me.zombie_striker.pixelprinter.data.FileCreatorData;
import me.zombie_striker.pixelprinter.data.MaterialData;
import me.zombie_striker.pixelprinter.util.*;
import me.zombie_striker.pixelprinter.util.RGBBlockColor.Pixel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.*;

public class PixelPrinter extends JavaPlugin {

	public static boolean isAbove113 = false;
	private static PixelPrinter instance;
	private static int skin_creator_delay = 0;
	public Map<UUID, FileCreatorData> downloadFile = new HashMap<UUID, FileCreatorData>();

	public List<GifHolder> gifs = new ArrayList<GifHolder>();
	public int loadCount = 500;
	public Material[] supportedMaterials = null;
	File images = null;
	File resoucepackFolder = null;
	private String prefix = ChatColor.DARK_PURPLE + "[PixelPrinter]" + ChatColor.WHITE;
	private ArrayList<String> cAU = new ArrayList<>();

	public static PixelPrinter getInstance() {
		return instance;
	}

	public static void saveImage(URL url, File file) throws IOException {
		InputStream is = url.openStream();
		file.getParentFile().mkdirs();
		file.createNewFile();
		OutputStream os = new FileOutputStream(file);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
	}

	public String getPrefix() {
		return prefix;
	}

	@Override
	public void onEnable() {
		new BukkitRunnable() {

			@Override
			public void run() {
				File mapFolder = new File(getDataFolder(), "mapdata");
				if (!mapFolder.exists())
					mapFolder.mkdirs();
				SimilarMapUtil.registerAllMaps(mapFolder);
			}
		}.runTaskLater(this, 1);

		GifHolder.registerClass();

		// Download the API dependancy
		/*
		 * try { if (Bukkit.getPluginManager().getPlugin("PluginConstructorAPI") ==
		 * null) GithubDependDownloader.autoUpdate(this, new
		 * File(getDataFolder().getParentFile(), "PluginConstructorAPI.jar"),
		 * "ZombieStriker", "PluginConstructorAPI", "PluginConstructorAPI.jar"); // new
		 * DependencyDownloader(this, 276723); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		new BukkitRunnable() {
			@Override
			public void run() {
				if (PixelPrinter.skin_creator_delay > 0)
					PixelPrinter.skin_creator_delay = PixelPrinter.skin_creator_delay - 1;
			}
		}.runTaskTimer(this, 0, 10);

		instance = this;
		images = new File(getDataFolder(), "images");
		resoucepackFolder = new File(getDataFolder(), "custom_textures");

		Bukkit.getPluginManager().registerEvents(new PPListener(this), this);
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		if (!images.exists()) {
			images.mkdir();
			getLogger().info("Drag all images to the \"PixelPrinter/Images/\" directory");
		}
		if (!resoucepackFolder.exists()) {
			resoucepackFolder.mkdir();
			getLogger().info(
					"Drag all custom block textures from your resourcepack (*if you are using one) to the \"PixelPrinter/custom_textures/\" directory");
		}

		// This will attempt to load the resourcepack.
			RGBBlockColor.activateBlocks();
			RGBBlockColor.loadResourcepackTextures(resoucepackFolder);

		initHelp();
		if (getConfig() == null)
			saveConfig();
		if (getConfig().contains("activegifs"))
			gifs = (List<GifHolder>) getConfig().get("activegifs");
		if (getConfig().contains("loadCount")) {
			loadCount = getConfig().getInt("loadCount");
		} else {
			getConfig().set("loadCount", 500);
			saveConfig();
		}
		if (!getConfig().contains("auto-update")) {
			getConfig().set("auto-update", true);
			saveConfig();
		}

		if (!getConfig().contains("whitelistedMaterialsEnabled") || !getConfig().contains("whitelistedMaterials")) {
			supportedMaterials = null;
			getConfig().set("whitelistedMaterialsEnabled", false);
			getConfig().set("whitelistedMaterials", Arrays.asList(Material.STONE.name(), Material.NETHERRACK.name()));
			List<String> fullanmes = new ArrayList<>();
			for (Material m : Material.values()) {
				if (m.isBlock())
					fullanmes.add(m.name());
			}
			getConfig().set("FULL_MATERIAL_LIST", fullanmes);
			saveConfig();
		} else if (getConfig().getBoolean("whitelistedMaterialsEnabled")) {
			List<String> k = getConfig().getStringList("whitelistedMaterials");
			supportedMaterials = new Material[k.size()];
			int i = 0;
			for (String kk : k) {
				try {
					supportedMaterials[i] = Material.matchMaterial(kk);
					i++;
				} catch (Error | Exception e) {
				}
			}
		} else {
			supportedMaterials = null;
		}
		try {
			isAbove113 = ReflectionUtil.isVersionHigherThan(1, 13);
		} catch (Error | Exception e45) {

		}

		// bStats metrics
		Metrics met = new Metrics(this);
		met.addCustomChart(new Metrics.SimplePie("images-loaded", new java.util.concurrent.Callable<String>() {
			@Override
			public String call() throws Exception {
				return String.valueOf(images.listFiles().length);
			}
		}));
		met.addCustomChart(new Metrics.SimplePie("load-count", new java.util.concurrent.Callable<String>() {
			@Override
			public String call() throws Exception {
				return String.valueOf(loadCount);
			}
		}));
		met.addCustomChart(new Metrics.SimplePie("updater-active", new java.util.concurrent.Callable<String>() {
			@Override
			public String call() throws Exception {
				return String.valueOf(getConfig().getBoolean("auto-update"));
			}
		}));

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			boolean nearPlayers = false;

			public void run() {
				for (GifHolder gif : gifs) {
					try {
						nearPlayers = false;
						if (gif == null) {
							System.out.println("gif is null");
							continue;
						}
						if (gif.isLoaded()) {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (p.getLocation().distance(gif.getMinCorner()) < 300) {
									nearPlayers = true;
									break;
								}
							}
							if (nearPlayers) {
								gif.loadFrame();
								// gif.loadFrame();
							}
						}
					} catch (Exception e45) {
						e45.printStackTrace();
					}
				}
			}
			// TODO: Doubling loadframe and the delay so it is less taxing on
			// low end computers
		}, 10, 4);

		/*
		 * final Updater updater = new Updater(instance, 98985,
		 * getConfig().getBoolean("auto-update"));
		 */
		/*
		 * new BukkitRunnable() { public void run() { // TODO: Works well. Make changes
		 * for the updaters of // PixelPrinter and Music later. if
		 * (updater.updaterActive) updater.download(false); }
		 * }.runTaskTimerAsynchronously(this, 20 /* * 60 * /, 20 * 60 * 5);
		 */
		GithubUpdater.autoUpdate(this, "ZombieStriker", "PixelPrinter", "PixelPrinter.jar");
	}

	public void onDisable() {
		if (gifs.size() > 0) {
			getConfig().set("activegifs", gifs);
			saveConfig();
		}
		PixelPrinter.instance = null;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		if (command.getName().equalsIgnoreCase("PixelPrinter")) {
			if (args.length == 1) {
				List<String> ls = new ArrayList<String>();
				if ("HELP".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("HELP");
				if ("?".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("?");
				if ("undo".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("undo");
				if ("create".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("create");
				if ("createskin".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("createSkin");
				if ("stopGif".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("stopGif");
				if ("debug".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("debug");
				if ("debug2".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("debug2");
				if ("setLoadCount".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("SetLoadCount");
				if ("stopallGifs".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("stopallGifs");
				if ("listGifs".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("listGifs");
				if ("specs".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("specs");
				if ("d".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("d");
				if ("cf".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("cf");
				if ("createFrame".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("createFrame");
				if ("delete".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("delete");
				if ("di".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("di");
				if ("download".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("download");
				if ("downloadimage".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("downloadimage");
				return ls;
			}
			if (args[0].equalsIgnoreCase("delete")) {
				List<String> ls = new ArrayList<String>();
				if (!images.exists())
					images.mkdir();

				for (File f : images.listFiles()) {
					if (!f.isDirectory() && !f.getName().contains(".yml")) {
						if (f.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							ls.add(f.getName());
					}
				}
				return ls;
			} else if (args[0].equalsIgnoreCase("undo")) {
				List<String> ls = new ArrayList<String>();
				for (String dir : UndoUtil.getSnapshots())
					if (dir!=null && dir.toLowerCase().startsWith(args[1].toLowerCase()))
						ls.add(dir);
				return ls;
			} else if (args[0].equalsIgnoreCase("createSkin")) {
				if (args.length == 2) {
					List<String> ls = new ArrayList<String>();
					for (Direction dir : Direction.values())
						if (dir.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							ls.add(dir.getName());

					return ls;
				}
			} else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("createFrame")
					|| args[0].equalsIgnoreCase("cf")) {
				if (args.length == 2) {
					List<String> ls = new ArrayList<String>();
					for (Direction dir : Direction.values())
						if (dir.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							ls.add(dir.getName());

					return ls;
				}
				if (args.length == 3) {
					List<String> ls = new ArrayList<String>();
					if (!images.exists())
						images.mkdir();

					for (File f : images.listFiles()) {
						if (!f.isDirectory() && !f.getName().contains(".yml")) {
							if (f.getName().toLowerCase().startsWith(args[2].toLowerCase()))
								ls.add(f.getName());
						}
					}
					return ls;
				}
			} else if (args[0].equalsIgnoreCase("specs")) {
				List<String> ls = new ArrayList<String>();
				if (!images.exists())
					images.mkdir();

				for (File f : images.listFiles()) {
					if (!f.isDirectory() && !f.getName().contains(".yml")) {
						if (f.getName().toLowerCase().startsWith(args[1].toLowerCase()))
							ls.add(f.getName());
					}
				}
				return ls;
			} else if (args[0].equalsIgnoreCase("download")) {
				return null;
			} else if (args[0].equalsIgnoreCase("downloadimage")) {
				return null;
			}
		}

		return null;
	}

	// @Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("PixelPrinter")) {
			if (args.length < 1 || args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")) {
				int page = 0;
				if (args.length > 1) {
					try {
						page = Integer.parseInt(args[1]) - 1;
						if (page < 0)
							page = 0;
					} catch (Exception e) {
					}
				}
				final int msgPerPage = 12;
				sender.sendMessage(
						getPrefix() + " ===== Page: " + (page + 1) + "/ " + ((cAU.size() / msgPerPage) + 1) + " =====");
				for (int i = page * msgPerPage; i < (page * msgPerPage) + msgPerPage; i++) {
					if (i >= cAU.size())
						break;
					sender.sendMessage((i % 2 == 0 ? ChatColor.WHITE : ChatColor.GRAY) + cAU.get(i));
				}
				return true;
			}

			// Commands that do not require Player objects

			// ======================================================================DEBUG
			else if (args[0].equalsIgnoreCase("debug")) {
				List<Material> loaded = new ArrayList<Material>();
				for (MaterialData k : RGBBlockColor.materialValue.keySet()) {
					if (!loaded.contains(k.getMaterial()))
						loaded.add(k.getMaterial());
				}
				// int color = 0;
				List<ItemStack> mat = new ArrayList<ItemStack>();
				for (Material m : Material.values()) {
					if (m.isBlock() && !loaded.contains(m) && !m.name().endsWith("SHULKER_BOX")
							&& !m.name().endsWith("CORAL_FAN") && !m.name().endsWith("SAPLING")
							&& !m.name().endsWith("_BANNER") && !m.name().endsWith("_HEAD")
							&& !m.name().endsWith("CARPET") && !m.name().endsWith("STAINED_GLASS")
							&& !m.name().endsWith("_TRAPDOOR") && !m.name().endsWith("RAIL")
							&& !m.name().startsWith("INFESTED") && !m.name().endsWith("_BED")
							&& !m.name().endsWith("_SLAB") && !m.name().endsWith("_STAIRS")
							&& !m.name().endsWith("GLASS_PANE") && !m.name().endsWith("FENCE")  && !m.name().endsWith("_WALL")
							&& !m.name().endsWith("FENCE_GATE") && !m.name().endsWith("_BUTTON")
							&& !m.name().endsWith("PRESSURE_PLATE") && !m.name().endsWith("_DOOR")) {
						if(m != Material.SPAWNER && m != Material.BREWING_STAND && m != Material.DRAGON_EGG) {
							mat.add(new ItemStack(m));
							Bukkit.broadcastMessage(m.name());
						}
					}
				}
				int size = ((mat.size() / 9) + 1) * 9;
				if (size > 9 * 14)
					size = 9 * 14;
				//Inventory test = Bukkit.createInventory(null, size);
				//for (Material a : mat)
				//	test.addItem(new ItemStack(a));
				EasyGUI gui = EasyGUI.generateGUIIfNoneExist(mat.toArray(new ItemStack[mat.size()]),"LeftBehindMaterials",false);
				((Player) sender).openInventory(gui.getPageByID(0));

			}else if (args[0].equalsIgnoreCase("debug2")) {
				RGBBlockColor.loadResourcepackTextures(resoucepackFolder,true);
				sender.sendMessage(prefix+" Output text generated.");

			} else if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
				sender.sendMessage(getPrefix() + " All the saved images:");
				for (File f : images.listFiles())
					if (!f.isDirectory() && !f.getName().contains(".yml"))
						sender.sendMessage("-" + f.getName());
			} else if (args[0].equalsIgnoreCase("stopAllGifs")) {
				if (sender.hasPermission("pixelprinter.stop")) {
					gifs.clear();
					GifHolder.freeID.clear();
					sender.sendMessage(getPrefix() + " You have stopped all of the gifs on the server");
				} else {
					sender.sendMessage(getPrefix() + " You do not have permission to perform this command");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("listGifs")) {
				sender.sendMessage(getPrefix() + " All gifs playing right now");
				for (GifHolder gif : gifs)
					sender.sendMessage("-" + gif.getID() + ": " + gif.getFileName() + " AT "
							+ gif.getMinCorner().getBlockX() + ", " + gif.getMinCorner().getBlockY() + ", "
							+ gif.getMinCorner().getBlockZ() + ", ");

			} else if (args[0].equalsIgnoreCase("specs")) {
				if (args.length < 2) {
					sender.sendMessage(prefix + " You must specify an image");
					return true;
				}
				File f = new File(images + File.separator + args[1]);
				if (!f.exists()) {
					sender.sendMessage(getPrefix() + " This file does not exist");
					return true;
				}
				BufferedImage bi = null;
				int wid = -1;
				int heigh = -1;
				if (f.getName().endsWith("txt")) {
					try {
						BufferedReader br = new BufferedReader(new FileReader(f));
						String urlString = br.readLine();
						br.close();
						if (urlString.contains(".gif")) {
							// TODO: Add fix for gifs.
						} else {
							bi = ImageIO.read(new URL(urlString));
							wid = bi.getWidth();
							heigh = bi.getHeight();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						bi = ImageIO.read(f);
						wid = bi.getWidth();
						heigh = bi.getHeight();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				sender.sendMessage("Specs for file \"" + f.getName() + "\"");
				sender.sendMessage("-Width: " + wid);
				sender.sendMessage("-Height:  " + heigh);
				BufferedImage bi2 = RGBBlockColor.resize(bi, (int) (wid * (((double) 20 * 2) / heigh)), 20 * 2);// createResizedCopy(bi,
				// 20
				// *
				// 2,
				// true);
				// //
				// ==================================================
				final Pixel[][] result = RGBBlockColor.convertTo2DWithoutUsingGetRGB(bi2);

				int wMin = 0;
				int wMax = bi2.getWidth();
				if (wMax > 88) {
					wMin = (bi2.getWidth() / 2) - 44;
					wMax = wMin + 88;
				}
				for (int height = 0; height < (bi2.getHeight() - 1); height += 2) {

					StringBuilder sb = new StringBuilder();

					for (int width = wMin; width < wMax; width += 2) {
						Color[] color = new Color[4];
						int y = (height + 1 < result.length) ? height + 1 : height;
						int x = (width + 1 < result[y].length) ? width + 1 : width;
						color[0] = new Color(result[y][x - 1].r, result[y][x - 1].g, result[y][x - 1].b);
						color[1] = new Color(result[y][x].r, result[y][x].g, result[y][x].b);
						color[2] = new Color(result[y - 1][x - 1].r, result[y - 1][x - 1].g, result[y - 1][x - 1].b);
						color[3] = new Color(result[y - 1][x].r, result[y - 1][x].g, result[y - 1][x].b);
						String c = RGBChatColor.getClosestBlockValue(color);
						sb.append(c);
					}
					sender.sendMessage(sb.toString());
				}
				Color[] color = new Color[4];
				for (int i = 0; i < 4; i++) {
					color[i] = new Color(result[0][0].r, result[0][0].g, result[0][0].b);
				}
				System.out.println("Start generic");
				// TODO: String c = RGBChatColor.getClosestBlockValue(color);

			} else if (args[0].equalsIgnoreCase("setloadcount")) {
				int a = 0;
				try {
					a = Integer.parseInt(args[1]);
				} catch (Exception e) {
					sender.sendMessage(prefix + "Please provide a valid number");
					sender.sendMessage(prefix
							+ "# Use this to change the amount of blocks should load when an image is being created.");
					sender.sendMessage(prefix
							+ "# Default amount of blocks is 500. If your run MC on the same comp as your server");
					sender.sendMessage(prefix + "# or if you are on a old computer, set the amount to less than 200.");
					return true;
				}
				this.loadCount = a;
				getConfig().set("loadCount", a);
				saveConfig();
				sender.sendMessage(prefix + " Load count updated to " + a);
			} else if (args[0].equalsIgnoreCase("delete")) {
				if (sender.hasPermission("pixelprinter.delete")) {
					if (args.length < 2) {
						sender.sendMessage(prefix + " You must specify an image");
						return true;
					}
					File outputfile = new File(images + File.separator + args[1]);
					if (!outputfile.exists()) {
						sender.sendMessage(getPrefix() + " The file with the specified name does not exist.");
						return true;
					}
					outputfile.delete();
					sender.sendMessage(getPrefix() + " The file \"" + args[1] + "\" has been deleted");
				}
			} else if (args[0].equalsIgnoreCase("stopGif")) {
				if (sender.hasPermission("pixelprinter.stop")) {
					if (args.length < 2) {
						sender.sendMessage(prefix + " You must specify a gif.");
						return true;
					}
					int id = Integer.parseInt(args[1]);
					for (int i = 0; i < gifs.size(); i++) {
						if (gifs.get(i).getID() == id) {
							gifs.remove(i);
							sender.sendMessage(getPrefix() + " You have stopped the gif " + id + ".");
							Iterator<Integer> array = GifHolder.freeID.iterator();
							int index = 0;
							while (array.hasNext()) {
								if (array.next() == id) {
									GifHolder.freeID.remove(index);
									break;
								}
								index++;
							}
							return true;
						}
					}
					sender.sendMessage(getPrefix() + " The gif with that id does not exist.");
					return true;
				} else {
					sender.sendMessage(getPrefix() + " You do not have permission to perform this command");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("download") || args[0].equalsIgnoreCase("d")
					|| args[0].equalsIgnoreCase("downloadimage") || args[0].equalsIgnoreCase("di")) {
				if (args.length < 2) {
					sender.sendMessage(prefix + " You must provide the file name");
					return true;
				}
				if (!sender.hasPermission("pixelprinter.download")) {
					sender.sendMessage(prefix + " You do not have permission to use this command.");
					return true;
				}
				FileCreatorData fcd = new FileCreatorData(
						((args[0].equalsIgnoreCase("download") || args[0].equalsIgnoreCase("d")) ? ".txt" : "fFind"),
						args[1]);
				if (args.length == 2)
					sender.sendMessage(prefix + " Now paste the link into the chat.");
				if (args.length > 2) {
					File outputfile = new File(
							images + File.separator + fcd.getName() + (fcd.getType().equalsIgnoreCase(".txt") ? ".txt"
									: (args[2].endsWith("gif") ? ".gif" : ".png")));
					if (outputfile.exists()) {
						sender.sendMessage(getPrefix()
								+ " A file already exists with this name. Either choose a new name or contact the server admin to delete this image.");
						return true;
					}
					if (fcd.getType().equalsIgnoreCase(".txt")) {
						try {
							BufferedWriter br = new BufferedWriter(new FileWriter(outputfile));
							br.write(args[2]);
							br.flush();
							br.close();
							sender.sendMessage(getPrefix() + " Completed downloading image path. File \""
									+ outputfile.getName() + "\" created.");
						} catch (IOException e2) {
							sender.sendMessage(getPrefix()
									+ " Something failed when downloading the image. Check console for details.");
							e2.printStackTrace();
						}
					} else {
						try {
							if (args[2].toLowerCase().endsWith(".gif")) {
								//byte[] bytes = IOUtils.toByteArray(new URL(args[2]).openStream());
								//FileUtils.writeByteArrayToFile(outputfile, bytes);
								saveImage(new URL(args[2]), outputfile);
								sender.sendMessage(getPrefix() + " Completed downloading gif. File \""
										+ outputfile.getName() + "\" created.");
							} else {
								BufferedImage bi = ImageIO.read(new URL(args[2]));
								ImageIO.write(bi, "png", outputfile);
								sender.sendMessage(getPrefix() + " Completed downloading image. File \""
										+ outputfile.getName() + "\" created.");
								bi.flush();
							}
						} catch (Exception er) {
							sender.sendMessage(getPrefix()
									+ " Something failed when downloading the image. Check console for details.");
							er.printStackTrace();

						}
					}
					// downloadFile.remove(player.getUniqueId());
				}
				return true;
			} else

				// Commands that require Player
				if (sender instanceof Player) {
					Player player = (Player) sender;

					if (args[0].equalsIgnoreCase("createFrame") || args[0].equalsIgnoreCase("cf")) {
						try {
							if (args.length < 4) {
								sender.sendMessage(prefix + " You must specify the image you want to render");
								return true;
							}
							Direction dir = Direction.getDir(args[1]);
							File f = new File(images, args[2]);
							if (!f.exists()) {
								sender.sendMessage("You did something wrong");
								return true;
							}
							int height = 0;
							try {
								height = Integer.parseInt(args[3]);
							} catch (Exception e) {
								sender.sendMessage(prefix + " you must provide a valid height");
								return true;
							}
							if (f.getName().contains(".gif")) {

								createMapAnim(dir, (Player) sender, GifHolder.getFrames(f, height * 128), height);
							} else if (f.getName().contains(".txt")) {
								try {
									BufferedReader br = new BufferedReader(new FileReader(f));
									String urlString = br.readLine();
									br.close();
									if (urlString.contains(".gif")) {
										createMapAnim(dir, ((Player) sender),
												GifHolder.getFrames(new URL(urlString), height), height);
									} else {
										BufferedImage bi2 = ImageIO.read(new URL(urlString));
										createMap(dir, ((Player) sender), bi2, height);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								try {
									BufferedImage bi2 = ImageIO.read(f);
									createMap(dir, ((Player) sender), bi2, height);
								} catch (Exception e) {
									sender.sendMessage(
											getPrefix() + " Something failed. Please check console for more details.");
									e.printStackTrace();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (args[0].equalsIgnoreCase("createskin")) {
						if (args.length < 2) {
							sender.sendMessage(prefix + " You must provide all the required arguments.");
						}

						if (player.hasPermission("pixelprinter.create")) {
							String uuid = player.getUniqueId().toString();
							if (Bukkit.getOnlineMode()) {
								if (args.length > 2) {
									try {
										UUID temp = UUID.fromString(args[2]);
										if (temp.equals(null)) {
											// Purposfully trying to throw NPE to
											// make sure the UUID is valid.
											uuid = temp.toString();
											System.out.println("1  - " + uuid);
										}
									} catch (Exception e) {
										if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
											uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
											System.out.println("2  - " + uuid);
										} else {
											if ((uuid = MojangAPI.getUUIDFromName(args[2])) == null) {
												uuid = args[2];
												System.out.println("3  - " + uuid);
											}
										}
									}
								}
							} else {
								if (args.length > 2) {
									if ((uuid = MojangAPI.getUUIDFromName(args[2])) == null) {
										uuid = args[2];
									}
								}
							}
							try {
								SkinCreator.createStatue(SkinCreator.getSkin(uuid), player.getLocation(),
										Direction.getDir(args[1]));
								skin_creator_delay = 2 * 30;
							} catch (NullPointerException e) {
								player.sendMessage(prefix + " The UUID/Name you entered is not valid.");
								skin_creator_delay = 2 * 30;
							} catch (IOException e) {
								player.sendMessage(prefix + " Please wait " + (skin_creator_delay / 2)
										+ " seconds before issuing the command again.");
							}
						}
					} else if (args[0].equalsIgnoreCase("undo")) {
						if (args.length < 2) {
							sender.sendMessage(prefix + " You must provide all the required arguments.");
							return true;
						}
						if (!UndoUtil.snapshotExists(args[1])) {
							sender.sendMessage(prefix + " There is no snapshot registered with that name,");
							return true;
						}

						if (player.hasPermission("pixelprinter.create")) {
							UndoUtil.undo(args[1]);
							sender.sendMessage(prefix
									+ " Image has been undone. If you made a mistake, or wish to have that image again, resend the command to have the image reappear.");
						} else {
							sender.sendMessage(prefix + " You do not have permission to undo images.");
						}
					} else if (args[0].equalsIgnoreCase("create")) {
						if (args.length < 4) {
							sender.sendMessage(prefix + " You must provide all the required arguments.");
							return true;
						}
						File loadedImage = new File(images + File.separator + args[2]);
						if (!loadedImage.exists()) {
							sender.sendMessage(getPrefix() + " That Image does not exist");
							return true;
						} else {
							final String dir = args[1];
							if (player.hasPermission("pixelprinter.create")) {
								int height = 0;
								try {
									height = Integer.parseInt(args[3]);
								} catch (NumberFormatException e) {
									sender.sendMessage(getPrefix() + " You can only use numbers for the size. \"" + args[3]
											+ "\" is not acceptable");
									return true;
								}

								boolean enableTrans = false;
								try {
									enableTrans = Boolean.parseBoolean(args[4]);
								} catch (Exception e) {
									enableTrans = false;
								}

								final Location loc = new Location(player.getWorld(), player.getLocation().getBlockX(),
										player.getLocation().getBlockY(), player.getLocation().getBlockZ());
								if (loadedImage.getName().contains(".gif")) {
									createGif(args, loc, height, dir, player);
								} else if (loadedImage.getName().contains(".txt")) {
									try {
										BufferedReader br = new BufferedReader(new FileReader(loadedImage));
										String urlString = br.readLine();
										br.close();
										if (urlString.contains(".gif")) {
											createGif(args, loc, height, dir, player);
										} else {
											BufferedImage bi2;
											try {
												bi2 = ImageIO.read(new URL(urlString));
												if(bi2==null){
													sender.sendMessage(prefix+" The image at "+urlString+" is cannot be read for some reason.");
													return false;
												}
												createImage(player, bi2, dir, height, enableTrans, loadedImage.getName());
											} catch (IOException e) {
												sender.sendMessage(prefix
														+ " Error: The image cannot be found. Make sure that the link is valid and that the image still exists.");
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									try {
										BufferedImage bi2 = ImageIO.read(loadedImage);
										createImage(player, bi2, dir, height, enableTrans, loadedImage.getName());
									} catch (Exception e) {
										sender.sendMessage(
												getPrefix() + " Something failed. Please check console for more details.");
									}
								}
							}
						}
					} else {
						sender.sendMessage(getPrefix() + " The subbcomands " + args[0]
								+ " does not exist. To see all commands or command useages, please type /pp help");
						return true;
					}
				} else {
					{
						sender.sendMessage(getPrefix() + " The subbcomands " + args[0]
								+ " does not exist. To see all commands or command useages, please type /pp help");
						return true;
					}
				}

		}
		return false;

	}

	public void createMap(Direction dir, Player p, BufferedImage bi2, int height) {
		BufferedImage bi = RGBBlockColor.resize(bi2,
				(int) (bi2.getWidth() * (((double) height) * 128 / bi2.getHeight())), height * 128);
		// createResizedCopy(bi2, height * 128, true);
		BufferedImage[] images = new BufferedImage[1];
		images[0] = bi;
		createMapAnim(dir, p, images, height);
	}

	public void createMapAnim(Direction dir, Player p, BufferedImage[] bi2, int height) {
		ItemStack[][] im = MapWallUtil.getMaps(bi2);
		for (int x = 0; x < im.length; x++) {
			for (int y = 0; y < im[x].length; y++) {
				MapWallUtil.setBlockAt(dir, p, y, x, im[x][(im[x].length - 1) - y]);
			}
		}
	}

	public void createImage(Player p, BufferedImage bi, String dir, int height, boolean enableTrans, String name) {
		bi = RGBBlockColor.resize(bi, (int) (bi.getWidth() * (((double) height) * 2 / bi.getHeight())), height * 2);
		Pixel[][] result = RGBBlockColor.convertTo2DWithoutUsingGetRGB(bi);
		final Location loc1 = p.getLocation().clone();
		if (Direction.getDir(dir) == null) {
			p.sendMessage(prefix + " You must provide a valid direction.");
			return;
		}
		new AsyncImageHolder(name, result, p, loc1, Direction.getDir(dir), bi, enableTrans).loadImage(true);
	}

	public void createGif(String[] args, final Location loc, int height, final String dir, final Player p) {
		try {
			final GifHolder gif = new GifHolder(args[2], loc, height, dir, p.getUniqueId());
			if (Direction.getDir(dir) == null) {
				p.sendMessage(prefix + " You must provide a valid direction.");
				return;
			}
			new BukkitRunnable() {
				public void run() {
					if (gif.getFrames() == null || gif.getFrames().length < 1)
						return;
					new BukkitRunnable() {
						public void run() {
							gif.init();
							gifs.add(gif);
							p.sendMessage(getPrefix() + " Added a new Gif. The gif's ID is " + gif.getID() + " with "
									+ gif.getSize() + " frames.");
						}
					}.runTaskLater(PixelPrinter.getInstance(), 5);
					cancel();
				}
			}.runTaskTimer(this, 0, 5);
		} catch (Exception e) {
			p.sendMessage(getPrefix() + " Something failed. Please check console for more details.");
			e.printStackTrace();
		}
	}

	private void initHelp() {
		cAU.add("/pp help <page>");
		cAU.add("-Lists all commands and usages");
		cAU.add("/pp <d or download> <File Name> <Image location>");
		cAU.add("-This downloads the URL to the txt file <file name>");
		cAU.add("/pp <di or downloadImage> <File Name> <Image location>");
		cAU.add("-The same as /pp d, but downloads the image file to the file <file name>");
		cAU.add("/pp specs <file name>");
		cAU.add("-Lists the specs for image <file name>");
		cAU.add("/pp create <direction> <file name> <height> <Optional:EnableTrans>");
		cAU.add("-Creates the image <file name> out of blocks, moving towards the <direction>, with a height of <hieght> blocks. EnableTrans will not render black pixels if true.");
		cAU.add("/pp <cf or createFrame> <direction> <file name> <height>");
		cAU.add("-Same as /pp create, but creates the image out of maps and frames");
		cAU.add("/pp createskin <direction> <Playername or UUID>");
		cAU.add("-Same as /pp create, but creates a giant skin of the player");
		cAU.add("/pp stopGif <ID>");
		cAU.add("-Stops the gif with id <ID>");
		cAU.add("/pp stopAllGifs");
		cAU.add("-Stops all the gifs on the server");
		cAU.add("/pp listGifs");
		cAU.add("-Lists all the gifs active on the server");
		cAU.add("/pp delete <file name>");
		cAU.add("- Deletes the file at <file name>");
		cAU.add("/pp setLoadCount <blocks>");
		cAU.add("-Sets the amount of blocks that will be placed per tick. Set <blocks> to a small number (like 10) if you run on an old computer.");

	}

	public File getImageFile() {
		return this.images;
	}
	/*
	 * public BufferedImage createResizedCopy(BufferedImage originalImage, int
	 * scaledHeight, boolean preserveAlpha) { int imageType =
	 * BufferedImage.TYPE_INT_RGB; int WIDTH = (int) ((double)
	 * originalImage.getWidth() * ((double) scaledHeight / originalImage
	 * .getHeight())); int HEIGHT = scaledHeight; BufferedImage scaledBI = new
	 * BufferedImage(WIDTH, HEIGHT, imageType); Graphics2D g =
	 * scaledBI.createGraphics(); if (preserveAlpha) {
	 * g.setComposite(AlphaComposite.Src); } g.drawImage(originalImage, 0, 0, (int)
	 * scaledBI.getWidth(), scaledBI.getHeight(), null); g.dispose();
	 *
	 * return scaledBI; }
	 */
}
