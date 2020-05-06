/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group7.hud;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.PositionPart;
import group7.common.services.IGamePluginService;

/**
 *
 * @author Daniel
 */
public class LifeBar_HUD {

    private Entity lifeBar;
    private String lifeSprite;

    public Entity createLifeBar() {
        float x = 20;
        float y = 750;
        int width = 200;
        int height = 40;
        Entity lifeBar = new HUD();
        lifeBar.setFileName("life100.png");
        lifeBar.setSpriteHeight(height);
        lifeBar.setSpriteWidth(width);
        lifeBar.add(new PositionPart(x, y, "LifeBar"));
        return lifeBar;
    }

//    @Override
//    public void start(GameData gameData, World world) {
//        lifeBar = createLifeBar();
//        world.addEntity(lifeBar);
//    }
//
//    @Override
//    public void stop(GameData gameData, World world) {
//        world.addEntity(lifeBar);
//    }
    public void setLifeBar(String lifeSprite) {
        this.lifeSprite = lifeSprite;
    }

    public void updateLifeSprite(int life) {

        if (life == 100000 && life > 75000) {
            this.lifeSprite = "life100.png";
        }
        if (life == 75000 && life > 50000) {

            this.lifeSprite = "life75.png";
        }
        if (life == 50000 && life > 25000) {
            this.lifeSprite = "life50.png";
        }
        if (life == 25000 && life > 0) {
            this.lifeSprite = "life25.png";
        }
        if (life == 0) {
            this.lifeSprite = "life0.png";
        }

    }

}
