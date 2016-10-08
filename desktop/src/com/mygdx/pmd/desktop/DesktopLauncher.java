package com.mygdx.pmd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pmd.UISimpleTest;
import com.mygdx.pmd.PMD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 30;
		config.width = 1080;
		config.height = 720;

		PMD game = new PMD();

		UISimpleTest test = new UISimpleTest();

		new LwjglApplication(game, config);
	}
}
