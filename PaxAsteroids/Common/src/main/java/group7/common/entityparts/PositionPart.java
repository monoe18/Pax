package group7.common.entityparts;

import group7.common.data.Entity;
import group7.common.data.GameData;

public class PositionPart implements EntityPart {

    private float x;
    private float y;
    private String type;

    public PositionPart(float x, float y, String type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }

}
