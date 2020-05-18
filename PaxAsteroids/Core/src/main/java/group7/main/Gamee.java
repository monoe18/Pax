package group7.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.services.IEntityProcessingService;
import group7.common.services.IGamePluginService;
import group7.common.services.IPostEntityProcessingService;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import group7.common.data.GameData;
import group7.common.entityparts.PositionPart;
import group7.common.data.IMap;
import group7.common.services.ISpriteService;

import group7.common.entityparts.ShootingPart;
import group7.common.services.IBulletManager;
import group7.common.services.IWaveManager;
import group7.common.services.IHUD;

import group7.manager.GameInputProcessor;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Gamee implements Screen {
// Game + screen 

    ScreenSetter game;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData; // = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IBulletManager> bulletManagerList = new CopyOnWriteArrayList<>();
    private static final List<ISpriteService> spriteServiceList = new CopyOnWriteArrayList<>();
    private static final List<IHUD> hudList = new CopyOnWriteArrayList<>();

    private SpriteBatch batch;
    private BitmapFont font;
    private static final HashMap<ISpriteService, Sprite> spriteHashMap = new HashMap();
    private static final HashMap<IHUD, Sprite> hudHahsMap = new HashMap();
    private HashMap<Entity, Sprite> entitySpriteMap = new HashMap();

    public Gamee(ScreenSetter game) {
        this.game = game;
        gameData = new GameData();
        batch = new SpriteBatch();
        font = new BitmapFont();
        //  font.setColor(Color.valueOf("FBDF6B"));

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1440, 800);
        cam.update();
        System.out.println("Gamee bliver kaldt");

        create();
        update();
        float f = 0;
        render(f);

        System.out.println(spriteServiceList.size() + "size of sprites");
        System.out.println(entityProcessorList.size() + "size of Entities");
        System.out.println(gamePluginList.size() + "size of Gameplugins");
        System.out.println(entityProcessorList.size() + "size of Entityprocessor");
    }

    public Gamee() {
        this.game = new ScreenSetter();
        gameData = new GameData(); // remove?  
    }

//INFO [org.netbeans.core.netigso.Netigso]: bundle org.eclipse.osgi@3.9.1.v20140110-1610 started
//    private void init() {
//        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
//        cfg.title = "Shooter";
//        cfg.width = 1440;
//        cfg.height = 800;
//        cfg.useGL30 = false;
//        cfg.resizable = false;
//
//        new LwjglApplication(this, cfg);  
//
//        
//        
//    }
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());

        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        batch = new SpriteBatch();
        //Loads all textures from sprite ServiceProviders
        spriteServiceList.forEach((spriteService) -> {
            if (spriteService instanceof IMap) {
                ((IMap) spriteService).initMap(gameData, world);
            }
            Texture tex = new Texture(Gdx.files.internal(spriteService.getSprite()));
            Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
            sprite.setSize(spriteService.getSpriteWidth(), spriteService.getSpriteHeight());
            spriteHashMap.put(spriteService, sprite);
        });

    }

    @Override
    public void resize(int i, int j) {

    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        for (Entity e : world.getEntities()) {
            checkForShooting(e, gameData);
        }

        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }

        for (IGamePluginService plugin : gamePluginList) {
            if (plugin instanceof IWaveManager) {
                IWaveManager waveManager = (IWaveManager) plugin;
                waveManager.checkWaveStatus(world);
                if (waveManager.isSpawnStatus()) {
                    waveManager.spawnEnemies(gameData, world);
                }
            }
        }
        for (IHUD hud : hudList) {
            if (hud.getHudType().equals("WaveBar")) {
                for (IGamePluginService plugin : gamePluginList) {
                    if (plugin instanceof IWaveManager) {
                        hud.updateHUD(world, gameData, ((IWaveManager) plugin).getWaveCount());
                    }
                }
            } else {
                hud.updateHUD(world, gameData, 0);
            }

        }
        // checkForLifeUpdate();
    }
    @Override
    public void render(float f) {
        //starts the batch and loads all sprites
        batch.begin();

        spriteServiceList.forEach((spriteService) -> {
//            System.out.println("GAME " + spriteService.getSprite());
//
//            Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
//            sprite.setSize(spriteService.getSpriteWidth(), spriteService.getSpriteHeight());
            //  spriteHashMap.put(spriteService, sprite);
        });

        for (Map.Entry<ISpriteService, Sprite> entry : spriteHashMap.entrySet()) {
            Texture tex = new Texture(Gdx.files.internal(entry.getKey().getSprite()));
            try {

                entry.getValue().setTexture(tex);
                entry.getValue().setX(entry.getKey().getX(world));
                entry.getValue().setY(entry.getKey().getY(world));
                entry.getValue().draw(batch);
            } catch (java.lang.NullPointerException e) {

            }

        }

        for (Entity entity : world.getEntities()) {
            try {
                Texture tex = new Texture(Gdx.files.internal(entity.getFileName()));
                Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
                sprite.setSize(entity.getSpriteWidth(), entity.getSpriteHeight());
                sprite.rotate(entity.getRotate());
                PositionPart p = entity.getPart(PositionPart.class); // Overholder PositionPartdet CBSE regler???

                sprite.setX(p.getX() - (entity.getSpriteWidth() / 2));
                sprite.setY(p.getY() - (entity.getSpriteHeight() / 2));
                sprite.flip(entity.isFlipRightLeft(), false);

                sprite.draw(batch);
            } catch (NullPointerException ex) {

            }
        }

        for (IHUD hudElement : hudList) {
            // hudElement.updateHUD(world, gameData,0);
            Texture tex = new Texture(Gdx.files.internal(hudElement.getSprite()));
            Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
            sprite.setSize(hudElement.getSpriteWidth(), hudElement.getSpriteHeight());
            sprite.setX(hudElement.getX());
            sprite.setY(hudElement.getY());
            sprite.draw(batch);
        }

        batch.end();
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        update();
    }

    private void checkForShooting(Entity e, GameData g) {
        ShootingPart shootingPart = e.getPart(ShootingPart.class);
        if (shootingPart != null && shootingPart.isShooting()) {
            createBullet(e, g);
        }
    }

    private void checkForLifeUpdate() {
        for (ISpriteService sprite : spriteServiceList) {

        }
    }

    private void createBullet(Entity e, GameData g) {
        for (IBulletManager bm : bulletManagerList) {
            world.addEntity(bm.createBullet(e, g));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        font.dispose();

    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

    public void addSpriteService(ISpriteService eps) {
        spriteServiceList.add(eps);

    }

    public void removeSpriteService(ISpriteService eps) {
        spriteServiceList.remove(eps);
    }

    public void addBulletManager(IBulletManager eps) {
        Gamee.bulletManagerList.add(eps);
    }

    public void removeBulletManager(IBulletManager eps) {
        Gamee.bulletManagerList.remove(eps);
    }

    public void addHUD(IHUD hud) {
        hudList.add(hud);
    }

    public void removeHUD(IHUD hud) {
        hudList.remove(hud);

    }

    @Override
    public void show() {
    }


    @Override
    public void hide() {
    }

}
