/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.commonweapon;

import group7.common.data.Entity;
import group7.common.data.GameData;

/**
 *
 * @author Daniel
 */
public interface IWeaponSystem {

    Entity createWeapon(Entity ent, GameData gameData);
}
