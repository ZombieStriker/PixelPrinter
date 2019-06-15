/*
 *  Copyright (C) 2016 Zombie_Striker
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

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class CustomImageRenderer extends MapRenderer {

	public static int TICK_FOR_STILLS = 500;
	private BufferedImage[] image;
	private int frameCount = 0;

	public CustomImageRenderer(BufferedImage[] bi, int ticks) {
		super(true);
		this.image = bi;
	}

	public CustomImageRenderer(BufferedImage bi, int ticks) {
		super(true);
		this.image = new BufferedImage[1];
		image[0] = bi;
	}

	// maps update multiple times per second.
	// for still images, set ticks to 100;
	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		if (image != null && image[frameCount] != null)
			canvas.drawImage(0, 0, image[frameCount]);
		frameCount = (frameCount + 1);// % image.length;
		if (frameCount >= image.length)
			frameCount = 0;
	}

}
