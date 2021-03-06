package group7.endgamehud;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.services.IHUD;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;

public class EndGame_Hud implements IHUD {

    private String sprite = ".";
    private int x = 360;
    private int y = 200;
    private int SpriteHeight = 400;
    private int SpriteWidth = 720;
    LifePart lp;

    @Override
    public String getHudType() {
        return "EndGame";
    }

    @Override
    public String getSprite() {
        return sprite;
    }

    @Override
    public int getSpriteWidth() {
        return SpriteWidth;
    }

    @Override
    public int getSpriteHeight() {
        return SpriteHeight;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void updateHUD(World world, int displayInfo) {
        for (Entity e : world.getEntities(PlayerCharacter.class)) {
            lp = e.getPart(LifePart.class);
        }

        if (lp.isDead()) {
            sprite = "gameOver.png";
        } else if (displayInfo == 5 && world.getEntities(Enemy.class).isEmpty()) {
            sprite = "Win_screen.png";
        }

    }
}
