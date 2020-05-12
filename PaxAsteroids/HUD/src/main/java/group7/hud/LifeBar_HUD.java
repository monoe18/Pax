package group7.hud;

import group7.common.data.Entity;
import group7.common.data.GameData;

import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.services.IHUD;
import group7.commonplayer.PlayerCharacter;

public class LifeBar_HUD implements IHUD {

    private String lifeSprite = "life100.png";
    private int x = 20;
    private int y = 730;
    private int SpriteHeight = 50;
    private int SpriteWidth = 200;

    @Override
    public void updateHUD(World world, GameData gameData, int displayInfo) {
        Entity player = null;
        for (Entity ent : world.getEntities(PlayerCharacter.class)) {
            player = ent;
            LifePart life = ent.getPart(LifePart.class);
            updateLifeSprite(life.getLife());

        }
        if (player == null) {
            this.lifeSprite = "life0.png";

        }
    }

    public void updateLifeSprite(int life) {

        if (life <= 100000 && life > 75000) {
            this.lifeSprite = "life100.png";
        }
        if (life <= 75000 && life > 50000) {
            this.lifeSprite = "life75.png";
        }
        if (life <= 50000 && life > 25000) {
            this.lifeSprite = "life50.png";
        }
        if (life <= 25000 && life > 0) {
            this.lifeSprite = "life25.png";
        }

    }

    @Override
    public String getSprite() {
        return this.lifeSprite;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public int getSpriteWidth() {
        return this.SpriteWidth;
    }

    @Override
    public int getSpriteHeight() {
        return this.SpriteHeight;
    }

    @Override
    public String getHudType() {
        return "LifeBar";
    }

}
