package group7.pickup;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.IPickUp;
import group7.common.data.World;
import group7.common.entityparts.PositionPart;
import group7.common.services.IGamePluginService;
import java.util.Random;

public class PickUpPlugin implements IGamePluginService {

    private Entity pickUp;
    private String fileName = "healthFlask.png";
    private int width = 20;
    private int height = 22;

    public Entity createPickUp(GameData gameData) {

        Entity pickUp = new Pickup();
        pickUp.setRadius(20);
        pickUp.setFileName(fileName);
        pickUp.setSpriteHeight(height);
        pickUp.setSpriteWidth(width);
        pickUp.setRadius(10);
        float x = 350;
        float y = 400;

        Random r = new Random();
        float randomX = (float) r.nextInt(800) + 200;
        float randomY = (float) r.nextInt(200) + 200;

        pickUp.add(new PositionPart(randomX, randomY, "PickUp"));

        return pickUp;
    }

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 2; i++) {
            pickUp = createPickUp(gameData);
            world.addEntity(pickUp);
        }

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(pickUp);
    }

}
