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
import group7.common.map.Node;
import java.util.HashSet;
import java.util.Set;

public class EnemyController implements IEntityProcessingService {
    Node prevNode = new Node(0,0);

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Enemy.class)) {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            MovingPart enemyMovingPart = entity.getPart(MovingPart.class);
            AIPart aiPart = entity.getPart(AIPart.class);

            PositionPart playerPosition = getPlayerPos(world);
            MovingPart playerMovingPart =  getPlayerMov(world);
            if(prevNode ==null){
                prevNode.direction ="right";
                prevNode.isStart = true;
                prevNode.x = (int) playerPosition.getX()/45;
                prevNode.y = (int) playerPosition.getY()/25;
                prevNode.getNode = 0;
            }
            
            prevNode =  aiPart.processAi(playerPosition, positionPart, enemyMovingPart, playerMovingPart, prevNode);
 

            enemyMovingPart.setDirection(prevNode.direction);
            
            
            
            enemyMovingPart.process(gameData, entity);
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
    
    private MovingPart getPlayerMov(World world) {
        MovingPart playermov = null;
        for (Entity entity : world.getEntities(PlayerCharacter.class)) {
            playermov = entity.getPart(MovingPart.class);
        }
        return playermov;
    }

}
