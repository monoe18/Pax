package group7.common.markInterfaces;

import group7.common.data.GameData;
import group7.common.data.World;

public interface IMap {

    public void initMap(GameData gamedata, World world);

    public float[] getMapBoundaryPoints();
}
