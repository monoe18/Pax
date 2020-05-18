package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.map.Node;
import java.util.ArrayList;

public interface IArtificialIntelligence {

    public void solutionPathCalculation(PositionPart enemyPositionPart, World world, GameData game);

}
