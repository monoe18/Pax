package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;

public interface EntityPart {

    void process(GameData gameData, Entity entity);
}
