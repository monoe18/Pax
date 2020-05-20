package group7.common.data;

import group7.common.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private String fileName;
    private int spriteWidth, spriteHeight;

    private float radius;
    private float x, y;
    private boolean flipRightLeft, flipUpDown, prevflipRightLeft, prevflipUpDown = false;
    private float rotate = 0;
    private Map<Class, EntityPart> parts;

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public String getID() {
        return ID.toString();
    }

    public boolean isFlipRightLeft() {
        return flipRightLeft && flipRightLeft != prevflipRightLeft;
    }

    public void setFlipRightLeft(boolean flipRightLeft) {
        this.prevflipRightLeft = this.flipRightLeft;
        this.flipRightLeft = flipRightLeft;
    }

    public void setFlipUpDown(boolean flipUpDown) {
        this.prevflipUpDown = this.flipUpDown;
        this.flipUpDown = flipUpDown;
    }

    public boolean isFlipUpDown() {
        return flipUpDown && flipUpDown != prevflipUpDown;
    }

    public boolean isPrevflipRightLeft() {
        return prevflipRightLeft;
    }

    public void setPrevflipRightLeft(boolean prevflipRightLeft) {
        this.prevflipRightLeft = prevflipRightLeft;
    }

    public boolean isPrevflipUpDown() {
        return prevflipUpDown;
    }

    public void setPrevflipUpDown(boolean prevflipUpDown) {
        this.prevflipUpDown = prevflipUpDown;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

}
