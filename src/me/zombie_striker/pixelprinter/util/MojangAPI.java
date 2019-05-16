package me.zombie_striker.pixelprinter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MojangAPI {

	private static final String NAME_URL = "https://api.mojang.com/users/profiles/minecraft/";

	public static String getUUIDFromName(String name) {
		try {
			URL url = new URL(NAME_URL + name);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			List<String> list = new ArrayList<>();
			String input = null;
			while ((input = br.readLine()) != null) {
				list.add(input);
			}
			br.close();

			for (String s : list) {
				System.out.println(s);
				return s.split(",")[0].split("id\":")[1].replaceAll("\"", "")
						.trim();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
