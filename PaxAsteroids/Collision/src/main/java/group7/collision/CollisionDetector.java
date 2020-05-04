package group7.collision;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.IBullet;
import group7.common.data.IMap;
import group7.common.data.World;
import group7.common.entityparts.PositionPart;
import group7.common.services.IPostEntityProcessingService;
import group7.common.data.ICharacter;
import group7.common.entityparts.LifePart;
import group7.commonbullet.BulletEntity;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (world.getMap() != null) {
                mapCollision(e, world.getMap(), world);
            }
            for (Entity enemy : world.getEntities(Enemy.class)) {
                if (world.getMap() != null) {
                    mapCollision(enemy, world.getMap(), world);
                }
                for (Entity bullet : world.getEntities(BulletEntity.class)) {
                    if (circleCollision(bullet, enemy)) {
                        LifePart lpe = enemy.getPart(LifePart.class);
                        int newLife = lpe.getLife();
                        lpe.setLife(newLife - 25);

                        if (lpe.getLife() < 25) {
                            world.removeEntity(enemy);

                        }
//                    world.removeEntity(f);
                    }
                }

                for (Entity player : world.getEntities(PlayerCharacter.class)) {

                    if (enemy.getID().equals(player.getID())) {
                        continue;
                    }

                    if (circleCollision(enemy, player)) {
                        LifePart lpe = player.getPart(LifePart.class);
                        int newLife = lpe.getLife();
                        lpe.setLife(newLife - 25);

                        if (lpe.getLife() < 25) {
                            world.removeEntity(player);

                        }
//                    world.removeEntity(f);
                    }

                }
            }
        }
    }

    private void mapCollision(Entity e, IMap map, World world) {
        float[] points = map.getMapBoundaryPoints();
        if (e instanceof ICharacter) {
            characterMapCollision(e, points);
        } else if (e instanceof IBullet) {
            if (bulletCollison(e, points)) {
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
        PositionPart ep = e.getPart(PositionPart.class);
        //Left side collision check
        if (ep.getX() - e.getRadius() <= points[0]) {
            return true;
        } else //Right side collision check
        if (ep.getX() + e.getRadius() >= points[2]) {
            return true;
        }
        //Top side collision check
        if (ep.getY() + e.getRadius() >= points[3]) {
            return true;
        } else //Bottom side collision check
        if (ep.getY() - e.getRadius() <= points[1]) {
            return true;
        }
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
