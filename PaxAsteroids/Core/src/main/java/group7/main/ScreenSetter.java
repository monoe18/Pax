package group7.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenSetter extends Game {

    //used to render objects onto the screen, such as textures
    public SpriteBatch batch;

    public ScreenSetter() {
        init();
    }

    private void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Shooters";
        cfg.width = 1440;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);

    }

    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();

    }

}
