package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;
import group7.common.map.Node;
import group7.common.services.AIMover;
import group7.common.services.IAIProcessing;

public class EnemyController implements IEntityProcessingService, AIMover {

    Node prevNode;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {
            PositionPart enemyPositionPart = entity.getPart(PositionPart.class);
            MovingPart enemyMovingPart = entity.getPart(MovingPart.class);


//            enemyMovingPart.setDirection(prevNode.direction);
            enemyMovingPart.process(gameData, entity);
            enemyPositionPart.process(gameData, entity);

        }
    }

    private PositionPart getPlayerPos(World world) {
        PositionPart playerPos = null;
        for (Entity entity : world.getEntities(PlayerCharacter.class)) {
            playerPos = entity.getPart(PositionPart.class);
        }
        return playerPos;
    }

    private MovingPart getPlayerMov(World world) {
        MovingPart playermov = null;
        for (Entity entity : world.getEntities(PlayerCharacter.class)) {
            playermov = entity.getPart(MovingPart.class);
        }
        return playermov;
    }

    @Override
    public void move(IAIProcessing aip, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart playerPosition = getPlayerPos(world);
            MovingPart playerMovingPart = getPlayerMov(world);
            PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);
            MovingPart enemyMovingPart = enemy.getPart(MovingPart.class);

            aip.processAi(playerPosition, enemyPositionPart);
        }

    }
}
