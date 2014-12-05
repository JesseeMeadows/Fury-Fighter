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
		splashView.render(Graphics2DMock.getMockObject(),0,0);
	}
	
	@Test
	public void testGetVisibleViewsSplashView()
	{
		assertTrue(splashView.getVisibleViews() instanceof HashMap);
		assertEquals(0, splashView.getVisibleViews().size());
	}
	
}