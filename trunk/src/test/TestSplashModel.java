
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
public class TestSplashModel {
	private ModelController modelController;
	private ViewController viewController;
	private SplashModel splashModel;
	
	@Before
	public void initialize() {
		modelController = new ModelController();
		viewController = new ViewController();
		
		modelController.setViewController(viewController);
		viewController.setModelController(modelController);	
		splashModel = new SplashModel(modelController);
	}
	
	@Test
	public void TestUpdate() 
	{
		// Nothing should update until 5(000ms) seconds
		splashModel.update(1000);		
		assertFalse(modelController.getMainModel() instanceof TitleModel);
		assertFalse(viewController.getMainView() instanceof TitleView);	
		
		splashModel.update(5001); 
		assertTrue(modelController.getMainModel() instanceof TitleModel);
		assertTrue(viewController.getMainView() instanceof TitleView);	
		
	}
	
}
