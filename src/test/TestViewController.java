import static org.junit.Assert.*;
import org.junit.*;

public class TestViewController {
    private ViewController viewController;
    private View view;

    @Before
    public void setUp(){
        viewController = new ViewController(new ModelController(null));
    }

    @After
    public void setDown(){
        System.out.println("Test complete.");
    }

    @Test
    public void testRender(){
        viewController.render();
        assertTrue(viewController.getJFrame().isVisible());
    }

    @Test
    public void testSetModelController(){
        assertTrue(viewController.getModelController() != null);
    }

    @Test
    public void testGetModelController(){
        assertTrue(viewController.getModelController() != null);
    }

    @Test
    public void testSetAndGetMainView(){
        viewController.setMainView(view);
        View test = viewController.getMainView();
        assertEquals(view,test);
    }

    @Test
    public void testGetDrawPanel(){
        assertTrue(512 == viewController.getDrawPanel().getHeight());
    }
}
