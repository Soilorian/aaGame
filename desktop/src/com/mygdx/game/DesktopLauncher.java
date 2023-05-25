package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import main.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(972, 1200);
		config.setForegroundFPS(60);
		config.setTitle("aaGame");
		config.setResizable(false);
		config.setMaximized(false);
		config.setWindowIcon("pictures/aa.png");
		new Lwjgl3Application(new Main(), config);
	}
}
