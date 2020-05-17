package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;
import group7.common.services.AIMover;
import group7.common.services.IAIProcessing;
import group7.common.services.IArtificialIntelligence;

public class EnemyController implements IEntityProcessingService {

    IArtificialIntelligence ai;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {
            PositionPart enemyPositionPart = entity.getPart(PositionPart.class);
            MovingPart enemyMovingPart = entity.getPart(MovingPart.class);

            if (ai != null) {
                Enemy e = (Enemy) entity;

                e.mySuperCoolAI.getSolutionArray(enemyPositionPart, enemyPositionPart, enemyMovingPart);
            } else {
                continue;
            }

            //enemyMovingPart.setDirection(prevNode.direction);
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

//    @Override
//    public void move(IAIProcessing aip, World world) {
//        for (Entity enemy : world.getEntities(Enemy.class)) {
//            PositionPart playerPosition = getPlayerPos(world);
//
//            PositionPart enemyPositionPart = enemy.getPart(PositionPart.class);
//            MovingPart enemyMovingPart = enemy.getPart(MovingPart.class);
//
//            aip.processAi(playerPosition, enemyPositionPart, enemyMovingPart);
//        }
//
//    }
    //TODO: Dependency injection via Declarative Services
    public void setAIService(IArtificialIntelligence ai) {
        this.ai = ai;
    }

    public void removeAIService() {
        this.ai = null;
    }

}
