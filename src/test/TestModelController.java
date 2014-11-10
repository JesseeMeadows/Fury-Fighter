import org.junit.*;
import static org.junit.Assert.*;


public class TestModelController {

	@Test
	public void testModelController()
		{

		ModelController modelController = new ModelController();
		assertNotNull(modelController);
		assertEquals(null, modelController.getMainModel());
	}

	@Test
	public void testGetViewController()
		{
		ModelController modelController = new ModelController();
		modelController.setViewController(new ViewController());
		assertNotNull(modelController.getViewController());
	}
}