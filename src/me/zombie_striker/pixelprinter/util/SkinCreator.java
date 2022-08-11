package me.zombie_striker.pixelprinter.util;

import me.zombie_striker.pixelprinter.data.Direction;
import me.zombie_striker.pixelprinter.util.RGBBlockColor.Pixel;
import org.bukkit.Location;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Base64;

public class SkinCreator {

	public static BufferedImage[] getSkin(String uuid)
			throws NullPointerException, IOException {
		int linecode = 21;
		try {
			StringBuilder code = new StringBuilder();
			InputStreamReader is = new InputStreamReader(new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/"
							+ uuid.replace("-", "")).openStream());
			int charI = 0;
			while ((charI = is.read()) != -1) {
				code.append(((char) charI));
			}
			linecode = 31;
			String[] aaaa = code.toString().split("\"value\" : \"");
			if (aaaa.length == 1) {
				System.out.println("The user does not exist- AAAA does not contain value. Response:");
				System.out.println(code);
				throw new NullPointerException();
			}
			String decode;
			try {
				linecode = 39;
				Method m = Class.forName("javax.xml.bind.DatatypeConverter").getMethod("parseBase64Binary", String.class);
				/*
					javax.xml.bind.DatatypeConverter.parseBase64Binary(*/
				decode = (String) m.invoke(null, aaaa[1]
						.split("\"}],\"legacy\"")[0].split("\"}}}")[0].split("\"")[0]);
			} catch (Error | Exception e4) {
				linecode = 45;
				decode = new String(Base64.getDecoder().decode(aaaa[1]
						.split("\"}],\"legacy\"")[0].split("\"}}}")[0].split("\"")[0]));
			}
			linecode = 49;
			System.out.println(decode);
			String url = decode.split("url\" : \"")[1].split("\"}")[0].split("\",")[0];
			linecode = 52;
			System.out.println(url);
			BufferedImage[] images = new BufferedImage[2];
			images[0] = ImageIO.read(new URL(url));
			linecode = 56;
			if (decode.contains("CAPE")) {
				String urlcape = decode.split("url\" : \"")[2].split("\"}")[0];
				linecode = 59;
				images[1] = ImageIO.read(new URL(urlcape));
			}
			return images;
		} catch (NullPointerException e) {
			System.out
					.println("The Mojang servers denied the request. Wait a minute or so until you are allowed to get the texture again." + linecode);
			throw new NullPointerException();
		} catch (IOException e2) {
			System.out.println("The user does not exist- ErrorLineCode=" + linecode + ".");
			throw new IOException();
		}
	}

	@SuppressWarnings("deprecation")
	public static void createStatue(BufferedImage[] images, Location center,
									Direction dir) {
		BufferedImage skin = images[0];
		BufferedImage cape = images[1];

		Direction back = null;
		Direction left = null;
		Direction right = null;
		Direction flat = null;

		switch (dir) {
			case UP_EAST -> {
				back = Direction.UP_WEST;
				right = Direction.UP_NORTH;
				left = Direction.UP_SOUTH;
				flat = Direction.FLAT_SOUTHEAST;
			}
			case UP_WEST -> {
				back = Direction.UP_EAST;
				right = Direction.UP_SOUTH;
				left = Direction.UP_NORTH;
				flat = Direction.FLAT_NORTHWEST;
			}
			case UP_NORTH -> {
				back = Direction.UP_SOUTH;
				right = Direction.UP_EAST;
				left = Direction.UP_WEST;
				flat = Direction.FLAT_SOUTHEAST;
			}
			case UP_SOUTH -> {
				back = Direction.UP_NORTH;
				right = Direction.UP_WEST;
				left = Direction.UP_EAST;
				flat = Direction.FLAT_NORTHWEST;
			}
			default -> {
			}
		}
		/**
		 *
		 * This code would be right, If the direction the block is facing is the
		 * same as the direction of the wall.
		 * <p>
		 * switch (front) {
		 * <p>
		 * case UP_SOUTH: back = Direction.UP_NORTH; right = Direction.UP_WEST;
		 * left = Direction.UP_EAST; flat = Direction.FLAT_SOUTHWEST; break;
		 * case UP_NORTH: back = Direction.UP_SOUTH; right = Direction.UP_EAST;
		 * left = Direction.UP_WEST; flat = Direction.FLAT_NORTHEAST;
		 * <p>
		 * break; case UP_EAST: back = Direction.UP_WEST; right =
		 * Direction.UP_SOUTH; left = Direction.UP_NORTH; flat =
		 * Direction.FLAT_SOUTHEAST; break; case UP_WEST: back =
		 * Direction.UP_EAST; right = Direction.UP_NORTH; left =
		 * Direction.UP_SOUTH; flat = Direction.FLAT_NORTHWEST; break; default:
		 * break; }
		 */
		final Location loc = center.clone();
		// legs (left)
		{
			a(2,
					0,
					-3,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(0, 32 - 12, 4, 12), 24, false));
			a(-1,
					0,
					0,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(8, 32 - 12, 4, 12), 24, false));
			a(2,
					0,
					-3,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(4, 32 - 12, 4, 12), 24, false));
			a(-1, 0, 0, loc, back, dir, RGBBlockColor.createResizedCopy(
					skin.getSubimage(12, 32 - 12, 4, 12), 24, false));
		}

		// Legs (right)
		{
			int x = 16;
			int y = 64;
			if (skin.getHeight() == 32) {
				x = 0;
				y = 32;
			}
			a(2,
					0,
					1,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x, y - 12, 4, 12), 24, false));
			a(-1,
					0,
					4,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 8, y - 12, 4, 12), 24, false));
			a(2,
					0,
					1,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 4, y - 12, 4, 12), 24, false));
			a(-1,
					0,
					4,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 12, y - 12, 4, 12), 24, false));
		}

		// arm (left)
		{
			a(-1,
					23,
					-3 + 8,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40 + 4, 16, 4, 4), 8, false));
			a(-1,
					12,
					-3 + 8,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40 + 4 + 4, 16, 4, 4), 8, false));

			a(2,
					12,
					-3 + 8,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40, 20, 4, 12), 24, false));
			a(-1,
					12,
					8,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40 + 8, 20, 4, 12), 24, false));
			a(2,
					12,
					-3 + 8,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40 + 4, 20, 4, 12), 24, false));
			a(-1,
					12,
					8,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(40 + 12, 20, 4, 12), 24, false));
		}
		// arm (right)
		{
			int x = 32;
			int y = 20;

			// Tops and bottoms

			a(2,
					12,
					-3 - 4,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x, y, 4, 12), 24, false));
			a(-1,
					12,
					-4,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 8, y, 4, 12), 24, false));
			a(-1,
					12,
					-3 - 4,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 4, y, 4, 12), 24, false));
			a(2,
					12,
					-4,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 12, y, 4, 12), 24, false));

			a(-1,
					23,
					-3 - 4,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 4, y - 4, 4, 4), 8, false));
			a(-1,
					12,
					-3 - 4,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(x + 4 + 4, y - 4, 4, 4), 8, false));
		}
		// chest
		{
			a(2,
					12,
					-3,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16, 20, 4, 12), 24, false));
			a(-1,
					12,
					4,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16 + 12, 20, 4, 12), 24, false));
			a(2,
					12,
					-3,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16 + 4, 20, 8, 12), 24, false));
			a(-1,
					12,
					4,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16 + 16, 20, 8, 12), 24, false));
		}
		//Helmet bottom
		{
			a(-3,
					23,
					-3,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32 + 16, 0, 8, 8), 16, true), true);
			a(-3,
					32,
					-3,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32 + 8, 0, 8, 8), 16, true), true);
		}

		// head
		{
			a(-3,
					24,
					-3,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16, 0, 8, 8), 16, false));
			a(-3,
					31,
					-3,
					loc,
					flat,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(8, 0, 8, 8), 16, false));

			a(-3,
					24,
					-3,
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(0, 8, 8, 8), 16, false));
			a(4,
					24,
					4,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(16, 8, 8, 8), 16, false));
			a(-3,
					24,
					4,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(24, 8, 8, 8), 16, false));
			a(4,
					24,
					-3,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(8, 8, 8, 8), 16, false));
		}
		// helmet
		{

			a(-3,
					24,
					-4,//-3
					loc,
					right,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32, 8, 8, 8), 16, true), true);
			a(4,
					24,
					5,//4,
					loc,
					left,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32 + 16, 8, 8, 8), 16, true), true);
			a(-4,//-3,
					24,
					4,
					loc,
					back,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32 + 24, 8, 8, 8), 16, true), true);
			a(5,//4,
					24,
					-3,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							skin.getSubimage(32 + 8, 8, 8, 8), 16, true), true);
		}

		if (cape != null) {
			a(-6 + 4,
					6,
					-9 + 4,
					loc,
					dir,
					dir,
					RGBBlockColor.createResizedCopy(
							cape.getSubimage(0, 0, 12, 18), 18 * 2, true), true);
		}

	}

	private static void a(int x, int y, int z, Location loc, Direction d,
						  Direction f, BufferedImage skin2) {
		a(x, y, z, loc, d, f, skin2, false);
	}

	private static void a(int x, int y, int z, Location loc, Direction d,
						  Direction f, BufferedImage skin2, boolean enableTrans) {

		if (d == Direction.UP_EAST) {
			d = Direction.UP_SOUTH;
		} else if (d == Direction.UP_WEST) {
			d = Direction.UP_NORTH;
		} else if (d == Direction.UP_NORTH) {
			d = Direction.UP_EAST;
			/*
			 * if (f == Direction.UP_EAST || f == Direction.UP_WEST) { d =
			 * Direction.UP_EAST; } else { d = Direction.UP_WEST; }
			 */
		} else if (d == Direction.UP_SOUTH) {
			d = Direction.UP_WEST;
			/*
			 * if (f == Direction.UP_EAST || f == Direction.UP_WEST) { d =
			 * Direction.UP_WEST; } else { d = Direction.UP_EAST; }
			 */
		}
		Pixel[][] result = RGBBlockColor.convertTo2DWithoutUsingGetRGB(skin2);
		new AsyncImageHolder(result, null, getOffset(loc, f, x, y, z), d, skin2,
				enableTrans).loadImage(false);
	}

	public static Location getOffset(Location start, Direction d, double xoff,
									 int yoff, double zoff) {
		if (d == Direction.UP_SOUTH) {
			return start.clone().add(-zoff, yoff, -xoff);
		}
		if (d == Direction.UP_NORTH) {
			return start.clone().add(zoff, yoff, xoff);
		}
		if (d == Direction.UP_EAST) {
			return start.clone().add(xoff, yoff, zoff);
		}
		if (d == Direction.UP_WEST) {
			return start.clone().add(-xoff, yoff, -zoff);
		}
		return start.clone().add(xoff, yoff, zoff);
	}

}
