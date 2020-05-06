package group7.common.services;

import group7.common.data.World;

public interface ISpriteService {

    String getSprite();

    int getSpriteWidth();

    int getSpriteHeight();

    float getX(World world);

    float getY(World world);
}
