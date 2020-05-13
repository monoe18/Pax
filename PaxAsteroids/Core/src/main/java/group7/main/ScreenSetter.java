/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author pradeepthayaparan
 */
public class ScreenSetter extends Game {

// Screen and Game objects are used to create a simple and powerful structure for games.
    
    
    
 //used to render objects onto the screen, such as textures
    public SpriteBatch batch; 
    
    
    //  public BitmapFont font;

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
        //Use LibGDX's default Arial font.  
        // Starts the MainMenu Screen, which takes the ScreenSetter Object as 
        // a parameter. 
        this.setScreen(new MainMenu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();

    }

}
