package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.AIPart;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IGamePluginService;
import group7.commonenemy.Enemy;
import java.util.Random;

public class EnemyCreator implements IGamePluginService {

    private Entity enemy;
    private String filename = "RobotEnemy.png";
    private int SpriteWidth = 32;
    private int SpriteHeight = 40;

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 4; i++) {
            enemy = createEnemy(gameData);
            world.addEntity(enemy);
        }

    }

    private Entity createEnemy(GameData gameData) {
        enemy = new Enemy();
        float maxSpeed = 50;

//        float x = 400;
//        float y = 400;
        int life = 100;

        enemy.setRadius(10);
        enemy.setFileName(filename);
        enemy.setSpriteHeight(SpriteHeight);
        enemy.setSpriteWidth(SpriteWidth);

        Random r = new Random();
        float randomX = (float) r.nextInt(800) + 200;
        float randomY = (float) r.nextInt(200) + 200;

        enemy.add(new MovingPart(maxSpeed, "Enemy"));
        enemy.add(new PositionPart(randomX, randomY, "Enemy"));
        enemy.add(new LifePart(life));
        enemy.add(new AIPart(45, 25));

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
