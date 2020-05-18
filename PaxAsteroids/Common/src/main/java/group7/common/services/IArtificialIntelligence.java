package group7.common.services;

import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.map.Node;
import java.util.ArrayList;

public interface IArtificialIntelligence {

    public void getSolutionArray(PositionPart enemyPosition, PositionPart playerPosition, MovingPart enemyMoving, Entity entity);
     public void AddEntities(World world);
    
    

}
