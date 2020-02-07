package me.zombie_striker.pixelprinter.data;

import me.zombie_striker.pixelprinter.util.RGBBlockColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.HashMap;
import java.util.Map;

public class MaterialData implements ConfigurationSerializable, Comparable<MaterialData> {

	private Material m;
	private byte data;

	private ImageRelativeBlockDirection direction = null;

	public MaterialData(Material m, byte data, ImageRelativeBlockDirection direction) {
		ConfigurationSerialization.registerClass(MaterialData.class);
		this.setMaterial(m);
		this.data = data;
		this.direction = direction;

	}

	public MaterialData(Material m, byte data) {
		this(m, data, null);
	}

	public MaterialData(Material m) {
		this(m, ((byte) 0), null);
	}

	public MaterialData(Map<String, Object> data) {
		this.setMaterial(Material.valueOf((String) data.get("m")));
		this.data = Byte.parseByte((String) data.get("data"));
	}

	public static MaterialData getMatDataByTypes(Material mat, byte data) {
		return getMatDataByTypes(mat, data, null);
	}

	public static MaterialData getMatDataByTypes(Material mat, byte data, ImageRelativeBlockDirection direction) {
		for (MaterialData key : RGBBlockColor.materialValue.keySet())
			if (key.getData() == data && key.getMaterial() == mat && ((direction == null && !key.hasDirection()) || direction == key.getDirection()))
				return key;
		return null;
	}

	public boolean hasDirection() {
		return direction != null;
	}

	public ImageRelativeBlockDirection getDirection() {
		return direction;
	}
	public BlockFace getBlockFace() {
		return direction.convertToBlockFace();
	}

	public byte getData() {
		return data;
	}

	public Material getMaterial() {
		return m;
	}

	public void setMaterial(Material m) {
		this.m = m;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("m", this.getMaterial().toString());
		data.put("data", this.data + "");
		return data;
	}

	@Override
	public int compareTo(MaterialData o) {
		if(this.m==o.m){
			String dir1 = this.direction==null?"":this.direction.name();
			String dir2 = o.direction==null?"":o.direction.name();
			return dir1.compareTo(dir2);
		}
		return this.m.name().compareTo(o.m.name());
	}
}
