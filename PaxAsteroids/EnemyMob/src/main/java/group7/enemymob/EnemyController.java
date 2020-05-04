package group7.enemymob;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.AIPart;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;

public class EnemyController implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart movingPart = entity.getPart(MovingPart.class);
            AIPart aiPart = entity.getPart(AIPart.class);

            PositionPart playerPosition = getPlayerPos(world);

            aiPart.processAi(playerPosition, positionPart);
            movingPart.process(gameData, entity);
            positionPart.process(gameData, entity);

        }
    }

    private PositionPart getPlayerPos(World world) {
        PositionPart playerPos = null;
        for (Entity entity : world.getEntities(PlayerCharacter.class)) {
            playerPos = entity.getPart(PositionPart.class);
        }
        return playerPos;
    }

}
