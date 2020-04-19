package group7.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.services.IEntityProcessingService;
import group7.common.services.IGamePluginService;
import group7.common.services.IPostEntityProcessingService;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import group7.common.data.GameData;
import group7.common.data.GameKeys;
import group7.common.entityparts.PositionPart;
import group7.common.data.IMap;
import group7.common.services.ISpriteService;

import group7.manager.GameInputProcessor;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();

    private static final List<ISpriteService> spriteServiceList = new CopyOnWriteArrayList<>();

    private SpriteBatch batch;
    private static final HashMap<ISpriteService, Sprite> spriteHashMap = new HashMap();
    private HashMap<Entity, Sprite> entitySpriteMap = new HashMap();

    public Game() {
        init();
    }
//INFO [org.netbeans.core.netigso.Netigso]: bundle org.eclipse.osgi@3.9.1.v20140110-1610 started

    private void init() {
        System.out.println("init");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Shooter";
        cfg.width = 1440;
        cfg.height = 800;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        System.out.println("started create");
        gameData.setDisplayWidth(Gdx.graphics.getWidth());

        System.out.println(Gdx.graphics.getWidth());

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
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }

    }

    private void drawSprites() {

//        for (Entity entity : world.getEntities()) {
//            if (entitySpriteMap.get(entity) == null) {
//                try {
//                    Texture tex = new Texture(Gdx.files.internal(entity.getFileName()));
//                    Sprite sprite = new Sprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
//                    sprite.setSize(entity.getSpriteWidth(), entity.getSpriteHeight());
//                    sprite.rotate(entity.getRotate());
//                    entitySpriteMap.put(entity, sprite);
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        entitySpriteMap.clear();
//        entitySpriteMap.putAll(world.getEntities());
    }

    private void spriteDirection() {

        //  gameData.is
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //starts the batch and loads all sprites
        batch.begin();
        drawSprites();
        for (Map.Entry<ISpriteService, Sprite> entry : spriteHashMap.entrySet()) {

            try {
                entry.getValue().setX(entry.getKey().getX(world));
                entry.getValue().setY(entry.getKey().getY(world));
                entry.getValue().draw(batch);
            } catch (java.lang.NullPointerException e) {
            }
        }
//        for (Map.Entry<Entity, Sprite> entry : entitySpriteMap.entrySet()) {
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
        batch.end();
        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        update();
    }



@Override
        public void pause() {

    }

    @Override
        public void resume() {

    }

    @Override
        public void dispose() {

    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        this.entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.add(plugin);
        plugin.start(gameData, world);

    }

    public void removeGamePluginService(IGamePluginService plugin) {
        this.gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

    public void addSpriteService(ISpriteService eps) {
        System.out.println("tried to add");
        this.spriteServiceList.add(eps);
        System.out.println(eps);
    }

    public void removeSpriteService(ISpriteService eps) {
        System.out.println("tried to remove");
        this.spriteServiceList.remove(eps);
    }

}
