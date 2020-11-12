package me.zombie_striker.easygui.examples;

import me.zombie_striker.easygui.ClickData;
import me.zombie_striker.easygui.EasyGUICallable;

public class HelloWorldCallable extends EasyGUICallable {

	@Override
	public void call(ClickData data) {
		data.getClicker().sendMessage("Hello World!");
	}
}
