package com.mygdx.pmd;

/**
 * Created by Cameron on 8/31/2016.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.mygdx.pmd.ui.Button;

import java.io.IOException;

import static com.badlogic.gdx.Gdx.input;

public class UISimpleTest extends Game {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    private Table table;

    OrthographicCamera camera;

    Table table1;

    @Override
    public void create() {
    /*    batch = new SpriteBatch();
        stage = new Stage();
        input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".

        // Store the default libgdx font under the name "default".

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.


        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.atlas"));

        skin = new Skin(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.json"), atlas);
        // BitmapFont font = new BitmapFont(Gdx.files.internal("Calibri.fnt"),Gdx.files.internal("Calibri.png"),false);

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();

        final TextButton button = new TextButton("Click me!", skin);

        table.add(button);
        //final Label label = new Label("Hello how", new Skin(new TextureAtlas("TREEKO_WALKSHEET.txt")));
        // button.add(label);
        table.setBounds(0, 0, 100, 100);

        final TextButton button1 = new TextButton("This is terrible", skin);
        final Label label = new Label("hello",skin);
        label.setVisible(true);
        table.add(label);

        Texture texture = new Texture(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

      *//*  table.setFillParent(true);*//*
        stage.addActor(table);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        //table.add(button);
        //table.setBounds(0, 0, 100, 100);

        // Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
        // revert the checked state.
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button.isChecked());

                stage.clear();
            }
        });

        button1.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Clicked! Is checked: " + button1.isChecked());


                stage.clear();
                stage.addActor(table);
            }
        });

        button.setVisible(true);

        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).



        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();*/

        /*FileHandle handle = Gdx.files.internal("Menu.json");
        String fileContent = handle.readString();
        Json json = new Json();
        Menu string = json.fromJson(Menu.class, fileContent);
        System.out.println(string.menuButtons);

        stage = new Stage();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.atlas"));

        table = new Table();
        table.setBounds(0,0,1000,1000);
        skin = new Skin(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.json"), atlas);

        final TextButton button1 = new TextButton("This is terrible", skin);
        final Label label = new Label("hello", skin);
        label.setVisible(true);
        table.add(label);

        Texture texture = new Texture(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.png"));
        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
*/

      /*  TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.atlas"));

        skin = new Skin(Gdx.files.internal("com.mygdx.pmd.ui/uiskin.json"), atlas);
        final Label button1 = new Label("This is terrible", skin);
        Menu menu = new Menu();
        menu.menuButtons.add(button1);
        final Label button2 = new Label("This is terrible", skin);
        Json json = new Json();
        menu.menuButtons.add(button2);
        System.out.println(json.prettyPrint(menu));*/

        batch = new SpriteBatch();
        stage = new Stage();
        input.setInputProcessor(stage);

        XmlReader xmlReader = new XmlReader();
        try {
            table = new Table();
            table.setDebug(true);
            table.setFillParent(true);
            table.top().left();

            XmlReader.Element root = xmlReader.parse(Gdx.files.internal("table.xml"));

            TextureAtlas textureAtlas = new TextureAtlas(root.get("textureatlas"));

            Skin skin = new Skin(Gdx.files.internal(root.get("json")), textureAtlas);

            Array<XmlReader.Element> nodeList = root.getChildrenByName("Button");
            /*for(XmlReader.Element e: nodeList)
            {
                table.add(new Button(e.get("text"), e.get("classifier"), skin, table, controller)).WIDTH(200);
                table.row();
            }*/
            //have a HashMap of tables that I load on startup and can access

            System.out.println(root.getChildCount());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        stage.addActor(table);

    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       /* stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();*/
       stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

