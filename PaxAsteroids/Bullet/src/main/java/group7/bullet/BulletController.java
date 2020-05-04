package group7.bullet;

import group7.commonbullet.BulletEntity;
import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;
import group7.common.services.IBulletManager;
import group7.commonbullet.BulletEntity;


public class BulletController implements IEntityProcessingService, IBulletManager {

    private String fileName = "Laser.png";
    private int width = 40;
    private int height = 20;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(BulletEntity.class)) {

            bullet.setFlipRightLeft(false);
            bullet.setFlipUpDown(false);

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            movingPart.setDirection(movingPart.getDirection());
            movingPart.process(gameData, bullet);

            positionPart.process(gameData, bullet);
        }
    }

    @Override
    public Entity createBullet(Entity e, GameData gameData) {
        PositionPart shooterPos = e.getPart(PositionPart.class);
        MovingPart shooterMovingPart = e.getPart(MovingPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float dt = gameData.getDelta();
        float speed = 1000;

        Entity bullet = new BulletEntity();
        bullet.setRadius(2);

        bullet.setFileName(fileName);
        bullet.setSpriteHeight(height);
        bullet.setSpriteWidth(width);

        flipBullet(bullet, shooterMovingPart.getDirection());
        
        bullet.add(new PositionPart(x, y, "Laser"));
        bullet.add(new MovingPart(speed, "Laser", shooterMovingPart.getDirection()));

        return bullet;
    }

    // Rotate Sprites
    public void flipBullet(Entity weapon, String direction) {

        switch (direction) {
            case "left":
                weapon.setRotate(180);
                break;
            case "right":
                weapon.setRotate(0);
                break;
            case "up":
                weapon.setRotate(90);
                break;
            case "down":
                weapon.setRotate(270);
                break;

            default:
                weapon.setFlipRightLeft(false);
                weapon.setFlipUpDown(false);
                break;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
