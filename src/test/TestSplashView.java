import static org.junit.Assert.*;
import org.junit.*;

import java.util.HashMap;

public class TestSplashView{
	
	private SplashView splashView;
	
	@Before
	public void instantiate()
	{
	
		// Tons of objects need to be made to prevent null pointers....
		
			ModelController modelController = new ModelController(null);
		ViewController viewController = new ViewController(modelController);

		viewController.setModelController(modelController);
		modelController.setViewController(viewController);

		//modelController.setMainModel(new LevelModel(modelController, "assets/test_level.json"));
		//viewController.setMainView(new LevelView(viewController, "assets/test_level.png"));
		 modelController.setMainModel(new SplashModel(modelController)); // Controller for Opening screen
		 viewController.setMainView(new SplashView(viewController)); // View for opening screen
		splashView = new SplashView(viewController);
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