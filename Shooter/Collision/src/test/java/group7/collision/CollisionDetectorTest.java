package group7.collision;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.PositionPart;
import group7.commonbullet.BulletEntity;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CollisionDetectorTest {

    CollisionDetector collision = new CollisionDetector();
    GameData game;
    World world;
    Entity player;
    Entity enemy;
    Entity bullet;
    PositionPart position;
    PositionPart position2;
    LifePart life1;
    LifePart life2;

    public CollisionDetectorTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        world = new World();
        game = new GameData();
        player = new PlayerCharacter();
        enemy = new Enemy();
        bullet = new BulletEntity();

        enemy.setRadius(2);
        player.setRadius(2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCircleCollision() {
        position = new PositionPart(5, 5, "dummy");
        position2 = new PositionPart(5, 5, "dummy");
        enemy.add(position);
        player.add(position2);

        //Expect true result. Entities within the collision boundary
        assertTrue(collision.circleCollision(enemy, player));

        position = new PositionPart(10, 10, "dummy");
        position2 = new PositionPart(5, 5, "dummy");
        enemy.add(position);
        player.add(position2);

        //Expect false result. Entities outside the collision boundary
        assertFalse(collision.circleCollision(enemy, player));

    }

    @Test
    public void testEnemyRemoved() {
        position = new PositionPart(5, 5, "dummy");
        position2 = new PositionPart(5, 5, "dummy");
        life1 = new LifePart(15);
        enemy.add(position);
        enemy.add(life1);
        bullet.add(position2);
        world.addEntity(enemy);
        world.addEntity(bullet);

        assertTrue(world.getEntities().size() == 2);
        collision.process(game, world);
        assertTrue(world.getEntities().size() == 0);
    }
}
