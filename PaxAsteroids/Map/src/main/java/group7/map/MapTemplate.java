/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.map;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.IMap;
import group7.common.data.World;
import group7.common.entityparts.ShootingPart;
import group7.common.services.ISpriteService;
import java.util.ArrayList;
import org.javatuples.Pair;

/**
 *
 * @author morte
 */
public class MapTemplate implements ISpriteService, IMap {

    private final Pair<Integer, Integer>[] mapBoundaryPoints = new Pair[4];
    private final Pair<Integer, Integer>[] spawnAreaBoundaryPoints = new Pair[4];
    private final Pair<Integer, Integer>[] spawnPoints = new Pair[4];
    private Pair<Integer, Integer> playerSpawnPoint = Pair.with(0, 0);

    private String fileName = "Map.png";
    private int width = 1440;
    private int height = 800;

    public MapTemplate() {

    }

    @Override
    public void initMap(GameData gameData, World world) {

        world.setMap(this);

        mapBoundaryPoints[0] = Pair.with(152, 152);
        mapBoundaryPoints[1] = Pair.with(152, 648);
        mapBoundaryPoints[2] = Pair.with(1288, 152);
        mapBoundaryPoints[3] = Pair.with(1288, 648);

        spawnAreaBoundaryPoints[0] = Pair.with(252, 252);
        spawnAreaBoundaryPoints[1] = Pair.with(252, 548);
        spawnAreaBoundaryPoints[2] = Pair.with(1188, 252);
        spawnAreaBoundaryPoints[3] = Pair.with(1188, 548);

        spawnPoints[0] = Pair.with(152, 244);
        spawnPoints[1] = Pair.with(152, 556);
        spawnPoints[2] = Pair.with(1288, 244);
        spawnPoints[3] = Pair.with(1288, 556);

        playerSpawnPoint = Pair.with(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
    }

    @Override
    public float[] getMapBoundaryPoints() {
        float[] floatPoints = {mapBoundaryPoints[0].getValue0(),
            mapBoundaryPoints[0].getValue1(),
            mapBoundaryPoints[3].getValue0(),
            mapBoundaryPoints[3].getValue1()};
        return floatPoints;
    }

    @Override
    public String getSprite() {
        return this.fileName;
    }

    @Override
    public int getSpriteWidth() {
        return this.width;
    }

    @Override
    public int getSpriteHeight() {
        return this.height;
    }

    @Override
    public float getX(World world) {
        return 0.0f;
    }

    @Override
    public float getY(World world) {
        return 0.0f;
    }

}
