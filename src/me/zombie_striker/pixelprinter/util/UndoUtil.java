package me.zombie_striker.pixelprinter.util;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Set;

public class UndoUtil {

	static HashMap<String, BlockTypeSnapshot> savedLocs = new HashMap<>();

	public static String verifyNewName(String start) {
		String test = start;
		int id = 0;
		while (savedLocs.containsKey(test)) {
			id++;
			test = test + "(" + id + ")";
		}
		return test;
	}

	public static void addNewSnapshot(String name, Location start, Location end) {
		savedLocs.put(verifyNewName(name), new BlockTypeSnapshot(start, end));
	}

	public static void undo(String name) {
		BlockTypeSnapshot bts = savedLocs.get(name);
		if (bts != null)
			bts.undo();
	}

	public static void remove(String name) {
		savedLocs.remove(name);
	}

	public static Set<String> getSnapshots() {
		return savedLocs.keySet();
	}

	public static boolean snapshotExists(String name) {
		for (String s : savedLocs.keySet()) {
			if (s.equals(name))
				return true;
		}
		return false;
	}

}
