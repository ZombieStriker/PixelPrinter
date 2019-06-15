package me.zombie_striker.pixelprinter.data;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.List;

public class CustomMapView implements MapView {

	List<MapRenderer> renderers = new ArrayList<>();
	private int mapID = 0;

	public CustomMapView(int id) {
		this.mapID = id;
	}

	@Override
	public void addRenderer(MapRenderer arg0) {
		renderers.add(arg0);
	}

	@Override
	public int getCenterX() {
		return 0;
	}

	@Override
	public void setCenterX(int arg0) {
	}

	@Override
	public int getCenterZ() {
		return 0;
	}

	@Override
	public void setCenterZ(int arg0) {
	}

	@Override
	public int getId() {
		return mapID;
	}

	@Override
	public List<MapRenderer> getRenderers() {
		return renderers;
	}

	@Override
	public Scale getScale() {
		return Scale.NORMAL;
	}

	@Override
	public void setScale(Scale arg0) {
	}

	@Override
	public World getWorld() {
		return Bukkit.getWorlds().get(0);
	}

	@Override
	public void setWorld(World arg0) {
	}

	@Override
	public boolean isUnlimitedTracking() {
		return true;
	}

	@Override
	public void setUnlimitedTracking(boolean arg0) {
	}

	@Override
	public boolean isVirtual() {
		return true;
	}

	@Override
	public boolean removeRenderer(MapRenderer arg0) {
		return renderers.remove(arg0);
	}

	@Override
	public boolean isTrackingPosition() {
		return false;
	}

	@Override
	public void setTrackingPosition(boolean b) {

	}

	@Override
	public boolean isLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLocked(boolean arg0) {
		// TODO Auto-generated method stub

	}


}
