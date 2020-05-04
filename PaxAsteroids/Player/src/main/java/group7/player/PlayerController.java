package group7.player;

import group7.common.data.Entity;
import group7.common.data.GameData;
import static group7.common.data.GameKeys.LEFT;
import static group7.common.data.GameKeys.RIGHT;
import static group7.common.data.GameKeys.UP;
import static group7.common.data.GameKeys.DOWN;
import static group7.common.data.GameKeys.SPACE;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.entityparts.ShootingPart;
import group7.common.services.IEntityProcessingService;
import group7.commonplayer.PlayerCharacter;

public class PlayerController implements IEntityProcessingService {

    boolean right = true;
    boolean left = false;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(PlayerCharacter.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            ShootingPart shootingPart = player.getPart(ShootingPart.class);
            double random = Math.random();

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            movingPart.setDown(gameData.getKeys().isDown(DOWN));
            movingPart.setSpace(gameData.getKeys().isPressed(SPACE));
            
            checkShooting(gameData, shootingPart);
            
            // Used to flip sprite 
            flipPlayer(player, gameData);

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            shootingPart.process(gameData, player);
//            lifePart.process(gameData, player);

        }
    }

    // Flip Sprite when going from left to right
    public void flipPlayer(Entity player, GameData gameData) {
        if (right == true && gameData.getKeys().isDown(LEFT)) {
            player.setFlipRightLeft(true);
            right = false;
            left = true;
        } else if (left == true && gameData.getKeys().isDown(RIGHT)) {
            player.setFlipRightLeft(false);
            left = false;
            right = true;

        } //else {
//            player.setFlipRightLeft(false);
//        }
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    private void checkShooting(GameData gameData, ShootingPart shootingPart) {
        if (gameData.getKeys().isDown(SPACE)) {
            shootingPart.engageShooting();
        } else {
            shootingPart.disengageShooting();
        }
    }
}
