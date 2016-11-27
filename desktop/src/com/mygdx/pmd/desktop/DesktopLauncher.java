package com.mygdx.pmd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pmd.UISimpleTest;
import com.mygdx.pmd.PMD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		PMD game = new PMD();
		config.foregroundFPS = 60;
		config.width = game.WIDTH;
		config.height = game.HEIGHT;
		config.title = game.TITLE;

		UISimpleTest test = new UISimpleTest();

		new LwjglApplication(game, config);
	}
}
