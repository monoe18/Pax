package group7.laser;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.MovingPart;
import group7.common.entityparts.PositionPart;
import group7.common.services.IEntityProcessingService;

public class LaserController implements IEntityProcessingService {

    private String fileName = "Laser.png";
    private boolean canShoot = true;

    private int width = 40;
    private int height = 20;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity laser : world.getEntities(Weapon.class)) {

            laser.setFlipRightLeft(false);
            laser.setFlipUpDown(false);

            PositionPart positionPart = laser.getPart(PositionPart.class);
            MovingPart movingPart = laser.getPart(MovingPart.class);
//            TimerPart timerPart = laser.getPart(TimerPart.class);
            movingPart.setDirection(movingPart.getDirection());
            movingPart.process(gameData, laser);

            positionPart.process(gameData, laser);
        }
    }

    public Entity createWeapon(Entity shooter, GameData gameData) {
        PositionPart shooterPos = shooter.getPart(PositionPart.class);
        MovingPart shooterMovingPart = shooter.getPart(MovingPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float dt = gameData.getDelta();
        float speed = 1000;

        Entity weapon = new Weapon();
        weapon.setRadius(2);

        weapon.setFileName(fileName);
        weapon.setSpriteHeight(height);
        weapon.setSpriteWidth(width);

        flipBullet(weapon, shooterMovingPart.getDirection());
        System.out.println("sprightheight  " + weapon.getSpriteHeight());
        System.out.println("sprightWidth  " + weapon.getSpriteWidth());
        weapon.add(new PositionPart(x, y, "Laser"));

        //weapon.add(new LifePart(1));
        weapon.add(new MovingPart(speed, "Laser", shooterMovingPart.getDirection()));

        return weapon;
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
                System.out.println("upppp");
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
