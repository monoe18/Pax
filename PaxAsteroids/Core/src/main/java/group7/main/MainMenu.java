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

    ScreenSetter MainMenu;
    private OrthographicCamera camera;
    private Stage stage;
    private TextureAtlas atlas;
    private Texture backgound;
    private Skin skin;
    private Table table;
    private Texture playButton, ExitButton;
    private BitmapFont white, black;
    private Label label;
    

//    private static final int PlaybuttonX = 300;
//    private static final int PlaybuttonY = 300;

    public MainMenu(ScreenSetter MainMenu) {
        this.MainMenu = MainMenu;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 800);

        playButton = new Texture("PlayButton.png");
        ExitButton = new Texture("QuitButton.png");

        
       // A drawable has information about its size and how to draw itself. It's used to determine size and position by ui components. 
       //Since you are using a texture, you can use a TextureRegionDrawable. 
        
       
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playButton));
        Drawable drawables = new TextureRegionDrawable(new TextureRegion(ExitButton));
        ImageButton Play = new ImageButton(drawable);
        ImageButton Exit = new ImageButton(drawables);

        Stage stage = new Stage(new ScreenViewport()); //Set up a stage for the ui  
        stage.addActor(Play);
        stage.addActor(Exit);

        Gdx.input.setInputProcessor(stage);
        Play.setPosition(500, 500);
        Play.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {   
                System.out.println("TJEK FOR PLAY");  
                System.out.println("X = "+ x +" Y = "+y);  
                MainMenu.setScreen(new Gamee(MainMenu));  
                dispose();
          
            }

        });

        
        Exit.setPosition(500, 200);
        Exit.addListener(new ClickListener()  {
         
            
            @Override
            public void clicked(InputEvent event, float x, float y) {   
                System.out.println("TJEK FOR EXIT");  
                System.out.println("X = "+ x +" Y = "+y);   
                Gdx.app.exit();
                dispose();
          
            } 

        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float f) {
        // Clear frame
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        MainMenu.batch.setProjectionMatrix(camera.combined);

        MainMenu.batch.begin();

        MainMenu.batch.draw(playButton, 500, 500);

        MainMenu.batch.draw(ExitButton, 500, 200);

        
        // Draw background
//        MainMenu.batch.draw(
//                backgroundTexture, 0, 0, background.getX(), background.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
//        );
//        System.out.println("Hej");
        MainMenu.batch.end();

        // Set game screen when user has touched inside the window
//	if (Gdx.input.isTouched()) {  
//            System.out.println("Der er blevet trykket en gang");
//            MainMenu.setScreen(new Gamee(MainMenu));
//            dispose();
//	}   
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
    }

    @Override
    public void dispose() { 
       playButton.dispose(); 
       ExitButton.dispose();
     //  white.dispose();
   //    atlas.dispose();
    //    stage.dispose();
     

        
    }

}
