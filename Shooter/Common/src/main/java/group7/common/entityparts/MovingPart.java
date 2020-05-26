package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;

public class MovingPart implements EntityPart {

    private float dx, dy;
    private float maxSpeed;
    private boolean left, right, up, down, space;
    private String type;
    private String direction;

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public MovingPart(float maxSpeed, String type) {
        this.type = type;
        this.maxSpeed = maxSpeed;

    }

    public MovingPart(float maxSpeed, String type, String direction) {
        this.type = type;
        this.maxSpeed = maxSpeed;
        this.direction = direction;
    }

    public float getDx() {
        return dx;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getDy() {
        return dy;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float dt = gameData.getDelta();

        try {
            if (positionPart.getType().equals("Player")) {

                if (left) {
                    x -= maxSpeed * dt;
                    this.direction = "left";
                }
                if (right) {
                    x += maxSpeed * dt;
                    this.setDirection("right");
                }
                if (up) {
                    y += maxSpeed * dt;
                    this.setDirection("up");
                }
                if (down) {
                    y -= maxSpeed * dt;
                    this.setDirection("down");
                }

            } else if (positionPart.getType().equals("Laser") || positionPart.getType().equals("Enemy")) {
                if (direction.equals("left")) {
                    x -= maxSpeed * dt;
                }
                if (direction.equals("right")) {
                    x += maxSpeed * dt;
                }
                if (direction.equals("up")) {
                    y += maxSpeed * dt;
                }
                if (direction.equals("down")) {
                    y -= maxSpeed * dt;
                } else {

                }
            }
        } catch (NullPointerException e) {
        }

        x += dx * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        } else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += dy * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        } else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);
    }

}
