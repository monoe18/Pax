package group7.hud;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.services.IHUD;

import group7.common.services.ISpriteService;
import group7.commonplayer.PlayerCharacter;

public class HudManager implements ISpriteService, IHUD {

    private String lifeBar = "life100.png";
    private int width = 200;
    private int height = 40;
    private int x = 20;
    private int y = 750;

    public HudManager() {
        System.out.println("HUD created ");
    }

    @Override
    public void updateHUD(World world, GameData game) {
        for (Entity e : world.getEntities(PlayerCharacter.class)) {
            LifePart playerLifePoint = e.getPart(LifePart.class);
            updateLifeSprite(playerLifePoint.getLife());

        }
    }

    public void updateLifeSprite(int life) {

        if (life == 100000) {
            this.lifeBar = "life100.png";
        }
        if (life <= 75000) {
            this.lifeBar = "life75.png";
        }
        if (life <= 50000) {
            this.lifeBar = "life50.png";
        }
        if (life <= 25000) {
            this.lifeBar = "life25.png";
        }
        if (life == 0) {
            this.lifeBar = "life0.png";
        }

    }

    @Override
    public String getSprite() {
        return this.lifeBar;
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
        return this.x;
    }

    @Override
    public float getY(World world) {
        return this.y;
    }

}
