package group7.collision;

import group7.common.data.Entity;
import group7.common.data.GameData;
import group7.common.data.World;
import group7.common.entityparts.LifePart;
import group7.common.entityparts.PositionPart;
import group7.commonbullet.BulletEntity;
import group7.commonenemy.Enemy;
import group7.commonplayer.PlayerCharacter;
import java.util.List;
import java.util.ResourceBundle.Control;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollisionDetectorTest {

    World w;
    CollisionDetector coll = new CollisionDetector();
    //PlayerCharacter mockedPlayer = mock(PlayerCharacter.class);
    BulletEntity mockedEntity1;
    Enemy mockedEntity2;

    World worldMock;
    GameData gameMock;

    PositionPart p1;
    PositionPart p2;

    LifePart l1;
    LifePart l2;

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
        mockedEntity1 = mock(BulletEntity.class);

        mockedEntity2 = mock(Enemy.class);
        worldMock = mock(World.class);
        gameMock = mock(GameData.class);

        p1 = mock(PositionPart.class);
        p2 = mock(PositionPart.class);
        l1 = mock(LifePart.class);
        l2 = mock(LifePart.class);
        when(mockedEntity1.getRadius()).thenReturn(2f);
        when(mockedEntity2.getRadius()).thenReturn(2f);
        when(p1.getX()).thenReturn(10f);
        when(p1.getY()).thenReturn(12f);
        when(p2.getX()).thenReturn(10f);
        when(p2.getY()).thenReturn(12f);
        mockedEntity1.add(p1);
        mockedEntity1.add(l1);
        mockedEntity2.add(p2);
        mockedEntity2.add(l2);
        w = new World();
        w.addEntity(mockedEntity1);
        System.out.println("w" + w.getEntities().size());
        worldMock.addEntity(mockedEntity1);
        worldMock.addEntity(mockedEntity2);

    }

    @After

    public void tearDown() {
    }

    @Test
    public void testProcess() {

        //verify(worldMock.addEntity(mockedEntity1));
        System.out.println("SIZE LIGE HER :" + worldMock.getEntities().size());
        System.out.println("Rad" + mockedEntity1.getRadius());
        System.out.println("X and Y : " + p1.getX() + " " + p2.getY());
        System.out.println("SIZE : " + worldMock.getEntities().size());
        assertTrue(coll.i(mockedEntity1, mockedEntity2));
        assertEquals(2, coll.returnRad(mockedEntity1), 4);
        assertTrue(coll.circleCollision(mockedEntity1, mockedEntity2));
        //  PositionPart p = mockedEntity1.getPart(PositionPart.class);
        //  System.out.println("Ent" + p.getX());
        //verify(worldMock.getEntities().size());
        // coll.process(gameMock, worldMock);

        // worldMock.getEntities().size();// assertTrue(coll.circleCollision(mockedEntity1, mockedEntity2));
        // assertTrue(worldMock.getEntities().size() == 1);
    }

}
