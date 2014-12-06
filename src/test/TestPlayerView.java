import java.util.Collection;
import java.util.Arrays;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class TestPlayerView
{
	private PlayerView playerView;

	public float rw=1;

	public float rh=1;

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
	
	
		PlayerModel model=  new PlayerModel(new LevelModel(modelController));
		playerView = new PlayerView(model);
	}

	@Test
	public void testRender()
	{
		playerView.render(Graphics2DMock.getMockObject(), rw, rh);
	}


	@Test
	public void testGetVisibleViews()
	{
		assertEquals(0, playerView.getVisibleViews().size());
	}
}
