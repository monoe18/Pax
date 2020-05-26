package group7.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu implements Screen {

    ScreenSetter MainMenu;
    private OrthographicCamera camera;
    private Stage stage;
    private TextureAtlas atlas;
    private Texture backgound;
    private Skin skin;
    private Table table;
    private Texture playButton, ExitButton, Background;
    private BitmapFont white, black;
    private Label label;
    Texture texture;
    Sprite sprite;

    public MainMenu(ScreenSetter MainMenu) {
        this.MainMenu = MainMenu;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 800);
        texture = new Texture("STUBBI.png");
        sprite = new Sprite(texture);
        playButton = new Texture("START.png");
        ExitButton = new Texture("EXIT.png");

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
        Play.setPosition(613, 500);
        Play.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                MainMenu.setScreen(new Main(MainMenu));
                dispose();
            }
        });

        Exit.setPosition(636, 300);
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

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        MainMenu.batch.setProjectionMatrix(camera.combined);

        MainMenu.batch.begin();
        MainMenu.batch.draw(sprite, 0, 0);
        MainMenu.batch.draw(playButton, 613, 500);

        MainMenu.batch.draw(ExitButton, 636, 300);

        MainMenu.batch.end();
    }

    // LibGDX Methods
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
    public void show() {

    }

    @Override
    public void dispose() {
        playButton.dispose();
        ExitButton.dispose();
    }
}
