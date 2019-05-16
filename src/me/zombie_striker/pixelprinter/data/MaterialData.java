package me.zombie_striker.pixelprinter.data;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import me.zombie_striker.pixelprinter.util.RGBBlockColor;

public class MaterialData implements ConfigurationSerializable {

	private Material m;
	private byte data;
	
	private BlockFace direction = null;
	
	public boolean hasDirection() {
		return direction !=null;
	}
	public BlockFace getDirection() {
		return direction;
	}

	public MaterialData(Material m, byte data, BlockFace direction) {
		ConfigurationSerialization.registerClass(MaterialData.class);
		this.setMaterial(m);
		this.data = data;
		this.direction = direction;
		
	}
	public MaterialData(Material m, byte data) {
		this(m,data,null);
	}

	public MaterialData(Material m) {
		this(m,((byte)0),null);
	}

	public static MaterialData getMatDataByTypes(Material mat, byte data) {
		return getMatDataByTypes(mat, data, null);
	}
	public static MaterialData getMatDataByTypes(Material mat, byte data, BlockFace direction) {
		for (MaterialData key : RGBBlockColor.materialValue.keySet())
			if (key.getData() == data && key.getMaterial() == mat && ((direction==null&&!key.hasDirection())||direction==key.getDirection()))
				return key;
		return null;
	}

	public byte getData() {
		return data;
	}

	public Material getMaterial() {
		return m;
	}

	public MaterialData(Map<String, Object> data) {
		this.setMaterial(Material.valueOf((String) data.get("m")));
		this.data = Byte.parseByte((String) data.get("data"));
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("m", this.getMaterial().toString());
		data.put("data", this.data + "");
		return data;
	}

	public void setMaterial(Material m) {
		this.m = m;
	}
}
