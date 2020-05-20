package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;

public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
