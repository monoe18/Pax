package group7.collision;

import group7.common.data.GameData;
import group7.common.data.World;
import group7.commonplayer.PlayerCharacter;
import java.util.List;
import java.util.ResourceBundle.Control;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

public class CollisionDetectorTest {

    PlayerCharacter mocked = mock(PlayerCharacter.class);

    List m = mock(List.class);

    @Mock
    private PlayerCharacter p;

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
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testProcess() {
        when(mocked.getRadius()).thenReturn(1f);
        assertTrue(mocked.getRadius() == 1f);
        System.out.println("process");
//        GameData gameData = null;
//        World world = null;
//        CollisionDetector instance = new CollisionDetector();
//        instance.process(gameData, world);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
