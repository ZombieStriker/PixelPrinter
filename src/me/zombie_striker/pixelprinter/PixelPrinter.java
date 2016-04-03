package me.zombie_striker.pixelprinter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import me.zombie_striker.pixelprinter.RGBBlockColor.MaterialData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelPrinter extends JavaPlugin {

	String prefix = ChatColor.DARK_PURPLE + "[PixelPrinter]" + ChatColor.WHITE;
	private File images = new File(getDataFolder() + File.separator + "images");

	private List<GifHolder> gifs = new ArrayList<>();

	@Override
	public void onEnable() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		if (!images.exists()) {
			images.mkdir();
			getLogger().info(
					"Drag all images to the PixelPrinter/Images/ directory");
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			boolean nearPlayers = false;

			public void run() {
				for (GifHolder gif : gifs) {
					nearPlayers = false;
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().distance(gif.minCorner) < 200) {
							nearPlayers = true;
							break;
						}
					}
					if (nearPlayers) {
						//gif.setFrame(gif.getCurrentFrame() + 1);
						gif.loadFrame(gif.getCurrentFrame());
					}
				}
			}
		}, 0, 3);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		if (command.getName().equalsIgnoreCase("PixelPrinter")||command.getName().equalsIgnoreCase("pp")) {
			if (args.length == 1) {
				List<String> ls = new ArrayList<String>();
				if ("create".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("create");
				if ("stopGifs".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("stopGifs");
				if ("specs".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("specs");
				if ("preview".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("preview");
				if ("download".toLowerCase().startsWith(args[0].toLowerCase()))
					ls.add("download");
				return ls;
			}
			if (args[0].equalsIgnoreCase("create")
					|| args[0].equalsIgnoreCase("preview")) {
				if (args.length == 2) {
					List<String> ls = new ArrayList<String>();
					if ("west".toLowerCase().startsWith(args[1].toLowerCase()))
						ls.add("west");
					if ("east".toLowerCase().startsWith(args[1].toLowerCase()))
						ls.add("east");
					if ("north".toLowerCase().startsWith(args[1].toLowerCase()))
						ls.add("north");
					if ("south".toLowerCase().startsWith(args[1].toLowerCase()))
						ls.add("south");
					return ls;
				}
				if (args.length == 3) {
					List<String> ls = new ArrayList<String>();
					if (!images.exists())
						images.mkdir();

					for (File f : images.listFiles()) {
						if (!f.isDirectory() && !f.getName().contains(".yml")) {
							if (f.getName().toLowerCase()
									.startsWith(args[2].toLowerCase()))
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
						if (f.getName().toLowerCase()
								.startsWith(args[2].toLowerCase()))
							ls.add(f.getName());
					}
				}
				return ls;
			} else if (args[0].equalsIgnoreCase("download")) {
				return null;
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("PixelPrinter")||command.getName().equalsIgnoreCase("pp")) {
			if (args.length == 0 || args.length == 1) {
				if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(prefix + " All the loaded images:");
					for (File f : images.listFiles()) {
						if (!f.isDirectory() && !f.getName().contains(".yml")) {
							sender.sendMessage("-" + f.getName());
						}
					}
					return true;
				}else if (args[0].equalsIgnoreCase("stopGifs")){
					gifs.clear();
					sender.sendMessage(prefix + " You have stopped all of the gifs on the server");
					return true;
				}
				helpMessage(sender);
				return true;
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("specs")) {
					File f = new File(images + File.separator + args[1]);
					if (!f.exists()) {
						sender.sendMessage(prefix + " This file does not exist");
						return true;
					}
					BufferedImage bi;
					try {
						bi = ImageIO.read(f);
						sender.sendMessage("Specs for file \"" + f.getName()
								+ "\"");
						sender.sendMessage("-Width: " + bi.getWidth());
						sender.sendMessage("-Height: " + bi.getHeight());
						sender.sendMessage("-Larger than world: "
								+ (bi.getHeight() - 250 > 0)
								+ " "
								+ ((bi.getHeight() - 250 > 0) ? " pixels over height:"
										+ (bi.getHeight() - 250)
										: ""));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					helpMessage(sender);
				}
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("download")) {
					try {
						if (args[1].contains(".gif")) {
							BufferedImage bi = ImageIO.read(new URL(args[1]));
							File outputfile = new File(images + File.separator
									+ args[2] + ".gif");
							ImageIO.write(bi, "gif", outputfile);
							sender.sendMessage(prefix
									+ " Completed downloading image. File \""
									+ outputfile.getName() + "\" created");
						} else {
							BufferedImage bi = ImageIO.read(new URL(args[1]));
							File outputfile = new File(images + File.separator
									+ args[2] + ".jpg");
							ImageIO.write(bi, "jpg", outputfile);
							sender.sendMessage(prefix
									+ " Completed downloading image. File \""
									+ outputfile.getName() + "\" created");
						}
					} catch (Exception e) {
						sender.sendMessage(prefix
								+ " The URL you added does not have a image on it.");
						return true;
					}
				} else {
					helpMessage(sender);

				}
			} else if (args.length == 4) {
				if (args[0].equalsIgnoreCase("create")) {
					File loadedImage = new File(images + File.separator
							+ args[2]);
					if (!loadedImage.exists()) {
						sender.sendMessage(prefix
								+ " That Image does not exist");
						return true;
					} else {
						if (sender instanceof Player) {
							final Player p = (Player) sender;
							final String dir = args[1];
							if (p.isOp()) {
								int height = 0;
								try {
									height = Integer.parseInt(args[3]);
								} catch (NumberFormatException e) {
									sender.sendMessage(prefix
											+ " You can only use numbers for the size. \""
											+ args[3] + "\" is not acceptable");
									return true;
								}
								
								if (loadedImage.getName().contains(".gif")) {
									try {
										final GifHolder gif = createFrames(
												loadedImage, height);
										BufferedImage bi = createResizedCopy(
												gif.frames[0], height, false);
										final Location loc = p.getLocation().clone();
										RGBBlockColor.Pixel[][] result = RGBBlockColor
												.convertTo2DWithoutUsingGetRGB(bi);
										int delay = new AsyncImageHolder(this,
												result, p, dir, bi).loadImage();
										if (gif.frames[0].getWidth()
												* gif.frames[0].getHeight() > (80 * 80)) {
											sender.sendMessage(prefix
													+ " Image must be less than "
													+ (80 * 80)
													+ " pixels. Your size:"
													+ (gif.frames[0].getWidth() * gif.frames[0]
															.getHeight()));
											return true;
										}
										Bukkit.getScheduler()
												.scheduleSyncDelayedTask(this,
														new Runnable() {
															public void run() {
																p.sendMessage(prefix+" Done!");
																gif.setEastOrWest(isMovingX(dir));
																gif.setNegDir(isMinNeg(dir));
																gif.setMinLocation(loc);
																gifs.add(gif);
																p.sendMessage(prefix
																		+ " Added a new Gif. The gif's ID is "
																		+ (gifs.size()));
															}
														}, delay + 5);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									if (height > 250) {
										sender.sendMessage(prefix
												+ " Image must be less than 250 pixels");
										return true;
									}
									try {
										BufferedImage bi2 = ImageIO
												.read(loadedImage);
										BufferedImage bi = createResizedCopy(
												bi2, height, false);
										RGBBlockColor.Pixel[][] result = RGBBlockColor
												.convertTo2DWithoutUsingGetRGB(bi);
										int delay = new AsyncImageHolder(this, result, p,
												dir, bi).loadImage();
										Bukkit.getScheduler()
										.scheduleSyncDelayedTask(this,
												new Runnable() {
													public void run() {
														for(Player p2 : p.getWorld().getPlayers())
															p2.sendMessage(prefix + " Done!");
													}
												}, delay + 5);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} else {
								p.sendMessage(prefix
										+ " You must be op in order to use this command.");
							}
						} else {
							sender.sendMessage(prefix
									+ "You must be a player to issue this command.");
						}

					}
				} else if (args[0].equalsIgnoreCase("preview")) {
					if (sender instanceof Player) {
						final Player p = (Player) sender;
						String direction = args[1];
						File f = new File(images + File.separator + args[2]);
						if (!f.exists()) {
							sender.sendMessage(prefix
									+ " This file does not exist");
							return true;
						}
						BufferedImage bi;
						int height = Integer.parseInt(args[3]);
						if (height > 250) {
							sender.sendMessage(prefix
									+ " Image must be less than 250 pixels");
							return true;
						}
						try {
							bi = ImageIO.read(f);
							try {
								bi = createResizedCopy(bi, height, false);
							} catch (NumberFormatException e) {
								sender.sendMessage(prefix
										+ " You can only use numbers for the size. \""
										+ args[3] + "\" is not acceptable");
								return true;
							}
							int blockstep = 0;
							int blockMaxStep = (bi.getWidth() / 8) + 3;
							final List<Location> updates = new ArrayList<Location>();
							boolean neg = isMinNeg(direction);
							boolean moving = isMovingX(direction);
							for (int y = bi.getHeight() - 1; y >= 0; y--) {
								if (neg) {
									for (int x = 0; x > -bi.getWidth(); x--) {
										if (x == 0 || y == 0
												|| blockstep == blockMaxStep) {
											blockstep = 0;
											Block b;
											if (moving)
												b = p.getLocation()
														.clone()
														.add(x,
																bi.getHeight()
																		- y - 1,
																0).getBlock();
											else
												b = p.getLocation()
														.clone()
														.add(0,
																bi.getHeight()
																		- y - 1,
																x).getBlock();
											p.sendBlockChange(b.getLocation(),
													Material.GOLD_BLOCK,
													(byte) 0);
											updates.add(b.getLocation());
										} else {
											blockstep++;
										}
									}
								} else {
									for (int x = 0; x < bi.getWidth(); x++) {
										if (x == 0 || y == 0
												|| blockstep == blockMaxStep) {
											blockstep = 0;
											Block b;
											if (moving)
												b = p.getLocation()
														.clone()
														.add(x,
																bi.getHeight()
																		- y - 1,
																0).getBlock();
											else
												b = p.getLocation()
														.clone()
														.add(0,
																bi.getHeight()
																		- y - 1,
																x).getBlock();
											p.sendBlockChange(b.getLocation(),
													Material.GOLD_BLOCK,
													(byte) 0);
											p.sendBlockChange(b.getLocation(),
													Material.GOLD_BLOCK,
													(byte) 0);
											updates.add(b.getLocation());
										} else {
											blockstep++;
										}
									}
								}
							}
							Bukkit.getScheduler().scheduleSyncDelayedTask(this,
									new Runnable() {
										public void run() {
											for (Location l : updates) {
												p.sendBlockChange(l, l
														.getBlock().getType(),
														l.getBlock().getData());
											}
											p.sendMessage(prefix
													+ " preview ended");
										}
									}, 20 * 8);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						sender.sendMessage(prefix
								+ " you must be a player to issue this command.");
					}
				} else {
					helpMessage(sender);
				}

			} else {
				helpMessage(sender);
			}
			return true;
		}
		return false;
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

	public void helpMessage(CommandSender sender) {
		sender.sendMessage(prefix + " Possible Commands");
		sender.sendMessage("-/PixelPrinter create <direction> <fileName> <height>");
		sender.sendMessage("-/PixelPrinter preview <direction> <fileName> <height>");
		sender.sendMessage("-/PixelPrinter stopGifs");
		sender.sendMessage("-/PixelPrinter specs <fileName>");
		sender.sendMessage("-/PixelPrinter download <URL> <save name>");
		sender.sendMessage("-/PixelPrinter list");
		sender.sendMessage("-/PixelPrinter ? or Help");
	}

	public GifHolder createFrames(File gif, int height) {
		try {
			ImageReader reader = (ImageReader) ImageIO
					.getImageReadersByFormatName("gif").next();
			reader.setInput(ImageIO.createImageInputStream(gif), true);
			Iterator<IIOImage> iter = reader.readAll(null);
			List<BufferedImage> bii = new ArrayList<>();
			int frameID = 0;
			while (iter.hasNext()) {
				frameID++;
				if (frameID == 1) {
					IIOImage img = iter.next();
					BufferedImage frame = (BufferedImage) img
							.getRenderedImage();
					frame = createResizedCopy(frame, height, false);
					bii.add(frame);// frame get? height
					frameID = 0;
				}
			}
			Object[] array1 = bii.toArray();
			BufferedImage[] array = new BufferedImage[array1.length];
			for (int i = 0; i < array1.length; i++) {
				if (array1[i] instanceof BufferedImage) {
					array[i] = (BufferedImage) array1[i];
				} else {
					System.out.println("ERROR: Image is not an image.");
				}
			}
			return new GifHolder(array, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage createResizedCopy(BufferedImage originalImage,
			int scaledHeight, boolean preserveAlpha) {
		// System.out.println("resizing...");
		// int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB
		// BufferedImage.TYPE_INT_ARGB;
		int imageType = BufferedImage.TYPE_INT_RGB;
		int WIDTH = (int) ((double) originalImage.getWidth() * ((double) scaledHeight / originalImage
				.getHeight()));
		int HEIGHT = scaledHeight;
		BufferedImage scaledBI = new BufferedImage(WIDTH, HEIGHT, imageType);
		// Image scaledBI = originalImage.getScaledInstance(, scaledHeight
		// ,Image.SCALE_DEFAULT);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, (int) scaledBI.getWidth(),
				scaledBI.getHeight(), null);
		g.dispose();

		return scaledBI;
	}
}
