/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;

/**
 *
 * @author morte
 */
public interface IWaveManager {

    void spawnEnemies(GameData gameData, World world);
    void checkWaveStatus(World world);
    boolean isSpawnStatus();
    int getWaveCount();
    int getEnemyCount();
}
