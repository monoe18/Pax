package group7.hud_wavebar;

import group7.common.data.GameData;

import group7.common.data.World;
import group7.common.services.IHUD;

public class WaveBar_HUD implements IHUD {

    private String waveSprite = "wave1.png";
    private int x = 630;
    private int y = 730;
    private int SpriteHeight = 45;
    private int SpriteWidth = 180;

    @Override
    public String getSprite() {
        return this.waveSprite;
    }

    @Override
    public void updateHUD(World world, GameData gameData, int displayInfo) {
        this.waveSprite = "wave" + displayInfo + ".png";

    }

    @Override
    public String getHudType() {
        return "WaveBar";
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

}
