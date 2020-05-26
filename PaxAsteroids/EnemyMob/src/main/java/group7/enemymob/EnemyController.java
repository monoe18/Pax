package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;
import group7.common.services.IArtificialIntelligence;

public class EnemyController implements IEntityProcessingService {

    IArtificialIntelligence ai;

    @Override
    public void process(GameData gameData, World world) {

        if (ai != null) {
            ai.AddEntities(world);
        }
        for (Entity entity : world.getEntities(Enemy.class)) {

            PositionPart enemyPositionPart = entity.getPart(PositionPart.class);
            MovingPart enemyMovingPart = entity.getPart(MovingPart.class);

            if (ai != null) {
                ai.processAI(enemyPositionPart, getPlayerPos(world), enemyMovingPart, entity);

            } else {
                continue;
            }

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

    public void setAIService(IArtificialIntelligence ai) {
        this.ai = ai;
    }

    public void removeAIService() {
        this.ai = null;
    }

}
