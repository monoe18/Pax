/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.common.services;

import group7.common.data.Entity;
import group7.common.data.GameData;

/**
 *
 * @author morte
 */
public interface IBulletManager {
    public Entity createBullet(Entity e, GameData g);
}
