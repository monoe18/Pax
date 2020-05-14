package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;

public interface IWaveManager {

    void spawnEnemies(GameData gameData, World world);

    void checkWaveStatus(World world);

    boolean isSpawnStatus();

    int getWaveCount();

    int getEnemyCount();
}
