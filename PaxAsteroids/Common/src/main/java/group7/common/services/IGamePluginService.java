package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
