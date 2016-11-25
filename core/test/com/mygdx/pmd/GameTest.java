package com.mygdx.pmd;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.pmd.Controller.Controller;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

/**
 * Created by Cameron on 11/24/2016.
 */
public class GameTest {
    // This is our "test" application
    public static Application application;
    public static Controller controller;

    // Before running any tests, initialize the application with the headless backend
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        PMD game = new PMD();
        controller = game.controller;
        Gdx.gl = Mockito.mock(GL20.class);

        application = new HeadlessApplication(game, config);
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}
