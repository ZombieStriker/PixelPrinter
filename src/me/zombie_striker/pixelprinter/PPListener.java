package me.zombie_striker.pixelprinter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PPListener implements Listener {

	private PixelPrinter inst;

	public PPListener(PixelPrinter p) {
		this.inst = p;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		if (inst.downloadFile.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
			if (!e.getMessage().contains(".")) {
				inst.downloadFile.remove(e.getPlayer().getUniqueId());
				e.getPlayer().sendMessage("The link sent is not valid. Resend the command and provide a valid link.");
				return;
			}
			File outputfile = new File(
					inst.images + File.separator + inst.downloadFile.get(e.getPlayer().getUniqueId()).getName()
					// + inst.downloadFile.get(e.getPlayer().getUniqueId())
					// .getType()
							+ (inst.downloadFile.get(e.getPlayer().getUniqueId()).getType().equalsIgnoreCase(".txt")
									? ".txt"
									: (e.getMessage().endsWith("gif") ? ".gif" : ".jpg")));
			if (outputfile.exists()) {
				e.getPlayer().sendMessage(inst.getPrefix()
						+ " A file already exists with this name. Either choose a new name or contact the server admin to delete this image.");
				return;
			}
			if (inst.downloadFile.get(e.getPlayer().getUniqueId()).getType().equalsIgnoreCase(".txt")) {
				try {
					BufferedWriter br = new BufferedWriter(new FileWriter(outputfile));
					br.write(e.getMessage());
					br.flush();
					br.close();
					e.getPlayer().sendMessage(inst.getPrefix() + " Completed downloading image path. File \""
							+ outputfile.getName() + "\" created.");
				} catch (IOException e2) {
					e.getPlayer().sendMessage(inst.getPrefix()
							+ " Something failed when downloading the image. Check console for details.");
					e2.printStackTrace();
				}
			} else {
				try {
					if (e.getMessage().toLowerCase().endsWith(".gif")) {
						// byte[] bytes = IOUtils.toByteArray(new URL(e
						// .getMessage()).openStream());
						// FileUtils.writeByteArrayToFile(outputfile, bytes);
						PixelPrinter.saveImage(new URL(e.getMessage()), outputfile);
						e.getPlayer().sendMessage(inst.getPrefix() + " Completed downloading image. File \""
								+ outputfile.getName() + "\" created.");
					} else {
						BufferedImage bi = ImageIO.read(new URL(e.getMessage()));
						ImageIO.write(bi, "jpg", outputfile);
						e.getPlayer().sendMessage(inst.getPrefix() + " Completed downloading image. File \""
								+ outputfile.getName() + "\" created.");
						bi.flush();
					}
				} catch (Exception er) {
					e.getPlayer().sendMessage(inst.getPrefix()
							+ " Something failed when downloading the image. Check console for details.");
					er.printStackTrace();

				}
			}
			inst.downloadFile.remove(e.getPlayer().getUniqueId());
		}
	}
}
