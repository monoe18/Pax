package group7.player;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IGamePluginService;
import group7.common.services.ISpriteService;

public class PlayerCreator implements IGamePluginService {

    private Entity player;
    private String fileName = "Player.png";
    private int width = 32;
    private int height = 40;

    @Override
    public void start(GameData gameData, World world) {        
        player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        float maxSpeed = 200;
        float x = 500;
        float y = 500;
        int life = 100;
        boolean isHit = false;

        Entity playerCharacter = new PlayerCharacter();
        playerCharacter.setRadius(20);
        playerCharacter.setFileName(fileName);
        playerCharacter.setSpriteHeight(height);
        playerCharacter.setSpriteWidth(width);
        playerCharacter.add(new MovingPart(maxSpeed, "Player"));
        playerCharacter.add(new PositionPart(x, y, "Player"));
        // playerShip.add(new LifePart(life, expiration));

        return playerCharacter;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
