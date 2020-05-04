package group7.hud;

import group7.common.data.Entity;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.services.ISpriteService;
import group7.commonplayer.PlayerCharacter;

public class HudManager implements ISpriteService {

    private String lifeBar = "life100.png";
    private int width = 200;
    private int height = 40;
    private int x = 20;
    private int y = 750;

    public HudManager() {
        System.out.println("HUD created ");
    }

    public void updateLifeBar(World world, Entity entety) {
        for (Entity e : world.getEntities(PlayerCharacter.class)) {
            LifePart playerLifePoint = e.getPart(LifePart.class);
            // updateLifeSprite();
        }
    }

    public void updateLifeSprite(int life) {
        switch (life) {
            case 100000:
                this.lifeBar = "life100.png";
                break;
            case 75000:
                this.lifeBar = "life75.png";
                break;
            case 50000:
                this.lifeBar = "life50.png";
                break;
            case 25000:
                this.lifeBar = "life25.png";
                break;
            case 0:
                this.lifeBar = "life0.png";
                break;
            default:
            // code block
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
