/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.common.data;

/**
 *
 * @author mathi
 */
public interface IMap {
    public void initMap(GameData gamedata, World world);
    public float[] getMapBoundaryPoints();
}
