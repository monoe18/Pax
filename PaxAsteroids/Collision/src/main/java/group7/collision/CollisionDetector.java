package group7.collision;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.IBullet;
import group7.common.data.IMap;
import group7.common.data.World;
import group7.common.entityparts.PositionPart;
import group7.common.services.IPostEntityProcessingService;
import group7.common.data.ICharacter;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        System.out.println("called process");
        for (Entity e : world.getEntities()) {
            System.out.println(world.getEntities().size());
            System.out.println(world.getMap());
            if (world.getMap() != null) {
                System.out.println("mapCollision");
                mapCollision(e, world.getMap(), world);
            }

            for (Entity f : world.getEntities()) {

                if (e.getID().equals(f.getID())) {
                    continue;
                }

                if (circleCollision(e, f)) {

                    //world.removeEntity(f);
                }

            }
        }
    }

    private void mapCollision(Entity e, IMap map, World world) {
        float[] points = map.getMapBoundaryPoints();
        if (e instanceof ICharacter) {
            characterMapCollision(e, points);
        } else if (e instanceof IBullet) {
            System.out.println("is bullet");
            if (bulletCollison(e, points)) {
                System.out.println("removed bullet");
                world.removeEntity(e);
            }
        }
    }

    private void characterMapCollision(Entity e, float[] points) {
        PositionPart ep = e.getPart(PositionPart.class);
        //Left side collision check
        if (ep.getX() - e.getRadius() <= points[0]) {
            ep.setPosition(points[0] + 1 + e.getRadius(), ep.getY());
        } else //Right side collision check
        if (ep.getX() + e.getRadius() >= points[2]) {
            ep.setPosition(points[2] - 1 - e.getRadius(), ep.getY());
        }
        //Top side collision check
        if (ep.getY() + e.getRadius() >= points[3]) {
            ep.setPosition(ep.getX(), points[3] - 1 - e.getRadius());
        } else //Bottom side collision check
        if (ep.getY() - e.getRadius() <= points[1]) {
            ep.setPosition(ep.getX(), points[1] + 1 + e.getRadius());
        }
    }

    private boolean bulletCollison(Entity e, float[] points) {
        System.out.println("runningBCollision");
        PositionPart ep = e.getPart(PositionPart.class);
        //Left side collision check
        if (ep.getX() - e.getRadius() <= points[0]) {
            System.out.println("L");
            return true;
        } else //Right side collision check            
        if (ep.getX() + e.getRadius() >= points[2]) {
            System.out.println("R");
            return true;
        }
        //Top side collision check
        if (ep.getY() + e.getRadius() >= points[3]) {
            System.out.println("T");
            return true;
        } else //Bottom side collision check
        if (ep.getY() - e.getRadius() <= points[1]) {
            System.out.println("B");
            return true;
        }
        System.out.println("return false");
        return false;
    }

    private boolean circleCollision(Entity e, Entity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);

        if ((ep.getX() - fp.getX()) * (ep.getX() - fp.getX()) + (ep.getY() - fp.getY()) * (ep.getY() - fp.getY())
                < (e.getRadius() + f.getRadius()) * (e.getRadius() + f.getRadius())) {
            return true;
        }

        return false;
    }

}
