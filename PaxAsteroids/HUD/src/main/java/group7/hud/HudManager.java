package group7.hud;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IGamePluginService;
import group7.common.services.IHUD;

import group7.common.services.ISpriteService;
import group7.commonplayer.PlayerCharacter;

public class HudManager implements IGamePluginService, IHUD {

    private LifeBar_HUD lifeHUD;
    private Score_HUD scoreHUD;

    public HudManager() {
        System.out.println("Started HUD");
    }

    @Override
    public void updateHUD(World world, GameData game) {

        for (Entity e : world.getEntities(PlayerCharacter.class
        )) {
            LifePart playerLifePoint = e.getPart(LifePart.class);
            //updateLifeSprite(playerLifePoint.getLife());

        }
    }

    @Override
    public void start(GameData gameData, World world) {
        Entity life = lifeHUD.createLifeBar();
        System.out.println("bliver det her kørt???");
        world.addEntity(life);

    }

    @Override
    public void stop(GameData gameData, World world) {
        System.out.println("");
    }

}
