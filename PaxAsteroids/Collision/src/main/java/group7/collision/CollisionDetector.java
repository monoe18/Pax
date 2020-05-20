package group7.collision;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.markInterfaces.IBullet;
import group7.common.markInterfaces.IMap;
import group7.common.data.World;
import group7.common.entityparts.PositionPart;
import group7.common.services.IPostEntityProcessingService;
import group7.common.markInterfaces.ICharacter;
import group7.common.markInterfaces.IPickUp;
import group7.common.entityparts.LifePart;
import group7.commonbullet.BulletEntity;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (world.getMap() != null) {
                mapCollision(entity, world.getMap(), world);
            }
            for (Entity enemy : world.getEntities(Enemy.class)) {
                if (world.getMap() != null) {
                    mapCollision(enemy, world.getMap(), world);
                }
                for (Entity bullet : world.getEntities(BulletEntity.class)) {
                    if (circleCollision(bullet, enemy)) {
                        LifePart enemyLifePart = enemy.getPart(LifePart.class);
                        int newLife = enemyLifePart.getLife();
                        enemyLifePart.setLife(newLife - 50);
                        world.removeEntity(bullet);

                        if (enemyLifePart.getLife() < 25) {
                            world.removeEntity(enemy);
                        }
                    }
                }

                for (Entity pickup : world.getEntities()) {
                    for (Entity player : world.getEntities(PlayerCharacter.class)) {
                        if (pickup instanceof IPickUp && circleCollision(player, pickup)) {
                            world.removeEntity(pickup);
                            LifePart life = player.getPart(LifePart.class);
                            int newlife = 25000 + life.getLife();
                            if (newlife > 100000) {
                                life.setLife(100000);
                            } else {
                                life.setLife(newlife);
                            }
                        }
                    }
                }

                for (Entity player : world.getEntities(PlayerCharacter.class)) {
                    if (enemy.getID().equals(player.getID())) {
                        continue;
                    }
                    if (circleCollision(enemy, player)) {
                        LifePart enemyLifePart = player.getPart(LifePart.class);
                        int newLife = enemyLifePart.getLife();
                        enemyLifePart.setLife(newLife - 25);

                        if (enemyLifePart.getLife() < 25) {
                            enemyLifePart.setDead();
                            world.removeEntity(player);

                        }
                    }

                }
            }
        }
    }

    private void mapCollision(Entity entity, IMap map, World world) {
        float[] points = map.getMapBoundaryPoints();
        if (entity instanceof ICharacter || entity instanceof Enemy) {
            characterMapCollision(entity, points);
        } else if (entity instanceof IBullet) {
            if (bulletCollison(entity, points)) {
                world.removeEntity(entity);
            }
        }
    }

    private void characterMapCollision(Entity entity, float[] points) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        //Left side collision check
        if (positionPart.getX() - entity.getRadius() <= points[0]) {
            positionPart.setPosition(points[0] + 1 + entity.getRadius(), positionPart.getY());
        } else //Right side collision check
        if (positionPart.getX() + entity.getRadius() >= points[2]) {
            positionPart.setPosition(points[2] - 1 - entity.getRadius(), positionPart.getY());
        }
        //Top side collision check
        if (positionPart.getY() + entity.getRadius() >= points[3]) {
            positionPart.setPosition(positionPart.getX(), points[3] - 1 - entity.getRadius());
        } else //Bottom side collision check
        if (positionPart.getY() - entity.getRadius() <= points[1]) {
            positionPart.setPosition(positionPart.getX(), points[1] + 1 + entity.getRadius());
        }
    }

    private boolean bulletCollison(Entity entity, float[] points) {
        PositionPart ep = entity.getPart(PositionPart.class);
        //Left side collision check
        if (ep.getX() - entity.getRadius() <= points[0]) {
            return true;
        } else //Right side collision check
        if (ep.getX() + entity.getRadius() >= points[2]) {
            return true;
        }
        //Top side collision check
        if (ep.getY() + entity.getRadius() >= points[3]) {
            return true;
        } else //Bottom side collision check
        if (ep.getY() - entity.getRadius() <= points[1]) {
            return true;
        }
        return false;
    }

    private boolean circleCollision(Entity entityA, Entity entityB) {
        PositionPart positionA = entityA.getPart(PositionPart.class);
        PositionPart positionB = entityB.getPart(PositionPart.class);

        if ((positionA.getX() - positionB.getX()) * (positionA.getX() - positionB.getX()) + (positionA.getY() - positionB.getY()) * (positionA.getY() - positionB.getY())
                < (entityA.getRadius() + entityB.getRadius()) * (entityA.getRadius() + entityB.getRadius())) {
            return true;
        }

        return false;
    }

}
