package group7.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.services.IEntityProcessingService;
import group7.common.services.IGamePluginService;
import group7.common.services.IPostEntityProcessingService;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import group7.common.data.GameData;
import group7.common.entityparts.PositionPart;
import group7.common.markInterfaces.IMap;
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

public class Main implements Screen {

    ScreenSetter game;
    private static OrthographicCamera cam;
    private final GameData gameData;
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
    private int currentWave = 1;
    private int starScroller = 0;

    public Main(ScreenSetter game) {
        this.game = game;
        gameData = new GameData();
        batch = new SpriteBatch();
        font = new BitmapFont();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1440, 800);
        cam.update();

        create();
        update();
        float f = 0;
        render(f);
    }

    public Main() {
        this.game = new ScreenSetter();
        gameData = new GameData();
    }

    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());

        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

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

    private void update() {

        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            checkForShooting(entity);
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
                        currentWave = ((IWaveManager) plugin).getWaveCount();
                        hud.updateHUD(world, ((IWaveManager) plugin).getWaveCount());
                    }
                }
            } else if (hud.getHudType().equals("EndGame")) {
                hud.updateHUD(world, currentWave);
            } else {
                hud.updateHUD(world, 0);
            }

        }
    }

    @Override
    public void render(float f) {
        //starts the batch and loads all sprites
        batch.begin();

        scrollStars(gameData);

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
                PositionPart p = entity.getPart(PositionPart.class);

                sprite.setX(p.getX() - (entity.getSpriteWidth() / 2));
                sprite.setY(p.getY() - (entity.getSpriteHeight() / 2));
                sprite.flip(entity.isFlipRightLeft(), false);

                sprite.draw(batch);
            } catch (NullPointerException ex) {

            }
        }

        for (IHUD hudElement : hudList) {
            // hudElement.updateHUD(world, gameData,0);
            if (!hudElement.getSprite().equals(".")) {
                Texture tex = new Texture(Gdx.files.internal(hudElement.getSprite()));
                Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
                sprite.setSize(hudElement.getSpriteWidth(), hudElement.getSpriteHeight());
                sprite.setX(hudElement.getX());
                sprite.setY(hudElement.getY());
                sprite.draw(batch);
            }
        }

        batch.end();
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        update();
    }

    private void scrollStars(GameData gamedata) {
        Texture tex = new Texture(Gdx.files.internal("stars.png"));
        Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
        sprite.setSize(2000, 70);

        sprite.setX(starScroller - 300);
        sprite.setY(650);
        sprite.draw(batch);
        if (starScroller < 490) {
            starScroller = starScroller + (5);
        } else {
            starScroller = 0;
        }
    }

    private void checkForShooting(Entity e) {
        ShootingPart shootingPart = e.getPart(ShootingPart.class);
        if (shootingPart != null && shootingPart.isShooting()) {
            createBullet(e);
        }
    }

    private void checkForLifeUpdate() {
        for (ISpriteService sprite : spriteServiceList) {

        }
    }

    private void createBullet(Entity e) {
        for (IBulletManager bm : bulletManagerList) {
            world.addEntity(bm.createBullet(e));
        }
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
        bulletManagerList.add(eps);
    }

    public void removeBulletManager(IBulletManager eps) {
        bulletManagerList.remove(eps);
    }

    public void addHUD(IHUD hud) {
        hudList.add(hud);
    }

    public void removeHUD(IHUD hud) {
        hudList.remove(hud);

    }

    // LibGDX methods
    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int i, int j) {
    }

}
