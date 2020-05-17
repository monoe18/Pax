
package group7.common.services;

import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;

public interface IAIProcessing {
    void processAi(PositionPart playerPosition, PositionPart enemyPosition);
}
