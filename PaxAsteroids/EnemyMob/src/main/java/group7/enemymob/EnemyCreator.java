package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IArtificialIntelligence;
import group7.common.services.IGamePluginService;
import group7.common.services.IWaveManager;
import group7.commonenemy.Enemy;
import java.util.Random;

public class EnemyCreator implements IGamePluginService, IWaveManager {

    IArtificialIntelligence ai;
    private int enemyCount = 4;
    private int waveCount = 1;
    private boolean spawnStatus = true;
    private String[] files = {"RobotEnemy.png", "enemy2.png"};
    private Enemy enemy;

    private int SpriteWidth = 32;
    private int SpriteHeight = 40;

    @Override
    public void start(GameData gameData, World world) {
        spawnEnemies(gameData, world);
//        world.addEntity(createEnemy(gameData));
    }

    private Enemy createEnemy(GameData gameData) {
        enemy = new Enemy();
        float maxSpeed = 50;
        Random r = new Random();
//        float x = 400;
//        float y = 400;
        int life = 100;
        int index = r.nextInt(2);
        enemy.setRadius(35);
        enemy.setFileName(files[index]);
        enemy.setSpriteHeight(SpriteHeight);
        enemy.setSpriteWidth(SpriteWidth);
        enemy.mySuperCoolAI = ai;
        float randomX = (float) r.nextInt(800) + 200;
        float randomY = (float) r.nextInt(200) + 200;
        enemy.add(new MovingPart(maxSpeed, "Enemy"));
        enemy.add(new PositionPart(randomX, randomY, "Enemy"));
        enemy.add(new LifePart(life));

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            world.removeEntity(enemy);
        }
    }

    @Override
    public void spawnEnemies(GameData gameData, World world) {
        for (int i = 0; i < getEnemyCount(); i++) {
            enemy = createEnemy(gameData);
            world.addEntity(enemy);
        }
    }

    @Override
    public void checkWaveStatus(World world) {
        if (world.getEntities(Enemy.class).isEmpty()) {
            enemyCount += waveCount * 2;
            if (waveCount < 5) {
                waveCount++;
                spawnStatus = true;
            }
        } else {
            spawnStatus = false;
        }
    }

    @Override
    public int getEnemyCount() {
        return enemyCount;
    }

    @Override
    public int getWaveCount() {
        return waveCount;
    }

    @Override
    public boolean isSpawnStatus() {
        return spawnStatus;
    }
}
