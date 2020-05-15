/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author pradeepthayaparan
 */
public class MainMenu implements Screen {

    ScreenSetter mainMenu;
    private OrthographicCamera camera;
    //  private Stage stage;
//    private TextureAtlas atlas;
//    private Texture backgound;
//    private Skin skin;
//    private Table table;
//    //private Texture playButton, ExitButton;
//    private BitmapFont white, black;
//    private Label label;

    Texture playButton = new Texture("PlayButton.png");
    Texture ExitButton = new Texture("QuitButton.png");
    Drawable drawable = new TextureRegionDrawable(new TextureRegion(playButton));
    Drawable drawables = new TextureRegionDrawable(new TextureRegion(ExitButton));
    ImageButton Play = new ImageButton(drawable);
    ImageButton Exit = new ImageButton(drawables);
    
    
    Stage stage = new Stage(new ScreenViewport()); //Set up a stage for the ui  
    //Creates a stage with the specified viewport.
    //The stage will use its own Batch which will be disposed when the stage is disposed.
    

    
//    private static final int PlaybuttonX = 300;
//    private static final int PlaybuttonY = 300;
    public MainMenu(ScreenSetter mainMenu) {
        System.out.println("Main menu kaldt første gang");
        this.mainMenu = mainMenu;       
        // A drawable has information about its size and how to draw itself. It's used to determine size and position by ui components. 
        //Since you are using a texture, you can use a TextureRegionDrawable. 

        
        
//        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playButton));
//        Drawable drawables = new TextureRegionDrawable(new TextureRegion(ExitButton));
//        ImageButton Play = new ImageButton(drawable);
//        ImageButton Exit = new ImageButton(drawables);
//        Stage stage = new Stage(new ScreenViewport()); //Set up a stage for the ui  
//        stage.addActor(Play);
//        stage.addActor(Exit);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 800);
        Gdx.input.setInputProcessor(stage);
        Play.setPosition(500, 500);
        stage.addActor(Play);
        stage.addActor(Exit);
        
            Play.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TJEK FOR PLAY");
                System.out.println("X = " + x + " Y = " + y);
                dispose();
                mainMenu.setScreen(new Game(mainMenu));
//                dispose();
             

// dispose();  
            }

        });

        Exit.setPosition(500, 200);
        Exit.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TJEK FOR EXIT");
                System.out.println("X = " + x + " Y = " + y);
                Gdx.app.exit();
                dispose();

            }

        });

    }

    @Override
    public void render(float f) {
        // Clear frame
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        mainMenu.batch.setProjectionMatrix(camera.combined);

        mainMenu.batch.begin();

        mainMenu.batch.draw(playButton, 500, 500);

        mainMenu.batch.draw(ExitButton, 500, 200);

        // Draw background
//        mainMenu.batch.draw(
//                backgroundTexture, 0, 0, background.getX(), background.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
//        );
//        System.out.println("Hej");
        mainMenu.batch.end();

    

    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        System.out.println("HIdden");
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() { 
        System.out.println("Disposed");
        camera = null;
        stage.dispose();
        ExitButton.dispose();
        playButton.dispose();
        drawable = null;
        drawables = null;
        Play.reset();
        Exit.reset();   
        Gdx.input.setInputProcessor(null);  
        
      
//        mainMenu.dispose();
//       playButton.dispose(); 
//       ExitButton.dispose();
        //  white.dispose();
        //    atlas.dispose();
        //    stage.dispose();
    }

}
