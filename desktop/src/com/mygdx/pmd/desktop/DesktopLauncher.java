package com.mygdx.pmd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pmd.PMD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		PMD game = new PMD();

        //set these things as these values for now can edit later
		//Gdx.graphics.getWidth and height return null because lwgwl app has not been made yet
		config.width = 1080;
		config.height = 720;

		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;

		config.title = PMD.TITLE;

		//UISimpleTest test = new UISimpleTest();

		LwjglApplication app = new LwjglApplication(game, config);
	}
}
