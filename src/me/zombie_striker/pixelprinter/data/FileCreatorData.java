package me.zombie_striker.pixelprinter.data;

public class FileCreatorData {

	String fileType;
	String fileName;

	public FileCreatorData(String t, String n) {
		this.fileName = n;
		this.fileType = t;
	}

	public String getName() {
		return this.fileName;
	}

	public String getType() {
		return this.fileType;
	}
}
