package group7.common.services;

import group7.common.data.GameData;
import group7.common.data.World;

public interface IHUD {

    String getHudType();

    String getSprite();

    int getSpriteWidth();

    int getSpriteHeight();

    float getX();

    float getY();

    public void updateHUD(World world, GameData gameData, int displayInfo);
}
