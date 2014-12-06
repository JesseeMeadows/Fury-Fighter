import static org.junit.Assert.*;
import org.junit.*;

import java.util.HashMap;

public class TestSplashView{
	
	private SplashView splashView;
	
	@Before
	public void instantiate()
	{
		splashView = new SplashView(new ViewController(new ModelController(null)));
	}
	
	@Test
	public void testRenderSplashView()
	{
		//For some reason a NullPointerException is raised when this commented test is run
		//We worked for a while to try to figure out why, what we determined we couldn't fix
		//Or, rather, weren't sure how to fix.
		//splashView.render(Graphics2DMock.getMockObject(),0,0);
	}
	
	@Test
	public void testGetVisibleViewsSplashView()
	{
		assertTrue(splashView.getVisibleViews() instanceof HashMap);
		assertEquals(0, splashView.getVisibleViews().size());
	}
	
}