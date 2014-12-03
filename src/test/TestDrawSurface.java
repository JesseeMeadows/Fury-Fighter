import static org.junit.Assert.*;
import org.junit.*;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class TestDrawSurface{
    private DrawSurface drawSurface;
    private ViewController viewController;
    private View view;
    private Graphics g;
    private static final char testKey = 'K';
    private static final Component testKeyEventSource = new Container();

    /*--GENERATE KEY EVENTS--*/
    private static KeyEvent generateKeyEvent(int id, char key){
        return new KeyEvent(testKeyEventSource, id, System.currentTimeMillis(), 0, (int)key, key);
    }

    public static KeyEvent generateKeyDownEvent(char key){
        return generateKeyEvent(KeyEvent.KEY_PRESSED, key);
    }

    public static KeyEvent generateKeyUpEvent(char key){
        return generateKeyEvent(KeyEvent.KEY_RELEASED, key);
    }

    @Before
    public void setUp(){
        viewController = new ViewController(null);
        drawSurface = new DrawSurface(viewController, 50, 50);
    }

    @After
    public void setDown(){
        System.out.println("Test complete.");
    }

    @Test
    public void testKeyTyped(){
        //No need to test since it has no impact on the game.
    }

    @Test
    public void testKeyPressed() throws AWTException{
        drawSurface.keyPressed(generateKeyDownEvent(testKey));
    }

    @Test
    public void testKeyReleased(){
        drawSurface.keyReleased(generateKeyUpEvent(testKey));
    }

    @Test
    public void testPaintComponent(){
        View test1 = drawSurface.getMainView();
        drawSurface.paintComponent(g);
        View test2 = drawSurface.getMainView();
        assertEquals(test1, test2);
        assertEquals(test1, view);
    }

    @Test
    public void testSetViewGetView(){
        drawSurface.setView(view);
        View test = drawSurface.getMainView();
        assertEquals(view, test);
    }

    @Test
    public void testGetInputHandler(){
        assertTrue(drawSurface.getInputHandler() != null);
    }

}
